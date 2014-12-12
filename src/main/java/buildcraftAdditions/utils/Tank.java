package buildcraftAdditions.utils;

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

	public Tank(int capacity, TileEntity entity) {
		super(capacity);
		this.tile = entity;
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
}
