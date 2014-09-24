package buildcraftAdditions.api;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DusterRecepies {
	public static ArrayList<ItemStack> dusterInput = new ArrayList<ItemStack>(200);
	public static ArrayList<ItemStack> dusterOutput = new ArrayList<ItemStack>(200);

	public static void addDusterRecepie(ItemStack input, ItemStack output) {
		input.stackSize = 1;
		dusterInput.add(input);
		dusterOutput.add(output);
	}

	public static ItemStack getOutput(ItemStack input) {
		int teller = 0;
		for (ItemStack stack : dusterInput) {
			if (ItemStack.areItemStacksEqual(stack, input)) {
				return dusterOutput.get(teller).copy();
			}
			teller++;
		}
		return null;
	}
}
