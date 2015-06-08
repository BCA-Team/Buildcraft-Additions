package buildcraftAdditions.compat.buildcraft.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
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
public class UpgradeRecipeUpgrade implements IIntegrationRecipe {
	private String name;
	private Item item;

	private String stickName, inputStickOreDictName, prevStickName;
	private ItemStack[] inputs;
	private Item stick;

	public UpgradeRecipeUpgrade(String stickName, String inputStickOreDictName, String prevStickName, Item stick, ItemRedstoneChipset.Chipset... chipsets) {
		this.stickName = stickName;
		this.inputStickOreDictName = inputStickOreDictName;
		this.prevStickName = prevStickName;
		this.stick = stick;
		inputs = new ItemStack[chipsets != null ? chipsets.length : 0];
		if (chipsets != null) {
			for (int i = 0; i < inputs.length; i++) {
				ItemRedstoneChipset.Chipset chipset = chipsets[i];
				if (chipset != null)
					inputs[i] = chipset.getStack();
			}
		}
	}

	public UpgradeRecipeUpgrade(String name, Item item) {
		this.name = name;
		this.item = item;
	}

	@Override
	public int getEnergyCost() {
		return 20000;
	}

	@Override
	public List<ItemStack> getExampleInput() {
		return Arrays.asList(new ItemStack(ItemLoader.itemKineticMultiTool));
	}

	@Override
	public List<List<ItemStack>> getExampleExpansions() {
		if (item != null)
			return Arrays.asList(Arrays.asList(new ItemStack(item)));
		List<List<ItemStack>> output = new ArrayList<List<ItemStack>>();
		output.add(Arrays.asList(new ItemStack(stick)));
		for (ItemStack stack : inputs)
			output.add(Arrays.asList(stack));
		return output;
	}

	@Override
	public List<ItemStack> getExampleOutput() {
		if (item != null) {
			ItemStack stack = new ItemStack(ItemLoader.itemKineticMultiTool);
			ItemKineticMultiTool.installUpgrade(name, stack);
			return Arrays.asList(stack);
		}
		ItemStack stack = new ItemStack(ItemLoader.itemKineticMultiTool);
		ItemKineticMultiTool.installStick(stack, stickName);
		return Arrays.asList(stack);
	}

	@Override
	public boolean isValidInput(ItemStack input) {
		return input != null && input.getItem() == ItemLoader.itemKineticMultiTool;
	}

	@Override
	public boolean isValidExpansion(ItemStack input, ItemStack expansion) {
		if (item != null)
			return expansion != null && expansion.getItem() == item && ItemKineticMultiTool.canInstallUpgrade(input, name);
		if (expansion != null && (expansion.getItem() == stick && (!ItemKineticMultiTool.isUpgradeInstalled(input, stickName) && (prevStickName == null || ItemKineticMultiTool.isUpgradeInstalled(input, prevStickName)))))
			return true;
		int oreID = OreDictionary.getOreID(inputStickOreDictName);
		for (int id : OreDictionary.getOreIDs(expansion))
			if (id == oreID)
				return true;
		return false;
	}

	@Override
	public ItemStack craft(ItemStack input, List<ItemStack> expansions, boolean preview) {
		if (item != null) {
			ItemStack stack = new ItemStack(ItemLoader.itemKineticMultiTool);
			ItemKineticMultiTool.installUpgrade(name, stack);
			if (!preview)
				expansions.get(0).stackSize--;
			return (stack);
		}
		ItemKineticMultiTool.installStick(input, stickName);
		if (!preview) {
			for (int i = 0; i < inputs.length; i++)
				expansions.get(i).stackSize--;
		}
		return input;
	}

	@Override
	public int getMaximumExpansionCount(ItemStack input) {
		return item == null ? inputs.length : 1;
	}
}
