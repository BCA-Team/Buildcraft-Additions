package buildcraftAdditions.utils;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.networking.ISyncObject;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class SideConfiguration implements ISyncObject {

	private final EnumSideStatus[] configurations = new EnumSideStatus[ForgeDirection.VALID_DIRECTIONS.length];
	private final EnumPriority[] priorities = new EnumPriority[ForgeDirection.VALID_DIRECTIONS.length];

	public SideConfiguration() {
		Arrays.fill(configurations, EnumSideStatus.INPUT);
		Arrays.fill(priorities, EnumPriority.NORMAL);
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			buf.writeInt(configurations[direction.ordinal()].ordinal());
			buf.writeInt(priorities[direction.ordinal()].ordinal());
		}
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			configurations[direction.ordinal()] = EnumSideStatus.values()[buf.readInt()];
			priorities[direction.ordinal()] = EnumPriority.values()[buf.readInt()];
		}
		return buf;
	}

	public SideConfiguration readFromNBT(NBTTagCompound tag) {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			if (tag.hasKey("configuration" + direction.ordinal(), Constants.NBT.TAG_INT))
				configurations[direction.ordinal()] = EnumSideStatus.values()[tag.getInteger("configuration" + direction.ordinal())];
			if (tag.hasKey("priority" + direction.ordinal(), Constants.NBT.TAG_INT))
				priorities[direction.ordinal()] = EnumPriority.values()[tag.getInteger("priority" + direction.ordinal())];
		}
		return this;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			tag.setInteger("configuration" + direction.ordinal(), configurations[direction.ordinal()].ordinal());
			tag.setInteger("priority" + direction.ordinal(), priorities[direction.ordinal()].ordinal());
		}
		return tag;
	}

	public void load(IConfigurableOutput configurableOutput) {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			configurations[direction.ordinal()] = configurableOutput.getStatus(direction);
			priorities[direction.ordinal()] = configurableOutput.getPriority(direction);
		}
	}

	public void load(SideConfiguration configuration) {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			configurations[direction.ordinal()] = configuration.getStatus(direction);
			priorities[direction.ordinal()] = configuration.getPriority(direction);
		}
	}

	public boolean canReceive(ForgeDirection side) {
		return configurations[side.ordinal()].canReceive();
	}

	public boolean canSend(ForgeDirection side) {
		return configurations[side.ordinal()].canSend();
	}

	public void changeStatus(ForgeDirection side) {
		configurations[side.ordinal()] = configurations[side.ordinal()].getNextStatus();
	}

	public void changePriority(ForgeDirection side) {
		priorities[side.ordinal()] = priorities[side.ordinal()].getNextPriority();
	}

	public EnumSideStatus getStatus(ForgeDirection side) {
		return configurations[side.ordinal()];
	}

	public EnumPriority getPriority(ForgeDirection side) {
		return priorities[side.ordinal()];
	}

	public void setStatus(EnumSideStatus status, ForgeDirection side) {
		configurations[side.ordinal()] = status;
	}

	public void setPriority(EnumPriority priority, ForgeDirection side) {
		priorities[side.ordinal()] = priority;
	}

	public void setAllStatus(EnumSideStatus status) {
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
			configurations[side.ordinal()] = status;
	}

	public void setAllPriority(EnumPriority priority) {
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
			priorities[side.ordinal()] = priority;
	}

	public void invalidate() {
		Arrays.fill(configurations, EnumSideStatus.INPUT);
		Arrays.fill(priorities, EnumPriority.NORMAL);
	}

	@Override
	public String toString() {
		String string = "SideConfiguration[ ";
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			string += direction.name() + ":{ ";
			string += getStatus(direction) + ", ";
			string += getPriority(direction) + " }, ";
		}
		return string + " ]";
	}
}
