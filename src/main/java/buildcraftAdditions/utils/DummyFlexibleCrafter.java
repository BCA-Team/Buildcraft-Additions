package buildcraftAdditions.utils;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import buildcraft.api.recipes.IFlexibleCrafter;

import buildcraftAdditions.utils.fluids.Tank;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DummyFlexibleCrafter implements IFlexibleCrafter {
	public final Tank input = new Tank(1000);
	public final Tank output = new Tank(1000);

	@Override
	public int getCraftingItemStackSize() {
		return 0;
	}

	@Override
	public ItemStack getCraftingItemStack(int slotid) {
		return null;
	}

	@Override
	public ItemStack decrCraftingItemStack(int slotid, int val) {
		return null;
	}

	@Override
	public FluidStack getCraftingFluidStack(int tankid) {
		return input.getFluid();
	}

	@Override
	public FluidStack decrCraftingFluidStack(int tankid, int amount) {
		FluidStack fluid;
		if (amount > input.getFluidAmount()) {
			fluid = input.getFluid();
			input.setFluid(null);
		} else {
			fluid = input.getFluid().copy();
			fluid.amount = amount;
			input.getFluid().amount -= amount;
		}
		return fluid;
	}

	@Override
	public int getCraftingFluidStackSize() {
		return 1;
	}
}
