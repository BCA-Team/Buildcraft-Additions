package buildcraftAdditions.items.Tools;

import net.minecraft.item.ItemStack;

import buildcraft.api.recipes.CraftingResult;
import buildcraft.silicon.ItemRedstoneChipset;
import buildcraft.silicon.TileIntegrationTable;
import buildcraft.transport.recipes.IntegrationTableRecipe;

import buildcraftAdditions.reference.ItemsAndBlocks;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class UpgradeRecipeGoldStick extends IntegrationTableRecipe {

	public UpgradeRecipeGoldStick() {
		setContents("goldStick", ItemsAndBlocks.itemKineticMultiTool, 10000, 600, ItemRedstoneChipset.Chipset.QUARTZ.getStack());
	}

	@Override
	public boolean isValidInputA(ItemStack inputA) {
		if (inputA != null && inputA.getItem() instanceof ItemKineticMultiTool)
			return !ItemKineticMultiTool.isStickInstalled(inputA, "goldStick");
		return false;
	}

	@Override
	public boolean isValidInputB(ItemStack inputB) {
		return inputB != null && inputB.getItem() == ItemsAndBlocks.goldStick;
	}

	@Override
	public CraftingResult<ItemStack> craft(TileIntegrationTable crafter, boolean preview, ItemStack inputA, ItemStack inputB) {
		CraftingResult<ItemStack> result = super.craft(crafter, preview, inputA, inputB);
		if (result == null)
			return null;
		ItemStack outputStack = inputA.copy();
		ItemKineticMultiTool.installStick(outputStack, "goldStick");
		result.crafted = outputStack;
		return result;
	}
}
