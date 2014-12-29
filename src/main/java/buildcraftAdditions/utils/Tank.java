package buildcraftAdditions.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Tank extends FluidTank {
	protected String name;

	public Tank(int capacity, TileEntity entity, String name) {
		super(capacity);
		this.tile = entity;
		this.name = name;
	}

	public Tank(int capacity) {
		super(capacity);
	}

	public boolean isEmpty(){
		return fluid == null || fluid.amount == 0;
	}

	public boolean isFull() {
		return fluid != null && fluid.amount == capacity;
	}

	public Fluid getFluidType() {
		if (fluid == null)
			return null;
		return fluid.getFluid();
	}

	public void setFluid(FluidStack stack) {
		fluid = stack;
	}

	public void saveToNBT(NBTTagCompound tag) {
		if (getFluid() == null) {
			tag.setInteger("fluidID" + name, -1);
		} else {
			tag.setInteger("fluidID" + name, getFluid().fluidID);
			tag.setInteger("fluidAmount" + name, getFluid().amount);
		}
	}

	public Tank readFromNBT(NBTTagCompound tag) {
		FluidStack stack;
		if (tag.getInteger("fluidID" + name) == -1)
			stack = null;
		else
			stack = new FluidStack(tag.getInteger("fluidID" + name), tag.getInteger("fluidAmount" + name));
		setFluid(stack);
		return this;
	}

	@Override
	public String toString() {
		String fluid = "nothing";
		if (this.fluid != null && this.getFluidAmount() > 0)
			fluid = getFluid().amount + " of " + getFluid().getLocalizedName();
		return name + ": " + fluid;
	}

	public int getMaxCapacity() {
		return capacity;
	}
}
