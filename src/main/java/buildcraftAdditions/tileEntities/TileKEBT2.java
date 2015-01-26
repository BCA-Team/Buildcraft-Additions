package buildcraftAdditions.tileEntities;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyReceiver;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockBase;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.utils.EnumPriority;
import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.MultiBlockData;

import eureka.api.EurekaKnowledge;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKEBT2 extends TileKineticEnergyBufferBase implements IMultiBlockTile {
	public int energyState;
	private MultiBlockData data = new MultiBlockData().setPatern(Variables.Paterns.KEBT2);
	public TileKEBT2 master;

	public TileKEBT2() {
		super(25000000, 75000, 75000, ConfigurationHandler.KEB2powerloss, 2);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (data.moved) {
			data.afterMoveCheck(worldObj);
			worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord), 80);
		}
		if (!data.isMaster || worldObj.isRemote) {
			return;
		}
		super.updateEntity();
		energyState = (energy * 5) / maxEnergy;
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
		if (energy == 0)
			return;
		ArrayList<Location> list = data.patern.getLocations(worldObj, xCoord, yCoord, zCoord, data.rotationIndex);
		for (Location from : list) {
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				for (EnumPriority priority : EnumPriority.values()) {
					if (configuration.getPriority(direction) != priority)
						continue;
					if (!configuration.canSend(direction))
						continue;
					Location location = from.copy();
					location.move(direction);
					if (location.getTileEntity() == null || !(location.getTileEntity() instanceof IEnergyReceiver))
						continue;
					IEnergyReceiver target = (IEnergyReceiver) location.getTileEntity();
					if (target instanceof IMultiBlockTile && isPartOfSameMultiblock((IMultiBlockTile) target))
						continue;
					int output = maxOutput;
					if (location.getTileEntity() instanceof TileKEBT2) {
						TileKEBT2 keb = (TileKEBT2) location.getTileEntity();
						TileKEBT2 keb2;
						if (keb.isMaster()) {
							keb2 = keb;
						} else {
							keb.findMaster();
							if (keb.master == null)
								continue;
							keb2 = keb.master;
						}
						if (keb2.configuration.canSend(direction.getOpposite()) && keb2.configuration.canReceive(direction.getOpposite())) {
							if (blocked[direction.ordinal()]) {
								blocked[direction.ordinal()] = false;
							} else {
								output = ((energy + keb2.energy) / 2) - keb2.energy;
							}
						}
					}
					if (output < 0)
						output = 0;
					if (output > energy)
						output = energy;
					energy -= target.receiveEnergy(direction.getOpposite(), output, false);
				}
			}
		}
	}

	private boolean isPartOfSameMultiblock(IMultiBlockTile tile) {
		return tile.getMasterX() == xCoord && tile.getMasterY() == yCoord && tile.getMasterZ() == zCoord;
	}

	@Override
	public boolean onBlockActivated(EntityPlayer player) {
		if (!data.partOfMultiBlock)
			return false;
		if (!worldObj.isRemote)
			sync();
		if (data.isMaster)
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.KEB, worldObj, xCoord, yCoord, zCoord);
		else if (master == null)
			findMaster();
		if (master != null)
			master.onBlockActivated(player);
		return true;
	}

	public void destroyMultiblock() {
		data.patern.destroyMultiblock(worldObj, xCoord, yCoord, zCoord, data.rotationIndex);
	}

	private void findMaster() {
		if (data.isMaster)
			master = (TileKEBT2) worldObj.getTileEntity(xCoord, yCoord, zCoord);
		TileEntity tileEntity = worldObj.getTileEntity(data.masterX, data.masterY, data.masterZ);
		if (tileEntity != null && tileEntity instanceof TileKEBT2)
			master = (TileKEBT2) tileEntity;
		else {
			MultiBlockBase block = (MultiBlockBase) worldObj.getBlock(xCoord, yCoord, zCoord);
			block.patern.destroyMultiblock(worldObj, data.masterX, data.masterY, data.masterZ, data.rotationIndex);
		}
	}

	@Override
	public void makeMaster(int rotationIndex) {
		data.isMaster = true;
		data.partOfMultiBlock = true;
		EurekaKnowledge.makeProgress(destroyer, "KEBT3", 1);
		data.rotationIndex = rotationIndex;
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf = super.writeToByteBuff(buf);
		buf.writeInt(energyState);
		buf = data.writeToByteBuff(buf);
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		buf = super.readFromByteBuff(buf);
		energyState = buf.readInt();
		buf = data.readFromByteBuff(buf);
		return buf;
	}

	@Override
	public void formMultiblock(int masterX, int masterY, int masterZ, int rotationIndex) {
		data.formMultiBlock(masterX, masterY, masterZ, rotationIndex);
	}

	@Override
	public void invalidateMultiblock() {
		if (data.isMaster) {
			data.patern.destroyMultiblock(worldObj, xCoord, yCoord, zCoord, data.rotationIndex);
			EurekaKnowledge.makeProgress(destroyer, "KEBT3", -1);
		} else
			data.patern.destroyMultiblock(worldObj, data.masterX, data.masterY, data.masterZ, data.rotationIndex);

	}

	@Override
	public void invalidateBlock() {
		data.invalidate();
		energy = 0;
		configuration.invalidate();
		energyState = 0;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
		worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, ItemsAndBlocks.kebT2, 80);
		sync();
	}

	@Override
	public void moved(ForgeDirection direction) {
		worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord), 80);
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
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord - 1, zCoord - 2, xCoord + 2, yCoord + 2, zCoord + 2);
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

	public void destruction() {
		if (data.isMaster)
			byeBye();
		if (master == null)
			findMaster();
		if (master != null)
			master.byeBye();
	}
}
