package buildcraftAdditions.reference;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.armour.ItemKineticBackpack;
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
import buildcraftAdditions.core.VersionCheck;
import buildcraftAdditions.items.ItemBase;
import buildcraftAdditions.items.ItemCanister;
import buildcraftAdditions.items.ItemMachineConfigurator;
import buildcraftAdditions.items.ItemMachineUpgrade;
import buildcraftAdditions.items.ItemPoweredBase;
import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.items.Tools.ItemKineticTool;
import buildcraftAdditions.items.Tools.ItemPipeColoringTool;
import buildcraftAdditions.items.Tools.ItemPortableLaser;
import buildcraftAdditions.items.Tools.ItemToolUpgrade;
import buildcraftAdditions.items.dust.ItemDust;
import buildcraftAdditions.items.itemBlocks.ItemBlockKEB;
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
	public static ItemBase ironStick;
	public static ItemBase goldStick;
	public static ItemBase diamondStick;
	public static ItemBase emeraldStick;
	public static ItemBase netherStarStick;
	public static ItemBase quartzStick;
	public static ItemBase enderStick;
	public static ItemBase redstoneStick;
	public static ItemBase glowstoneStick;
	public static ItemBase slimeStick;
	public static ItemBase boneStick;
	public static ItemBase flintStick;
	public static ItemBase blazeStick;
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
	@Deprecated
	public static ItemKineticTool kineticTool;
	public static Item itemKineticMultiTool;
	public static Item machineConfigurator;
	public static Item heatPlating;
	public static Item heatPlatingRaw;
	public static Item dust;
	public static Item pipeColoringTool;
	public static Item upgrade;
	public static Item blankUpgrade;
	public static Item portableLaser;

	public static ItemArmor kineticBackpack;


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

		ironStick = new ItemBase("stickIron");
		GameRegistry.registerItem(ironStick, "stickIron");
		OreDictionary.registerOre("stickIron", new ItemStack(ironStick));

		goldStick = new ItemBase("stickGold");
		GameRegistry.registerItem(goldStick, "stickGold");
		OreDictionary.registerOre("stickGold", new ItemStack(goldStick));

		diamondStick = new ItemBase("stickDiamond");
		GameRegistry.registerItem(diamondStick, "stickDiamond");
		OreDictionary.registerOre("stickDiamond", new ItemStack(diamondStick));

		emeraldStick = new ItemBase("stickEmerald");
		GameRegistry.registerItem(emeraldStick, "stickEmerald");
		OreDictionary.registerOre("stickEmerald", new ItemStack(emeraldStick));

		netherStarStick = new ItemBase("stickNetherStar");
		GameRegistry.registerItem(netherStarStick, "stickNetherStar");
		OreDictionary.registerOre("stickNetherStar", new ItemStack(netherStarStick));

		quartzStick = new ItemBase("stickQuartz");
		GameRegistry.registerItem(quartzStick, "stickQuartz");
		OreDictionary.registerOre("stickQuartz", new ItemStack(quartzStick));

		enderStick = new ItemBase("stickEnder");
		GameRegistry.registerItem(enderStick, "stickEnder");
		OreDictionary.registerOre("stickEnder", new ItemStack(enderStick));

		redstoneStick = new ItemBase("stickRedstone");
		GameRegistry.registerItem(redstoneStick, "stickRedstone");
		OreDictionary.registerOre("stickRedstone", new ItemStack(redstoneStick));

		glowstoneStick = new ItemBase("stickGlowstone");
		GameRegistry.registerItem(glowstoneStick, "stickGlowstone");
		OreDictionary.registerOre("stickGlowstone", new ItemStack(glowstoneStick));

		slimeStick = new ItemBase("stickSlime");
		GameRegistry.registerItem(slimeStick, "stickSlime");
		OreDictionary.registerOre("stickSlime", new ItemStack(slimeStick));

		boneStick = new ItemBase("stickBone");
		GameRegistry.registerItem(boneStick, "stickBone");
		OreDictionary.registerOre("stickBone", new ItemStack(boneStick));

		flintStick = new ItemBase("stickFlint");
		GameRegistry.registerItem(flintStick, "stickFlint");
		OreDictionary.registerOre("stickFlint", new ItemStack(flintStick));

		blazeStick = new ItemBase("stickBlaze");
		GameRegistry.registerItem(blazeStick, "stickBlaze");
		OreDictionary.registerOre("stickBlaze", new ItemStack(blazeStick));

		toolCore = new ItemBase("toolCore");
		GameRegistry.registerItem(toolCore, "toolCore");

		toolUpgradeHoe = new ItemToolUpgrade("Hoe");
		GameRegistry.registerItem(toolUpgradeHoe, "toolUpgradeHoe");

		toolUpgradeDigger = new ItemToolUpgrade("Digger");
		GameRegistry.registerItem(toolUpgradeDigger, "toolUpgradeDigger");

		toolUpgradeDrill = new ItemToolUpgrade("Drill");
		GameRegistry.registerItem(toolUpgradeDrill, "toolUpgradeDrill");

		toolUpgradeChainsaw = new ItemToolUpgrade("Chainsaw");
		GameRegistry.registerItem(toolUpgradeChainsaw, "toolUpgradeChaisaw");

		toolUpgradeArea = new ItemToolUpgrade("Area");
		GameRegistry.registerItem(toolUpgradeArea, "toolUpgradeArea");

		toolUpgradeSilky = new ItemToolUpgrade("Silky");
		GameRegistry.registerItem(toolUpgradeSilky, "toolUpgradeSilky");

		toolUpgradeFortune1 = new ItemToolUpgrade("Fortune1");
		GameRegistry.registerItem(toolUpgradeFortune1, "toolUpgradeFortune1");

		toolUpgradeFortune2 = new ItemToolUpgrade("Fortune2");
		GameRegistry.registerItem(toolUpgradeFortune2, "toolUpgradeFortune2");

		toolUpgradeFortune3 = new ItemToolUpgrade("Fortune3");
		GameRegistry.registerItem(toolUpgradeFortune3, "toolUpgradeFortune3");

		kineticTool = new ItemKineticTool();
		GameRegistry.registerItem(kineticTool, "kineticMultiTool");

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

		blankUpgrade = new ItemBase("baseUpgrade");
		GameRegistry.registerItem(blankUpgrade, "blankUpgrade");

		portableLaser = new ItemPortableLaser();
		GameRegistry.registerItem(portableLaser, "portableLaser");

		kineticBackpack = new ItemKineticBackpack();
		GameRegistry.registerItem(kineticBackpack, "kineticBackpack");

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
