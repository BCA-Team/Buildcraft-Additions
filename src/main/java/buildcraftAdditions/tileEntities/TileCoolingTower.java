package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.NetworkRegistry;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.api.CoolingTowerRecepie;
import buildcraftAdditions.api.RecepieMananger;
import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.networking.INetworkTile;
import buildcraftAdditions.networking.MessageMultiBlockData;
import buildcraftAdditions.networking.MessageTank;
import buildcraftAdditions.networking.PacketHandeler;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileBase;
import buildcraftAdditions.utils.ITankHolder;
import buildcraftAdditions.utils.MultiBlockData;
import buildcraftAdditions.utils.Tank;

import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileCoolingTower extends TileBase implements IMultiBlockTile, IFluidHandler, ITankHolder, INetworkTile {
	private MultiBlockData data = new MultiBlockData().setPatern(Variables.Paterns.COOLING_TOWER);
	public int tank;
	public boolean valve;
	private Tank input = new Tank(2000, this, "input");
	private Tank output = new Tank(2000, this, "output");
	private Tank coolant = new Tank(10000, this, "coolant");
	private TileCoolingTower master;
	private int timer;
	private CoolingTowerRecepie currentRecepie;
	public float heat;


	@Override
	public void updateEntity() {
		if (data.moved)
			data.afterMoveCheck(worldObj);
		if (!isMaster())
			return;
		if (timer == 0) {
			sync();
			timer = 40;
		} else
			timer--;
		if (currentRecepie == null || output.isFull() || heat > 20)
			return;
		input.drain(1, true);
		output.fill(new FluidStack(currentRecepie.getOutput(), 1), true);
		heat += currentRecepie.getHeat();
	}

	private void updateRecepie() {
		if (input.getFluid() == null || input.getFluidAmount() == 0)
			currentRecepie = null;
		else
			currentRecepie = RecepieMananger.getCoolingTowerRecepie(input.getFluid().getFluid());
	}

	@Override
	public void makeMaster(int rotationIndex) {
		data.isMaster = true;
		data.rotationIndex = rotationIndex;
	}

	@Override
	public void sync() {
		if (!worldObj.isRemote) {
			NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 15);
			PacketHandeler.instance.sendToAllAround(new MessageMultiBlockData(this, xCoord, yCoord, zCoord), point);
			PacketHandeler.instance.sendToAllAround(new MessageTank(this, xCoord, yCoord, zCoord), point);
		}
	}

	@Override
	public void invalidateMultiblock() {
		if (isMaster())
			data.patern.destroyMultiblock(worldObj, xCoord, yCoord, zCoord, data.rotationIndex);
		else
			data.patern.destroyMultiblock(worldObj, data.masterX, data.masterY, data.masterZ, data.rotationIndex);
		currentRecepie = null;
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
		sync();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		data.readFromNBT(tag);
		input.readFromNBT(tag);
		output.readFromNBT(tag);
		coolant.readFromNBT(tag);
		valve = tag.getBoolean("valve");
		heat = tag.getFloat("heat");
		tank = tag.getInteger("tank");
		updateRecepie();
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		data.writeToNBT(tag);
		input.saveToNBT(tag);
		output.saveToNBT(tag);
		coolant.saveToNBT(tag);
		tag.setFloat("heat", heat);
		tag.setBoolean("valve", valve);
		tag.setInteger("tank", tank);
	}

	@Override
	public void invalidateBlock() {
		data.invalidate();
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
		worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord), 80);
		input.setFluid(null);
		output.setFluid(null);
		coolant.setFluid(null);
		sync();
		currentRecepie = null;
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
		updateRecepie();
		return filled;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (isPartOfMultiblock() && valve) {
			if (masterCheck())
				return master.drain(maxDrain, doDrain, tank);
		}
		return null;
	}

	private FluidStack drain(int maxDrain, boolean doDrain, int tankID) {
		Tank tank = getTanks()[tankID];
		FluidStack drained = tank.drain(maxDrain, doDrain);
		updateRecepie();
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
	public void writeToByteBuff(ByteBuf buf) {

	}

	@Override
	public void readyFromByteBuff(ByteBuf buf) {

	}
}
