package buildcraftAdditions.utils.fluids;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.api.recipe.BCARecipeManager;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RefineryRecipeTank extends RestrictedTank {

	public RefineryRecipeTank(String name, int capacity, TileEntity tile) {
		super(name, capacity, tile, new IFluidAcceptor() {
			@Override
			public boolean accepts(FluidStack fluidStack) {
				return BCARecipeManager.refinery.getRecipe(fluidStack) != null;
			}

			@Override
			public String getDescription() {
				return "Only accepts fluids that are part of a refinery recipe";
			}
		});
	}
}
