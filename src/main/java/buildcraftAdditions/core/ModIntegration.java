package buildcraftAdditions.core;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.api.DusterRecepies;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ModIntegration {

	public static void railcraftIntegration() {
		addNuggets("Iron");
		addNuggets("Gold");
		addNuggets("Copper");
		addNuggets("Tin");
	}

	public static void addNuggets(String metal) {
		ArrayList<ItemStack> oreList = OreDictionary.getOres("orePoor" + metal);
		ArrayList<ItemStack> nuggetList = OreDictionary.getOres("nugget" + metal);
		if (oreList.isEmpty() || nuggetList.isEmpty())
			return;
		for (ItemStack poorOre : oreList) {
			DusterRecepies.addDusterRecepie(poorOre, new ItemStack(nuggetList.get(0).getItem(), 2, nuggetList.get(0).getItemDamage()));
		}
	}
}
