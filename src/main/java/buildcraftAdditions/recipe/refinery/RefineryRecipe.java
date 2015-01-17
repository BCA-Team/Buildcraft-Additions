package buildcraftAdditions.recipe.refinery;

import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.api.recipe.refinery.IRefineryRecipe;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RefineryRecipe implements IRefineryRecipe {

	private final FluidStack input, output;
	private final int requiredHeat;

	public RefineryRecipe(FluidStack input, FluidStack output, int requiredHeat) {
		this.input = input.copy();
		this.output = output.copy();
		this.requiredHeat = requiredHeat;
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
	public int getRequiredHeat() {
		return requiredHeat;
	}
}
