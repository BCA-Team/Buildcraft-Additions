package buildcraftAdditions.recipe.refinery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.api.recipe.refinery.IRefineryRecipe;
import buildcraftAdditions.api.recipe.refinery.IRefineryRecipeManager;
import buildcraftAdditions.core.Logger;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RefineryRecipeManager implements IRefineryRecipeManager {

	private final List<IRefineryRecipe> recipes = new ArrayList<IRefineryRecipe>();

	@Override
	public void addRecipe(FluidStack input, FluidStack output, int requiredHeat) {
		if (input == null || input.getFluid() == null || input.amount <= 0 || output == null || output.getFluid() == null || output.amount <= 0) {
			Logger.error("Tried to register an invalid refinery recipe! Skipping.");
			Logger.error("Was trying to add: Input: " + input + " Output: " + output + " Required heat: " + requiredHeat);
			return;
		}
		IRefineryRecipe recipe = getRecipe(input);
		if (recipe != null) {
			Logger.error("A refinery recipe with input  " + input + " is already registered! Skipping.");
			Logger.error("Was trying to add: Input: " + input + " Output: " + output + " Required heat: " + requiredHeat);
			Logger.error("Found: Input: " + input + " Output: " + recipe.getOutput() + " Required heat: " + recipe.getRequiredHeat());
			return;
		}
		recipes.add(new RefineryRecipe(input, output, requiredHeat));
	}

	@Override
	public void removeRecipe(FluidStack input) {
		if (input != null) {
			IRefineryRecipe recipe = null;
			for (Iterator<IRefineryRecipe> iterator = recipes.iterator(); iterator.hasNext(); recipe = iterator.next()) {
				if (recipe != null && input.isFluidEqual(recipe.getInput())) {
					iterator.remove();
					return;
				}
			}
		}
	}

	@Override
	public IRefineryRecipe getRecipe(FluidStack input) {
		if (input != null) {
			for (IRefineryRecipe recipe : recipes) {
				if (recipe != null && input.isFluidEqual(recipe.getInput())) {
					return recipe;
				}
			}
		}
		return null;
	}

	@Override
	public List<? extends IRefineryRecipe> getRecipes() {
		return Collections.unmodifiableList(recipes);
	}
}
