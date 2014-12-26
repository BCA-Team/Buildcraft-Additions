package buildcraftAdditions.tileEntities;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.NetworkRegistry;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyHandler;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockBase;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.core.Logger;
import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.networking.MessageKEBT3;
import buildcraftAdditions.networking.MessageMultiBlockData;
import buildcraftAdditions.networking.PacketHandeler;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.utils.EnumSideStatus;
import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.MultiBlockData;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKEBT3 extends TileKineticEnergyBufferBase implements IMultiBlockTile {
	public int energyState;
	public TileKEBT3 master;
	private MultiBlockData data = new MultiBlockData().setPatern(Variables.Paterns.KEBT3);

	public TileKEBT3() {
		super(100000000, 300000, 300000, ConfigurationHandler.KEB3powerloss, 3);
	}

	@Override
	public void updateEntity() {
		if (data.moved) {
			data.afterMoveCheck(worldObj);
		}
		if (!data.isMaster || worldObj.isRemote) {
			return;
		}
		super.updateEntity();
		energyState = (int) (((long) energy) * 4) / maxEnergy;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (!data.partOfMultiBlock)
			return 0;
		if (data.isMaster)
			return super.receiveEnergy(from, maxReceive, simulate);
		if (master == null)
			findMaster();
		if (master == null)
			return 0;
		return master.receiveEnergy(from, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if (!data.partOfMultiBlock)
			return 0;
		if (data.isMaster)
			return super.extractEnergy(from, maxExtract, simulate);
		if (master == null)
			findMaster();
		if (master == null)
			return 0;
		return master.receiveEnergy(from, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if (!data.partOfMultiBlock)
			return 0;
		if (data.isMaster)
			return super.getEnergyStored(from);
		if (master == null)
			findMaster();
		if (master == null)
			return 0;
		return master.getEnergyStored(from);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		if (!data.partOfMultiBlock)
			return 0;
		if (data.isMaster)
			return super.getMaxEnergyStored(from);
		if (master == null)
			findMaster();
		if (master == null)
			return 0;
		return master.getMaxEnergyStored(from);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		data.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		data.writeToNBT(tag);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if (!data.partOfMultiBlock)
			return false;
		if (data.isMaster)
			return super.canConnectEnergy(from);
		if (master == null)
			findMaster();
		if (master == null)
			return false;
		return master.canConnectEnergy(from);
	}

	@Override
	public void outputEnergy() {
		if (energy == 0 || !isMaster())
			return;
		ArrayList<Location> list = data.patern.getLocations(worldObj, xCoord, yCoord, zCoord, data.rotationIndex);
		for (Location from: list) {
			for (ForgeDirection direction: ForgeDirection.VALID_DIRECTIONS) {
				if (configuration[direction.ordinal()] != EnumSideStatus.OUTPUT && configuration[direction.ordinal()] != EnumSideStatus.BOTH)
					continue;
				Location location = from.copy();
				location.move(direction);
				IEnergyHandler target = (IEnergyHandler) location.getTileEntity();
				if (target == null || target instanceof TileKEBT3)
					continue;
				int output = maxOutput;
				if (output > energy)
					output = energy;
				energy -= target.receiveEnergy(direction.getOpposite(), output, false);
			}
		}
	}

	@Override
	public boolean onBlockActivated(EntityPlayer player) {
		if (!worldObj.isRemote)
			sync();
		if (!data.partOfMultiBlock)
			return false;
		if (data.isMaster) {
			player.openGui(BuildcraftAdditions.instance, Variables.GUI_KEB, worldObj, xCoord, yCoord, zCoord);
		}
		else {
			if (master == null)
				findMaster();
			if (master != null)
				master.onBlockActivated(player);
		}
		return true;
	}

	public void destroyMultiblock() {
		data.patern.destroyMultiblock(worldObj, data.masterX, data.masterY, data.masterZ, data.rotationIndex);
	}

	private void findMaster() {
		if (data.isMaster)
			master = (TileKEBT3) worldObj.getTileEntity(xCoord, yCoord, zCoord);
		TileEntity tileEntity = worldObj.getTileEntity(data.masterX, data.masterY, data.masterZ);
		if (tileEntity != null && tileEntity instanceof IMultiBlockTile)
			master = (TileKEBT3) tileEntity;
		else {
			Logger.info("UNABLE TO FIND MASTER, SELF DESTRUCT INITIATED");
			MultiBlockBase block = (MultiBlockBase) worldObj.getBlock(xCoord, yCoord, zCoord);
			block.patern.destroyMultiblock(worldObj, data.masterX, data.masterY, data.masterZ, data.rotationIndex);
		}
	}

	@Override
	public void makeMaster(int rotationIndex) {
		data.isMaster = true;
		data.partOfMultiBlock = true;
		data.rotationIndex = rotationIndex;
	}

	@Override
	public void sync() {
		if (!worldObj.isRemote) {
			NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 15);
			PacketHandeler.instance.sendToAllAround(new MessageKEBT3(this), point);
			PacketHandeler.instance.sendToAllAround(new MessageMultiBlockData(this, xCoord, yCoord, zCoord), point);
		}
	}

	@Override
	public void formMultiblock(int masterX, int masterY, int masterZ, int rotationIndex) {
		data.formMultiBlock(masterX, masterY, masterZ);
		if (!worldObj.isRemote)
			sync();
	}

	@Override
	public void invalidateMultiblock() {
		if (data.isMaster)
			data.patern.destroyMultiblock(worldObj, xCoord, yCoord, zCoord, data.rotationIndex);
		else
			data.patern.destroyMultiblock(worldObj, data.masterX, data.masterY, data.masterZ, data.rotationIndex);

	}

	@Override
	public void invalidateBlock() {
		if (data.isMaster)
			worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, ItemsAndBlocks.kebT3Core, 80);
		data.invalidate();
		energy = 0;
		for (int teller = 0; teller < 6; teller++) {
			configuration[teller] = EnumSideStatus.INPUT;
		}
		energyState = 0;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
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

	}

	public void destruction() {
		if (data.isMaster)
			byeBye();
		if (master == null)
			findMaster();
		if (master != null)
			master.byeBye();
	}
}
