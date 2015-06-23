package buildcraftAdditions.tileEntities;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyReceiver;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.api.configurableOutput.EnumPriority;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.reference.BlockLoader;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.tileEntities.varHelpers.MultiBlockData;
import buildcraftAdditions.utils.Location;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKEBT3 extends TileKineticEnergyBufferBase implements IMultiBlockTile {
	private final MultiBlockData data = new MultiBlockData().setPatern(Variables.Paterns.KEBT3);
	public int energyState;
	public TileKEBT3 master;

	public TileKEBT3() {
		super(ConfigurationHandler.capacityKEBTier3, ConfigurationHandler.maxTransferKEBTier3, ConfigurationHandler.KEB3powerloss, 3, Variables.SyncIDs.KEBT3.ordinal());
	}

	@Override
	public void updateEntity() {
		if (data.moved) {
			data.afterMoveCheck(worldObj);
			worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord), 80);
		}
		if (!data.isMaster || worldObj.isRemote) {
			return;
		}
		super.updateEntity();
		energyState = (int) (((long) energy) * 4) / capacity;
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
		return master.extractEnergy(from, maxExtract, simulate);
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
	public int getEnergyLevel() {
		if (!data.partOfMultiBlock)
			return 0;
		if (data.isMaster)
			return super.getEnergyLevel();
		if (master == null)
			findMaster();
		if (master == null)
			return 0;
		return master.getEnergyLevel();
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
		return master != null && master.canConnectEnergy(from);
	}

	@Override
	public void outputEnergy() {
		if (energy == 0 || !isMaster())
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
					long output = maxTransfer;
					if (location.getTileEntity() instanceof TileKEBT3) {
						TileKEBT3 keb = (TileKEBT3) location.getTileEntity();
						TileKEBT3 keb2;
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
								blocked[direction.ordinal()] = true;
							}
						}
					}
					if (output < 0)
						output = 0;
					if (output > energy)
						output = energy;
					energy -= target.receiveEnergy(direction.getOpposite(), (int) output, false);
				}
			}
		}
	}

	private boolean isPartOfSameMultiblock(IMultiBlockTile tile) {
		return tile.getMasterX() == xCoord && tile.getMasterY() == yCoord && tile.getMasterZ() == zCoord;
	}

	@Override
	public boolean onBlockActivated(EntityPlayer player) {
		if (!worldObj.isRemote)
			sync();
		if (!data.partOfMultiBlock)
			return false;
		if (data.isMaster) {
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.KEB.ordinal(), worldObj, xCoord, yCoord, zCoord);
		} else {
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

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord - 2, zCoord - 2, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	private void findMaster() {
		if (data.isMaster)
			master = (TileKEBT3) worldObj.getTileEntity(xCoord, yCoord, zCoord);
		TileEntity tileEntity = worldObj.getTileEntity(data.masterX, data.masterY, data.masterZ);
		if (tileEntity != null && tileEntity instanceof TileKEBT3 && ((TileKEBT3) tileEntity).isMaster())
			master = (TileKEBT3) tileEntity;
		else
			data.invalidataMultiblock(worldObj);
	}

	@Override
	public void makeMaster(int rotationIndex) {
		data.isMaster = true;
		data.partOfMultiBlock = true;
		data.rotationIndex = rotationIndex;
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		super.writeToByteBuff(buf);
		buf.writeInt(energyState);
		data.writeToByteBuff(buf);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		super.readFromByteBuff(buf);
		energyState = buf.readInt();
		data.readFromByteBuff(buf);
	}

	@Override
	public void formMultiblock(int masterX, int masterY, int masterZ, int rotationIndex) {
		data.formMultiBlock(masterX, masterY, masterZ, rotationIndex);
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
			worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, BlockLoader.kebT3Core, 80);
		data.invalidate();
		energy = 0;
		configuration.invalidate();
		energyState = 0;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
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
	public void setMasterX(int masterX) {
		data.masterX = masterX;
	}

	@Override
	public int getMasterY() {
		return data.masterY;
	}

	@Override
	public void setMasterY(int masterY) {
		data.masterY = masterY;
	}

	@Override
	public int getMasterZ() {
		return data.masterZ;
	}

	@Override
	public void setMasterZ(int masterZ) {
		data.masterZ = masterZ;
	}

	@Override
	public int getRotationIndex() {
		return data.rotationIndex;
	}

	@Override
	public void setRotationIndex(int rotationIndex) {
		data.rotationIndex = rotationIndex;
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
	public void setIsMaster(boolean isMaster) {
		data.isMaster = isMaster;
	}

	@Override
	public void setPartOfMultiBlock(boolean partOfMultiBlock) {
		data.partOfMultiBlock = partOfMultiBlock;
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
