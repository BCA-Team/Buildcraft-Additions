package buildcraftAdditions.api;

import java.util.ArrayList;

import net.minecraftforge.fluids.Fluid;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RecipeMananger {
	private static ArrayList<CoolingTowerRecipe> coolingTowerRecipes = new ArrayList<CoolingTowerRecipe>();
	private static ArrayList<RefineryRecipe> refineryRecipes = new ArrayList<RefineryRecipe>();

	public static boolean registerRecipe(CoolingTowerRecipe recipe) {
		if (coolingTowerRecipes.contains(recipe))
			return false;
		coolingTowerRecipes.add(recipe);
		return true;
	}

	public static boolean registerRecipe(RefineryRecipe recipe) {
		if (refineryRecipes.contains(recipe))
			return false;
		refineryRecipes.add(recipe);
		return true;
	}

	public static CoolingTowerRecipe getCoolingTowerRecipe(Fluid input) {
		for (CoolingTowerRecipe recipe : coolingTowerRecipes) {
			if (recipe.getInput() == input)
				return recipe;
		}
		return null;
	}

	public static RefineryRecipe getRefineryRecipe(Fluid input) {
		for (RefineryRecipe recipe : refineryRecipes) {
			if (recipe.getInput() == input)
				return recipe;
		}
		return null;
	}
}
