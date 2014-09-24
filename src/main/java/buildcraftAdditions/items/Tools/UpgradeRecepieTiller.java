package buildcraftAdditions.items.Tools;

import net.minecraft.item.ItemStack;

import buildcraft.api.recipes.IIntegrationRecipeManager;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class UpgradeRecepieTiller implements IIntegrationRecipeManager.IIntegrationRecipe {

	@Override
	public double getEnergyCost() {
		return 1000;
	}

	@Override
	public boolean isValidInputA(ItemStack inputA) {
		if (inputA != null && inputA.getItem() instanceof ItemKineticTool) {
			ItemKineticTool tool = (ItemKineticTool) inputA.getItem();
			return tool.canInstallUpgrade(inputA) && !tool.isUpgradeInstalled(inputA, "Hoe");
		}
		return false;
	}

	@Override
	public boolean isValidInputB(ItemStack inputB) {
		return inputB != null && inputB.getItem() instanceof ToolUpgrade && ((ToolUpgrade) inputB.getItem()).getType() == "Hoe";
	}

	@Override
	public ItemStack getOutputForInputs(ItemStack inputA, ItemStack inputB, ItemStack[] components) {
		ItemStack outputStack = inputA.copy();
		ItemKineticTool output = (ItemKineticTool) outputStack.getItem();
		output.installUpgrade("Hoe", outputStack);
		output.writeUpgrades(outputStack);
		return outputStack;
	}

	@Override
	public ItemStack[] getComponents() {
		return new ItemStack[0];
	}

	@Override
	public ItemStack[] getExampleInputsA() {
		return new ItemStack[0];
	}

	@Override
	public ItemStack[] getExampleInputsB() {
		return new ItemStack[0];
	}
}
