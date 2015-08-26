package buildcraftAdditions.compat.buildcraft.recipe;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Strings;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import buildcraft.api.recipes.IIntegrationRecipe;
import buildcraft.silicon.ItemRedstoneChipset;

import buildcraftAdditions.reference.ItemLoader;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ToolCoreRecipe implements IIntegrationRecipe {
	@Override
	public int getEnergyCost() {
		return 10000;
	}

	@Override
	public List<ItemStack> getExampleInput() {
		return OreDictionary.getOres("gearGold");
	}

	@Override
	public List<List<ItemStack>> getExampleExpansions() {
		return Arrays.asList((List<ItemStack>) OreDictionary.getOres("chipset" + ItemRedstoneChipset.Chipset.DIAMOND.name().toUpperCase().substring(0, 1) + ItemRedstoneChipset.Chipset.DIAMOND.name().toLowerCase().substring(1)));
	}

	@Override
	public List<ItemStack> getExampleOutput() {
		return Arrays.asList(new ItemStack(ItemLoader.toolCore));
	}

	@Override
	public boolean isValidInput(ItemStack input) {
		if (input != null && input.getItem() != null && input.stackSize > 0) {
			int[] oreIDs = OreDictionary.getOreIDs(input);
			if (oreIDs != null && oreIDs.length > 0) {
				for (int oreID : oreIDs) {
					String oreName = OreDictionary.getOreName(oreID);
					if (!Strings.isNullOrEmpty(oreName)) {
						if (oreName.equals("gearGold"))
							return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean isValidExpansion(ItemStack input, ItemStack expansion) {
		if (expansion != null && expansion.getItem() != null && expansion.stackSize > 0) {
			int[] oreIDs = OreDictionary.getOreIDs(expansion);
			if (oreIDs != null && oreIDs.length > 0) {
				for (int oreID : oreIDs) {
					String oreName = OreDictionary.getOreName(oreID);
					if (!Strings.isNullOrEmpty(oreName)) {
						if (oreName.equals("chipset" + ItemRedstoneChipset.Chipset.DIAMOND.name().toUpperCase().substring(0, 1) + ItemRedstoneChipset.Chipset.DIAMOND.name().toLowerCase().substring(1)))
							return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack craft(ItemStack input, List<ItemStack> expansions, boolean preview) {
		if (!preview)
			expansions.get(0).stackSize--;
		return new ItemStack(ItemLoader.toolCore);
	}

	@Override
	public int getMaximumExpansionCount(ItemStack input) {
		return 1;
	}
}
