package buildcraftAdditions.tileEntities;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyReceiver;

import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.utils.EnumPriority;
import buildcraftAdditions.utils.Location;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKineticEnergyBufferTier1 extends TileKineticEnergyBufferBase {
	public int energyState, lastEnergyState;
	private boolean creative, init;

	public TileKineticEnergyBufferTier1() {
		super(3000000, 30000, 30000, ConfigurationHandler.KEB1powerloss, 1);
		creative = false;
		init = false;
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		if (creative) {
			if (!init) {
				energy = maxEnergy;
				energyState = 9;
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 9, 2);
				init = true;
			}
			outputEnergy();
			return;
		}
		super.updateEntity();
		if (maxEnergy == 0)
			return;
		energyState = (energy * 8) / maxEnergy;
		if (lastEnergyState != energyState && worldObj.getBlock(xCoord, yCoord, zCoord) == ItemsAndBlocks.kebT1)
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, energyState, 2);
		lastEnergyState = energyState;
	}

	@Override
	public int getEnergyLevel() {
		return maxEnergy;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		creative = tag.getBoolean("creative");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setBoolean("creative", creative);
	}

	@Override
	public void outputEnergy() {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			for (EnumPriority priority : EnumPriority.values()) {
				if (configuration.getPriority(direction) != priority)
					continue;
				if (!configuration.canSend(direction))
					continue;
				Location location = new Location(worldObj, xCoord, yCoord, zCoord);
				location.move(direction);
				IEnergyReceiver energyHandler = null;
				if (location.getTileEntity() != null && location.getTileEntity() instanceof IEnergyReceiver)
					energyHandler = (IEnergyReceiver) location.getTileEntity();
				if (energyHandler != null) {
					int sendEnergy = energy;
					if (canSharePower(location.getTileEntity(), direction)) {
						TileKineticEnergyBufferTier1 keb = (TileKineticEnergyBufferTier1) location.getTileEntity();
						sendEnergy = ((energy + keb.energy) / 2) - keb.energy;
					}
					if (sendEnergy < 0)
						sendEnergy = 0;
					if (sendEnergy > maxOutput)
						sendEnergy = maxOutput;

					int output = energyHandler.receiveEnergy(direction.getOpposite(), sendEnergy, false);
					if (!creative) {
						energy -= output;
					}
				}
			}
		}
	}

	public boolean isCreative() {
		return creative;
	}
}
