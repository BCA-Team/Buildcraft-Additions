package buildcraftAdditions.reference;

import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.blocks.BlockBCKinesisPipeWood;
import buildcraftAdditions.blocks.BlockBCKinisisPipeStone;
import buildcraftAdditions.blocks.BlockBasicCoil;
import buildcraftAdditions.blocks.BlockBasicDuster;
import buildcraftAdditions.blocks.BlockChargingStation;
import buildcraftAdditions.blocks.BlockFluidicCompressor;
import buildcraftAdditions.blocks.BlockHeatedFurnace;
import buildcraftAdditions.blocks.BlockItemSorter;
import buildcraftAdditions.blocks.BlockKineticCoil;
import buildcraftAdditions.blocks.BlockKineticDuster;
import buildcraftAdditions.blocks.BlockKineticEnergyBufferTier1;
import buildcraftAdditions.blocks.BlockLavaCoil;
import buildcraftAdditions.blocks.BlockMechanicalDuster;
import buildcraftAdditions.blocks.BlockSemiAutomaticDuster;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockCoolingTowerValve;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockCoolingTowerWalls;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockKEBT2;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockKEBT3Core;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockKEBT3Plating;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockRefineryValve;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockRefineryWalls;
import buildcraftAdditions.items.BatteryBase;
import buildcraftAdditions.items.ItemBase;
import buildcraftAdditions.items.ItemCanister;
import buildcraftAdditions.items.ItemMachineConfigurator;
import buildcraftAdditions.items.ItemPipeColoringTool;
import buildcraftAdditions.items.Tools.ItemKineticTool;
import buildcraftAdditions.items.Tools.ToolUpgrade;
import buildcraftAdditions.items.dust.ItemDust;
import buildcraftAdditions.items.itemBlocks.ItemBlockKEB;
import buildcraftAdditions.tileEntities.TileBasicCoil;
import buildcraftAdditions.tileEntities.TileBasicDuster;
import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.tileEntities.TileCoolingTower;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;
import buildcraftAdditions.tileEntities.TileItemSorter;
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
 * Copyright (c) 2014, AEnterprise
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

	public static BatteryBase powerCapsuleTier1;
	public static BatteryBase powerCapsuleTier2;
	public static BatteryBase powerCapsuleTier3;
	public static Item ironStick;
	public static Item goldStick;
	public static Item diamondStick;
	public static Item emeraldStick;
	public static Item toolCore;
	public static Item toolUpgradeHoe;
	public static Item toolUpgradeDigger;
	public static Item toolUpgradeDrill;
	public static Item toolUpgradeChainsaw;
	public static Item itemGrindingWheel;
	public static Item itemIronWireUnhardened;
	public static Item itemIronWire;
	public static Item goldWireUnhardened;
	public static Item goldWire;
	public static Item diamondWireUnhardened;
	public static Item diamondWire;
	public static ItemKineticTool kineticTool;
	public static Item machineConfigurator;
	public static Item heatPlating;
	public static Item heatPlatingRaw;
	public static Item dust;
	public static Item pipeColoringTool;

	public static void init() {
		ironCanister = new ItemCanister("ironCanister", 2000);
		GameRegistry.registerItem(ironCanister, "ironCanister");

		goldCanister = new ItemCanister("goldCanister", 8000);
		GameRegistry.registerItem(goldCanister, "goldCanister");

		diamondCanister = new ItemCanister("diamondCanister", 64000);
		GameRegistry.registerItem(diamondCanister, "diamondCanister");

		powerCapsuleTier1 = new BatteryBase(100000, 1000, 1000, 1, "T1_battery");
		powerCapsuleTier1.setUnlocalizedName("PowerCapsuleTier1");
		GameRegistry.registerItem(powerCapsuleTier1, "PowerCapsuleTier1");

		powerCapsuleTier2 = new BatteryBase(300000, 2000, 2000, 2, "T2_battery");
		powerCapsuleTier2.setUnlocalizedName("PowerCapsuleTier2");
		GameRegistry.registerItem(powerCapsuleTier2, "powerCapsuleTier2");

		powerCapsuleTier3 = new BatteryBase(1000000, 4000, 4000, 3, "T3_battery");
		powerCapsuleTier3.setUnlocalizedName("PowerCapsuleTier3");
		GameRegistry.registerItem(powerCapsuleTier3, "powerCapsuleTier3");

		ironStick = new ItemBase("stickIron");
		GameRegistry.registerItem(ironStick, "stickIron");

		goldStick = new ItemBase("stickGold");
		GameRegistry.registerItem(goldStick, "stickGold");

		diamondStick = new ItemBase("stickDiamond");
		GameRegistry.registerItem(diamondStick, "stickDiamond");

		emeraldStick = new ItemBase("stickEmerald");
		GameRegistry.registerItem(emeraldStick, "stickEmerald");

		toolCore = new ItemBase("toolCore");
		GameRegistry.registerItem(toolCore, "toolCore");

		toolUpgradeHoe = new ToolUpgrade("Hoe");
		GameRegistry.registerItem(toolUpgradeHoe, "toolUpgradeHoe");

		toolUpgradeDigger = new ToolUpgrade("Digger");
		GameRegistry.registerItem(toolUpgradeDigger, "toolUpgradeDigger");

		toolUpgradeDrill = new ToolUpgrade("Drill");
		GameRegistry.registerItem(toolUpgradeDrill, "toolUpgradeDrill");

		toolUpgradeChainsaw = new ToolUpgrade("Chainsaw");
		GameRegistry.registerItem(toolUpgradeChainsaw, "toolUpgradeChaisaw");

		kineticTool = new ItemKineticTool();
		GameRegistry.registerItem(kineticTool, "kineticMultiTool");

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

		//START BLOCKS

		kinesisPipeWood = new BlockBCKinesisPipeWood();
		kinesisPipeWood.setBlockName("kinesisPipeWood");
		GameRegistry.registerBlock(kinesisPipeWood, "kinesisPipeWood");

		kinisisPipeStone = new BlockBCKinisisPipeStone();
		kinisisPipeStone.setBlockName("kinesisPipeCobble");
		GameRegistry.registerBlock(kinisisPipeStone, "kinesisPipeCobble");

		fluidicCompressorBlock = new BlockFluidicCompressor();
		fluidicCompressorBlock.setBlockName("blockFluidicCompressor").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(fluidicCompressorBlock, "blockFluidicCompressor");

		chargingStationBlock = new BlockChargingStation();
		chargingStationBlock.setBlockName("blockChargingStation").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(chargingStationBlock, "blockChargingStation");

		heatedFurnaceBlock = new BlockHeatedFurnace();
		heatedFurnaceBlock.setBlockName("blockHeatedFurnace").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(heatedFurnaceBlock, "blockHeatedFurnace");

		basicCoilBlock = new BlockBasicCoil();
		basicCoilBlock.setBlockName("blockCoilBasic").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(basicCoilBlock, "blockCoilBasic");

		lavaCoilBlock = new BlockLavaCoil();
		lavaCoilBlock.setBlockName("blockCoilLava").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(lavaCoilBlock, "blockCoilLava");

		basicDusterBlock = new BlockBasicDuster();
		basicDusterBlock.setBlockName("blockDusterBasic").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(basicDusterBlock, "blockDusterBasic");

		semiAutomaticDusterBlock = new BlockSemiAutomaticDuster();
		semiAutomaticDusterBlock.setBlockName("blockDusterSemiAutomatic").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(semiAutomaticDusterBlock, "blockDusterSemiAutomatic");

		mechanicalDusterBlock = new BlockMechanicalDuster();
		mechanicalDusterBlock.setBlockName("blockDusterMechanical").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(mechanicalDusterBlock, "blockDusterMechanical");

		kineticDusterBlock = new BlockKineticDuster();
		kineticDusterBlock.setBlockName("blockDusterKinetic").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(kineticDusterBlock, "blockDusterKinetic");

		kineticCoil = new BlockKineticCoil();
		kineticCoil.setBlockName("blockCoilKinetic").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(kineticCoil, "blockCoilKinetic");

		kebT1 = new BlockKineticEnergyBufferTier1();
		kebT1.setBlockName("blockKEBT1").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(kebT1, ItemBlockKEB.class, "KEBT1");

		kebT2 = new MultiBlockKEBT2();
		kebT2.setBlockName("blockKEBT2").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(kebT2, "KEBT2");

		kebT3Core = new MultiBlockKEBT3Core();
		kebT3Core.setBlockName("blockKEBT3Core").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(kebT3Core, "KEBT3Core");

		kebT3Plating = new MultiBlockKEBT3Plating();
		kebT3Plating.setBlockName("blockKEBT3Plating").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(kebT3Plating, "KEBT3Plating");

		refineryWalls = new MultiBlockRefineryWalls();
		refineryWalls.setBlockName("blockRefineryWalls").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(refineryWalls, "refinerywals");

		refineryValve = new MultiBlockRefineryValve();
		refineryValve.setBlockName("blockRefineryValve").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(refineryValve, "refineryValve");

		coolingTowerWalls = new MultiBlockCoolingTowerWalls();
		coolingTowerWalls.setBlockName("blockCoolingTowerWalls").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(coolingTowerWalls, "blockCoolingTowerWalls)");

		coolingTowerValve = new MultiBlockCoolingTowerValve();
		coolingTowerValve.setBlockName("blockCoolingTowerValve").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(coolingTowerValve, "blockCoolingTowerValve");

		itemSorter = new BlockItemSorter();
		itemSorter.setBlockName("blockItemSorter").setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(itemSorter, "blockItemSorter");
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
		GameRegistry.registerTileEntity(TileItemSorter.class, "ItemSorter");
	}
}
