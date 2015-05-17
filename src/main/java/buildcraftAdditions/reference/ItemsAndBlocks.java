package buildcraftAdditions.reference;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;

import buildcraftAdditions.armour.ItemHoverBoots;
import buildcraftAdditions.armour.ItemKineticBackpack;
import buildcraftAdditions.armour.ItemRocketPants;
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
import buildcraftAdditions.items.ItemBase;
import buildcraftAdditions.items.ItemCanister;
import buildcraftAdditions.items.ItemMachineConfigurator;
import buildcraftAdditions.items.ItemMachineUpgrade;
import buildcraftAdditions.items.ItemPoweredBase;
import buildcraftAdditions.items.ItemStickBCA;
import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.items.Tools.ItemPipeColoringTool;
import buildcraftAdditions.items.Tools.ItemPortableLaser;
import buildcraftAdditions.items.Tools.ItemToolUpgrade;
import buildcraftAdditions.items.dust.ItemDust;
import buildcraftAdditions.items.itemBlocks.ItemBlockKEB;
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
public final class ItemsAndBlocks {
	public static ItemCanister ironCanister;
	public static ItemCanister goldCanister;
	public static ItemCanister diamondCanister;
	public static BlockFluidicCompressor fluidicCompressorBlock;
	public static BlockChargingStation chargingStationBlock;
	public static BlockHeatedFurnace heatedFurnaceBlock;
	public static BlockBasicCoil basicCoilBlock;
	public static BlockBasicDuster basicDusterBlock;
	public static BlockSemiAutomaticDuster semiAutomaticDusterBlock;
	public static BlockMechanicalDuster mechanicalDusterBlock;
	public static BlockKineticDuster kineticDusterBlock;
	public static BlockLavaCoil lavaCoilBlock;
	public static BlockKineticCoil kineticCoil;
	public static BlockBCKinesisPipeWood kinesisPipeWood;
	public static BlockBCKinisisPipeStone kinisisPipeStone;
	public static BlockKineticEnergyBufferTier1 kebT1;
	public static MultiBlockKEBT2 kebT2;
	public static MultiBlockKEBT3Core kebT3Core;
	public static MultiBlockKEBT3Plating kebT3Plating;
	public static MultiBlockRefineryWalls refineryWalls;
	public static MultiBlockRefineryValve refineryValve;
	public static MultiBlockCoolingTowerWalls coolingTowerWalls;
	public static MultiBlockCoolingTowerValve coolingTowerValve;
	public static BlockItemSorter itemSorter;
	public static Block testBlock;
	public static Block backpackStand;
	public static Block backpackStandGhost;

	public static ItemPoweredBase powerCapsuleTier1;
	public static ItemPoweredBase powerCapsuleTier2;
	public static ItemPoweredBase powerCapsuleTier3;
	public static ItemStickBCA ironStick;
	public static ItemStickBCA goldStick;
	public static ItemStickBCA diamondStick;
	public static ItemStickBCA emeraldStick;
	public static ItemStickBCA netherStarStick;
	public static ItemStickBCA quartzStick;
	public static ItemStickBCA enderStick;
	public static ItemStickBCA redstoneStick;
	public static ItemStickBCA glowstoneStick;
	public static ItemStickBCA slimeStick;
	public static ItemStickBCA blazeStick;
	public static Item toolCore;
	public static ItemToolUpgrade toolUpgradeHoe;
	public static ItemToolUpgrade toolUpgradeDigger;
	public static ItemToolUpgrade toolUpgradeDrill;
	public static ItemToolUpgrade toolUpgradeChainsaw;
	public static ItemToolUpgrade toolUpgradeArea;
	public static ItemToolUpgrade toolUpgradeSilky;
	public static ItemToolUpgrade toolUpgradeFortune1;
	public static ItemToolUpgrade toolUpgradeFortune2;
	public static ItemToolUpgrade toolUpgradeFortune3;
	public static Item itemGrindingWheel;
	public static Item itemIronWireUnhardened;
	public static Item itemIronWire;
	public static Item goldWireUnhardened;
	public static Item goldWire;
	public static Item diamondWireUnhardened;
	public static Item diamondWire;
	public static Item itemKineticMultiTool;
	public static Item machineConfigurator;
	public static Item heatPlating;
	public static Item heatPlatingRaw;
	public static Item dust;
	public static Item pipeColoringTool;
	public static Item upgrade;
	public static Item blankUpgrade;
	public static Item portableLaser;
	public static Item gildedRedMetalIngot;
	public static Item conductivePlateRaw;
	public static Item conductivePlate;

