package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;

import cpw.mods.fml.common.network.NetworkRegistry;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import buildcraftAdditions.api.CoolingTowerRecepie;
import buildcraftAdditions.api.RecepieMananger;
import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.networking.MessageMultiBlockData;
import buildcraftAdditions.networking.MessageTank;
import buildcraftAdditions.networking.PacketHandeler;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileBase;
import buildcraftAdditions.utils.ITankHolder;
import buildcraftAdditions.utils.MultiBlockData;
import buildcraftAdditions.utils.Tank;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileCoolingTower extends TileBase implements IMultiBlockTile, IFluidHandler, ITankHolder {
	private MultiBlockData data = new MultiBlockData().setPatern(Variables.Paterns.COOLING_TOWER);
	public boolean valve;
	private Tank input = new Tank(2000, this, "input");
	private Tank output = new Tank(2000, this, "output");
	private Tank coolant = new Tank(10000, this, "coolant");
	private TileCoolingTower master;
	private int timer;
	private CoolingTowerRecepie currentRecepie;
	private float heat;


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
			player.addChatComponentMessage(new ChatComponentText(input.toString()));
			player.addChatComponentMessage(new ChatComponentText(output.toString()));
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
	}

	@Override
	public void invalidateBlock() {
		data.invalidate();
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
		worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord), 80);
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
		if (isMaster()) {
			int filled = input.fill(resource, doFill);
			updateRecepie();
			return filled;
		}
		if (isPartOfMultiblock() && valve) {
			if (masterCheck())
				return master.fill(from, resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (isMaster()) {
			FluidStack drained = output.drain(maxDrain, doDrain);
			updateRecepie();
			return drained;
		}
		if (isPartOfMultiblock() && valve) {
			if (masterCheck())
				return master.drain(from, maxDrain, doDrain);
		}
		return null;
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
		return new Tank[]{input, output};
	}

	private boolean masterCheck() {
		if (master == null)
			findMaster();
		return master != null;
	}
}
