package buildcraftAdditions.compat.buildcraft.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
public class KineticToolUpgradeHandler implements IIntegrationRecipe {
	private static ArrayList<BCAIntegrationRecipe> recipes = new ArrayList<BCAIntegrationRecipe>();
	private static HashMap<Item, BCAIntegrationRecipe> instalations = new HashMap<Item, BCAIntegrationRecipe>();
	private static ArrayList<List<ItemStack>> exampleExpansions = new ArrayList<List<ItemStack>>();
	private static ArrayList<ItemStack> exampleOutputs = new ArrayList<ItemStack>();

	public static void addRecipe(BCAIntegrationRecipe recipe) {
		recipes.add(recipe);
		if (exampleExpansions.isEmpty()) {
			exampleExpansions.add(new ArrayList<ItemStack>());
		}
		List<ItemStack> list = exampleExpansions.get(0);
		list.add(new ItemStack(recipe.getExpansion()));
		ItemStack output = new ItemStack(ItemLoader.kineticMultiTool);
		if (recipe.installStick()) {
			ItemKineticMultiTool.installStick(output, recipe.getInstallation());
		} else {
			ItemKineticMultiTool.installUpgrade(output, recipe.getInstallation());
		}
		exampleOutputs.add(output);
		instalations.put(recipe.getExpansion(), recipe);
	}

	@Override
	public int getEnergyCost() {
		return 20000;
	}

	@Override
	public List<ItemStack> getExampleInput() {
		return Arrays.asList(new ItemStack(ItemLoader.kineticMultiTool));
	}

	@Override
	public List<List<ItemStack>> getExampleExpansions() {
		return exampleExpansions;
	}

	@Override
	public List<ItemStack> getExampleOutput() {
		return exampleOutputs;
	}

	@Override
	public boolean isValidInput(ItemStack input) {
		return input != null && input.getItem() == ItemLoader.kineticMultiTool;
	}

	@Override
	public boolean isValidExpansion(ItemStack input, ItemStack expansion) {
		if (expansion != null) {
			for (BCAIntegrationRecipe recipe : recipes) {
				if (recipe.getExpansion() == expansion.getItem()) {
					if (recipe.installStick()) {
						return !ItemKineticMultiTool.isUpgradeInstalled(input, recipe.getInstallation()) && ItemKineticMultiTool.isUpgradeInstalled(input, recipe.getPrevStick());
					} else {
						return ItemKineticMultiTool.canInstallUpgrade(input, recipe.getInstallation());
					}
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack craft(ItemStack input, List<ItemStack> expansions, boolean preview) {
		BCAIntegrationRecipe recipe = instalations.get(expansions.get(0).getItem());
		if (recipe.installStick()) {
			ItemKineticMultiTool.installStick(input, recipe.getInstallation());
		} else {
			ItemKineticMultiTool.installUpgrade(input, recipe.getInstallation());
		}
		if (!preview) {
			expansions.get(0).stackSize--;
		}
		return input;
	}

	@Override
	public int getMaximumExpansionCount(ItemStack input) {
		return 1;
	}
}
