package buildcraftAdditions.items.Tools;

import net.minecraft.item.ItemStack;

import buildcraft.api.recipes.CraftingResult;
import buildcraft.api.recipes.IFlexibleCrafter;
import buildcraft.api.recipes.IIntegrationRecipe;

import buildcraftAdditions.items.ItemBase;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class UpgradeRecipeGoldStick implements IIntegrationRecipe {

	@Override
	public boolean isValidInputA(ItemStack inputA) {
		if (inputA != null && inputA.getItem() instanceof ItemKineticTool) {
			ItemKineticTool tool = (ItemKineticTool) inputA.getItem();
			return !tool.isStickInstalled(inputA, "goldStick");
		}
		return false;
	}

	@Override
	public boolean isValidInputB(ItemStack inputB) {
		return inputB != null && inputB.getItem() instanceof ItemBase && inputB.getItem().getUnlocalizedName() == "stickGold";
	}

	public ItemStack getOutputForInputs(ItemStack inputA, ItemStack inputB, ItemStack[] components) {
		ItemStack outputStack = inputA.copy();
		ItemKineticTool output = (ItemKineticTool) outputStack.getItem();
		output.installStick(outputStack, "goldStick");
		output.writeUpgrades(outputStack);
		return outputStack;
	}

	@Override
	public boolean canBeCrafted(IFlexibleCrafter crafter) {
		return false;
	}

	@Override
	public CraftingResult<ItemStack> craft(IFlexibleCrafter crafter, boolean preview) {
		return null;
	}

	@Override
	public CraftingResult<ItemStack> canCraft(ItemStack expectedOutput) {
		return null;
	}

	@Override
	public String getId() {
		return "upgradeRecipeGoldStick";
	}
}
