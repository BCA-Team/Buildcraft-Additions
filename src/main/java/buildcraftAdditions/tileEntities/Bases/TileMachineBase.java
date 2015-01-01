package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyReceiver;

import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileMachineBase extends TileBase implements IEnergyReceiver {
	protected int energy;
	private int maxEnergy;

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
		tag.setInteger("engergy", energy);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		int energyRecieved = maxReceive;
		if (energyRecieved > maxEnergy - energy)
			energyRecieved = maxReceive - energy;
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
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf.writeInt(energy);
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		energy = buf.readInt();
		return buf;
	}
}
