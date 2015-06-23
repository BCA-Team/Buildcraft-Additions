package buildcraftAdditions.tileEntities.Bases;

import io.netty.buffer.ByteBuf;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileCoilBase extends TileBase {
	public boolean shouldHeat, burning;
	public int increment;
	public int burnTime, fullBurnTime;

	public TileCoilBase(int identifier) {
		super(identifier);
	}

	public void startHeating() {
		shouldHeat = true;
		sync();
	}

	public void stopHeating() {
		shouldHeat = false;
		//force a sync so client doesn't burn additional fuel and causes sync issues
		sync();
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
	public void writeToByteBuff(ByteBuf buf) {
		buf.writeBoolean(shouldHeat);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		shouldHeat = buf.readBoolean();
	}

}