	public static ItemArmor kineticBackpack;
	public static ItemArmor rocketPants;
	public static ItemArmor hoverBoots;


	public static void init() {
		ironCanister = new ItemCanister("ironCanister", 2000);
		GameRegistry.registerItem(ironCanister, "ironCanister");

		goldCanister = new ItemCanister("goldCanister", 8000);
		GameRegistry.registerItem(goldCanister, "goldCanister");

		diamondCanister = new ItemCanister("diamondCanister", 64000);
		GameRegistry.registerItem(diamondCanister, "diamondCanister");

		powerCapsuleTier1 = new ItemPoweredBase("PowerCapsuleTier1", 100000, 1024);
		GameRegistry.registerItem(powerCapsuleTier1, "PowerCapsuleTier1");

		powerCapsuleTier2 = new ItemPoweredBase("PowerCapsuleTier2", 300000, 4096);
		GameRegistry.registerItem(powerCapsuleTier2, "powerCapsuleTier2");

		powerCapsuleTier3 = new ItemPoweredBase("PowerCapsuleTier3", 1000000, 16384);
		GameRegistry.registerItem(powerCapsuleTier3, "powerCapsuleTier3");

		ironStick = new ItemStickBCA("Iron");
		GameRegistry.registerItem(ironStick, "stickIron");

		goldStick = new ItemStickBCA("Gold");
		GameRegistry.registerItem(goldStick, "stickGold");

		diamondStick = new ItemStickBCA("Diamond");
		GameRegistry.registerItem(diamondStick, "stickDiamond");

		emeraldStick = new ItemStickBCA("Emerald");
		GameRegistry.registerItem(emeraldStick, "stickEmerald");

		netherStarStick = new ItemStickBCA("NetherStar");
		GameRegistry.registerItem(netherStarStick, "stickNetherStar");

		quartzStick = new ItemStickBCA("Quartz");
		GameRegistry.registerItem(quartzStick, "stickQuartz");

		enderStick = new ItemStickBCA("Ender");
		GameRegistry.registerItem(enderStick, "stickEnder");

		redstoneStick = new ItemStickBCA("Redstone");
		GameRegistry.registerItem(redstoneStick, "stickRedstone");

		glowstoneStick = new ItemStickBCA("Glowstone");
		GameRegistry.registerItem(glowstoneStick, "stickGlowstone");

		slimeStick = new ItemStickBCA("Slime");
		GameRegistry.registerItem(slimeStick, "stickSlime");

		blazeStick = new ItemStickBCA("Blaze");
		GameRegistry.registerItem(blazeStick, "stickBlaze");

		toolCore = new ItemBase("toolCore");
		GameRegistry.registerItem(toolCore, "toolCore");

		toolUpgradeHoe = new ItemToolUpgrade("Hoe", "upgrades/hoe");
		GameRegistry.registerItem(toolUpgradeHoe, "toolUpgradeHoe");

		toolUpgradeDigger = new ItemToolUpgrade("Digger", "upgrades/digger");
		GameRegistry.registerItem(toolUpgradeDigger, "toolUpgradeDigger");

		toolUpgradeDrill = new ItemToolUpgrade("Drill", "upgrades/drill");
		GameRegistry.registerItem(toolUpgradeDrill, "toolUpgradeDrill");

		toolUpgradeChainsaw = new ItemToolUpgrade("Chainsaw", "upgrades/chainsaw");
		GameRegistry.registerItem(toolUpgradeChainsaw, "toolUpgradeChaisaw");

		toolUpgradeArea = new ItemToolUpgrade("Area", "upgrades/area");
		GameRegistry.registerItem(toolUpgradeArea, "toolUpgradeArea");

		toolUpgradeSilky = new ItemToolUpgrade("Silky", "upgrades/silky");
		GameRegistry.registerItem(toolUpgradeSilky, "toolUpgradeSilky");

		toolUpgradeFortune1 = new ItemToolUpgrade("Fortune1", "upgrades/fortune1");
		GameRegistry.registerItem(toolUpgradeFortune1, "toolUpgradeFortune1");

		toolUpgradeFortune2 = new ItemToolUpgrade("Fortune2", "upgrades/fortune2");
		GameRegistry.registerItem(toolUpgradeFortune2, "toolUpgradeFortune2");

		toolUpgradeFortune3 = new ItemToolUpgrade("Fortune3", "upgrades/fortune3");
		GameRegistry.registerItem(toolUpgradeFortune3, "toolUpgradeFortune3");

		itemKineticMultiTool = new ItemKineticMultiTool();
		GameRegistry.registerItem(itemKineticMultiTool, "itemKineticMultiTool");

		itemGrindingWheel = new ItemBase("grindingWheel");
		GameRegistry.registerItem(itemGrindingWheel, "grindingWheel");

		itemIronWireUnhardened = new ItemBase("wireIronUnhardened");
		GameRegistry.registerItem(itemIronWireUnhardened, "wireIronUnhardened");

		itemIronWire = new ItemBase("wireIron");
		GameRegistry.registerItem(itemIronWire, "wireIron");

		goldWireUnhardened = new ItemBase("wireGoldUnhardened");
		GameRegistry.registerItem(goldWireUnhardened, "wireGoldUnhardened");

		goldWire = new ItemBase("wireGold");
		GameRegistry.registerItem(goldWire, "wireGold");

		diamondWireUnhardened = new ItemBase("wireDiamondUnhardened");
		GameRegistry.registerItem(diamondWireUnhardened, "wireDiamondUnhardened");

		diamondWire = new ItemBase("wireDiamond");
		GameRegistry.registerItem(diamondWire, "wireDiamond");

		machineConfigurator = new ItemMachineConfigurator();
		GameRegistry.registerItem(machineConfigurator, "machineConfigurator");

		heatPlatingRaw = new ItemBase("heatPlatingRaw");
		GameRegistry.registerItem(heatPlatingRaw, "heatPlatingRaw");

		heatPlating = new ItemBase("heatPlating");
		GameRegistry.registerItem(heatPlating, "heatPlating");

		dust = new ItemDust();
		GameRegistry.registerItem(dust, "dust");

		pipeColoringTool = new ItemPipeColoringTool();
		GameRegistry.registerItem(pipeColoringTool, "pipeColoringTool");

		upgrade = new ItemMachineUpgrade();
		GameRegistry.registerItem(upgrade, "upgrade");

		blankUpgrade = new ItemBase("baseUpgrade", "upgrades/base");
		GameRegistry.registerItem(blankUpgrade, "blankUpgrade");

		portableLaser = new ItemPortableLaser();
		GameRegistry.registerItem(portableLaser, "portableLaser");

		kineticBackpack = new ItemKineticBackpack();
		GameRegistry.registerItem(kineticBackpack, "kineticBackpack");

		rocketPants = new ItemRocketPants();
		GameRegistry.registerItem(rocketPants, "rocketPants");

		gildedRedMetalIngot = new ItemBase("gildedRedMetalIngot");
		GameRegistry.registerItem(gildedRedMetalIngot, "ingotGildedRedMetal");
		OreDictionary.registerOre("ingotGildedRedMetal", gildedRedMetalIngot);

		conductivePlateRaw = new ItemBase("conductivePlateRaw");
		GameRegistry.registerItem(conductivePlateRaw, "condictuvePlateRaw");

		conductivePlate = new ItemBase("conductivePlate");
		GameRegistry.registerItem(conductivePlate, "condictuvePlate");

		hoverBoots = new ItemHoverBoots();
		GameRegistry.registerItem(hoverBoots, "hoverBoots");

		//START BLOCKS

		kinesisPipeWood = new BlockBCKinesisPipeWood();
		GameRegistry.registerBlock(kinesisPipeWood, "kinesisPipeWood");

		kinisisPipeStone = new BlockBCKinisisPipeStone();
		GameRegistry.registerBlock(kinisisPipeStone, "kinesisPipeCobble");

		fluidicCompressorBlock = new BlockFluidicCompressor();
		GameRegistry.registerBlock(fluidicCompressorBlock, "blockFluidicCompressor");

		chargingStationBlock = new BlockChargingStation();
		GameRegistry.registerBlock(chargingStationBlock, "blockChargingStation");

		heatedFurnaceBlock = new BlockHeatedFurnace();
		GameRegistry.registerBlock(heatedFurnaceBlock, "blockHeatedFurnace");

		basicCoilBlock = new BlockBasicCoil();
		GameRegistry.registerBlock(basicCoilBlock, "blockCoilBasic");

		lavaCoilBlock = new BlockLavaCoil();
		GameRegistry.registerBlock(lavaCoilBlock, "blockCoilLava");

		basicDusterBlock = new BlockBasicDuster();
		GameRegistry.registerBlock(basicDusterBlock, "blockDusterBasic");

		semiAutomaticDusterBlock = new BlockSemiAutomaticDuster();
		GameRegistry.registerBlock(semiAutomaticDusterBlock, "blockDusterSemiAutomatic");

		mechanicalDusterBlock = new BlockMechanicalDuster();
		GameRegistry.registerBlock(mechanicalDusterBlock, "blockDusterMechanical");

		kineticDusterBlock = new BlockKineticDuster();
		GameRegistry.registerBlock(kineticDusterBlock, "blockDusterKinetic");

		kineticCoil = new BlockKineticCoil();
		GameRegistry.registerBlock(kineticCoil, "blockCoilKinetic");

		kebT1 = new BlockKineticEnergyBufferTier1();
		GameRegistry.registerBlock(kebT1, ItemBlockKEB.class, "KEBT1");

		kebT2 = new MultiBlockKEBT2();
		GameRegistry.registerBlock(kebT2, "KEBT2");

		kebT3Core = new MultiBlockKEBT3Core();
		GameRegistry.registerBlock(kebT3Core, "KEBT3Core");

		kebT3Plating = new MultiBlockKEBT3Plating();
		GameRegistry.registerBlock(kebT3Plating, "KEBT3Plating");

		refineryWalls = new MultiBlockRefineryWalls();
		GameRegistry.registerBlock(refineryWalls, "refinerywals");

		refineryValve = new MultiBlockRefineryValve();
		GameRegistry.registerBlock(refineryValve, "refineryValve");

		coolingTowerWalls = new MultiBlockCoolingTowerWalls();
		GameRegistry.registerBlock(coolingTowerWalls, "blockCoolingTowerWalls)");

		coolingTowerValve = new MultiBlockCoolingTowerValve();
		GameRegistry.registerBlock(coolingTowerValve, "blockCoolingTowerValve");

		itemSorter = new BlockItemSorter();

		backpackStand = new BlockBackpackStand();
		GameRegistry.registerBlock(backpackStand, "backpackStand");

		backpackStandGhost = new BlockGhostBackpackStand();
		GameRegistry.registerBlock(backpackStandGhost, "backpackstandGhost");

		if (VersionCheck.currentVersion.contains("@")) {
			testBlock = new BlockTest();
			GameRegistry.registerBlock(testBlock, "testingBlock");
		}
	}

