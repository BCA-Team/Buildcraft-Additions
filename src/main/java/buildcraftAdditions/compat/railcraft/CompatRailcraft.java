package buildcraftAdditions.compat.railcraft;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.event.FMLLoadCompleteEvent;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.compat.CompatModule;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@CompatModule(id = "Railcraft", requiredMods = "Railcraft")
public class CompatRailcraft {

	@CompatModule.Handler
	public void doneLoading(FMLLoadCompleteEvent event) {
		addNuggets("Iron");
		addNuggets("Gold");
		addNuggets("Copper");
		addNuggets("Tin");
		addNuggets("Lead");
	}

	private void addNuggets(String metal) {
		ArrayList<ItemStack> oreList = OreDictionary.getOres("orePoor" + metal);
		ArrayList<ItemStack> nuggetList = OreDictionary.getOres("nugget" + metal);
		if (oreList.isEmpty() || nuggetList.isEmpty())
			return;
		for (ItemStack poorOre : oreList) {
			BCARecipeManager.duster.addRecipe(poorOre, new ItemStack(nuggetList.get(0).getItem(), 4, nuggetList.get(0).getItemDamage()));
		}
	}
}
