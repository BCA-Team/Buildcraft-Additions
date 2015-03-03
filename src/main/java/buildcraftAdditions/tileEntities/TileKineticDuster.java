package buildcraftAdditions.tileEntities;

import io.netty.buffer.ByteBuf;

import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.network.NetworkRegistry;

import buildcraft.api.power.ILaserTarget;

import buildcraftAdditions.api.networking.MessageByteBuff;
import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileDusterWithConfigurableOutput;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKineticDuster extends TileDusterWithConfigurableOutput implements ILaserTarget {

	public int progressStage, oldProgressStage;

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
		if (progress > 20000) {
			progress = 0;
			progressStage = 0;
			dust();
		} else {
			if (progress > 15000)
				progressStage = 3;
			else if (progress > 10000)
				progressStage = 2;
			else if (progress > 5000)
				progressStage = 1;
			else
				progressStage = 0;
		}
		if (progressStage != oldProgressStage) {
			PacketHandler.instance.sendToAllAround(new MessageByteBuff(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, Variables.NETWORK_RANGE));
			oldProgressStage = progressStage;
		}
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
		return yCoord;
	}

	@Override
	public double getZCoord() {
		return zCoord;
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf = super.writeToByteBuff(buf);
		buf = buf.writeInt(progressStage);
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		buf = super.readFromByteBuff(buf);
		progressStage = buf.readInt();
		return buf;
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
