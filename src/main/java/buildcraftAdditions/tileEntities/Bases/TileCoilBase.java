package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.network.NetworkRegistry;

import buildcraftAdditions.networking.MessageByteBuff;
import buildcraftAdditions.networking.PacketHandeler;

import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileCoilBase extends TileBase {
	public boolean shouldHeat, burning;
	public int increment;
	public int burnTime, fullBurnTime;

	public void startHeating() {
		shouldHeat = true;
		PacketHandeler.instance.sendToAllAround(new MessageByteBuff(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 10));
	}

	public void stopHeating() {
		shouldHeat = false;
		PacketHandeler.instance.sendToAllAround(new MessageByteBuff(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 10));
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		shouldHeat = nbt.getBoolean("shouldHeat");
		burning = nbt.getBoolean("burning");
		increment = nbt.getInteger("increment");
		burnTime = nbt.getInteger("burntime");
		fullBurnTime = nbt.getInteger("fullBurnTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("shouldHeat", shouldHeat);
		nbt.setBoolean("burning", burning);
		nbt.setInteger("increment", increment);
		nbt.setInteger("burntime", burnTime);
		nbt.setInteger("fullBurnTime", fullBurnTime);
	}

	public int getIncrement() {
		return increment;
	}

	public boolean isBurning() {
		return burning;
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf.writeBoolean(shouldHeat);
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		shouldHeat = buf.readBoolean();
		return buf;
	}

}
