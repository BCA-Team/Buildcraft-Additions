package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.nbt.NBTTagCompound;

import cofh.api.energy.IEnergyStorage;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileMachineBase extends TileBase implements IEnergyStorage {
	protected int energy;
	private int maxEnergy;

	protected TileMachineBase(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energyRecieved = maxReceive;
		if (energyRecieved > maxEnergy - energy)
			energyRecieved = maxReceive - energy;
		if (!simulate)
			energy += energyRecieved;
		return energyRecieved;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored() {
		return energy;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		energy = tag.getInteger("energy");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("engergy", energy);
	}

	@Override
	public int getMaxEnergyStored() {
		return maxEnergy;
	}
}
