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

    List<IDusterRecipe> recipes = new ArrayList<IDusterRecipe>();

    @Override
    public void addRecipe(ItemStack input, ItemStack output) {
        if (input == null || input.getItem() == null || output == null || output.getItem() == null || output.stackSize <= 0) {
            Logger.error("Tried to register invalid duster recipe! Skipping.");
            return;
        }
        IDusterRecipe recipe = getRecipe(input);
        if (recipe != null) {
            Logger.error("Duster recipe with input  " + input + " is already registered! Skipping.");
            Logger.error("Was trying to add: Input: " + input + " Output: " + output);
            Logger.error("Found: Input: " + recipe.getInput() + " Output: " + recipe.getOutput());
            return;
        }
        recipes.add(new DusterRecipe(input, output));
    }

    @Override
    public void addRecipe(String oreInput, ItemStack output) {
        if (StringUtils.isNullOrEmpty(oreInput) || output == null || output.getItem() == null || output.stackSize <= 0) {
            Logger.error("Tried to register invalid duster recipe! Skipping.");
            return;
        }
        for (ItemStack input : OreDictionary.getOres(oreInput)) {
            addRecipe(input, output);
        }
    }

    @Override
    public void removeRecipe(ItemStack input) {
        if (input != null) {
            IDusterRecipe recipe = null;
            for (Iterator<IDusterRecipe> iterator = recipes.iterator(); iterator.hasNext(); recipe = iterator.next()) {
                if (recipe != null && recipe.getInput().getItem().equals(input.getItem())) {
                    if (recipe.getInput().getItemDamage() == input.getItemDamage() || recipe.getInput().getItemDamage() == OreDictionary.WILDCARD_VALUE || input.getItemDamage() == OreDictionary.WILDCARD_VALUE || recipe.getInput().getItem().isDamageable()) {
                        iterator.remove();
                        return;
                    }
                }
            }
        }
    }

    @Override
    public IDusterRecipe getRecipe(ItemStack input) {
        if (input != null) {
            for (IDusterRecipe recipe : recipes) {
                if (recipe != null && recipe.getInput().getItem().equals(input.getItem())) {
                    if (recipe.getInput().getItemDamage() == input.getItemDamage() || recipe.getInput().getItemDamage() == OreDictionary.WILDCARD_VALUE || input.getItemDamage() == OreDictionary.WILDCARD_VALUE || recipe.getInput().getItem().isDamageable()) {
                        return recipe;
                    }
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
