package buildcraftAdditions.items.dust;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
			BCARecipeManager.duster.addRecipe("ore" + name, twoDusts);
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

	public static final IDustType COAL_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dustCoal", dust.copy());
			BCARecipeManager.duster.addRecipe(new ItemStack(Items.coal), dust.copy());
		}

		@Override
		public String getName() {
			return "Coal";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return true;
		}
	};

	public static final IDustType CHARCOAL_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dustCharcoal", dust.copy());
			BCARecipeManager.duster.addRecipe(new ItemStack(Items.coal, 1, 1), dust.copy());
		}

		@Override
		public String getName() {
			return "Charcoal";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return true;
		}
	};

	public static final IDustType OBSIDIAN_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dustObsidian", dust.copy());
			ItemStack fourDusts = dust.copy();
			fourDusts.stackSize = 4;
			BCARecipeManager.duster.addRecipe(new ItemStack(Blocks.obsidian), fourDusts);
		}

		@Override
		public String getName() {
			return "Obsidian";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return true;
		}
	};

	public static final IDustType ENDER_PEARL_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dustEnderPearl", dust.copy());
			BCARecipeManager.duster.addRecipe(new ItemStack(Items.ender_pearl), dust.copy());
			BCARecipeManager.duster.addRecipe(new ItemStack(Items.ender_eye), dust.copy());
		}

		@Override
		public String getName() {
			return "Ender Pearl";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return true;
		}
	};

	public static final IDustType NETHER_QUARTZ_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dustNetherQuartz", dust.copy());
			BCARecipeManager.duster.addRecipe(new ItemStack(Items.quartz), dust.copy());
		}

		@Override
		public String getName() {
			return "Nether Quartz";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return true;
		}
	};

	public static final IDustType SULFUR_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dustSulfur", dust.copy());
			ItemStack sixDusts = dust.copy();
			sixDusts.stackSize = 6;
			BCARecipeManager.duster.addRecipe("oreSulfur", sixDusts);
		}

		@Override
		public String getName() {
			return "Sulfur";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return OreDictionary.getOres("oreSulfur").size() > 0 || OreDictionary.getOres("dustSulfur").size() > 0;
		}
	};

	public static final IDustType SALTPETER_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dustSaltpeter", dust.copy());
			ItemStack fourDusts = dust.copy();
			fourDusts.stackSize = 4;
			BCARecipeManager.duster.addRecipe("oreSaltpeter", fourDusts);
		}

		@Override
		public String getName() {
			return "Saltpeter";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return OreDictionary.getOres("oreSaltpeter").size() > 0 || OreDictionary.getOres("dustSaltpeter").size() > 0;
		}
	};

	public static final IDustType CERTUS_QUARTZ_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dustCertusQuartz", dust.copy());
			ItemStack twoDusts = dust.copy();
			twoDusts.stackSize = 2;
			BCARecipeManager.duster.addRecipe("oreCertusQuartz", twoDusts);
		}

		@Override
		public String getName() {
			return "Certus Quartz";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return OreDictionary.getOres("oreCertusQuartz").size() > 0 || OreDictionary.getOres("dustCertusQuartz").size() > 0;
		}
	};

	public static final IDustType ENDERIUM_DUST = new IDustType() {

		@Override
		public void register(int meta, String name, ItemStack dust) {
			OreDictionary.registerOre("dust" + name, dust.copy());
			BCARecipeManager.duster.addRecipe("ingot" + name, dust.copy());
		}

		@Override
		public String getName() {
			return "Enderium";
		}

		@Override
		public boolean isValid(int meta, String name, ItemStack dust) {
			return OreDictionary.getOres("ore" + name).size() > 0 || OreDictionary.getOres("ingot" + name).size() > 0 || OreDictionary.getOres("dust" + name).size() > 0;
		}

	};

}
