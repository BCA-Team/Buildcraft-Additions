package buildcraftAdditions.recipe.duster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.api.recipe.duster.IDusterRecipe;
import buildcraftAdditions.api.recipe.duster.IDusterRecipeManager;
import buildcraftAdditions.core.Logger;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DusterRecipeManager implements IDusterRecipeManager {

	private final List<IDusterRecipe> recipes = new ArrayList<IDusterRecipe>();

	@Override
	public void addRecipe(ItemStack input, ItemStack output) {
		if (input == null || input.getItem() == null || output == null || output.getItem() == null || output.stackSize <= 0) {
			Logger.error("Tried to register an invalid duster recipe! Skipping.");
			Logger.error("Was trying to add: Input: " + input + " Output: " + output);
			return;
		}
		IDusterRecipe recipe = getRecipe(input);
		if (recipe != null) {
			Logger.error("A duster recipe with input  " + input + " is already registered! Skipping.");
			Logger.error("Was trying to add: Input: " + input + " Output: " + output);
			Logger.error("Found: Input: " + input + " Output: " + recipe.getOutput(input));
			return;
		}
		recipes.add(new DusterRecipe(input, output));
	}

	@Override
	public void addRecipe(String oreInput, ItemStack output) {
		if (StringUtils.isNullOrEmpty(oreInput) || output == null || output.getItem() == null || output.stackSize <= 0) {
			Logger.error("Tried to register invalid duster recipe! Skipping.");
			Logger.error("Was trying to add: Input: " + oreInput + " Output: " + output);
			return;
		}
		for (ItemStack input : OreDictionary.getOres(oreInput)) {
			recipes.add(new DusterRecipeOreDict(oreInput, output));
		}
	}

	@Override
	public void addRecipe(IDusterRecipe recipe) {
		if (recipe == null || recipe.getInputs() == null || recipe.getInputs().size() <= 0) {
			Logger.error("Tried to register invalid duster recipe! Skipping.");
			Logger.error("Was trying to add: " + recipe);
		}
		recipes.add(recipe);
	}

	@Override
	public void removeRecipe(ItemStack input) {
		if (input != null) {
			IDusterRecipe recipe = null;
			for (Iterator<IDusterRecipe> iterator = recipes.iterator(); iterator.hasNext(); recipe = iterator.next()) {
				if (recipe != null && recipe.getOutput(input) != null) {
					iterator.remove();
					return;
				}
			}
		}
	}

	@Override
	public IDusterRecipe getRecipe(ItemStack input) {
		if (input != null) {
			for (IDusterRecipe recipe : recipes) {
				if (recipe != null && recipe.getOutput(input) != null) {
					return recipe;
				}
			}
		}
		return null;
	}

	@Override
	public List<? extends IDusterRecipe> getRecipes() {
		return Collections.unmodifiableList(recipes);
	}

}
