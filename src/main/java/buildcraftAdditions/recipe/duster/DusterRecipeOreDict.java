package buildcraftAdditions.recipe.duster;

import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.api.recipe.duster.IDusterRecipe;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DusterRecipeOreDict implements IDusterRecipe {
	private final String input;
	private final ItemStack output;

	public DusterRecipeOreDict(String input, ItemStack output) {
		this.input = new String(input.toCharArray());
		this.output = output.copy();
	}

	@Override
	public List<ItemStack> getInputs() {
		return OreDictionary.getOres(input);
	}

	@Override
	public ItemStack getOutput(ItemStack input) {
		for (ItemStack stack : OreDictionary.getOres(this.input)) {
			if (Utils.areItemStacksEqualItem(stack, input))
				return output.copy();
		}
		return null;
	}
}
