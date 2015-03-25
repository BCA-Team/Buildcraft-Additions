package buildcraftAdditions.tileEntities;

import io.netty.buffer.ByteBuf;

import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.power.ILaserTarget;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.tileEntities.Bases.TileDusterWithConfigurableOutput;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKineticDuster extends TileDusterWithConfigurableOutput implements ILaserTarget {

	public int progressStage, oldProgressStage;
	private boolean receivedLaserEnergy = false;

	public TileKineticDuster() {
		super("");
	}

	@Override
	public boolean requiresLaserEnergy() {
		return BCARecipeManager.duster.getRecipe(getStackInSlot(0)) != null;
	}

	@Override
	public void receiveLaserEnergy(int energy) {
		progress += energy;
		receivedLaserEnergy = true;
		if (progress >= 20000) {
			progress = 0;
			progressStage = 0;
			dust();
		} else {
			if (progress >= 15000)
				progressStage = 3;
			else if (progress >= 10000)
				progressStage = 2;
			else if (progress >= 5000)
				progressStage = 1;
			else progressStage = 0;
		}
		if (progressStage != oldProgressStage) {
			sync();
			oldProgressStage = progressStage;
		}
	}

	@Override
	public boolean canUpdate() {
		return true;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!worldObj.isRemote && receivedLaserEnergy) {
			spawnDustingParticles();
			receivedLaserEnergy = false;
		}
	}

	@Override
	public double getProgress() {
		return progress / 20000D;
	}

	@Override
	public boolean isInvalidTarget() {
		return isInvalid();
	}

	@Override
	public double getXCoord() {
		return xCoord;
	}

	@Override
	public double getYCoord() {
		return yCoord + 0.08;
	}

	@Override
	public double getZCoord() {
		return zCoord;
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		super.writeToByteBuff(buf);
		buf.writeInt(progressStage);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		super.readFromByteBuff(buf);
		progressStage = buf.readInt();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		progressStage = tag.getInteger("progressStage");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("progressStage", progressStage);
	}
}
