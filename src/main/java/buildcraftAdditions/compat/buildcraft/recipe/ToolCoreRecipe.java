package buildcraftAdditions.compat.buildcraft.recipe;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;

import buildcraft.api.recipes.IIntegrationRecipe;
import buildcraft.silicon.ItemRedstoneChipset;

import buildcraftAdditions.compat.buildcraft.BCItems;
import buildcraftAdditions.reference.ItemLoader;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ToolCoreRecipe implements IIntegrationRecipe {
	@Override
	public int getEnergyCost() {
		return 10000;
	}

	@Override
	public List<ItemStack> getExampleInput() {
		return Arrays.asList(new ItemStack(BCItems.GOLD_GEAR));
	}

	@Override
	public List<List<ItemStack>> getExampleExpansions() {
		return Arrays.asList(Arrays.asList(ItemRedstoneChipset.Chipset.DIAMOND.getStack()));
	}

	@Override
	public List<ItemStack> getExampleOutput() {
		return Arrays.asList(new ItemStack(ItemLoader.toolCore));
	}

	@Override
	public boolean isValidInput(ItemStack input) {
		return input != null && input.getItem() == BCItems.GOLD_GEAR;
	}

	@Override
	public boolean isValidExpansion(ItemStack input, ItemStack expansion) {
		return expansion != null && expansion.getItem() == ItemRedstoneChipset.Chipset.DIAMOND.getStack().getItem();
	}

	@Override
	public ItemStack craft(ItemStack input, List<ItemStack> expansions, boolean preview) {
		if (!preview)
			expansions.get(0).stackSize--;
		return new ItemStack(ItemLoader.toolCore);
	}

	@Override
	public int getMaximumExpansionCount(ItemStack input) {
		return 1;
	}
}