	public static void addRecipes() {
		if (ConfigurationHandler.enabled("MultiTools")) {
			addStickRecipe(ItemsAndBlocks.ironStick, 1000, "ingotIron");
			addStickRecipe(ItemsAndBlocks.goldStick, 2000, "ingotGold");
			addStickRecipe(ItemsAndBlocks.diamondStick, 3000, "stickGold", "gemDiamond");
			addStickRecipe(ItemsAndBlocks.netherStarStick, 10000, "stickEmerald", Items.nether_star);
			addStickRecipe(ItemsAndBlocks.quartzStick, 2000, "stickIron", "gemQuartz");
			addStickRecipe(ItemsAndBlocks.enderStick, 5000, "stickGold", Items.ender_pearl);
			addStickRecipe(ItemsAndBlocks.redstoneStick, 3000, "stickIron", "dustRedstone");
			addStickRecipe(ItemsAndBlocks.glowstoneStick, 3000, "stickIron", "dustGlowstone");
			addStickRecipe(ItemsAndBlocks.slimeStick, 2000, "stickIron", "slimeball");
			addStickRecipe(ItemsAndBlocks.blazeStick, 4000, "stickQuartz", Items.blaze_rod);
			addAssemblyRecipe("kineticTool", ItemsAndBlocks.itemKineticMultiTool, 8000, "gemDiamond", "gemDiamond", "gemDiamond", "stickIron", ItemsAndBlocks.toolCore);
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeChainsaw);
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeDrill);
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeDigger);
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeHoe);
			/*BuildcraftRecipeRegistry.integrationTable.addRecipe(new ToolCoreRecipe());
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("drill"));
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("digger"));
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("chainsaw"));
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("hoe"));*/
		}

		if (ConfigurationHandler.enabled("MultiToolsArea")) {
			//BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("area"));
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeArea, new ItemStack(Blocks.sticky_piston), new ItemStack(Items.ender_pearl), "ingotGold");
		}

		if (ConfigurationHandler.enabled("MultiToolsSilky")) {
			//BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("silky"));
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeSilky, new ItemStack(Items.string, 3), "slimeball", "ingotGold");
		}

		if (ConfigurationHandler.enabled("MultiToolsFortune")) {
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeFortune1, new ItemStack(Items.string, 3), "gemLapis", "blockLapis", "ingotGold");
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeFortune2, new ItemStack(ItemsAndBlocks.toolUpgradeFortune1), "gemDiamond", "blockLapis", "ingotGold");
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeFortune3, new ItemStack(ItemsAndBlocks.toolUpgradeFortune2), "gemEmerald", "blockLapis", "ingotGold");
			/*BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("fortune1"));
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("fortune2"));
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("fortune3"));*/
		}

		if (ConfigurationHandler.enabled("FluidCanisters")) {
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.ironCanister, 4), "PIP", "IGI", "PIP", 'P', BCItems.SEALANT, 'I', Items.iron_ingot, 'G', Blocks.glass_pane);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.goldCanister), "PGP", "GIG", "PGP", 'P', BCItems.SEALANT, 'G', Items.gold_ingot, 'I', ItemsAndBlocks.ironCanister);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.diamondCanister), "PDP", "DGD", "PDP", 'P', BCItems.SEALANT, 'D', Items.diamond, 'G', ItemsAndBlocks.goldCanister);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.fluidicCompressorBlock), "IFI", "PGP", "IMI", 'I', BCItems.IRON_GEAR, 'F', BCItems.FLOODGATE, 'P', Blocks.piston, 'G', ItemsAndBlocks.goldCanister, 'M', BCItems.PUMP);
		}

		if (ConfigurationHandler.enabled("ChargingStation")) {
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.chargingStationBlock), "I I", "WKW", "I I", 'I', BCItems.IRON_GEAR, 'W', BCItems.PIPE_POWER_WOOD, 'K', ItemsAndBlocks.powerCapsuleTier2);
		}

		if (ConfigurationHandler.enabled("PowerCapsules")) {
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.powerCapsuleTier1), "IGI", "IRI", "IGI", 'I', Items.iron_ingot, 'G', Items.gold_ingot, 'R', Blocks.redstone_block);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.powerCapsuleTier2), "GDG", "GPG", "GDG", 'G', Items.gold_ingot, 'D', Items.diamond, 'P', ItemsAndBlocks.powerCapsuleTier1);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.powerCapsuleTier3), "DED", "DPD", "DED", 'D', Items.diamond, 'E', Items.emerald, 'P', ItemsAndBlocks.powerCapsuleTier2);
		}

		if (ConfigurationHandler.enabled("Duster")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.basicDusterBlock), "GIG", "SLS", "SSS", 'G', BCItems.STONE_GEAR, 'I', Items.iron_ingot, 'S', Blocks.stone, 'L', "slimeball"));
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.mechanicalDusterBlock), "GMG", "SFS", "SSS", 'G', BCItems.IRON_GEAR, 'M', Items.gold_ingot, 'F', ItemsAndBlocks.itemGrindingWheel, 'S', Blocks.stone);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.semiAutomaticDusterBlock), "GMG", "PLP", "SSS", 'G', BCItems.IRON_GEAR, 'M', Items.gold_ingot, 'P', BCItems.PIPE_ITEMS_GOLD, 'L', "slimeball", 'S', Blocks.stone));
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.kineticDusterBlock), "GGG", "P P", "IDI", 'G', Blocks.glass, 'P', BCItems.PIPE_ITEMS_GOLD, 'I', BCItems.GOLD_GEAR, 'D', BCItems.DIAMOND_GEAR);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.itemGrindingWheel), "FFF", "FGF", "FFF", 'F', Items.flint, 'G', BCItems.STONE_GEAR);
		}

		if (ConfigurationHandler.enabled("Coils")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.itemIronWireUnhardened, 2), "DDD", 'D', "dustIron"));
			GameRegistry.addSmelting(ItemsAndBlocks.itemIronWireUnhardened, new ItemStack(ItemsAndBlocks.itemIronWire, 2), 0.5f);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.basicCoilBlock), "WWW", "WIW", "WWW", 'W', ItemsAndBlocks.itemIronWire, 'I', Items.iron_ingot);

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.goldWireUnhardened, 2), "DDD", 'D', "dustGold"));
			GameRegistry.addSmelting(ItemsAndBlocks.goldWireUnhardened, new ItemStack(ItemsAndBlocks.goldWire, 2), 0.5f);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.lavaCoilBlock), "WWW", "WIW", "WWW", 'W', ItemsAndBlocks.goldWire, 'I', Items.iron_ingot);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.diamondWireUnhardened, 2), "DDD", 'D', "dustDiamond"));
			GameRegistry.addSmelting(ItemsAndBlocks.diamondWireUnhardened, new ItemStack(ItemsAndBlocks.diamondWire, 2), 0.5f);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.kineticCoil), "WWW", "WIW", "WWW", 'W', ItemsAndBlocks.diamondWire, 'I', Items.iron_ingot);
		}

		if (ConfigurationHandler.enabled("HeatedFurnace"))
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.heatedFurnaceBlock), "III", "IFI", "III", 'I', Items.iron_ingot, 'F', Blocks.furnace);

		if (ConfigurationHandler.enabled("KineticEnergyBuffer")) {
			GameRegistry.addRecipe(ItemsAndBlocks.kebT1.createEmptyKEB(), "IBI", "PBP", "IBI", 'I', Items.iron_ingot, 'B', ItemsAndBlocks.powerCapsuleTier1, 'P', BCItems.PIPE_POWER_GOLD);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.kebT2), "III", "PBP", "III", 'I', Items.iron_ingot, 'B', ItemsAndBlocks.powerCapsuleTier2, 'P', BCItems.PIPE_POWER_GOLD);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.kebT3Core), "DBD", "PBP", "DBD", 'D', Items.diamond, 'B', ItemsAndBlocks.powerCapsuleTier3, 'P', BCItems.PIPE_POWER_DIAMOND);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.kebT3Plating), "PGP", "GGG", "III", 'P', BCItems.PIPE_POWER_DIAMOND, 'G', Items.gold_ingot, 'I', Items.iron_ingot);
		}

		if (ConfigurationHandler.enabled("MultiBlockRefining")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.refineryWalls, 10), "PPP", "PDP", "PPP", 'P', ItemsAndBlocks.heatPlating, 'D', "dustDiamond"));
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.refineryValve, 4), " P ", "PBP", " P ", 'P', ItemsAndBlocks.heatPlating, 'B', Blocks.iron_bars);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.coolingTowerWalls, 10), "PPP", "PDP", "PPP", 'P', ItemsAndBlocks.heatPlating, 'D', Items.redstone);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.coolingTowerValve), "V", 'V', ItemsAndBlocks.refineryValve);
		}

		if (ConfigurationHandler.enabled("MachineUpgrades")) {
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.upgrade, 1, EnumMachineUpgrades.AUTO_OUTPUT.ordinal()), "IEI", "PBP", "IEI", 'I', Items.iron_ingot, 'E', Items.ender_pearl, 'P', Blocks.piston, 'B', ItemsAndBlocks.blankUpgrade);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.upgrade, 1, EnumMachineUpgrades.EFFICIENCY_1.ordinal()), "CIC", "IBI", "CIC", 'C', ItemsAndBlocks.powerCapsuleTier1, 'I', Items.iron_ingot, 'B', ItemsAndBlocks.blankUpgrade);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.upgrade, 1, EnumMachineUpgrades.EFFICIENCY_2.ordinal()), "GCG", "UGU", "GCG", 'G', Items.gold_ingot, 'C', ItemsAndBlocks.powerCapsuleTier2, 'U', new ItemStack(ItemsAndBlocks.upgrade, 1, EnumMachineUpgrades.EFFICIENCY_1.ordinal()));
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.upgrade, 1, EnumMachineUpgrades.EFFICIENCY_3.ordinal()), "CDC", "UGU", "CDC", 'C', ItemsAndBlocks.powerCapsuleTier3, 'D', Items.diamond, 'U', new ItemStack(ItemsAndBlocks.upgrade, 1, EnumMachineUpgrades.EFFICIENCY_2.ordinal()), 'G', Items.gold_ingot);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.upgrade, 1, EnumMachineUpgrades.SPEED_1.ordinal()), "IGI", "IBI", "IGI", 'I', Items.iron_ingot, 'G', Items.glowstone_dust, 'B', ItemsAndBlocks.blankUpgrade);
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.upgrade, 1, EnumMachineUpgrades.SPEED_2.ordinal()), "GBG", "GUG", "GBG", 'G', Items.gold_ingot, 'B', Blocks.glowstone, 'U', new ItemStack(ItemsAndBlocks.upgrade, 1, EnumMachineUpgrades.SPEED_1.ordinal()));
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.upgrade, 1, EnumMachineUpgrades.SPEED_3.ordinal()), "GBG", "DUD", "GBG", 'G', Items.gold_ingot, 'B', Blocks.glowstone, 'D', Items.diamond, 'U', new ItemStack(ItemsAndBlocks.upgrade, 1, EnumMachineUpgrades.SPEED_2.ordinal()));
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.upgrade, 1, EnumMachineUpgrades.AUTO_IMPORT.ordinal()), "ISI", "EBE", "ISI", 'I', Items.iron_ingot, 'E', Items.ender_pearl, 'S', Blocks.sticky_piston, 'B', ItemsAndBlocks.blankUpgrade);
		}

		if (ConfigurationHandler.enabled("PortableLaser"))
			addAssemblyRecipe("portableLaser", ItemsAndBlocks.portableLaser, 8000, "blockGlass", "gemDiamond", "stickBlaze", "stickBlaze", BCItems.LASER, ItemsAndBlocks.toolCore);


		/*BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("goldStick", "stickGold", null, ItemRedstoneChipset.Chipset.IRON));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("diamondStick", "stickDiamond", "goldStick", ItemRedstoneChipset.Chipset.GOLD));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("emeraldStick", "stickEmerald", "diamondStick", ItemRedstoneChipset.Chipset.DIAMOND));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("netherStarStick", "stickNetherStar", "emeraldStick", ItemRedstoneChipset.Chipset.EMERALD, ItemRedstoneChipset.Chipset.PULSATING));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("quartzStick", "stickQuartz", "glowstoneStick", ItemRedstoneChipset.Chipset.IRON));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("enderStick", "stickEnder", "goldStick", ItemRedstoneChipset.Chipset.COMP));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("redstoneStick", "stickRedstone", null, ItemRedstoneChipset.Chipset.IRON));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("glowstoneStick", "stickGlowstone", "redstoneStick", ItemRedstoneChipset.Chipset.RED));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("slimeStick", "stickSlime", "redstoneStick", ItemRedstoneChipset.Chipset.RED));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("blazeStick", "stickBlaze", "redstoneStick", ItemRedstoneChipset.Chipset.RED));*/

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.heatPlatingRaw, 2), "DD", "DD", 'D', "dustIron"));
		GameRegistry.addSmelting(new ItemStack(ItemsAndBlocks.heatPlatingRaw), new ItemStack(ItemsAndBlocks.heatPlating), 0);

		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.toolUpgradeChainsaw), "U", 'U', ItemsAndBlocks.toolUpgradeDigger);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.toolUpgradeDigger), "U", 'U', ItemsAndBlocks.toolUpgradeDrill);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.toolUpgradeDrill), "U", 'U', ItemsAndBlocks.toolUpgradeHoe);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.toolUpgradeHoe), "U", 'U', ItemsAndBlocks.toolUpgradeChainsaw);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.machineConfigurator), "RIB", " W ", "YIY", 'B', "dyeBlue", 'I', "ingotIron", 'R', "dyeRed", 'W', BCItems.WRENCH, 'Y', "dyeYellow"));

		if (ConfigurationHandler.enabled("ColorSorter"))
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.itemSorter), "SUS", "PCP", "SGS", 'S', Blocks.stone, 'U', EnumMachineUpgrades.AUTO_OUTPUT.getItemStack(), 'P', BCItems.PIPE_ITEMS_LAPIS, 'C', Blocks.chest, 'G', BCItems.PIPE_ITEMS_GOLD);

		if (ConfigurationHandler.enabled("ColoringTool"))
			GameRegistry.addRecipe(new ShapedOreRecipe(ItemsAndBlocks.pipeColoringTool, "  S", " C ", "W  ", 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), 'S', "stickIron", 'C', ItemsAndBlocks.powerCapsuleTier1));

		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.blankUpgrade, 2), "GGG", "GPG", "GGG", 'G', Items.gold_ingot, 'P', ItemsAndBlocks.heatPlating);

		GameRegistry.addSmelting(conductivePlateRaw, new ItemStack(conductivePlate), 0.5f);
		GameRegistry.addRecipe(new ItemStack(kineticBackpack), "PLP", "PPP", "PPP", 'P', conductivePlate, 'L', Items.leather);
		GameRegistry.addRecipe(new ItemStack(backpackStand), "III", " I ", "III", 'I', Items.iron_ingot);

		//remove BC refinery recipe
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

	private static void addUpgradeRecipe(ItemToolUpgrade upgrade, Object... inputs) {
		if (inputs != null) {
			Object[] inputs2 = new Object[inputs.length + 1];
			inputs2[0] = new ItemStack(ItemsAndBlocks.toolCore);
			System.arraycopy(inputs, 0, inputs2, 1, inputs2.length - 1);
			addAssemblyRecipe("toolUpgrade" + upgrade.getType(), upgrade, 1000, inputs2);
		}
	}

	private static void addUpgradeRecipe(ItemToolUpgrade upgrade) {
		addAssemblyRecipe("toolUpgrade" + upgrade.getType(), upgrade, 1000, ItemsAndBlocks.toolCore, "ingotIron", "ingotIron", "ingotIron", "ingotGold", "ingotGold");
	}

	private static void addAssemblyRecipe(String name, Item output, int power, Object... inputs) {
		BuildcraftRecipeRegistry.assemblyTable.addRecipe(Variables.MOD.ID + ":" + name, power, new ItemStack(output), inputs);
	}

	private static void addStickRecipe(ItemBase stick, int power, String materialItem) {
		BuildcraftRecipeRegistry.assemblyTable.addRecipe(Variables.MOD.ID + ":" + stick.getName(), power, new ItemStack(stick), materialItem);
	}

	private static void addStickRecipe(ItemBase stick, int power, String stickInput, String materialItem) {
		BuildcraftRecipeRegistry.assemblyTable.addRecipe(Variables.MOD.ID + ":" + stick.getName(), power, new ItemStack(stick), stickInput, materialItem);
	}

	private static void addStickRecipe(ItemBase stick, int power, String stickInput, Item materialItem) {
		BuildcraftRecipeRegistry.assemblyTable.addRecipe(Variables.MOD.ID + ":" + stick.getName(), power, new ItemStack(stick), stickInput, new ItemStack(materialItem));
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
