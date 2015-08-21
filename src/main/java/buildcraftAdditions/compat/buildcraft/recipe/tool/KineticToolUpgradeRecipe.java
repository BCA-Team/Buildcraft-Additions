package buildcraftAdditions.compat.buildcraft.recipe.tool;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import buildcraft.api.recipes.IIntegrationRecipe;

import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.reference.ItemLoader;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class KineticToolUpgradeRecipe implements IIntegrationRecipe {


	private final List<IKineticToolUpgrade> upgrades;

	public KineticToolUpgradeRecipe() {
		upgrades = new ArrayList<IKineticToolUpgrade>();
	}

	public void register(String stickName, String inputStickOreDictName) {
		register(stickName, inputStickOreDictName, null);
	}

	public void register(String stickName, String inputStickOreDictName, String prevStickName) {
		upgrades.add(new KineticToolStickUpgrade(stickName, inputStickOreDictName, prevStickName));
	}

	public void register(String upgradeName, Item upgradeItem) {
		register(upgradeName, new ItemStack(upgradeItem));
	}

	public void register(String upgradeName, Block upgradeBlock) {
		register(upgradeName, new ItemStack(upgradeBlock));
	}

	public void register(String upgradeName, ItemStack upgradeStack) {
		upgrades.add(new KineticToolUpgrade(upgradeName, upgradeStack));
	}

	@Override
	public int getEnergyCost() {
		return 20000;
	}

	@Override
	public List<ItemStack> getExampleInput() {
		List<ItemStack> list = Lists.newArrayList();
		list.add(new ItemStack(ItemLoader.kineticMultiTool));
		return list;
	}

	@Override
	public List<List<ItemStack>> getExampleExpansions() {
		List<List<ItemStack>> lists = Lists.newArrayList();
		List<ItemStack> list = Lists.newArrayList();
		list.add(new ItemStack(ItemLoader.goldStick));
		list.add(new ItemStack(ItemLoader.toolUpgradeDrill));
		lists.add(list);
		return lists;
	}

	@Override
	public List<ItemStack> getExampleOutput() {
		List<ItemStack> list = Lists.newArrayList();
		list.add(new ItemStack(ItemLoader.kineticMultiTool));
		return list;
	}

	@Override
	public boolean isValidInput(ItemStack input) {
		if (input != null && input.getItem() != null && input.stackSize > 0 && input.getItem() instanceof ItemKineticMultiTool) {
			for (IKineticToolUpgrade upgrade : upgrades) {
				if (upgrade.canUpgradeBeApplied(input))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean isValidExpansion(ItemStack input, ItemStack expansion) {
		if (input != null && input.getItem() != null && input.stackSize > 0 && input.getItem() instanceof ItemKineticMultiTool && expansion != null && expansion.getItem() != null && expansion.stackSize > 0) {
			for (IKineticToolUpgrade upgrade : upgrades) {
				if (upgrade.canUpgradeBeApplied(input) && upgrade.doesExpansionMatch(expansion))
					return true;
			}
		}
		return false;
	}

	@Override
	public ItemStack craft(ItemStack input, List<ItemStack> expansions, boolean preview) {

		if (input == null || input.getItem() == null || input.stackSize <= 0)
			return null;
		if (expansions.size() != 1)
			return null;
		ItemStack expansion = expansions.get(0);
		if (expansion == null || expansion.getItem() == null || expansion.stackSize <= 0)
			return null;

		for (IKineticToolUpgrade upgrade : upgrades) {

			if (!upgrade.canUpgradeBeApplied(input) || !upgrade.doesExpansionMatch(expansion))
				continue;

			if (!preview)
				expansion.stackSize--;

			ItemStack output = input.copy();
			output.stackSize = 1;

			upgrade.install(output);

			return output;
		}
		return null;
	}

	@Override
	public int getMaximumExpansionCount(ItemStack input) {
		return 1;
	}
}
