package buildcraftAdditions.items.Tools;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import buildcraft.api.recipes.CraftingResult;
import buildcraft.api.recipes.IFlexibleCrafter;
import buildcraft.api.recipes.IIntegrationRecipe;

import buildcraftAdditions.BuildcraftAdditions;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ToolCoreRecipe implements IIntegrationRecipe {
	private Item goldGearItem = GameRegistry.findItem("BuildCraft|Core", "goldGearItem");

	@Override
	public boolean isValidInputA(ItemStack inputA) {
		return inputA != null && inputA.getItem() == goldGearItem;
	}

	@Override
	public boolean isValidInputB(ItemStack inputB) {
		//return inputB != null && inputB.getItem() == ItemRedstoneChipset.Chipset.DIAMOND.getStack().getItem();
		return false;
	}


	public ItemStack getOutputForInputs(ItemStack inputA, ItemStack inputB, ItemStack[] components) {
		if (!isValidInputA(inputA)) {
			return null;
		}

		if (!isValidInputB(inputB)) {
			return null;
		}
		return new ItemStack(BuildcraftAdditions.toolCore, 1);
	}

	@Override
	public boolean canBeCrafted(IFlexibleCrafter crafter) {
		return crafter.getCraftingItemStack(0)!= null && crafter.getCraftingItemStack(0).getItem() == goldGearItem;
	}

	@Override
	public CraftingResult<ItemStack> craft(IFlexibleCrafter crafter, boolean preview) {
		CraftingResult<ItemStack> result = new CraftingResult<ItemStack>();
		result.crafted = new ItemStack(BuildcraftAdditions.toolCore);
		result.craftingTime = 100000;
		result.energyCost = 30000;
		ArrayList<ItemStack> used = new ArrayList<ItemStack>(2);
		used.add(new ItemStack(goldGearItem));
		result.usedItems = used;
		return result;
	}

	@Override
	public CraftingResult<ItemStack> canCraft(ItemStack expectedOutput) {
		return null;
	}

	@Override
	public String getId() {
		return "toolcore";
	}
}
