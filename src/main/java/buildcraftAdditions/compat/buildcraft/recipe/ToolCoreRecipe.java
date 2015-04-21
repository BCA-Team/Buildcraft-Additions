package buildcraftAdditions.compat.buildcraft.recipe;

import net.minecraft.item.ItemStack;

import buildcraft.BuildCraftCore;
import buildcraft.api.recipes.CraftingResult;
import buildcraft.silicon.ItemRedstoneChipset;
import buildcraft.silicon.TileIntegrationTable;
import buildcraft.silicon.recipes.IntegrationTableRecipe;

import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ToolCoreRecipe extends IntegrationTableRecipe {

	public ToolCoreRecipe() {
		setContents(Variables.MOD.ID + ":toolCore", ItemsAndBlocks.toolCore, 30000, 100000);
	}

	@Override
	public CraftingResult<ItemStack> craft(TileIntegrationTable crafter, boolean preview, ItemStack inputA, ItemStack inputB) {
		CraftingResult<ItemStack> result = super.craft(crafter, preview, inputA, inputB);
		if (result != null)
			result.crafted = new ItemStack(ItemsAndBlocks.toolCore);
		return result;
	}

	@Override
	public boolean isValidInputA(ItemStack inputA) {
		return inputA != null && inputA.getItem() == BuildCraftCore.goldGearItem;
	}

	@Override
	public boolean isValidInputB(ItemStack inputB) {
		return inputB != null && inputB.getItem() == ItemRedstoneChipset.Chipset.DIAMOND.getStack().getItem();
	}
}
