package buildcraftAdditions.utils;

import io.netty.buffer.ByteBuf;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.ByteBufUtils;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import buildcraftAdditions.networking.ISyncObject;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Tank extends FluidTank implements ISyncObject {

	protected String name;

	public Tank(int capacity, TileEntity entity, String name) {
		super(capacity);
		this.tile = entity;
		this.name = name;
	}

	public Tank(int capacity) {
		this(capacity, null, "Tank");
	}

	public boolean isEmpty() {
		return fluid == null || fluid.amount <= 0;
	}

	public boolean isFull() {
		return fluid != null && fluid.amount >= capacity;
	}

	public Fluid getFluidType() {
		if (fluid == null)
			return null;
		return fluid.getFluid();
	}

	@Override
	public String toString() {
		return name + ": " + getFluidAmount() + " / " + capacity + " mB" + (fluid != null ? (" of " + fluid.getLocalizedName()) : "");
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf.writeInt(fluid != null ? fluid.fluidID : -1);
		buf.writeInt(getFluidAmount());
		ByteBufUtils.writeTag(buf, fluid != null ? fluid.tag : null);
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		int id = buf.readInt();
		int amount = buf.readInt();
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		if (id < 0)
			setFluid(null);
		else
			setFluid(new FluidStack(id, amount, tag));
		return buf;
	}
}
