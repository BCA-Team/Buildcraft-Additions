package buildcraftAdditions.tileEntities;

import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.power.ILaserTarget;

import buildcraftAdditions.tileEntities.Bases.TileCoilBase;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKineticCoil extends TileCoilBase implements ILaserTarget {
	public static int buffer;

	@Override
	public boolean requiresLaserEnergy() {
		return shouldHeat;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		nbt.setInteger("buffer", buffer);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		buffer = nbt.getInteger("buffer");
	}

	@Override
	public int getIncrement() {
		increment = buffer;
		buffer = 0;
		return increment;
	}

	@Override
	public void receiveLaserEnergy(double energy) {
		buffer += energy * 50;
	}

	@Override
	public boolean isInvalidTarget() {
		return false;
	}

	@Override
	public int getXCoord() {
		return xCoord;
	}

	@Override
	public int getYCoord() {
		return yCoord;
	}

	@Override
	public int getZCoord() {
		return zCoord;
	}
}
