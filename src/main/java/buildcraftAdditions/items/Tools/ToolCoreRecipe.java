package buildcraftAdditions.items.Tools;

import net.minecraft.item.ItemStack;

import buildcraft.BuildCraftCore;
import buildcraft.silicon.ItemRedstoneChipset;
import buildcraft.transport.recipes.IntegrationTableRecipe;

import buildcraftAdditions.BuildcraftAdditions;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ToolCoreRecipe extends IntegrationTableRecipe {

	public ToolCoreRecipe(){
		setContents("toolCore", BuildcraftAdditions.toolCore, 30000, 100000, BuildCraftCore.goldGearItem, ItemRedstoneChipset.Chipset.DIAMOND.getStack().getItem());
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
