package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyHandler;

import buildcraftAdditions.utils.Location;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKineticEnergyBufferBase extends TileEntity implements IEnergyHandler {
	protected int energy, maxEnergy, maxInput, maxOutput, loss;

	public TileKineticEnergyBufferBase(int maxEnergy, int maxInput, int maxOutput, int loss) {
		this.maxEnergy = maxEnergy;
		this.maxInput = maxInput;
		this.maxOutput = maxOutput;
		this.loss = loss;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		int recieved = maxReceive;
		if (recieved > maxEnergy - energy)
			recieved = maxEnergy - energy;
		if (recieved > maxInput)
			recieved = maxInput;
		if (!simulate)
			energy += recieved;
		return recieved;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		int extracted = maxExtract;
		if (extracted > energy)
			extracted = energy;
		if (extracted > maxOutput)
			extracted = maxOutput;
		if (!simulate)
			energy -= extracted;
		return extracted;
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
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		energy = tag.getInteger("energy");
		maxEnergy = tag.getInteger("maxEnergy");
		maxInput = tag.getInteger("maxInput");
		maxOutput = tag.getInteger("maxOutput");
		loss = tag.getInteger("loss");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("energy", energy);
		tag.setInteger("maxEnergy", maxEnergy);
		tag.setInteger("maxInput", maxInput);
		tag.setInteger("maxOutput", maxOutput);
		tag.setInteger("loss", loss);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (energy - loss > 0)
			energy -= loss;
		else
			energy = 0;

		for (ForgeDirection direction: ForgeDirection.VALID_DIRECTIONS){
			Location location = new Location(worldObj, xCoord, yCoord, zCoord);
			location.move(direction);
			IEnergyHandler energyHandler = (IEnergyHandler) location.getTileEntity();
			if (energyHandler != null) {
				int sendEnergy = energy;
				if (sendEnergy > maxOutput)
					sendEnergy = maxOutput;
				energy -= energyHandler.receiveEnergy(direction.getOpposite(), sendEnergy, false);
			}
		}
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}
}
