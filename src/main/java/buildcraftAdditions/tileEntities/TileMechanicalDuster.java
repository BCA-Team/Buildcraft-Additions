package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyReceiver;

import buildcraftAdditions.api.networking.MessageByteBuff;
import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileBaseDuster;
import buildcraftAdditions.utils.Utils;

import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileMechanicalDuster extends TileBaseDuster implements IEnergyReceiver {

	public final int maxEnergy = 2000;
	public int progressStage, oldProgressStage, energy;
	public EntityPlayer player;

	public TileMechanicalDuster() {
		super(Variables.Eureka.DustT2Key2);
	}

	@Override
	public boolean canUpdate() {
		return true;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (energy >= 10) {
			if (BCARecipeManager.duster.getRecipe(getStackInSlot(0)) != null) {
				progress++;
				energy -= 10;
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
			PacketHandler.instance.sendToAllAround(new MessageByteBuff(this), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, Variables.NETWORK_RANGE));
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
		if (!worldObj.isRemote)
			PacketHandler.instance.sendToAllAround(new MessageByteBuff(this), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, Variables.NETWORK_RANGE));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		buf = super.readFromByteBuff(buf);
		energy = buf.readInt();
		progressStage = buf.readInt();
		return buf;
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf = super.writeToByteBuff(buf);
		buf = buf.writeInt(energy);
		buf = buf.writeInt(progressStage);
		return buf;
	}
}

