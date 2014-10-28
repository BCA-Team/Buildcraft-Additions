package buildcraftAdditions.items.Tools;

import net.minecraft.item.ItemStack;

import buildcraft.BuildCraftCore;
import buildcraft.api.recipes.CraftingResult;
import buildcraft.silicon.ItemRedstoneChipset;
import buildcraft.silicon.TileIntegrationTable;
import buildcraft.transport.recipes.IntegrationTableRecipe;

import buildcraftAdditions.variables.ItemsAndBlocks;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ToolCoreRecipe extends IntegrationTableRecipe {

	public ToolCoreRecipe(){
		setContents("toolCore", ItemsAndBlocks.toolCore, 30000, 100000, BuildCraftCore.goldGearItem, ItemRedstoneChipset.Chipset.DIAMOND.getStack().getItem());
	}

	@Override
	public CraftingResult<ItemStack> craft(TileIntegrationTable crafter, boolean preview, ItemStack inputA, ItemStack inputB) {
		return super.craft(crafter, preview, inputA, inputB);
	}

	@Override
	public boolean isValidInputA(ItemStack inputA) {
		return inputA != null && inputA.getItem() == BuildCraftCore.goldGearItem;
	}

	@Override
	public boolean isValidInputB(ItemStack inputB) {
		return inputB != null && inputB.getItem() == ItemRedstoneChipset.Chipset.DIAMOND.getStack().getItem();
	}

	@Override
	public String getId() {
		return "toolcore";
	}
}
