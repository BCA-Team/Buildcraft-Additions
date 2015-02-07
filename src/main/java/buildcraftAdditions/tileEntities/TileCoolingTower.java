package buildcraftAdditions.tileEntities;

import java.util.EnumSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import buildcraft.api.fuels.ICoolant;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;
import buildcraft.energy.fuels.CoolantManager;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.api.recipe.refinery.ICoolingTowerRecipe;
import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.networking.ISyncronizedTile;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.reference.enums.EnumMachineUpgrades;
import buildcraftAdditions.tileEntities.Bases.TileBase;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;
import buildcraftAdditions.tileEntities.varHelpers.MultiBlockData;
import buildcraftAdditions.tileEntities.varHelpers.Upgrades;
import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.utils.fluids.CoolantTank;
import buildcraftAdditions.utils.fluids.CoolingRecipeTank;
import buildcraftAdditions.utils.fluids.ITankHolder;
import buildcraftAdditions.utils.fluids.Tank;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileCoolingTower extends TileBase implements IMultiBlockTile, IFluidHandler, ITankHolder, ISyncronizedTile, IPipeConnection, IUpgradableMachine {
	private MultiBlockData data = new MultiBlockData().setPatern(Variables.Paterns.COOLING_TOWER);
	public int tank;
	public boolean valve;
	private Tank input = new CoolingRecipeTank("input", 2000, this);
	private Tank output = new Tank(2000, this, "output");
	private Tank coolant = new CoolantTank("coolant", 10000, this);
	private TileCoolingTower master;
	private ICoolingTowerRecipe recipe;
	public float heat;
	private Upgrades upgrades = new Upgrades(0);


	@Override
	public void updateEntity() {
		super.updateEntity();
		if (data.moved) {
			data.afterMoveCheck(worldObj);
			worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord), 80);
		}
		if (master == null && !isMaster())
			findMaster();
		if (master == null && !isMaster())
			return;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.AUTO_OUTPUT)) {
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				Location location = new Location(this).move(direction);
				TileEntity entity = location.getTileEntity();
				if (entity != null && entity instanceof IFluidHandler && !(entity instanceof TileCoolingTower) && master.output.getFluidType() != null) {
					IFluidHandler tank = (IFluidHandler) entity;
					int drain = tank.fill(direction.getOpposite(), new FluidStack(master.output.getFluidType(), 100), false);
					FluidStack stack = master.drain(drain, true, 1);
					tank.fill(direction.getOpposite(), stack, true);
				}
			}
		}
		if (!isMaster())
			return;
		if (input.getFluid() != null && input.getFluid().amount <= 0)
			input.setFluid(null);
		if (output.getFluid() != null && output.getFluid().amount <= 0)
			output.setFluid(null);
		int max = 20;
		while (!coolant.isEmpty() && heat > 0 && max > 0) {
			ICoolant cooling = CoolantManager.INSTANCE.getCoolant(coolant.getFluid().getFluid());
			if (cooling == null)
				break;
			coolant.drain(1, true);
			float factor = 1;
			if (upgrades.hasUpgrade(EnumMachineUpgrades.EFFICIENCY_1))
				factor += 0.25;
			if (upgrades.hasUpgrade(EnumMachineUpgrades.EFFICIENCY_2))
				factor += 0.5;
			if (upgrades.hasUpgrade(EnumMachineUpgrades.EFFICIENCY_3))
				factor += 1;
			heat -= cooling.getDegreesCoolingPerMB(heat) * factor;
			max--;
		}
		int count = 1;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.SPEED_1))
			count++;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.SPEED_2))
			count += 2;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.SPEED_3))
			count += 3;
		for (int i = 0; i < count; i++) {
			if (heat > 80 || recipe == null || output.isFull() || input.isEmpty() || !input.getFluid().isFluidEqual(recipe.getInput()) || input.getFluidAmount() < recipe.getInput().amount || (!output.isEmpty() && !output.getFluid().isFluidEqual(recipe.getOutput())) || output.getCapacity() - output.getFluidAmount() < recipe.getOutput().amount)
				return;
			input.drain(recipe.getInput().amount, true);
			output.fill(recipe.getOutput(), true);
			heat += recipe.getHeat();
		}
	}

	private void updateRecipe() {
		if (input.getFluid() == null || input.getFluidAmount() == 0)
			recipe = null;
		else
			recipe = BCARecipeManager.cooling.getRecipe(input.getFluid());
	}

	@Override
	public void makeMaster(int rotationIndex) {
		data.isMaster = true;
		data.rotationIndex = rotationIndex;
		upgrades.blacklistUpgrade(EnumMachineUpgrades.AUTO_OUTPUT).setMaxUpgrades(5);
	}

	@Override
	public void invalidateMultiblock() {
		if (isMaster())
			data.patern.destroyMultiblock(worldObj, xCoord, yCoord, zCoord, data.rotationIndex);
		else
			data.patern.destroyMultiblock(worldObj, data.masterX, data.masterY, data.masterZ, data.rotationIndex);
		recipe = null;
	}

	@Override
	public boolean onBlockActivated(EntityPlayer player) {
		if (isMaster()) {
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.COOLING_TOWER, worldObj, xCoord, yCoord, zCoord);
			return true;
		}
		if (masterCheck())
			return master.onBlockActivated(player);
		return isPartOfMultiblock();
	}

	@Override
	public void formMultiblock(int masterX, int masterY, int masterZ, int rotationIndex) {
		data.formMultiBlock(masterX, masterY, masterZ, rotationIndex);
		if (valve)
			upgrades.whitelistUpgrade(EnumMachineUpgrades.AUTO_OUTPUT).setMaxUpgrades(1);
		sync();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		valve = tag.getBoolean("valve");
		heat = tag.getFloat("heat");
		tank = tag.getInteger("tank");
		data.readFromNBT(tag);
		if (tag.hasKey("input", Constants.NBT.TAG_COMPOUND))
			input.readFromNBT(tag.getCompoundTag("input"));
		if (tag.hasKey("output", Constants.NBT.TAG_COMPOUND))
			output.readFromNBT(tag.getCompoundTag("output"));
		if (tag.hasKey("coolant", Constants.NBT.TAG_COMPOUND))
			coolant.readFromNBT(tag.getCompoundTag("coolant"));
		updateRecipe();
		upgrades.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setFloat("heat", heat);
		tag.setBoolean("valve", valve);
		tag.setInteger("tank", tank);
		data.writeToNBT(tag);
		tag.setTag("input", input.writeToNBT(new NBTTagCompound()));
		tag.setTag("output", output.writeToNBT(new NBTTagCompound()));
		tag.setTag("coolant", coolant.writeToNBT(new NBTTagCompound()));
		upgrades.writeToNBT(tag);
	}

	@Override
	public void invalidateBlock() {
		data.invalidate();
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
		worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord), 80);
		input.setFluid(null);
		output.setFluid(null);
		coolant.setFluid(null);
		heat = 0;
		sync();
		recipe = null;
	}

	private void findMaster() {
		TileEntity entity = worldObj.getTileEntity(data.masterX, data.masterY, data.masterZ);
		if (entity instanceof TileCoolingTower)
			master = (TileCoolingTower) entity;
		else
			data.invalidataMultiblock(worldObj);
	}

	@Override
	public void moved(ForgeDirection direction) {
		data.onMove(direction);
	}

	@Override
	public int getMasterX() {
		return data.masterX;
	}

	@Override
	public int getMasterY() {
		return data.masterY;
	}

	@Override
	public int getMasterZ() {
		return data.masterZ;
	}

	@Override
	public int getRotationIndex() {
		return data.rotationIndex;
	}

	@Override
	public boolean isMaster() {
		return data.isMaster;
	}

	@Override
	public boolean isPartOfMultiblock() {
		return data.partOfMultiBlock;
	}

	@Override
	public void setMasterX(int masterX) {
		data.masterX = masterX;
	}

	@Override
	public void setMasterY(int masterY) {
		data.masterY = masterY;
	}

	@Override
	public void setMasterZ(int masterZ) {
		data.masterZ = masterZ;
	}

	@Override
	public void setIsMaster(boolean isMaster) {
		data.isMaster = isMaster;
	}

	@Override
	public void setPartOfMultiBlock(boolean partOfMultiBlock) {
		data.partOfMultiBlock = partOfMultiBlock;
	}

	@Override
	public void setRotationIndex(int rotationIndex) {
		data.rotationIndex = rotationIndex;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (isPartOfMultiblock() && valve) {
			if (masterCheck())
				return master.fill(resource, doFill, tank);
		}
		return 0;
	}

	private int fill(FluidStack resouce, boolean doFill, int tankID) {
		Tank tank = getTanks()[tankID];
		int filled = tank.fill(resouce, doFill);
		updateRecipe();
		return filled;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (isMaster())
			return drain(maxDrain, doDrain, tank);
		if (isPartOfMultiblock() && valve) {
			if (masterCheck())
				return master.drain(maxDrain, doDrain, tank);
		}
		return null;
	}

	private FluidStack drain(int maxDrain, boolean doDrain, int tankID) {
		Tank tank = getTanks()[tankID];
		FluidStack drained = tank.drain(maxDrain, doDrain);
		updateRecipe();
		return drained;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return valve;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return valve;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if (valve) {
			if (masterCheck())
				return master.getRealInfo();
			return new FluidTankInfo[]{new FluidTankInfo(input), new FluidTankInfo(output)};
		}
		return new FluidTankInfo[0];
	}

	private FluidTankInfo[] getRealInfo() {
		return new FluidTankInfo[]{new FluidTankInfo(input), new FluidTankInfo(output)};
	}

	@Override
	public Tank[] getTanks() {
		return new Tank[]{input, output, coolant};
	}

	private boolean masterCheck() {
		if (master == null)
			findMaster();
		return master != null;
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf.writeBoolean(valve);
		buf.writeFloat(heat);
		input.writeToByteBuff(buf);
		output.writeToByteBuff(buf);
		coolant.writeToByteBuff(buf);
		data.writeToByteBuff(buf);
		upgrades.writeToByteBuff(buf);
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		valve = buf.readBoolean();
		heat = buf.readFloat();
		buf = input.readFromByteBuff(buf);
		buf = output.readFromByteBuff(buf);
		buf = coolant.readFromByteBuff(buf);
		buf = data.readFromByteBuff(buf);
		buf = upgrades.readFromByteBuff(buf);
		return buf;
	}

	@Override
	public int getX() {
		return xCoord;
	}

	@Override
	public int getY() {
		return yCoord;
	}

	@Override
	public int getZ() {
		return zCoord;
	}

	@Override
	public ConnectOverride overridePipeConnection(IPipeTile.PipeType type, ForgeDirection with) {
		return valve && type == IPipeTile.PipeType.FLUID ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
	}

	@Override
	public boolean canAcceptUpgrade(EnumMachineUpgrades upgrade) {
		if (valve || isMaster()) {
			return upgrades.canInstallUpgrade(upgrade);
		} else {
			if (master == null)
				findMaster();
			if (master == null)
				return false;
			return master.canAcceptUpgrade(upgrade);
		}
	}

	@Override
	public void installUpgrade(EnumMachineUpgrades upgrade) {
		if (valve || isMaster()) {
			upgrades.installUpgrade(upgrade);
		} else {
			if (master == null)
				findMaster();
			if (master == null)
				return;
			master.installUpgrade(upgrade);
		}
	}

	@Override
	public Set<EnumMachineUpgrades> getInstalledUpgrades() {
		if (isMaster()) {
			return upgrades.getUpgrades();
		} else {
			if (master == null)
				findMaster();
			if (master == null)
				return EnumSet.noneOf(EnumMachineUpgrades.class);
			Set<EnumMachineUpgrades> set = EnumSet.noneOf(EnumMachineUpgrades.class);
			set.addAll(master.getInstalledUpgrades());
			if (valve)
				set.addAll(upgrades.getUpgrades());
			return ImmutableSet.copyOf(set);
		}
	}

	@Override
	public void removeUpgrade() {
		if (valve || isMaster()) {
			EnumMachineUpgrades upgrade = upgrades.removeUpgrade();
			if (upgrade == null)
				return;
			ItemStack stack = upgrade.getItemStack();
			Utils.dropItemstack(worldObj, xCoord, yCoord, zCoord, stack);
		} else {
			if (master == null)
				findMaster();
			if (master == null)
				return;
			EnumMachineUpgrades upgrade = master.upgrades.removeUpgrade();
			if (upgrade == null)
				return;
			ItemStack stack = upgrade.getItemStack();
			Utils.dropItemstack(worldObj, xCoord, yCoord, zCoord, stack);
		}
	}
}
