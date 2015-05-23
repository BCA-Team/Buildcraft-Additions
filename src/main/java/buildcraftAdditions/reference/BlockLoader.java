package buildcraftAdditions.reference;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.ShapedOreRecipe;

import buildcraftAdditions.blocks.BlockBCKinesisPipeWood;
import buildcraftAdditions.blocks.BlockBCKinisisPipeStone;
import buildcraftAdditions.blocks.BlockBackpackStand;
import buildcraftAdditions.blocks.BlockBasicCoil;
import buildcraftAdditions.blocks.BlockBasicDuster;
import buildcraftAdditions.blocks.BlockChargingStation;
import buildcraftAdditions.blocks.BlockFluidicCompressor;
import buildcraftAdditions.blocks.BlockGhostBackpackStand;
import buildcraftAdditions.blocks.BlockHeatedFurnace;
import buildcraftAdditions.blocks.BlockItemSorter;
import buildcraftAdditions.blocks.BlockKineticCoil;
import buildcraftAdditions.blocks.BlockKineticDuster;
import buildcraftAdditions.blocks.BlockKineticEnergyBufferTier1;
import buildcraftAdditions.blocks.BlockLavaCoil;
import buildcraftAdditions.blocks.BlockMechanicalDuster;
import buildcraftAdditions.blocks.BlockSemiAutomaticDuster;
import buildcraftAdditions.blocks.BlockTest;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockCoolingTowerValve;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockCoolingTowerWalls;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockKEBT2;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockKEBT3Core;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockKEBT3Plating;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockRefineryValve;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockRefineryWalls;
import buildcraftAdditions.compat.buildcraft.BCItems;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.core.VersionCheck;
import buildcraftAdditions.reference.enums.EnumMachineUpgrades;
import buildcraftAdditions.tileEntities.TileBCKinesisPipeStonePlacer;
import buildcraftAdditions.tileEntities.TileBCKinesisPipeWoodPlacer;
import buildcraftAdditions.tileEntities.TileBackpackStand;
import buildcraftAdditions.tileEntities.TileBasicCoil;
import buildcraftAdditions.tileEntities.TileBasicDuster;
import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.tileEntities.TileCoolingTower;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;
import buildcraftAdditions.tileEntities.TileKEBT2;
import buildcraftAdditions.tileEntities.TileKEBT3;
import buildcraftAdditions.tileEntities.TileKineticCoil;
import buildcraftAdditions.tileEntities.TileKineticDuster;
import buildcraftAdditions.tileEntities.TileKineticEnergyBufferTier1;
import buildcraftAdditions.tileEntities.TileLavaCoil;
import buildcraftAdditions.tileEntities.TileMechanicalDuster;
import buildcraftAdditions.tileEntities.TileRefinery;
import buildcraftAdditions.tileEntities.TileSemiAutomaticDuster;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockLoader {
	public static Block fluidicCompressorBlock;
	public static Block chargingStationBlock;
	public static Block heatedFurnaceBlock;
	public static Block basicCoilBlock;
	public static Block basicDusterBlock;
	public static Block semiAutomaticDusterBlock;
	public static Block mechanicalDusterBlock;
	public static Block kineticDusterBlock;
	public static Block lavaCoilBlock;
	public static Block kineticCoil;
	public static Block kinesisPipeWood;
	public static Block kinisisPipeStone;
	public static BlockKineticEnergyBufferTier1 kebT1;
	public static Block kebT2;
	public static Block kebT3Core;
	public static Block kebT3Plating;
	public static Block refineryWalls;
	public static Block refineryValve;
	public static Block coolingTowerWalls;
	public static Block coolingTowerValve;
	public static Block itemSorter;
	public static Block testBlock;
	public static Block backpackStand;
	public static Block backpackStandGhost;

	public static void loadBlocks() {
		//kinesis blocks
		kinesisPipeWood = new BlockBCKinesisPipeWood();
		kinisisPipeStone = new BlockBCKinisisPipeStone();

		//machines
		fluidicCompressorBlock = new BlockFluidicCompressor();
		chargingStationBlock = new BlockChargingStation();

		//furnace + coils
		heatedFurnaceBlock = new BlockHeatedFurnace();
		basicCoilBlock = new BlockBasicCoil();
		lavaCoilBlock = new BlockLavaCoil();
		kineticCoil = new BlockKineticCoil();

		//dusters
		basicDusterBlock = new BlockBasicDuster();
		semiAutomaticDusterBlock = new BlockSemiAutomaticDuster();
		mechanicalDusterBlock = new BlockMechanicalDuster();
		kineticDusterBlock = new BlockKineticDuster();

		//KEB
		kebT1 = new BlockKineticEnergyBufferTier1();
		kebT2 = new MultiBlockKEBT2();
		kebT3Core = new MultiBlockKEBT3Core();
		kebT3Plating = new MultiBlockKEBT3Plating();

		//refinery
		refineryWalls = new MultiBlockRefineryWalls();
		refineryValve = new MultiBlockRefineryValve();

		//cooling tower
		coolingTowerWalls = new MultiBlockCoolingTowerWalls();
		coolingTowerValve = new MultiBlockCoolingTowerValve();

		//sort-o-tron
		itemSorter = new BlockItemSorter();

		//backpack stand
		backpackStand = new BlockBackpackStand();
		backpackStandGhost = new BlockGhostBackpackStand();
		//other
		if (VersionCheck.currentVersion.contains("@")) {
			testBlock = new BlockTest();
		}


	}

	public static void addRecipes() {
		if (ConfigurationHandler.enabled("ChargingStation")) {
			GameRegistry.addRecipe(new ItemStack(chargingStationBlock), "I I", "WKW", "I I", 'I', BCItems.IRON_GEAR, 'W', BCItems.PIPE_POWER_WOOD, 'K', ItemLoader.powerCapsuleTier2);
		}

		if (ConfigurationHandler.enabled("PowerCapsules")) {
			GameRegistry.addRecipe(new ItemStack(ItemLoader.powerCapsuleTier1), "IGI", "IRI", "IGI", 'I', Items.iron_ingot, 'G', Items.gold_ingot, 'R', Blocks.redstone_block);
			GameRegistry.addRecipe(new ItemStack(ItemLoader.powerCapsuleTier2), "GDG", "GPG", "GDG", 'G', Items.gold_ingot, 'D', Items.diamond, 'P', ItemLoader.powerCapsuleTier1);
			GameRegistry.addRecipe(new ItemStack(ItemLoader.powerCapsuleTier3), "DED", "DPD", "DED", 'D', Items.diamond, 'E', Items.emerald, 'P', ItemLoader.powerCapsuleTier2);
		}

		if (ConfigurationHandler.enabled("Duster")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(basicDusterBlock), "GIG", "SLS", "SSS", 'G', BCItems.STONE_GEAR, 'I', Items.iron_ingot, 'S', Blocks.stone, 'L', "slimeball"));
			GameRegistry.addRecipe(new ItemStack(mechanicalDusterBlock), "GMG", "SFS", "SSS", 'G', BCItems.IRON_GEAR, 'M', Items.gold_ingot, 'F', ItemLoader.itemGrindingWheel, 'S', Blocks.stone);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(semiAutomaticDusterBlock), "GMG", "PLP", "SSS", 'G', BCItems.IRON_GEAR, 'M', Items.gold_ingot, 'P', BCItems.PIPE_ITEMS_GOLD, 'L', "slimeball", 'S', Blocks.stone));
			GameRegistry.addRecipe(new ItemStack(kineticDusterBlock), "GGG", "P P", "IDI", 'G', Blocks.glass, 'P', BCItems.PIPE_ITEMS_GOLD, 'I', BCItems.GOLD_GEAR, 'D', BCItems.DIAMOND_GEAR);
			GameRegistry.addRecipe(new ItemStack(ItemLoader.itemGrindingWheel), "FFF", "FGF", "FFF", 'F', Items.flint, 'G', BCItems.STONE_GEAR);
		}

		if (ConfigurationHandler.enabled("Coils")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.itemIronWireUnhardened, 2), "DDD", 'D', "dustIron"));
			GameRegistry.addSmelting(ItemLoader.itemIronWireUnhardened, new ItemStack(ItemLoader.itemIronWire, 2), 0.5f);
			GameRegistry.addRecipe(new ItemStack(basicCoilBlock), "WWW", "WIW", "WWW", 'W', ItemLoader.itemIronWire, 'I', Items.iron_ingot);

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.goldWireUnhardened, 2), "DDD", 'D', "dustGold"));
			GameRegistry.addSmelting(ItemLoader.goldWireUnhardened, new ItemStack(ItemLoader.goldWire, 2), 0.5f);
			GameRegistry.addRecipe(new ItemStack(lavaCoilBlock), "WWW", "WIW", "WWW", 'W', ItemLoader.goldWire, 'I', Items.iron_ingot);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.diamondWireUnhardened, 2), "DDD", 'D', "dustDiamond"));
			GameRegistry.addSmelting(ItemLoader.diamondWireUnhardened, new ItemStack(ItemLoader.diamondWire, 2), 0.5f);
			GameRegistry.addRecipe(new ItemStack(kineticCoil), "WWW", "WIW", "WWW", 'W', ItemLoader.diamondWire, 'I', Items.iron_ingot);
		}

		if (ConfigurationHandler.enabled("HeatedFurnace"))
			GameRegistry.addRecipe(new ItemStack(heatedFurnaceBlock), "III", "IFI", "III", 'I', Items.iron_ingot, 'F', Blocks.furnace);

		if (ConfigurationHandler.enabled("KineticEnergyBuffer")) {
			GameRegistry.addRecipe(kebT1.createEmptyKEB(), "IBI", "PBP", "IBI", 'I', Items.iron_ingot, 'B', ItemLoader.powerCapsuleTier1, 'P', BCItems.PIPE_POWER_GOLD);
			GameRegistry.addRecipe(new ItemStack(kebT2), "III", "PBP", "III", 'I', Items.iron_ingot, 'B', ItemLoader.powerCapsuleTier2, 'P', BCItems.PIPE_POWER_GOLD);
			GameRegistry.addRecipe(new ItemStack(kebT3Core), "DBD", "PBP", "DBD", 'D', Items.diamond, 'B', ItemLoader.powerCapsuleTier3, 'P', BCItems.PIPE_POWER_DIAMOND);
			GameRegistry.addRecipe(new ItemStack(kebT3Plating), "PGP", "GGG", "III", 'P', BCItems.PIPE_POWER_DIAMOND, 'G', Items.gold_ingot, 'I', Items.iron_ingot);
		}

		if (ConfigurationHandler.enabled("MultiBlockRefining")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refineryWalls, 10), "PPP", "PDP", "PPP", 'P', ItemLoader.heatPlating, 'D', "dustDiamond"));
			GameRegistry.addRecipe(new ItemStack(refineryValve, 4), " P ", "PBP", " P ", 'P', ItemLoader.heatPlating, 'B', Blocks.iron_bars);
			GameRegistry.addRecipe(new ItemStack(coolingTowerWalls, 10), "PPP", "PDP", "PPP", 'P', ItemLoader.heatPlating, 'D', Items.redstone);
			GameRegistry.addRecipe(new ItemStack(coolingTowerValve), "V", 'V', refineryValve);
		}

		if (ConfigurationHandler.enabled("MachineUpgrades")) {
			GameRegistry.addRecipe(new ItemStack(ItemLoader.upgrade, 1, EnumMachineUpgrades.AUTO_OUTPUT.ordinal()), "IEI", "PBP", "IEI", 'I', Items.iron_ingot, 'E', Items.ender_pearl, 'P', Blocks.piston, 'B', ItemLoader.blankUpgrade);
			GameRegistry.addRecipe(new ItemStack(ItemLoader.upgrade, 1, EnumMachineUpgrades.EFFICIENCY_1.ordinal()), "CIC", "IBI", "CIC", 'C', ItemLoader.powerCapsuleTier1, 'I', Items.iron_ingot, 'B', ItemLoader.blankUpgrade);
			GameRegistry.addRecipe(new ItemStack(ItemLoader.upgrade, 1, EnumMachineUpgrades.EFFICIENCY_2.ordinal()), "GCG", "UGU", "GCG", 'G', Items.gold_ingot, 'C', ItemLoader.powerCapsuleTier2, 'U', new ItemStack(ItemLoader.upgrade, 1, EnumMachineUpgrades.EFFICIENCY_1.ordinal()));
			GameRegistry.addRecipe(new ItemStack(ItemLoader.upgrade, 1, EnumMachineUpgrades.EFFICIENCY_3.ordinal()), "CDC", "UGU", "CDC", 'C', ItemLoader.powerCapsuleTier3, 'D', Items.diamond, 'U', new ItemStack(ItemLoader.upgrade, 1, EnumMachineUpgrades.EFFICIENCY_2.ordinal()), 'G', Items.gold_ingot);
			GameRegistry.addRecipe(new ItemStack(ItemLoader.upgrade, 1, EnumMachineUpgrades.SPEED_1.ordinal()), "IGI", "IBI", "IGI", 'I', Items.iron_ingot, 'G', Items.glowstone_dust, 'B', ItemLoader.blankUpgrade);
			GameRegistry.addRecipe(new ItemStack(ItemLoader.upgrade, 1, EnumMachineUpgrades.SPEED_2.ordinal()), "GBG", "GUG", "GBG", 'G', Items.gold_ingot, 'B', Blocks.glowstone, 'U', new ItemStack(ItemLoader.upgrade, 1, EnumMachineUpgrades.SPEED_1.ordinal()));
			GameRegistry.addRecipe(new ItemStack(ItemLoader.upgrade, 1, EnumMachineUpgrades.SPEED_3.ordinal()), "GBG", "DUD", "GBG", 'G', Items.gold_ingot, 'B', Blocks.glowstone, 'D', Items.diamond, 'U', new ItemStack(ItemLoader.upgrade, 1, EnumMachineUpgrades.SPEED_2.ordinal()));
			GameRegistry.addRecipe(new ItemStack(ItemLoader.upgrade, 1, EnumMachineUpgrades.AUTO_IMPORT.ordinal()), "ISI", "EBE", "ISI", 'I', Items.iron_ingot, 'E', Items.ender_pearl, 'S', Blocks.sticky_piston, 'B', ItemLoader.blankUpgrade);
		}

		if (ConfigurationHandler.enabled("ColorSorter"))
			GameRegistry.addRecipe(new ItemStack(itemSorter), "SUS", "PCP", "SGS", 'S', Blocks.stone, 'U', EnumMachineUpgrades.AUTO_OUTPUT.getItemStack(), 'P', BCItems.PIPE_ITEMS_LAPIS, 'C', Blocks.chest, 'G', BCItems.PIPE_ITEMS_GOLD);

		if (!ConfigurationHandler.forceEnableBCRefinery) {
			ItemStack stack = new ItemStack(BCItems.REFINERY);
			ArrayList recipeList = (ArrayList) CraftingManager.getInstance().getRecipeList();
			for (int t = 0; t < recipeList.size(); t++) {
				IRecipe recipe = (IRecipe) recipeList.get(t);
				ItemStack recipeResult = recipe.getRecipeOutput();
				if (ItemStack.areItemStacksEqual(stack, recipeResult)) {
					recipeList.remove(t);
					break;
				}
			}
		}
	}

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileFluidicCompressor.class, "TileFluidicCompressor");
		GameRegistry.registerTileEntity(TileChargingStation.class, "TileChargingStation");
		GameRegistry.registerTileEntity(TileHeatedFurnace.class, "TileHeatedFurnace");
		GameRegistry.registerTileEntity(TileBasicCoil.class, "TileBasicCoil");
		GameRegistry.registerTileEntity(TileLavaCoil.class, "TileCoilLava");
		GameRegistry.registerTileEntity(TileKineticCoil.class, "TileCoilKinetic");
		GameRegistry.registerTileEntity(TileBasicDuster.class, "TileBasicDuster");
		GameRegistry.registerTileEntity(TileSemiAutomaticDuster.class, "TileSemiAutomaticDuster");
		GameRegistry.registerTileEntity(TileMechanicalDuster.class, "TileMechanicalDuster");
		GameRegistry.registerTileEntity(TileKineticDuster.class, "TileKineticDuster");
		GameRegistry.registerTileEntity(TileKineticEnergyBufferTier1.class, "TileKineticEnergyBufferTier1");
		GameRegistry.registerTileEntity(TileKEBT2.class, "TileKineticEnergyBufferTier2");
		GameRegistry.registerTileEntity(TileKEBT3.class, "TileKineticEnergyBufferTier3");
		GameRegistry.registerTileEntity(TileRefinery.class, "TileRefinery");
		GameRegistry.registerTileEntity(TileCoolingTower.class, "CoolingTower");
		GameRegistry.registerTileEntity(TileBCKinesisPipeStonePlacer.class, "BCKinesisPipeStonePlacer");
		GameRegistry.registerTileEntity(TileBCKinesisPipeWoodPlacer.class, "BCKinesisPipeWoodPlacer");
		GameRegistry.registerTileEntity(TileBackpackStand.class, "TileBackpackStand");
	}
}
