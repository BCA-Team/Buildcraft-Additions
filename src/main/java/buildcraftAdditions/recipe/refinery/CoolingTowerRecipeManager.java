package buildcraftAdditions.recipe.refinery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.api.recipe.refinery.ICoolingTowerRecipe;
import buildcraftAdditions.api.recipe.refinery.ICoolingTowerRecipeManager;
import buildcraftAdditions.core.Logger;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class CoolingTowerRecipeManager implements ICoolingTowerRecipeManager {

	private final List<ICoolingTowerRecipe> recipes = new ArrayList<ICoolingTowerRecipe>();

	@Override
	public void addRecipe(FluidStack input, FluidStack output, float heat) {
		if (input == null || input.getFluid() == null || input.amount <= 0 || output == null || output.getFluid() == null || output.amount <= 0) {
			Logger.error("Tried to register an invalid cooling tower recipe! Skipping.");
			Logger.error("Was trying to add: Input: " + input + " Output: " + output + " Heat: " + heat);
			return;
		}
		ICoolingTowerRecipe recipe = getRecipe(input);
		if (recipe != null) {
			Logger.error("A cooling tower recipe with input  " + input + " is already registered! Skipping.");
			Logger.error("Was trying to add: Input: " + input + " Output: " + output + " Heat: " + heat);
			Logger.error("Found: Input: " + input + " Output: " + recipe.getOutput() + " Heat: " + recipe.getHeat());
			return;
		}
		recipes.add(new CoolingTowerRecipe(input, output, heat));
	}

	@Override
	public void removeRecipe(FluidStack input) {
		if (input != null) {
			ICoolingTowerRecipe recipe = null;
			for (Iterator<ICoolingTowerRecipe> iterator = recipes.iterator(); iterator.hasNext(); recipe = iterator.next()) {
				if (recipe != null && input.isFluidEqual(recipe.getInput())) {
					iterator.remove();
					return;
				}
			}
		}
	}

	@Override
	public ICoolingTowerRecipe getRecipe(FluidStack input) {
		if (input != null) {
			for (ICoolingTowerRecipe recipe : recipes) {
				if (recipe != null && input.isFluidEqual(recipe.getInput())) {
					return recipe;
				}
			}
		}
		return null;
	}

	@Override
	public List<? extends ICoolingTowerRecipe> getRecipes() {
		return Collections.unmodifiableList(recipes);
	}
}
