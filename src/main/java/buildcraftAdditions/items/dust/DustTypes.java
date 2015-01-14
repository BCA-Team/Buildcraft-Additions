package buildcraftAdditions.items.dust;

import java.util.List;

import net.minecraft.item.ItemStack;


import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.api.item.dust.IDustType;
import buildcraftAdditions.api.recipe.BCARecipeManager;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DustTypes {

	public static final IDustType METAL_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dust" + name, dust.copy());
			ItemStack twoDusts = dust.copy();
			twoDusts.stackSize = 2;
			BCARecipeManager.duster.addRecipe("ore" + name, twoDusts);
			BCARecipeManager.duster.addRecipe("ingot" + name, dust.copy());
			List<ItemStack> stacks = OreDictionary.getOres("ingot" + name);
			if (stacks.size() > 0) {
				GameRegistry.addSmelting(dust.copy(), stacks.get(0).copy(), 0);
			}
		}

		@Override
		public String getName() {
			return "Metal";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return OreDictionary.getOres("ore" + name).size() > 0 || OreDictionary.getOres("ingot" + name).size() > 0 || OreDictionary.getOres("dust" + name).size() > 0;
		}

	};

	public static final IDustType GEM_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dust" + name, dust.copy());
			BCARecipeManager.duster.addRecipe("gem" + name, dust.copy());
		}

		@Override
		public String getName() {
			return "Gem";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return OreDictionary.getOres("ore" + name).size() > 0 || OreDictionary.getOres("gem" + name).size() > 0 || OreDictionary.getOres("dust" + name).size() > 0;
		}
	};

}
