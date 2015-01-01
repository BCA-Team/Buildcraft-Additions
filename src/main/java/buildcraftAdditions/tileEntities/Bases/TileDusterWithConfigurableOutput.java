package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.utils.EnumSideStatus;
import buildcraftAdditions.utils.IConfigurableOutput;
import buildcraftAdditions.utils.Utils;

import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileDusterWithConfigurableOutput extends TileBaseDuster implements IConfigurableOutput {
	protected EnumSideStatus configuration[];

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (tag.hasKey("configuration")) {
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
				configuration[direction.ordinal()] = Utils.intToStatus(tag.getInteger("configuration" + direction.ordinal()));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setBoolean("configuration", true);
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			tag.setInteger("configuration" + direction.ordinal(), Utils.statusToInt(configuration[direction.ordinal()]));
		}
	}

	public TileDusterWithConfigurableOutput(String key) {
		super(key);
		configuration = new EnumSideStatus[6];
		for (int teller = 0; teller < 6; teller++) {
			configuration[teller] = EnumSideStatus.BOTH;
		}
	}

	@Override
	public EnumSideStatus getStatus(ForgeDirection side) {
		return configuration[side.ordinal()];
	}

	@Override
	public void changeStatus(ForgeDirection side) {
		EnumSideStatus status = configuration[side.ordinal()];
		if (status == EnumSideStatus.INPUT)
			status = EnumSideStatus.OUTPUT;
		else if (status == EnumSideStatus.OUTPUT)
			status = EnumSideStatus.BOTH;
		else if (status == EnumSideStatus.BOTH)
			status = EnumSideStatus.DISSABLED;
		else if (status == EnumSideStatus.DISSABLED)
			status = EnumSideStatus.INPUT;
		configuration[side.ordinal()] = status;
	}

	@Override
	public void overrideConfiguration(EnumSideStatus[] newConfiguration) {
		configuration = newConfiguration;
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
			buf.writeInt(Utils.statusToInt(configuration[direction.ordinal()]));
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
			configuration[direction.ordinal()] = Utils.intToStatus(buf.readInt());
		return buf;
	}
}
