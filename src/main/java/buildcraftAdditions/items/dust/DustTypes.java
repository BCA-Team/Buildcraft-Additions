package buildcraftAdditions.items.dust;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.api.item.dust.IDustType;
import buildcraftAdditions.api.recipe.BCARecipeManager;

/**
 * Copyright (c) 2014-2015, AEnterprise
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
			ItemStack nineDusts = dust.copy();
			nineDusts.stackSize = 9;
			BCARecipeManager.duster.addRecipe("ore" + name, twoDusts);
			BCARecipeManager.duster.addRecipe("ingot" + name, dust.copy());
			BCARecipeManager.duster.addRecipe("block" + name, nineDusts);
			for (ItemStack stack : OreDictionary.getOres("ingot" + name)) {
				ItemStack copy = ItemStack.copyItemStack(stack);
				if (copy != null && copy.getItem() != null) {
					copy.stackSize = 1;
					GameRegistry.addSmelting(dust.copy(), copy, 0);
					return;
				}
			}
		}

		@Override
		public String getName() {
			return "Metal";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return OreDictionary.getOres("ore" + name).size() > 0 || OreDictionary.getOres("ingot" + name).size() > 0 || OreDictionary.getOres("block" + name).size() > 0 || OreDictionary.getOres("dust" + name).size() > 0;
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

	public static final IDustType ENDERIUM_BASE_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dust" + name, dust.copy());
			BCARecipeManager.duster.addRecipe("ingot" + name, dust.copy());
			for (ItemStack stack : OreDictionary.getOres("ingot" + name)) {
				ItemStack copy = ItemStack.copyItemStack(stack);
				if (copy != null && copy.getItem() != null) {
					copy.stackSize = 1;
					GameRegistry.addSmelting(dust.copy(), copy, 0);
					return;
				}
			}
		}

		@Override
		public String getName() {
			return "Enderium Base";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return OreDictionary.getOres("ingot" + name).size() > 0 || OreDictionary.getOres("block" + name).size() > 0 || OreDictionary.getOres("dust" + name).size() > 0 || OreDictionary.getOres("ingotEnderium").size() > 0 || OreDictionary.getOres("blockEnderium").size() > 0 || OreDictionary.getOres("dustEnderium").size() > 0;
		}

	};

	public static final IDustType ENDERIUM_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			ItemStack nineDusts = dust.copy();
			nineDusts.stackSize = 9;
			BCARecipeManager.duster.addRecipe("ingot" + name, dust.copy());
			BCARecipeManager.duster.addRecipe("block" + name, nineDusts);
			for (ItemStack stack : OreDictionary.getOres("ingot" + name)) {
				ItemStack copy = ItemStack.copyItemStack(stack);
				if (copy != null && copy.getItem() != null) {
					copy.stackSize = 1;
					GameRegistry.addSmelting(dust.copy(), copy, 0);
					return;
				}
			}
		}

		@Override
		public String getName() {
			return "Enderium";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return OreDictionary.getOres("ingot" + name).size() > 0 || OreDictionary.getOres("block" + name).size() > 0 || OreDictionary.getOres("dust" + name).size() > 0 || OreDictionary.getOres("ingotEnderiumBase").size() > 0 || OreDictionary.getOres("blockEnderiumBase").size() > 0 || OreDictionary.getOres("dustEnderiumBase").size() > 0;
		}

	};

	public static final IDustType ONLY_ORE_DICTIONARY_REGISTRATION = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dust" + name, dust.copy());
		}

		@Override
		public String getName() {
			return "OreDictionary";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return OreDictionary.getOres("dust" + name).size() > 0;
		}
	};

	public static class SimpleDustAlwaysValid implements IDustType {

		private final ItemStack stack;
		private final int dustNumber;

		public SimpleDustAlwaysValid(ItemStack stack) {
			this(stack, 1);
		}

		public SimpleDustAlwaysValid(ItemStack stack, int dustNumber) {
			this.stack = stack;
			this.dustNumber = dustNumber;
		}

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dust" + name, dust.copy());
			ItemStack dusts = dust.copy();
			dusts.stackSize = dustNumber;
			BCARecipeManager.duster.addRecipe(stack, dusts);
		}

		@Override
		public String getName() {
			return "SimpleDust";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return true;
		}
	}

	public static class SimpleDustWithOre implements IDustType {

		private final int dustNumber;

		public SimpleDustWithOre(int dustNumber) {
			this.dustNumber = dustNumber;
		}

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dust" + name, dust.copy());
			ItemStack dusts = dust.copy();
			dusts.stackSize = dustNumber;
			BCARecipeManager.duster.addRecipe("ore" + name, dusts);
		}

		@Override
		public String getName() {
			return "Simple Dust with Ore (" + dustNumber + "x dust)";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return OreDictionary.getOres("ore" + name).size() > 0 || OreDictionary.getOres("dust" + name).size() > 0;
		}
	}

}
