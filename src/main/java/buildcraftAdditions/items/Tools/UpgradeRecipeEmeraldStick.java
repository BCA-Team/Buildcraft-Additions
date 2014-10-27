package buildcraftAdditions.items.Tools;

import net.minecraft.item.ItemStack;

import buildcraft.api.recipes.CraftingResult;
import buildcraft.silicon.TileIntegrationTable;
import buildcraft.transport.recipes.IntegrationTableRecipe;

import buildcraftAdditions.BuildcraftAdditions;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class UpgradeRecipeEmeraldStick extends IntegrationTableRecipe {

	public UpgradeRecipeEmeraldStick() {
		setContents("emeraldStick", BuildcraftAdditions.kineticTool, 10000, 60);
	}

	@Override
	public boolean isValidInputA(ItemStack inputA) {
		if (inputA != null && inputA.getItem() instanceof ItemKineticTool) {
			ItemKineticTool tool = (ItemKineticTool) inputA.getItem();
			return !tool.isStickInstalled(inputA, "emeraldStick") && tool.isStickInstalled(inputA, "diamondStick");
		}
		return false;
	}

	@Override
	public boolean isValidInputB(ItemStack inputB) {
		return inputB != null && inputB.getItem() == BuildcraftAdditions.emeraldStick;
	}

	@Override
	public CraftingResult<ItemStack> craft(TileIntegrationTable crafter, boolean preview, ItemStack inputA, ItemStack inputB) {
		CraftingResult<ItemStack> result = super.craft(crafter, preview, inputA, inputB);
		ItemStack outputStack = inputA.copy();
		ItemKineticTool output = (ItemKineticTool) outputStack.getItem();
		output.installStick(outputStack, "emeraldStick");
		output.writeUpgrades(outputStack);
		result.crafted = outputStack;
		return result;
	}
}
