package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;

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
	public int energy, maxEnergy, maxInput, maxOutput, loss;
	public int[] configuration = new int[6];
	public int tier;

	public TileKineticEnergyBufferBase(int maxEnergy, int maxInput, int maxOutput, int loss, int tier) {
		super();
		this.maxEnergy = maxEnergy;
		this.maxInput = maxInput;
		this.maxOutput = maxOutput;
		this.loss = loss;
		this.tier = tier;
		for (int teller = 0; teller < 6; teller++) {
			configuration[teller] = 0;
		}
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (configuration[from.ordinal()] != 0)
			return 0;
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
		if (configuration[from.ordinal()] != 1)
			return 0;
		int extracted = maxExtract;
		if (extracted > energy)
			extracted = energy;
		if (extracted > maxOutput)
			extracted = maxOutput;
		if (!simulate)
			energy -= extracted;
		return extracted;
	}

	public void changeSideMode(int side, EntityPlayer player) {
		ForgeDirection direction = ForgeDirection.getOrientation(side);
		if (configuration[direction.ordinal()] == 0){
			configuration[direction.ordinal()] = 1;
			if (player.worldObj.isRemote)
				player.addChatComponentMessage(new ChatComponentText(direction.name() + " set to output"));
			return;
		}
		if (configuration[direction.ordinal()] == 1) {
			configuration[direction.ordinal()] = 2;
			if (player.worldObj.isRemote)
				player.addChatComponentMessage(new ChatComponentText(direction.name() + " dissabled"));
			return;
		}
		configuration[direction.ordinal()] = 3;
		if (player.worldObj.isRemote)
			player.addChatComponentMessage(new ChatComponentText(direction.name() + " set to input"));
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
		if (tag.hasKey("configuration"))
		configuration = tag.getIntArray("configuration");
		}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("energy", energy);
		tag.setInteger("maxEnergy", maxEnergy);
		tag.setInteger("maxInput", maxInput);
		tag.setInteger("maxOutput", maxOutput);
		tag.setInteger("loss", loss);
		tag.setIntArray("configuration", configuration);
		}

	@Override
	public void updateEntity() {
		super.updateEntity();
		energy = energy - loss;
		if (energy < 0)
			energy = 0;
		outputEnergy();
	}

	public void outputEnergy() {
		for (ForgeDirection direction: ForgeDirection.VALID_DIRECTIONS){
			if (configuration[direction.ordinal()] != 1)
				continue;
			Location location = new Location(worldObj, xCoord, yCoord, zCoord);
			location.move(direction);
			IEnergyHandler energyHandler = null;
			if (location.getTileEntity() != null && location.getTileEntity() instanceof IEnergyHandler)
				energyHandler = (IEnergyHandler) location.getTileEntity();
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
