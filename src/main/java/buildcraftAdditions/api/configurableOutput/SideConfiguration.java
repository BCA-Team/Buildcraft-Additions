package buildcraftAdditions.api.configurableOutput;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;

import io.netty.buffer.ByteBuf;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.api.nbt.INBTSaveable;
import buildcraftAdditions.api.networking.ISyncObject;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class SideConfiguration implements ISyncObject, INBTSaveable {

	private static final EnumSet<ForgeDirection> VALID_DIRECTIONS = EnumSet.noneOf(ForgeDirection.class);
	private final EnumMap<ForgeDirection, EnumSideStatus> configurations = new EnumMap<ForgeDirection, EnumSideStatus>(ForgeDirection.class);
	private final EnumMap<ForgeDirection, EnumPriority> priorities = new EnumMap<ForgeDirection, EnumPriority>(ForgeDirection.class);

	public SideConfiguration() {
		this(EnumSideStatus.BOTH, EnumPriority.NORMAL);
	}

	public SideConfiguration(EnumSideStatus status) {
		this(status, EnumPriority.NORMAL);
	}

	public SideConfiguration(EnumPriority priority) {
		this(EnumSideStatus.BOTH, priority);
	}

	public SideConfiguration(EnumSideStatus status, EnumPriority priority) {
		setAllStatus(status);
		setAllPriority(priority);
	}

	private static boolean isValidDirection(ForgeDirection direction) {
		return direction != null && VALID_DIRECTIONS.contains(direction);
	}

	static {
		Collections.addAll(VALID_DIRECTIONS, ForgeDirection.VALID_DIRECTIONS);
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		for (ForgeDirection direction : VALID_DIRECTIONS) {
			buf.writeByte(getStatus(direction).ordinal());
			buf.writeByte(getPriority(direction).ordinal());
		}
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		for (ForgeDirection direction : VALID_DIRECTIONS) {
			setStatus(EnumSideStatus.values()[buf.readByte()], direction);
			setPriority(EnumPriority.values()[buf.readByte()], direction);
		}
	}

	//TODO: Remove Integer reading.
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		for (ForgeDirection direction : VALID_DIRECTIONS) {
			if (tag.hasKey("configuration" + direction.ordinal(), Constants.NBT.TAG_INT))
				setStatus(EnumSideStatus.values()[tag.getInteger("configuration" + direction.ordinal())], direction);
			if (tag.hasKey("configuration" + direction.ordinal(), Constants.NBT.TAG_BYTE))
				setStatus(EnumSideStatus.values()[tag.getByte("configuration" + direction.ordinal())], direction);
			if (tag.hasKey("priority" + direction.ordinal(), Constants.NBT.TAG_INT))
				setPriority(EnumPriority.values()[tag.getInteger("priority" + direction.ordinal())], direction);
			if (tag.hasKey("priority" + direction.ordinal(), Constants.NBT.TAG_BYTE))
				setPriority(EnumPriority.values()[tag.getByte("priority" + direction.ordinal())], direction);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		for (ForgeDirection direction : VALID_DIRECTIONS) {
			tag.setByte("configuration" + direction.ordinal(), (byte) (0xFF & getStatus(direction).ordinal()));
			tag.setByte("priority" + direction.ordinal(), (byte) (0xFF & getPriority(direction).ordinal()));
		}
	}

	public void load(IConfigurableOutput configurableOutput) {
		configurations.putAll(configurableOutput.getSideConfiguration().configurations);
		priorities.putAll(configurableOutput.getSideConfiguration().priorities);
	}

	public void load(SideConfiguration configuration) {
		configurations.putAll(configuration.configurations);
		priorities.putAll(configuration.priorities);
	}

	public boolean canReceive(ForgeDirection side) {
		return getStatus(side).canReceive();
	}

	public boolean canSend(ForgeDirection side) {
		return getStatus(side).canSend();
	}

	public void changeStatus(ForgeDirection side) {
		setStatus(getNextStatus(side), side);
	}

	public void changePriority(ForgeDirection side) {
		setPriority(getNextPriority(side), side);
	}

	public EnumSideStatus getStatus(ForgeDirection side) {
		return isValidDirection(side) ? configurations.get(side) : EnumSideStatus.DISABLED;
	}

	public EnumSideStatus getNextStatus(ForgeDirection side) {
		return getStatus(side).getNextStatus();
	}

	public EnumPriority getPriority(ForgeDirection side) {
		return isValidDirection(side) ? priorities.get(side) : EnumPriority.NORMAL;
	}

	public EnumPriority getNextPriority(ForgeDirection side) {
		return getPriority(side).getNextPriority();
	}

	public void setStatus(EnumSideStatus status, ForgeDirection side) {
		if (isValidDirection(side))
			configurations.put(side, status);
	}

	public void setPriority(EnumPriority priority, ForgeDirection side) {
		if (isValidDirection(side))
			priorities.put(side, priority);
	}

	public void setAllStatus(EnumSideStatus status) {
		for (ForgeDirection side : VALID_DIRECTIONS)
			setStatus(status, side);
	}

	public void setAllPriority(EnumPriority priority) {
		for (ForgeDirection side : VALID_DIRECTIONS)
			setPriority(priority, side);
	}

	public void invalidate() {
		setAllStatus(EnumSideStatus.BOTH);
		setAllPriority(EnumPriority.NORMAL);
	}

	@Override
	public String toString() {
		String string = "SideConfiguration[ ";
		for (ForgeDirection direction : VALID_DIRECTIONS) {
			string += direction.name() + ":{ ";
			string += getStatus(direction) + ", ";
			string += getPriority(direction) + " }, ";
		}
		return string + " ]";
	}
}
