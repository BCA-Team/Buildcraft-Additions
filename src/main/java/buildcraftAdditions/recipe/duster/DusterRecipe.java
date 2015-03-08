package buildcraftAdditions.recipe.duster;

import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import buildcraftAdditions.api.recipe.duster.IDusterRecipe;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DusterRecipe implements IDusterRecipe {

	private final ItemStack input, output;

	public DusterRecipe(ItemStack input, ItemStack output) {
		this.input = input.copy();
		this.input.stackSize = 1;
		this.output = output;
	}

	@Override
	public List<ItemStack> getInputs() {
		return Collections.singletonList(input.copy());
	}

	@Override
	public ItemStack getOutput(ItemStack input) {
		return Utils.areItemStacksEqualRecipe(this.input, input) ? output.copy() : null;
	}
}
