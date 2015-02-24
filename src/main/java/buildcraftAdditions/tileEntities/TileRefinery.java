package buildcraftAdditions.tileEntities;

import java.util.EnumSet;
import java.util.Set;

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

import cofh.api.energy.IEnergyReceiver;

import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.api.recipe.refinery.IRefineryRecipe;
import buildcraftAdditions.core.Logger;
import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.reference.enums.EnumMachineUpgrades;
import buildcraftAdditions.tileEntities.Bases.TileBase;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;
import buildcraftAdditions.tileEntities.varHelpers.MultiBlockData;
import buildcraftAdditions.tileEntities.varHelpers.Upgrades;
import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.RotationUtils;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.utils.fluids.ITankHolder;
import buildcraftAdditions.utils.fluids.RefineryRecipeTank;
import buildcraftAdditions.utils.fluids.Tank;

import com.google.common.collect.ImmutableSet;
import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileRefinery extends TileBase implements IMultiBlockTile, IFluidHandler, IEnergyReceiver, ITankHolder, IPipeConnection, IUpgradableMachine {
	public int energy, maxEnergy, currentHeat, requiredHeat, energyCost, heatTimer, lastRequiredHeat;
	public boolean init, valve, isCooling, moved;
	public TileRefinery master;
	private Tank input = new RefineryRecipeTank("Input", 3000, this);
	private Tank output = new Tank(3000, this, "Output");
	private FluidStack outputFluidStack;
	private FluidStack inputFluidStack;
	private MultiBlockData data = new MultiBlockData().setPatern(Variables.Paterns.REFINERY);
	protected Upgrades upgrades = new Upgrades(0);
	private String inputFluid, outputFluid;

	public TileRefinery() {
		maxEnergy = 50000;
		init = false;
		lastRequiredHeat = 20;
		currentHeat = 20;
		inputFluid = "";
		outputFluid = "";
	}

	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote)
			return;
		if (data.moved) {
			data.afterMoveCheck(worldObj);
			worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord), 80);
		}
		sync();
		if (valve && upgrades.hasUpgrade(EnumMachineUpgrades.AUTO_OUTPUT)) {
			if (master == null)
				findMaster();
			if (master == null)
				return;
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				Location location = new Location(this).move(direction);
				TileEntity entity = location.getTileEntity();
				if (entity != null && entity instanceof IFluidHandler && !(entity instanceof TileRefinery) && master.output.getFluidType() != null) {
					IFluidHandler tank = (IFluidHandler) entity;
					int drain = tank.fill(direction.getOpposite(), new FluidStack(master.output.getFluidType(), 100), false);
					FluidStack stack = master.drain(direction, drain, true);
					tank.fill(direction.getOpposite(), stack, true);
				}
			}
		}
		if (!data.isMaster)
			return;
		if (input.getFluid() != null && input.getFluid().amount <= 0)
			input.setFluid(null);
		if (output.getFluid() != null && output.getFluid().amount <= 0)
			output.setFluid(null);
		if (input.getFluid() == null)
			updateRecipe();
		updateHeat();
		energyCost = (input.getFluid() == null || isCooling || energy < (int) (50 + (50 * ((double) currentHeat / 100)))) ? 0 : (int) (50 + (50 * ((double) currentHeat / 100)));
		double factor = 0;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.EFFICIENCY_1))
			factor += 0.1;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.EFFICIENCY_2))
			factor += 0.15;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.EFFICIENCY_3))
			factor += 0.25;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.SPEED_1))
			factor -= 0.2;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.SPEED_2))
			factor -= 0.3;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.SPEED_3))
			factor -= 0.5;
		energyCost = (int) (energyCost - (energyCost * factor));
		energy -= energyCost;
		if (currentHeat < requiredHeat) {
			return;
		}
		int count = 1;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.SPEED_1))
			count++;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.SPEED_2))
			count += 2;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.SPEED_3))
			count += 3;
		for (int i = 0; i < count; i++) {
			if (energyCost == 0 || input.isEmpty() || output.isFull() || !input.getFluid().isFluidEqual(inputFluidStack) || input.getFluidAmount() < inputFluidStack.amount || (!output.isEmpty() && !output.getFluid().isFluidEqual(outputFluidStack)) || output.getCapacity() - output.getFluidAmount() < outputFluidStack.amount)
				return;
			input.drain(inputFluidStack.amount, true);
			output.fill(outputFluidStack, true);
		}
	}

	private void updateHeat() {
		if (worldObj.isRemote)
			return;
		if (heatTimer == 0) {
			if ((currentHeat > requiredHeat || (energy < energyCost || energyCost == 0)) && currentHeat > 20) {
				currentHeat--;
				isCooling = true;
			}
			if (currentHeat < requiredHeat && (energy >= energyCost && energyCost != 0)) {
				currentHeat++;
				isCooling = false;
			}
			heatTimer = 10;
		}
		if (currentHeat == 20)
			isCooling = false;
		heatTimer -= 1;
	}

	private void updateRecipe() {
		if (!isPartOfMultiblock())
			return;
		if (!input.isEmpty()) {
			IRefineryRecipe recipe = BCARecipeManager.refinery.getRecipe(input.getFluid());
			if (recipe != null) {
				requiredHeat = recipe.getRequiredHeat();
				lastRequiredHeat = requiredHeat;
				outputFluidStack = recipe.getOutput();
				inputFluidStack = recipe.getInput();
				inputFluid = inputFluidStack.getLocalizedName();
				outputFluid = outputFluidStack.getLocalizedName();
			}
		} else {
			requiredHeat = 0;
		}
	}

	@Override
	public void makeMaster(int rotationIndex) {
		data.isMaster = true;
		data.partOfMultiBlock = true;
		data.rotationIndex = rotationIndex;
		upgrades.blacklistUpgrade(EnumMachineUpgrades.AUTO_OUTPUT).setMaxUpgrades(4);
	}

	public void findMaster() {
		TileEntity entity = worldObj.getTileEntity(data.masterX, data.masterY, data.masterZ);
		if (entity instanceof TileRefinery) {
			master = (TileRefinery) entity;
		}
		if (master == null || !master.data.isMaster) {
			master = null;
			invalidateMultiblock();
		}
	}

	@Override
	public void invalidateMultiblock() {
		if (isMaster())
			data.patern.destroyMultiblock(worldObj, xCoord, yCoord, zCoord, data.rotationIndex);
		else
			data.patern.destroyMultiblock(worldObj, data.masterX, data.masterY, data.masterZ, data.rotationIndex);
	}

	private void emptyTanks() {
		if (input.getFluid() == null || input.getFluid().amount < 1000 || input.getFluidType() == null)
			return;
		ForgeDirection[] directions = RotationUtils.rotateDirections(new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.UP}, data.rotationIndex);
		Location location = new Location(this);
		location.move(directions);
		try {
			while (input.getFluid().amount >= 1000) {
				if (input.getFluidType().getBlock() != null)
					location.setBlock(input.getFluidType().getBlock());
				input.drain(1000, true);
				location.move(RotationUtils.rotatateDirection(ForgeDirection.NORTH, data.rotationIndex));
			}
		} catch (Exception e) {
			Logger.error("Error while trying to empty the tank of a refinery");
		} finally {
			input.setFluid(null);
		}
		location.move(RotationUtils.rotatateDirection(ForgeDirection.NORTH, data.rotationIndex));
		if (output.getFluid() == null || output.getFluid().amount < 1000 || output.getFluidType() == null)
			return;
		try {
			while (output.getFluid().amount >= 1000) {
				location.setBlock(output.getFluidType().getBlock());
				output.drain(1000, true);
				location.move(RotationUtils.rotatateDirection(ForgeDirection.NORTH, data.rotationIndex));
			}
		} catch (Exception e) {
			Logger.error("Error while trying to empty the tank of a refinery");
		} finally {
			input.setFluid(null);
		}
	}

	@Override
	public boolean onBlockActivated(EntityPlayer player) {
		if (data.isMaster) {
			sync();
			if (!worldObj.isRemote) {
				player.openGui(BuildcraftAdditions.instance, Variables.Gui.REFINERY.ordinal(), worldObj, xCoord, yCoord, zCoord);
			}
			return true;
		}
		if (data.partOfMultiBlock) {
			if (master == null)
				findMaster();
			if (master != null)
				return master.onBlockActivated(player);
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		valve = tag.getBoolean("valve");
		energy = tag.getInteger("energy");
		currentHeat = tag.getInteger("currentHeat");
		requiredHeat = tag.getInteger("requiredHeat");
		lastRequiredHeat = tag.getInteger("lastRequiredHeat");
		data.readFromNBT(tag);
		if (tag.hasKey("input", Constants.NBT.TAG_COMPOUND))
			input.readFromNBT(tag.getCompoundTag("input"));
		if (tag.hasKey("output", Constants.NBT.TAG_COMPOUND))
			output.readFromNBT(tag.getCompoundTag("output"));
		updateRecipe();
		upgrades.readFromNBT(tag);
		if (valve)
			upgrades.whitelistUpgrade(EnumMachineUpgrades.AUTO_OUTPUT);
		if (isMaster())
			upgrades.blacklistUpgrade(EnumMachineUpgrades.AUTO_OUTPUT);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		data.writeToNBT(tag);
		tag.setBoolean("valve", valve);
		tag.setInteger("energy", energy);
		tag.setInteger("currentHeat", currentHeat);
		tag.setInteger("requiredHeat", requiredHeat);
		tag.setInteger("lastRequiredHeat", lastRequiredHeat);
		tag.setTag("input", input.writeToNBT(new NBTTagCompound()));
		tag.setTag("output", output.writeToNBT(new NBTTagCompound()));
		upgrades.writeToNBT(tag);
	}

	@Override
	public void formMultiblock(int masterX, int masterY, int masterZ, int rotationIndex) {
		data.formMultiBlock(masterX, masterY, masterZ, rotationIndex);
		if (valve)
			upgrades.whitelistUpgrade(EnumMachineUpgrades.AUTO_OUTPUT).setMaxUpgrades(1);
		sync();
	}

	@Override
	public void invalidateBlock() {
		if (data.isMaster)
			emptyTanks();
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
		worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord), 80);
		input.setFluid(null);
		output.setFluid(null);
		requiredHeat = 0;
		data.invalidate();
		upgrades.invalidate();
		sync();
	}

	@Override
	public void moved(ForgeDirection direction) {
		data.onMove(direction);
		master = null;
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
		if (!valve)
			return 0;
		if (master == null)
			findMaster();
		if (master != null)
			return master.realFill(resource, doFill);
		return 0;
	}

	public int realFill(FluidStack resource, boolean doFill) {
		int result = input.fill(resource, doFill);
		updateRecipe();
		return result;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (!isMaster())
			return null;
		return output.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (isMaster())
			return realDrain(maxDrain, doDrain);
		if (!valve)
			return null;
		if (master == null)
			findMaster();
		if (master != null)
			return master.realDrain(maxDrain, doDrain);
		return null;
	}

	public FluidStack realDrain(int maxDrain, boolean doDrain) {
		FluidStack result = output.drain(maxDrain, doDrain);
		updateRecipe();
		return result;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return data.partOfMultiBlock && valve;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return data.partOfMultiBlock && valve;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if (!valve)
			return null;
		if (master == null)
			findMaster();
		if (master != null)
			return master.realGetTankInfo();
		return new FluidTankInfo[]{new FluidTankInfo(input), new FluidTankInfo(output)};
	}

	public FluidTankInfo[] realGetTankInfo() {
		return new FluidTankInfo[]{new FluidTankInfo(input), new FluidTankInfo(output)};
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (isMaster()) {
			int recieved = maxReceive;
			if (recieved > maxEnergy - energy)
				recieved = maxEnergy - energy;
			if (!simulate)
				energy += recieved;
			return recieved;
		} else {
			if (master == null)
				findMaster();
			if (master != null)
				return master.receiveEnergy(from, maxReceive, simulate);
		}
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if (data.isMaster)
			return energy;
		else {
			if (master == null)
				findMaster();
			if (master != null)
				return master.getEnergyStored(from);
		}
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		if (data.isMaster)
			return maxEnergy;
		else {
			if (master == null)
				findMaster();
			if (master != null)
				return master.getMaxEnergyStored(from);
		}
		return 0;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public Tank[] getTanks() {
		return new Tank[]{input, output};
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf.writeBoolean(valve);
		buf.writeInt(currentHeat);
		buf.writeInt(lastRequiredHeat);
		buf.writeInt(energyCost);
		buf.writeInt(requiredHeat);
		char[] chars = inputFluid.toCharArray();
		buf.writeInt(chars.length);
		for (int t = 0; t < chars.length; t++)
			buf.writeChar(chars[t]);
		chars = outputFluid.toCharArray();
		buf.writeInt(chars.length);
		for (int t = 0; t < chars.length; t++)
			buf.writeChar(chars[t]);
		input.writeToByteBuff(buf);
		output.writeToByteBuff(buf);
		data.writeToByteBuff(buf);
		upgrades.writeToByteBuff(buf);
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		valve = buf.readBoolean();
		currentHeat = buf.readInt();
		lastRequiredHeat = buf.readInt();
		energyCost = buf.readInt();
		requiredHeat = buf.readInt();
		int num = buf.readInt();
		inputFluid = "";
		for (int t = 0; t < num; t++)
			inputFluid += buf.readChar();
		inputFluid = inputFluid.trim();
		num = buf.readInt();
		outputFluid = "";
		for (int t = 0; t < num; t++)
			outputFluid += buf.readChar();
		outputFluid = outputFluid.trim();
		buf = input.readFromByteBuff(buf);
		buf = output.readFromByteBuff(buf);
		buf = data.readFromByteBuff(buf);
		buf = upgrades.readFromByteBuff(buf);
		return buf;
	}

	@Override
	public ConnectOverride overridePipeConnection(IPipeTile.PipeType type, ForgeDirection with) {
		return (valve && type == IPipeTile.PipeType.FLUID) || type == IPipeTile.PipeType.POWER ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
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

	public String getOutput() {
		return outputFluid;
	}

	public String getInput() {
		return inputFluid;
	}
}
