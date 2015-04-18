package buildcraftAdditions.utils.fluids;

import io.netty.buffer.ByteBuf;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.ByteBufUtils;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import buildcraftAdditions.api.networking.ISyncObject;
import buildcraftAdditions.tileEntities.Bases.TileBase;

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
		this(capacity, null, "");
	}

	public boolean isEmpty() {
		return fluid == null || fluid.amount <= 0;
	}

	public boolean isFull() {
		return fluid != null && fluid.amount >= capacity;
	}

	public int getFreeSpace() {
		return getCapacity() - getFluidAmount();
	}

	public Fluid getFluidType() {
		if (fluid == null)
			return null;
		return fluid.getFluid();
	}

	public FluidStack drain(FluidStack resource, boolean doDrain) {
		FluidStack drained = null;
		if (fluid != null && fluid.isFluidEqual(resource)) {
			if (tile instanceof TileBase)
				((TileBase) tile).sync();
			drained = drain(resource.amount, doDrain);
		}
		return drained;
	}

	@Override
	public String toString() {
		return name + ": " + getFluidAmount() + " / " + capacity + " mB" + (fluid != null ? (" of " + fluid.getLocalizedName()) : "");
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		buf.writeInt(fluid != null ? fluid.getFluidID() : -1);
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
			setFluid(new FluidStack(FluidRegistry.getFluid(id), amount, tag));
	}
}
