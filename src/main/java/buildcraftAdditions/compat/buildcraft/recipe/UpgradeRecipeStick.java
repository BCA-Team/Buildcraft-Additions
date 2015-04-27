package buildcraftAdditions.compat.buildcraft.recipe;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import buildcraft.api.recipes.CraftingResult;
import buildcraft.silicon.ItemRedstoneChipset;
import buildcraft.silicon.TileIntegrationTable;
import buildcraft.silicon.recipes.IntegrationTableRecipe;

import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class UpgradeRecipeStick extends IntegrationTableRecipe {

	private final String stickName, inputStickOreDictName, prevStickName;

	public UpgradeRecipeStick(String stickName, String inputStickOreDictName, String prevStickName, ItemRedstoneChipset.Chipset... chipsets) {
		this.stickName = stickName;
		this.inputStickOreDictName = inputStickOreDictName;
		this.prevStickName = prevStickName;
		Object[] inputs = new Object[chipsets != null ? chipsets.length : 0];
		if (chipsets != null) {
			for (int i = 0; i < inputs.length; i++) {
				ItemRedstoneChipset.Chipset chipset = chipsets[i];
				if (chipset != null)
					inputs[i] = chipset.getStack();
			}
		}
		setContents(Variables.MOD.ID + ":" + stickName, ItemsAndBlocks.itemKineticMultiTool, 10000, 60, inputs);
	}

	@Override
	public boolean isValidInputA(ItemStack inputA) {
		return inputA != null && inputA.getItem() instanceof ItemKineticMultiTool && !ItemKineticMultiTool.isUpgradeInstalled(inputA, stickName) && (prevStickName == null || prevStickName.isEmpty() || ItemKineticMultiTool.isUpgradeInstalled(inputA, prevStickName));
	}

	@Override
	public boolean isValidInputB(ItemStack inputB) {
		int oreID = OreDictionary.getOreID(inputStickOreDictName);
		for (int id : OreDictionary.getOreIDs(inputB))
			if (id == oreID)
				return true;
		return false;
	}

	@Override
	public CraftingResult<ItemStack> craft(TileIntegrationTable crafter, boolean preview, ItemStack inputA, ItemStack inputB) {
		CraftingResult<ItemStack> result = super.craft(crafter, preview, inputA, inputB);
		if (result == null)
			return null;
		ItemStack outputStack = inputA.copy();
		ItemKineticMultiTool.installStick(outputStack, stickName);
		result.crafted = outputStack;
		return result;
	}
}
