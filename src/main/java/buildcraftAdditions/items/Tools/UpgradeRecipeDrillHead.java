package buildcraftAdditions.items.Tools;

import java.util.ArrayList;

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

public class UpgradeRecipeDrillHead implements IIntegrationRecipe {
	private ItemStack inputA, inputB;
	@Override
	public boolean isValidInputA(ItemStack inputA) {
		if (inputA != null && inputA.getItem() instanceof ItemKineticTool) {
			ItemKineticTool tool = (ItemKineticTool) inputA.getItem();
			this.inputA = inputA;
			return tool.canInstallUpgrade(inputA) && !tool.isUpgradeInstalled(inputA, "Drill");
		}
		return false;
	}

	@Override
	public boolean isValidInputB(ItemStack inputB) {
		this.inputB = inputB;
		return inputB != null && inputB.getItem() instanceof ToolUpgrade && ((ToolUpgrade) inputB.getItem()).getType() == "Drill";
	}


	@Override
	public boolean canBeCrafted(IFlexibleCrafter crafter) {
		return false;
	}

	@Override
	public CraftingResult<ItemStack> craft(IFlexibleCrafter crafter, boolean preview) {
		CraftingResult<ItemStack> result = new CraftingResult<ItemStack>();
		result.energyCost = 1000;
		result.craftingTime = 1000;
		ItemStack outputStack = inputA.copy();
		ItemKineticTool output = (ItemKineticTool) outputStack.getItem();
		output.installUpgrade("Drill", outputStack);
		output.writeUpgrades(outputStack);
		result.crafted = outputStack;
		ArrayList<ItemStack> used = new ArrayList<ItemStack>(2);
		used.add(inputA);
		used.add(inputB);
		return result;
	}

	@Override
	public CraftingResult<ItemStack> canCraft(ItemStack expectedOutput) {
		return null;
	}

	@Override
	public String getId() {
		return "upgradeDrillHead";
	}
}
