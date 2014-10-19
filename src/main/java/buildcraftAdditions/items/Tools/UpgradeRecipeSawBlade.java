package buildcraftAdditions.items.Tools;

import net.minecraft.item.ItemStack;

import buildcraft.api.recipes.CraftingResult;
import buildcraft.api.recipes.IFlexibleCrafter;
import buildcraft.api.recipes.IIntegrationRecipe;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class UpgradeRecipeSawBlade implements IIntegrationRecipe {

	public double getEnergyCost() {
		return 1000;
	}

	public boolean isValidInputA(ItemStack inputA) {
		if (inputA != null && inputA.getItem() instanceof ItemKineticTool) {
			ItemKineticTool tool = (ItemKineticTool) inputA.getItem();
			return tool.canInstallUpgrade(inputA) && !tool.isUpgradeInstalled(inputA, "Chainsaw");
		}
		return false;
	}

	public boolean isValidInputB(ItemStack inputB) {
		return inputB != null && inputB.getItem() instanceof ToolUpgrade && ((ToolUpgrade) inputB.getItem()).getType() == "Chainsaw";
	}

	public ItemStack getOutputForInputs(ItemStack inputA, ItemStack inputB, ItemStack[] components) {
		ItemStack outputStack = inputA.copy();
		ItemKineticTool output = (ItemKineticTool) outputStack.getItem();
		output.installUpgrade("Chainsaw", outputStack);
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
		return null;
	}
}
