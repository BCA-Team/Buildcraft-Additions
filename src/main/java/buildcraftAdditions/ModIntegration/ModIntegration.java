package buildcraftAdditions.ModIntegration;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.ModIntegration.Buildcraft.BuildcraftIntegration;
import buildcraftAdditions.ModIntegration.Framez.FramezIntegration;
import buildcraftAdditions.ModIntegration.MineTweaker.MineTweakerIntegreation;
import buildcraftAdditions.api.item.BCAItemManager;
import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.blocks.BlockBasic;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.items.dust.DustTypes;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.BCItems;

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
		railcraftIntegration();
		metals();
		if (Loader.isModLoaded("framez"))
			FramezIntegration.Framez();

		if (Loader.isModLoaded("MineTweaker3"))
			MineTweakerIntegreation.integrate();
		if (Loader.isModLoaded("BuildCraft|Transport")) {
			BuildcraftIntegration.integrate();
			if (ConfigurationHandler.eurekaIntegration)
				eurekaResearch();
		}
	}

	private static void railcraftIntegration() {
		addNuggets("Iron");
		addNuggets("Gold");
		addNuggets("Copper");
		addNuggets("Tin");
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

		int meta = 4;

		BCAItemManager.dusts.addDust(meta++, "Bronze", 0xAD6726, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Manganese", 0xF3D2D2, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Hepatizon", 0x6B566B, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "DamascusSteel", 0x3D2C1F, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Angmallen", 0xFAFA6C, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Steel", 0x919191, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Eximite", 0x9E83B4, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Meutoite", 0x5F5269, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Desichalkos", 0x742EA8, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Prometheum", 0x5A8156, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "DeepIron", 0x495B69, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Infuscolium", 0xCB6293, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "BlackSteel", 0x395679, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Oureclase", 0xDCA82E, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "AstralSilver", 0xC8D4D5, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Carmot", 0xA99733, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Mithril", 0x08B5C3, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Rubracium", 0x8E2727, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Quicksilver", 0x7CD3C7, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Haderoth", 0xD1531E, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Orichalcum", 0x547A38, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Celenegil", 0x94CC48, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Adamantine", 0xF04040, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Atlarus", 0xF4D603, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Tartarite", 0xFF763C, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Ignatius", 0xE87400, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "ShadowIron", 0x8D7565, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Lemurite", 0xEFEFEF, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Midasium", 0xFFA826, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Vyroxeres", 0x55E001, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Ceruclase", 0x458FAB, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Alduorite", 0xA3DEDE, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Kalendrite", 0xAA5BBD, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Vulcanite", 0xFF8448, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Sanguinite", 0xB90000, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "ShadowSteel", 0x887362, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Inolashite", 0x40AA7D, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Amordrine", 0xA98DB1, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Zinc", 0xDCDFA4, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Brass", 0xD89634, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Electrum", 0xDFD0AA, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Aluminum", 0xEDEDED, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Ardite", 0xF28900, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Manyullyn", 0xAB7EE3, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Cobalt", 0x0064FF, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Copper", 0xBF5E1F, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Lead", 0x808096, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Nickel", 0xBAB0A4, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Platinum", 0xABCDEF, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Silver", 0xB3B3B3, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Tin", 0xF2F2F2, DustTypes.METAL_DUST);
	}

	private static void addNuggets(String metal) {
		ArrayList<ItemStack> oreList = OreDictionary.getOres("orePoor" + metal);
		ArrayList<ItemStack> nuggetList = OreDictionary.getOres("nugget" + metal);
		if (oreList.isEmpty() || nuggetList.isEmpty())
			return;
		for (ItemStack poorOre : oreList) {
			BCARecipeManager.duster.addRecipe(poorOre, new ItemStack(nuggetList.get(0).getItem(), 4, nuggetList.get(0).getItemDamage()));
		}
	}

}
