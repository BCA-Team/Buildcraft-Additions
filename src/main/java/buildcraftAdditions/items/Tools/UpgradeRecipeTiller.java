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
public class UpgradeRecipeTiller extends IntegrationTableRecipe {

	public UpgradeRecipeTiller() {
		setContents("tiller", BuildcraftAdditions.kineticTool, 10000, 600);
	}

	public boolean isValidInputA(ItemStack inputA) {
		if (inputA != null && inputA.getItem() instanceof ItemKineticTool) {
			ItemKineticTool tool = (ItemKineticTool) inputA.getItem();
			return tool.canInstallUpgrade(inputA) && !tool.isUpgradeInstalled(inputA, "Hoe");
		}
		return false;
	}

	public boolean isValidInputB(ItemStack inputB) {
		return inputB != null && inputB.getItem() instanceof ToolUpgrade && ((ToolUpgrade) inputB.getItem()).getType() == "Hoe";
	}

	@Override
	public CraftingResult<ItemStack> craft(TileIntegrationTable crafter, boolean preview, ItemStack inputA, ItemStack inputB) {
		CraftingResult<ItemStack> result = super.craft(crafter, preview, inputA, inputB);
		ItemStack outputStack = inputA.copy();
		ItemKineticTool output = (ItemKineticTool) outputStack.getItem();
		output.readUpgrades(outputStack);
		output.installUpgrade("Hoe", outputStack);
		output.writeUpgrades(outputStack);
		result.crafted = outputStack;
		return result;
	}
}
