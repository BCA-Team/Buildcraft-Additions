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
import buildcraftAdditions.multiBlocks.MultiBlockPatern;
import buildcraftAdditions.multiBlocks.MultiBlockPaternKEBT2;
import buildcraftAdditions.multiBlocks.MultiBlockPaternKEBT3;
import buildcraftAdditions.networking.MessageKEBT3;
import buildcraftAdditions.networking.PacketHandeler;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.utils.Location;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKEBT3 extends TileKineticEnergyBufferBase implements IMultiBlockTile {
	public MultiBlockPatern patern = new MultiBlockPaternKEBT3();
	public boolean isMaster, partOfMultiBlock, moved;
	public boolean renderUpdate = true;
	public int masterX, masterY, masterZ, energyState, lastEnergyState, oldmasterX, oldmasterY, oldmasterZ;
	public TileKEBT3 master;

	public TileKEBT3() {
		super(100000000, 300000, 300000, ConfigurationHandler.KEB3powerloss, 3);
	}

	@Override
	public void updateEntity() {
		if (moved) {
			if (!patern.isPaternValid(worldObj, masterX, masterY, masterZ)) {
				patern.destroyMultiblock(worldObj, masterX, masterY, masterZ);
				patern.destroyMultiblock(worldObj, oldmasterX, oldmasterY, oldmasterZ);
			}
			moved = false;
		}
		if (renderUpdate) {
			sync();
			renderUpdate = false;
		}
		if (!isMaster || worldObj.isRemote) {
			return;
		}
		super.updateEntity();
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (!partOfMultiBlock)
			return 0;
		if (isMaster)
			return super.receiveEnergy(from, maxReceive, simulate);
		if (master == null)
			findMaster();
		if (master == null)
			return 0;
		return master.receiveEnergy(from, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if (!partOfMultiBlock)
			return 0;
		if (isMaster)
			return super.extractEnergy(from, maxExtract, simulate);
		if (master == null)
			findMaster();
		if (master == null)
			return 0;
		return master.receiveEnergy(from, maxExtract, simulate);
	}

	@Override
	public void changeSideMode(int side) {
		if (!partOfMultiBlock)
			return;
		if (isMaster) {
			super.changeSideMode(side);
			return;
		}
		if (master == null)
			findMaster();
		if (master == null)
			return;
		master.changeSideMode(side);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if (!partOfMultiBlock)
			return 0;
		if (isMaster)
			return super.getEnergyStored(from);
		if (master == null)
			findMaster();
		if (master == null)
			return 0;
		return master.getEnergyStored(from);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		if (!partOfMultiBlock)
			return 0;
		if (isMaster)
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
		masterX = tag.getInteger("masterX");
		masterY = tag.getInteger("masterY");
		masterZ = tag.getInteger("masterZ");
		isMaster = tag.getBoolean("isMaster");
		partOfMultiBlock = tag.getBoolean("partOfMultiblock");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("masterX", masterX);
		tag.setInteger("masterY", masterY);
		tag.setInteger("masterZ", masterZ);
		tag.setBoolean("isMaster", isMaster);
		tag.setBoolean("partOfMultiblock", partOfMultiBlock);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if (!partOfMultiBlock)
			return false;
		if (isMaster)
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
		ArrayList<Location> list = patern.getLocations(worldObj, xCoord, yCoord, zCoord);
		for (Location from: list) {
			for (ForgeDirection direction: ForgeDirection.VALID_DIRECTIONS) {
				if (configuration[direction.ordinal()] != 1)
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
		if (!partOfMultiBlock)
			return false;
		if (isMaster) {
			player.openGui(BuildcraftAdditions.instance, Variables.GuiKEB, worldObj, xCoord, yCoord, zCoord);
			Logger.info("Energy stored: " + energy + " (" + worldObj.isRemote +")");
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
		MultiBlockPatern patern = new MultiBlockPaternKEBT2();
		patern.destroyMultiblock(worldObj, xCoord, yCoord, zCoord);
	}

	private void findMaster() {
		if (isMaster)
			master = (TileKEBT3) worldObj.getTileEntity(xCoord, yCoord, zCoord);
		TileEntity tileEntity = worldObj.getTileEntity(masterX, masterY, masterZ);
		if (tileEntity != null && tileEntity instanceof IMultiBlockTile)
			master = (TileKEBT3) tileEntity;
		else {
			Logger.info("UNABLE TO FIND MASTER, SELF DESTRUCT INITIATED");
			MultiBlockBase block = (MultiBlockBase) worldObj.getBlock(xCoord, yCoord, zCoord);
			block.patern.destroyMultiblock(worldObj, masterX, masterY, masterZ);
		}
	}

	@Override
	public void makeMaster() {
		isMaster = true;
		partOfMultiBlock = true;
	}

	@Override
	public void sync() {
		if (!worldObj.isRemote)
			PacketHandeler.instance.sendToAllAround(new MessageKEBT3(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 15));
	}

	@Override
	public void formMultiblock(int masterX, int masterY, int masterZ) {
		partOfMultiBlock = true;
		this.masterX = masterX;
		this.masterY = masterY;
		this.masterZ = masterZ;
		if (!worldObj.isRemote)
			sync();
	}

	@Override
	public void invalidateMultiblock() {
		if (isMaster)
			patern.destroyMultiblock(worldObj, xCoord, yCoord, zCoord);
		else
			patern.destroyMultiblock(worldObj, masterX, masterY, masterZ);

	}

	@Override
	public void invalidateBlock() {

		if (isMaster)
			worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, ItemsAndBlocks.kebT3Core, 80);
		partOfMultiBlock = false;
		isMaster = false;
		energy = 0;
		for (int teller = 0; teller < 6; teller++) {
			configuration[teller] = 0;
		}
		energyState = 0;
		lastEnergyState = 0;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
		sync();
	}

	@Override
	public void moved(ForgeDirection direction) {
		if (isMaster) {
			oldmasterX = xCoord;
			oldmasterY = yCoord;
			oldmasterZ = zCoord;
			masterX = xCoord + direction.offsetX;
			masterY = yCoord + direction.offsetY;
			masterZ = zCoord + direction.offsetZ;
			moved = true;
		} else {
			oldmasterX = masterX;
			oldmasterY = masterY;
			oldmasterZ = masterZ;
			moved = true;
			master = null;
			masterX += direction.offsetX;
			masterY += direction.offsetY;
			masterZ += direction.offsetZ;
		}
	}

	public void destruction() {
		if (isMaster)
			byeBye();
		if (master == null)
			findMaster();
		if (master != null)
			master.byeBye();
	}
}
