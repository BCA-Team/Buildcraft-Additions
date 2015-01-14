package buildcraftAdditions.api.recipe.duster;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface IDusterRecipeManager {

    void addRecipe(ItemStack input, ItemStack output);

    void addRecipe(String oreInput, ItemStack output);

    void removeRecipe(ItemStack input);

    IDusterRecipe getRecipe(ItemStack input);

    List<? extends IDusterRecipe> getRecipes();

}
