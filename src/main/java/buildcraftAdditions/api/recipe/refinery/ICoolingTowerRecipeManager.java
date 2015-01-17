package buildcraftAdditions.api.recipe.refinery;

import java.util.List;

import net.minecraftforge.fluids.FluidStack;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface ICoolingTowerRecipeManager {

	void addRecipe(FluidStack input, FluidStack output, float heat);

	void removeRecipe(FluidStack input);

	ICoolingTowerRecipe getRecipe(FluidStack input);

	List<? extends ICoolingTowerRecipe> getRecipes();

}
