package buildcraftAdditions.utils.fluids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.ByteBufUtils;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import buildcraftAdditions.api.networking.ISyncObject;

import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Tank extends FluidTank implements ISyncObject {

	protected final String name;

	public Tank(int capacity, TileEntity tile, String name) {
		super(capacity);
		this.tile = tile;
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

	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (fluid != null && fluid.isFluidEqual(resource))
			return drain(resource.amount, doDrain);
		return null;
	}

	@Override
	public String toString() {
		return name + ": " + getFluidAmount() + " / " + capacity + " mB" + (fluid != null ? (" of " + fluid.getLocalizedName()) : "");
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		buf.writeInt(fluid != null ? fluid.fluidID : -1);
		buf.writeInt(getFluidAmount());
		ByteBufUtils.writeTag(buf, fluid != null ? fluid.tag : null);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		int id = buf.readInt();
		int amount = buf.readInt();
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		if (id < 0 || amount <= 0)
			setFluid(null);
		else
			setFluid(new FluidStack(id, amount, tag));
	}
}
