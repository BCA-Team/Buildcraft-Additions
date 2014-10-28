package buildcraftAdditions.core;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.api.DusterRecipes;
import buildcraftAdditions.utils.BCItems;
import buildcraftAdditions.variables.ItemsAndBlocks;
import buildcraftAdditions.variables.Variables;


import eureka.api.EurekaInfo;
import eureka.api.EurekaRegistry;

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

	public static void eurekaResearch() {
		EurekaRegistry.registerCategory("BCA", new ItemStack(ItemsAndBlocks.kineticDusterBlock));

		EurekaRegistry.register(new EurekaInfo(Variables.DustT0Key, "BCA", 1, new ItemStack(ItemsAndBlocks.basicDusterBlock)));
		EurekaRegistry.registerDrops(Variables.DustT0Key, new ItemStack(eureka.utils.BCItems.STONE_GEAR, 2), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.stone, 5), new ItemStack(Items.slime_ball));
		EurekaRegistry.bindToKey(ItemsAndBlocks.basicDusterBlock, Variables.DustT0Key);

		EurekaRegistry.register(new EurekaInfo(Variables.DustT1Key, "BCA", 20, new ItemStack(ItemsAndBlocks.semiAutomaticDusterBlock), Variables.DustT0Key));
		EurekaRegistry.registerDrops(Variables.DustT1Key, new ItemStack(BCItems.PIPE_ITEMS_GOLD, 2), new ItemStack(Items.gold_ingot), new ItemStack(eureka.utils.BCItems.PIPE_ITEMS_GOLD, 2), new ItemStack(Blocks.stone, 3));
		EurekaRegistry.bindToKey(ItemsAndBlocks.semiAutomaticDusterBlock, Variables.DustT1Key);

		EurekaRegistry.register(new EurekaInfo(Variables.DustT2Key1, "BCA", 40, new ItemStack(ItemsAndBlocks.mechanicalDusterBlock), Variables.DustT1Key));
		EurekaRegistry.registerDrops(Variables.DustT2Key1, new ItemStack(BCItems.IRON_GEAR, 2), new ItemStack(Items.gold_ingot, 1), new ItemStack(ItemsAndBlocks.itemGrindingWheel, 1), new ItemStack(Blocks.stone, 5));
		EurekaRegistry.bindToKey(ItemsAndBlocks.mechanicalDusterBlock, Variables.DustT2Key1);

		EurekaRegistry.register(new EurekaInfo(Variables.DustT2Key2, "BCA", 20, new ItemStack(ItemsAndBlocks.kineticDusterBlock), Variables.DustT2Key1));
		EurekaRegistry.registerDrops(Variables.DustT2Key2, new ItemStack(Blocks.glass, 3), new ItemStack(BCItems.PIPE_ITEMS_GOLD, 2), new ItemStack(BCItems.GOLD_GEAR, 2), new ItemStack(BCItems.DIAMOND_GEAR));
		EurekaRegistry.bindToKey(ItemsAndBlocks.kineticDusterBlock, Variables.DustT2Key2);

		EurekaRegistry.register(new EurekaInfo(Variables.KineticToolKey, "BCA", 25, new ItemStack(ItemsAndBlocks.kineticTool)));
		EurekaRegistry.registerDrops(Variables.KineticToolKey, new ItemStack(Items.diamond, 3), new ItemStack(ItemsAndBlocks.ironStick), new ItemStack(ItemsAndBlocks.toolCore));
		EurekaRegistry.bindToKey(ItemsAndBlocks.kineticTool, Variables.KineticToolKey);

		EurekaRegistry.register(new EurekaInfo("heatedFurnace", "BCA", 5, new ItemStack(ItemsAndBlocks.heatedFurnaceBlock)));
		EurekaRegistry.registerDrops("heatedFurnace", new ItemStack(Blocks.furnace), new ItemStack(Items.iron_ingot, 8));
		EurekaRegistry.addPlaceBlockProgress(Blocks.furnace, "heatedFurnace");
		EurekaRegistry.bindToKey(ItemsAndBlocks.heatedFurnaceBlock, "heatedFurnace");

		EurekaRegistry.register(new EurekaInfo("basicCoil", "BCA", 1, new ItemStack(ItemsAndBlocks.basicCoilBlock), "heatedFurnace"));
		EurekaRegistry.registerDrops("basicCoil", new ItemStack(Items.iron_ingot), new ItemStack(ItemsAndBlocks.itemIronWire, 8));
		EurekaRegistry.addPlaceBlockProgress(ItemsAndBlocks.heatedFurnaceBlock, "basicCoil");
		EurekaRegistry.bindToKey(ItemsAndBlocks.basicCoilBlock, "basicCoil");

		EurekaRegistry.register(new EurekaInfo("lavaCoil", "BCA", 5, new ItemStack(ItemsAndBlocks.lavaCoilBlock), "basicCoil"));
		EurekaRegistry.registerDrops("lavaCoil", new ItemStack(Items.iron_ingot), new ItemStack(ItemsAndBlocks.goldWire, 8));
		EurekaRegistry.addPlaceBlockProgress(ItemsAndBlocks.basicCoilBlock, "lavaCoil");
		EurekaRegistry.bindToKey(ItemsAndBlocks.lavaCoilBlock, "lavaCoil");

		EurekaRegistry.register(new EurekaInfo("kineticCoil", "BCA", 4, new ItemStack(ItemsAndBlocks.kineticCoil), "lavaCoil"));
		EurekaRegistry.registerDrops("kineticCoil", new ItemStack(Items.iron_ingot), new ItemStack(ItemsAndBlocks.diamondWire, 8));
		EurekaRegistry.addPlaceBlockProgress(ItemsAndBlocks.lavaCoilBlock, "kineticCoil");
		EurekaRegistry.bindToKey(ItemsAndBlocks.kineticCoil, "kineticCoil");
	}

	public static void addNuggets(String metal) {
		ArrayList<ItemStack> oreList = OreDictionary.getOres("orePoor" + metal);
		ArrayList<ItemStack> nuggetList = OreDictionary.getOres("nugget" + metal);
		if (oreList.isEmpty() || nuggetList.isEmpty())
			return;
		for (ItemStack poorOre : oreList) {
			DusterRecipes.dusting().addDusterRecipe(poorOre, new ItemStack(nuggetList.get(0).getItem(), 2, nuggetList.get(0).getItemDamage()));
		}
	}
}
