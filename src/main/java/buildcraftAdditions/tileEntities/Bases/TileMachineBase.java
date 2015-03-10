package buildcraftAdditions.tileEntities.Bases;

import io.netty.buffer.ByteBuf;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyReceiver;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileMachineBase extends TileBase implements IEnergyReceiver {
	private final int maxEnergy;
	protected int energy;

	protected TileMachineBase(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		energy = tag.getInteger("energy");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("energy", energy);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (energy >= maxEnergy)
			return 0;
		int energyRecieved = maxReceive;
		if (energyRecieved > maxEnergy - energy)
			energyRecieved = maxEnergy - energy;
		if (!simulate)
			energy += energyRecieved;
		return energyRecieved;
	}


	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energy;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return maxEnergy;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		buf.writeInt(energy);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		energy = buf.readInt();
	}
}
