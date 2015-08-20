package buildcraftAdditions.compat.buildcraft.recipe;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import buildcraft.api.recipes.IIntegrationRecipe;
import buildcraft.silicon.ItemRedstoneChipset;

import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.reference.ItemLoader;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class UpgradeRecipeStick implements IIntegrationRecipe {

	private String stickName, inputStickOreDictName, prevStickName;
	private String[] extraExpansionChipsets;

	public UpgradeRecipeStick(String stickName, String inputStickOreDictName, ItemRedstoneChipset.Chipset... chipsets) {
		this(stickName, inputStickOreDictName, null, chipsets);
	}

	public UpgradeRecipeStick(String stickName, String inputStickOreDictName, String prevStickName, ItemRedstoneChipset.Chipset... chipsets) {
		this.stickName = stickName;
		this.inputStickOreDictName = inputStickOreDictName;
		this.prevStickName = prevStickName;
		extraExpansionChipsets = new String[chipsets != null ? chipsets.length : 0];
		if (chipsets != null) {
			for (int i = 0; i < extraExpansionChipsets.length; i++) {
				ItemRedstoneChipset.Chipset chipset = chipsets[i];
				if (chipset != null)
					extraExpansionChipsets[i] = "chipset" + chipset.name().toUpperCase().substring(0, 1) + chipset.name().toLowerCase().substring(1);
			}
		}
	}

	@Override
	public int getEnergyCost() {
		return 20000;
	}

	@Override
	public List<ItemStack> getExampleInput() {
		ItemStack input = new ItemStack(ItemLoader.kineticMultiTool);
		if (!Strings.isNullOrEmpty(prevStickName))
			ItemKineticMultiTool.installStick(input, prevStickName);
		return Lists.newArrayList(input);
	}

	@Override
	public List<List<ItemStack>> getExampleExpansions() {
		List<List<ItemStack>> exampleExpansions = new ArrayList<List<ItemStack>>();
		exampleExpansions.add(OreDictionary.getOres(inputStickOreDictName));
		for (String extraExpansionChipset : extraExpansionChipsets)
			exampleExpansions.add(OreDictionary.getOres(extraExpansionChipset));
		return exampleExpansions;
	}

	@Override
	public List<ItemStack> getExampleOutput() {
		ItemStack input = new ItemStack(ItemLoader.kineticMultiTool);
		if (!Strings.isNullOrEmpty(prevStickName))
			ItemKineticMultiTool.installStick(input, prevStickName);
		ItemKineticMultiTool.installStick(input, stickName);
		return Lists.newArrayList(input);
	}

	@Override
	public boolean isValidInput(ItemStack input) {
		return input != null && input.getItem() != null && input.stackSize > 0 && input.getItem() instanceof ItemKineticMultiTool && (Strings.isNullOrEmpty(prevStickName) || ItemKineticMultiTool.isUpgradeInstalled(input, prevStickName));
	}

	@Override
	public boolean isValidExpansion(ItemStack input, ItemStack expansion) {
		if (expansion != null && expansion.getItem() != null && expansion.stackSize > 0)
			return false;
		int[] oreIDs = OreDictionary.getOreIDs(expansion);
		if (oreIDs != null && oreIDs.length > 0) {
			for (int oreID : oreIDs) {
				String oreName = OreDictionary.getOreName(oreID);
				if (!Strings.isNullOrEmpty(oreName)) {
					if (oreName.equalsIgnoreCase(inputStickOreDictName))
						return true;
					for (String extraExpansionChipset : extraExpansionChipsets) {
						if (oreName.equalsIgnoreCase(extraExpansionChipset))
							return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack craft(ItemStack input, List<ItemStack> expansions, boolean preview) {
		ItemStack output = input.copy();
		output.stackSize = 1;
		boolean stick = false;
		int chipsets = 0;
		List<String> chipsetList = Lists.newArrayList(extraExpansionChipsets);
		boolean failure = false;

		ExpansionLoop:
		for (ItemStack expansion : expansions) {
			if (expansion != null && expansion.getItem() != null && expansion.stackSize > 0)
				continue;
			int[] oreIDs = OreDictionary.getOreIDs(expansion);
			if (oreIDs != null && oreIDs.length > 0) {
				for (int oreID : oreIDs) {
					String oreName = OreDictionary.getOreName(oreID);
					if (!Strings.isNullOrEmpty(oreName)) {
						if (!stick && oreName.equalsIgnoreCase(inputStickOreDictName)) {
							stick = true;
							continue ExpansionLoop;
						}
						if (chipsetList.size() > 0) {
							for (String extraExpansionChipset : extraExpansionChipsets) {
								if (oreName.equalsIgnoreCase(extraExpansionChipset)) {
									chipsets++;
									if (chipsetList.remove(extraExpansionChipset))
										continue ExpansionLoop;
									else
										failure = true;
								}
							}
						}
					}
				}
			}
			failure = true;
		}

		if (!failure)
			failure = !stick || chipsets < extraExpansionChipsets.length;

		if (failure)
			return null;

		if (!preview) {
			for (ItemStack expansion : expansions) {
				if (expansion != null && expansion.getItem() != null && expansion.stackSize > 0)
					continue;
				expansion.stackSize--;
			}
		}

		ItemKineticMultiTool.installStick(output, stickName);

		return output;
	}

	@Override
	public int getMaximumExpansionCount(ItemStack input) {
		return 1 + extraExpansionChipsets.length;
	}
}
