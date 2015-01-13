package buildcraftAdditions.ModIntegration;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.ModIntegration.Buildcraft.BuildcraftIntegration;
import buildcraftAdditions.ModIntegration.Framez.FramezIntegration;
import buildcraftAdditions.ModIntegration.MineTweaker.MineTweakerIntegreation;
import buildcraftAdditions.api.DusterRecipes;
import buildcraftAdditions.blocks.BlockBasic;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.core.Logger;
import buildcraftAdditions.items.ItemDust;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.BCItems;
import buildcraftAdditions.utils.RefineryRecipeConverter;

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

	public static void integrate() {
		if (ConfigurationHandler.eurekaIntegration)
			eurekaResearch();
		railcraftIntegration();
		metals();
		if (Loader.isModLoaded("framez"))
			FramezIntegration.Framez();
		RefineryRecipeConverter.doYourThing();
		if (Loader.isModLoaded("MineTweaker3"))
			MineTweakerIntegreation.integrate();
		if (Loader.isModLoaded("TConstruct"))
			tinkersConstructIntegration();
		if (Loader.isModLoaded("BuildCraft|Transport")) {
			BuildcraftIntegration.integrate();
		}
	}

	private static void railcraftIntegration() {
		addNuggets("Iron");
		addNuggets("Gold");
		addNuggets("Copper");
		addNuggets("Tin");
	}

	private static void tinkersConstructIntegration() { //NOTE: this will crash when a battery is placed on the tool for every TCon version before the 681 jenkins build! will be fixed
		try {
			Class<?> toolsClass = Class.forName("tconstruct.tools.TinkerTools");
			Object modifier = toolsClass.getField("modFlux").get(null);
			Class<?> modifierClass = Class.forName("tconstruct.modifiers.tools.ModFlux");
			List<ItemStack> batteries = (List<ItemStack>) modifierClass.getField("batteries").get(modifier);
			batteries.add(new ItemStack(ItemsAndBlocks.powerCapsuleTier1));
			batteries.add(new ItemStack(ItemsAndBlocks.powerCapsuleTier2));
			batteries.add(new ItemStack(ItemsAndBlocks.powerCapsuleTier3));
		} catch (Exception e) {
			Logger.error("power capsule haven't been initialized to TinkersConstruct's Flux Modifier, this is a bug!");
		}
	}

	private static void eurekaResearch() {
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

		EurekaRegistry.register(new EurekaInfo("KEBT1", "BCA", 3, new ItemStack(ItemsAndBlocks.kebT1, 1, 8)));
		EurekaRegistry.registerDrops("KEBT1", new ItemStack(Items.iron_ingot, 4), new ItemStack(ItemsAndBlocks.powerCapsuleTier1, 3), new ItemStack(BCItems.PIPE_POWER_GOLD, 2));
		EurekaRegistry.addCrafingProgress(ItemsAndBlocks.powerCapsuleTier1, "KEBT1");
		EurekaRegistry.bindToKey(ItemsAndBlocks.kebT1, "KEBT1");

		Block temp = new BlockBasic("energyBufferMultiblockSides5");
		GameRegistry.registerBlock(temp, "test");

		EurekaRegistry.register(new EurekaInfo("KEBT2", "BCA", 4, new ItemStack(temp), "KEBT1"));
		EurekaRegistry.registerDrops("KEBT2", new ItemStack(Items.iron_ingot, 6), new ItemStack(BCItems.PIPE_POWER_GOLD, 2), new ItemStack(ItemsAndBlocks.powerCapsuleTier2));
		EurekaRegistry.addPlaceBlockProgress(ItemsAndBlocks.kebT1, "KEBT2");
		EurekaRegistry.bindToKey(ItemsAndBlocks.kebT2, "KEBT2");

		//EurekaRegistry.register(new EurekaInfo("KEBT3", "BCA", 2, new ItemStack(temp), "KEBT2"));
		//EurekaRegistry.registerDrops("KEBT3", new ItemStack(BCItems.PIPE_POWER_DIAMOND, 2), new ItemStack(Items.gold_ingot, 4), new ItemStack(Items.iron_ingot, 3));
		//EurekaRegistry.bindToKey(ItemsAndBlocks.kebT3Plating, "KEBT3");
	}

	private static void metals() {

		addDusts("Bronze", 0xAD6726);
		addDusts("Manganese", 0xF3D2D2);
		addDusts("Hepatizon", 0x6B566B);
		addDusts("DamascusSteel", 0x3D2C1F);
		addDusts("Angmallen", 0xFAFA6C);
		addDusts("Steel", 0x919191);
		addDusts("Eximite", 0x9E83B4);
		addDusts("Meutoite", 0x5F5269);
		addDusts("Desichalkos", 0x742EA8);
		addDusts("Prometheum", 0x5A8156);
		addDusts("DeepIron", 0x495B69);
		addDusts("Infuscolium", 0xCB6293);
		addDusts("BlackSteel", 0x395679);
		addDusts("Oureclase", 0xDCA82E);
		addDusts("AstralSilver", 0xC8D4D5);
		addDusts("Carmot", 0xA99733);
		addDusts("Mithril", 0x08B5C3);
		addDusts("Rubracium", 0x8E2727);
		addDusts("Quicksilver", 0x7CD3C7);
		addDusts("Haderoth", 0xD1531E);
		addDusts("Orichalcum", 0x547A38);
		addDusts("Celenegil", 0x94CC48);
		addDusts("Adamantine", 0xF04040);
		addDusts("Atlarus", 0xF4D603);
		addDusts("Tartarite", 0xFF763C);
		addDusts("Ignatius", 0xE87400);
		addDusts("ShadowIron", 0x8D7565);
		addDusts("Lemurite", 0xEFEFEF);
		addDusts("Midasium", 0xFFA826);
		addDusts("Vyroxeres", 0x55E001);
		addDusts("Ceruclase", 0x458FAB);
		addDusts("Alduorite", 0xA3DEDE);
		addDusts("Kalendrite", 0xAA5BBD);
		addDusts("Vulcanite", 0xFF8448);
		addDusts("Sanguinite", 0xB90000);
		addDusts("ShadowSteel", 0x887362);
		addDusts("Inolashite", 0x40AA7D);
		addDusts("Amordrine", 0xA98DB1);
		addDusts("Zinc", 0xDCDFA4);
		addDusts("Brass", 0xD89634);
		addDusts("Electrum", 0xDFD0AA);
		addDusts("Aluminum", 0xEDEDED);
		addDusts("Ardite", 0xF28900);
		addDusts("Manyullyn", 0xAB7EE3);
		addDusts("Cobalt", 0x0064FF);
		addDusts("Copper", 0xBF5E1F);
		addDusts("Lead", 0x808096);
		addDusts("Nickel", 0xBAB0A4);
		addDusts("Platinum", 0xABCDEF);
		addDusts("Silver", 0xB3B3B3);
		addDusts("Tin", 0xF2F2F2);
}

	private static void addNuggets(String metal) {
		ArrayList<ItemStack> oreList = OreDictionary.getOres("orePoor" + metal);
		ArrayList<ItemStack> nuggetList = OreDictionary.getOres("nugget" + metal);
		if (oreList.isEmpty() || nuggetList.isEmpty())
			return;
		for (ItemStack poorOre : oreList) {
			DusterRecipes.dusting().addDusterRecipe(poorOre, new ItemStack(nuggetList.get(0).getItem(), 4, nuggetList.get(0).getItemDamage()));
		}
	}

	private static void addDusts(String metalName, int color) {
		Item itemDust;
		ArrayList<ItemStack> list;
		list = OreDictionary.getOres("ingot" + metalName);
		if (list.isEmpty())
			return;
		if (ConfigurationHandler.shouldRegisterDusts) {
			itemDust = new ItemDust(color).setUnlocalizedName("dust" + metalName);
			list = OreDictionary.getOres("dust" + metalName);
			if (!list.isEmpty()) {
				String name = list.get(0).getUnlocalizedName();
				name = name.replace("item.", "");
				name = name.replace("ic2.", "");
				itemDust.setUnlocalizedName(name);
			}

			GameRegistry.registerItem(itemDust, "dust" + metalName);
			OreDictionary.registerOre("dust" + metalName, itemDust);
			GameRegistry.addSmelting(itemDust, OreDictionary.getOres("ingot" + metalName).get(0).copy(), 0);
		} else {
			ArrayList<ItemStack> tempList = OreDictionary.getOres("dust" + metalName);
			if (tempList.isEmpty())
				return;
			ItemStack stack = tempList.get(0);
			if (stack == null)
				return;
			itemDust = stack.getItem();
		}
		list = OreDictionary.getOres("ore" + metalName);
		for (ItemStack stack : list)
			DusterRecipes.dusting().addDusterRecipe(stack.copy(), new ItemStack(itemDust, 2));
		list = OreDictionary.getOres("ingot" + metalName);
		for (ItemStack stack : list)
			DusterRecipes.dusting().addDusterRecipe(stack.copy(), new ItemStack(itemDust, 1));
	}
}
