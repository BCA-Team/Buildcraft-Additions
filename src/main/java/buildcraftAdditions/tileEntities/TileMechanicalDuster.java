package buildcraftAdditions.tileEntities;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyReceiver;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileBaseDuster;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileMechanicalDuster extends TileBaseDuster implements IEnergyReceiver {

	public final int capacity = ConfigurationHandler.capacityMechanicalDuster, maxTransfer = ConfigurationHandler.maxTransferMechanicalDuster;
	public int progressStage, oldProgressStage, energy;
	public EntityPlayer player;

	public TileMechanicalDuster() {
		super(Variables.Eureka.DustT2Key2);
		particles = 5;
	}

	@Override
	public boolean canUpdate() {
		return true;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (energy >= ConfigurationHandler.energyUseMechanicalDuster) {
			if (BCARecipeManager.duster.getRecipe(getStackInSlot(0)) != null) {
				progress++;
				energy -= ConfigurationHandler.energyUseMechanicalDuster;
				spawnDustingParticles();
				oldProgressStage = progressStage;
				if (progress > 25)
					progressStage = 1;
				if (progress > 50)
					progressStage = 2;
				if (progress > 75)
					progressStage = 3;
				if (progress >= 100) {
					dust();
					if (player != null)
						makeEurekaProgress(player);
					progress = 0;
					progressStage = 0;
				}
			} else {
				progress = 0;
				progressStage = 0;
			}
		}
		if (oldProgressStage != progressStage) {
			sync();
			oldProgressStage = progressStage;
		}
	}

	@Override
	public double getProgress() {
		return progress / 100D;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("energy", energy);
		tag.setInteger("progressStage", progressStage);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		energy = tag.getInteger("energy");
		progressStage = tag.getInteger("progressStage");
	}

	@Override
	public void dust() {
		Utils.dropItemstack(worldObj, xCoord, yCoord, zCoord, BCARecipeManager.duster.getRecipe(getStackInSlot(0)).getOutput(getStackInSlot(0)));
		setInventorySlotContents(0, null);
		sync();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		int energyReceived = Math.min(capacity - energy, Math.min(maxTransfer, maxReceive));
		if (!simulate)
			energy += energyReceived;
		return energyReceived;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energy;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return capacity;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		super.readFromByteBuff(buf);
		energy = buf.readInt();
		progressStage = buf.readInt();
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		super.writeToByteBuff(buf);
		buf.writeInt(energy);
		buf.writeInt(progressStage);
	}
}

