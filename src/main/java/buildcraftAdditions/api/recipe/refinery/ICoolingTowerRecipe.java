package buildcraftAdditions.api.recipe.refinery;

import net.minecraftforge.fluids.FluidStack;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface ICoolingTowerRecipe {

	FluidStack getInput();

	FluidStack getOutput();

	float getHeat();

}
