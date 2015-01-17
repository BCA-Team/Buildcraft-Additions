package buildcraftAdditions.recipe.refinery;

import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.api.recipe.refinery.ICoolingTowerRecipe;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class CoolingTowerRecipe implements ICoolingTowerRecipe {

	private final FluidStack input, output;
	private final float heat;

	public CoolingTowerRecipe(FluidStack input, FluidStack output, float heat) {
		this.input = input.copy();
		this.output = output.copy();
		this.heat = heat;
	}

	@Override
	public FluidStack getInput() {
		return input.copy();
	}

	@Override
	public FluidStack getOutput() {
		return output.copy();
	}

	@Override
	public float getHeat() {
		return heat;
	}
}
