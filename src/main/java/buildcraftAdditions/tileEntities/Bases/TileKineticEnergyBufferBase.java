package buildcraftAdditions.tileEntities.Bases;

import java.util.ArrayList;

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
	protected int energy, maxEnergy, maxInput, maxOutput, loss;
	protected ArrayList<ForgeDirection> inputs = new ArrayList<ForgeDirection>(6);
	protected ArrayList<ForgeDirection> outputs = new ArrayList<ForgeDirection>(6);

	public TileKineticEnergyBufferBase(int maxEnergy, int maxInput, int maxOutput, int loss) {
		super();
		this.maxEnergy = maxEnergy;
		this.maxInput = maxInput;
		this.maxOutput = maxOutput;
		this.loss = loss;
		for (ForgeDirection direction: ForgeDirection.VALID_DIRECTIONS)
			inputs.add(direction);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (!inputs.contains(from))
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
		if (!outputs.contains(from))
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
		if (inputs.contains(direction)){
			inputs.remove(direction);
			outputs.add(direction);
			if (player.worldObj.isRemote)
			player.addChatComponentMessage(new ChatComponentText(direction.name() + " set to output"));
			return;
		}
		if (outputs.contains(direction)) {
			outputs.remove(direction);
			if (player.worldObj.isRemote)
			player.addChatComponentMessage(new ChatComponentText(direction.name() + " dissabled"));
			return;
		}
		inputs.add(direction);
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
		for (ForgeDirection direction: ForgeDirection.VALID_DIRECTIONS) {
			String state = tag.getString(direction.name());
			if (state.equals("OUTPUT")) {
				inputs.remove(direction);
				outputs.add(direction);
			}
			else if (state.equals("DISSABLED"))
				inputs.remove(direction);
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("energy", energy);
		tag.setInteger("maxEnergy", maxEnergy);
		tag.setInteger("maxInput", maxInput);
		tag.setInteger("maxOutput", maxOutput);
		tag.setInteger("loss", loss);
		for (ForgeDirection direction: ForgeDirection.VALID_DIRECTIONS) {
			String state = "DISSABLED";
			if (inputs.contains(direction))
				state = "INPUT";
			if (outputs.contains(direction))
				state = "OUTPUT";
			tag.setString(direction.name(), state);
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (energy - loss > 0)
			energy -= loss;
		else
			energy = 0;
		outputEnergy();
	}

	public void outputEnergy() {
		for (ForgeDirection direction: outputs){
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
