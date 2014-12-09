package buildcraftAdditions.reference;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.ShapedOreRecipe;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.blocks.BlockBCKinesisPipeWood;
import buildcraftAdditions.blocks.BlockBCKinisisPipeCobble;
import buildcraftAdditions.blocks.BlockBasicCoil;
import buildcraftAdditions.blocks.BlockBasicDuster;
import buildcraftAdditions.blocks.BlockChargingStation;
import buildcraftAdditions.blocks.BlockFluidicCompressor;
import buildcraftAdditions.blocks.BlockHeatedFurnace;
import buildcraftAdditions.blocks.BlockKineticCoil;
import buildcraftAdditions.blocks.BlockKineticDuster;
import buildcraftAdditions.blocks.BlockKineticEnergyBufferTier1;
import buildcraftAdditions.blocks.BlockLavaCoil;
import buildcraftAdditions.blocks.BlockMechanicalDuster;
import buildcraftAdditions.blocks.BlockSemiAutomaticDuster;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockKEBT2;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockKEBT3Core;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockKEBT3Plating;
import buildcraftAdditions.blocks.multiBlocks.MultiBlockRefineryWalls;
import buildcraftAdditions.items.BatteryBase;
import buildcraftAdditions.items.ItemBase;
import buildcraftAdditions.items.ItemCanister;
import buildcraftAdditions.items.Tools.ItemKineticTool;
import buildcraftAdditions.items.Tools.ToolCoreRecipe;
import buildcraftAdditions.items.Tools.ToolUpgrade;
import buildcraftAdditions.items.Tools.UpgradeRecipeDiamondStick;
import buildcraftAdditions.items.Tools.UpgradeRecipeDrillHead;
import buildcraftAdditions.items.Tools.UpgradeRecipeEmeraldStick;
import buildcraftAdditions.items.Tools.UpgradeRecipeExcavationAttachment;
import buildcraftAdditions.items.Tools.UpgradeRecipeGoldStick;
import buildcraftAdditions.items.Tools.UpgradeRecipeSawBlade;
import buildcraftAdditions.items.Tools.UpgradeRecipeTiller;
import buildcraftAdditions.tileEntities.TileBasicCoil;
import buildcraftAdditions.tileEntities.TileBasicDuster;
import buildcraftAdditions.tileEntities.TileChargingStation;
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
import buildcraftAdditions.utils.BCItems;
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
	public static BlockBCKinisisPipeCobble kinisisPipeCobble;
	public static BlockKineticEnergyBufferTier1 kebT1;
	public static MultiBlockKEBT2 kebT2;
	public static MultiBlockKEBT3Core kebT3Core;
	public static MultiBlockKEBT3Plating kebT3Plating;
	public static MultiBlockRefineryWalls refineryWalls;

	public static Item powerCapsuleTier1;
	public static Item powerCapsuleTier2;
	public static Item powerCapsuleTier3;
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

	public static void init() {
		ironCanister = new ItemCanister("ironCanister", 1000);
		GameRegistry.registerItem(ironCanister, "ironCanister");

		goldCanister = new ItemCanister("goldCanister", 4000);
		GameRegistry.registerItem(goldCanister, "goldCanister");

		diamondCanister = new ItemCanister("diamondCanister", 16000);
		GameRegistry.registerItem(diamondCanister, "diamondCanister");

		powerCapsuleTier1 = new BatteryBase(100000, 1000, 1000, 1, "T1_battery");
		powerCapsuleTier1.setUnlocalizedName("powerCapsuleTier1");
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

		kinesisPipeWood = new BlockBCKinesisPipeWood();
		kinesisPipeWood.setBlockName("kinesisPipeWood");
		GameRegistry.registerBlock(kinesisPipeWood, "kinesisPipeWood");

		kinisisPipeCobble = new BlockBCKinisisPipeCobble();
		kinisisPipeCobble.setBlockName("kinesisPipeCobble");
		GameRegistry.registerBlock(kinisisPipeCobble, "kinesisPipeCobble");

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
		GameRegistry.registerBlock(kebT1, "KEBT1");

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
	}

	public static void addRecepies() {
		if (Loader.isModLoaded("BuildCraft|Transport")) {
			BuildcraftRecipeRegistry.assemblyTable.addRecipe("ironStick", 1000, new ItemStack(ironStick), Items.iron_ingot);
			BuildcraftRecipeRegistry.assemblyTable.addRecipe("goldStick", 2000, new ItemStack(goldStick), new ItemStack(Items.gold_ingot, 4));
			BuildcraftRecipeRegistry.assemblyTable.addRecipe("diamondStick", 3000, new ItemStack(diamondStick), new ItemStack(Items.diamond, 2));
			BuildcraftRecipeRegistry.assemblyTable.addRecipe("kineticTool", 8000, new ItemStack(kineticTool), new ItemStack(Items.diamond, 3), ironStick, toolCore);
			BuildcraftRecipeRegistry.assemblyTable.addRecipe("toolUpgradeChainsaw", 1000, new ItemStack(toolUpgradeChainsaw), toolCore, new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.gold_ingot, 2));
			BuildcraftRecipeRegistry.assemblyTable.addRecipe("toolUpgradeDrill", 1000, new ItemStack(toolUpgradeDrill), toolCore, new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.gold_ingot, 2));
			BuildcraftRecipeRegistry.assemblyTable.addRecipe("toolUpgradeDigger", 1000, new ItemStack(toolUpgradeDigger), toolCore, new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.gold_ingot, 2));
			BuildcraftRecipeRegistry.assemblyTable.addRecipe("toolUpgradeHoe", 1000, new ItemStack(toolUpgradeHoe), toolCore, new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.gold_ingot, 2));
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new ToolCoreRecipe());
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeDrillHead());
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeExcavationAttachment());
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeSawBlade());
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeTiller());
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeGoldStick());
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeDiamondStick());
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeEmeraldStick());

			GameRegistry.addRecipe(new ItemStack(ironCanister, 4), "PIP", "IGI", "PIP", 'P', BCItems.SEALANT, 'I', Items.iron_ingot, 'G', Blocks.glass_pane);
			GameRegistry.addRecipe(new ItemStack(goldCanister), "PGP", "GIG", "PGP", 'P', BCItems.SEALANT, 'G', Items.gold_ingot, 'I', ironCanister);
			GameRegistry.addRecipe(new ItemStack(diamondCanister), "PDP", "DGD", "PDP", 'P', BCItems.SEALANT, 'D', Items.diamond, 'G', goldCanister);
			GameRegistry.addRecipe(new ItemStack(fluidicCompressorBlock), "IFI", "PGP", "IMI", 'I', BCItems.IRON_GEAR, 'F', BCItems.FLOODGATE, 'P', Blocks.piston, 'G', goldCanister, 'M', BCItems.PUMP);
			GameRegistry.addRecipe(new ItemStack(chargingStationBlock), "I I", "WKW", "I I", 'I', BCItems.IRON_GEAR, 'W', BCItems.PIPE_POWER_WOOD, 'K', powerCapsuleTier2);
			GameRegistry.addRecipe(new ItemStack(powerCapsuleTier1), "IGI", "IRI", "IGI", 'I', Items.iron_ingot, 'G', Items.gold_ingot, 'R', Blocks.redstone_block);
			GameRegistry.addRecipe(new ItemStack(powerCapsuleTier2), "GDG", "GPG", "GDG", 'G', Items.gold_ingot, 'D', Items.diamond, 'P', powerCapsuleTier1);
			GameRegistry.addRecipe(new ItemStack(powerCapsuleTier3), "DED", "DPD", "DED", 'D', Items.diamond, 'E', Items.emerald, 'P', powerCapsuleTier2);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(basicDusterBlock), "GIG", "SLS", "SSS", 'G', BCItems.STONE_GEAR, 'I', Items.iron_ingot, 'S', Blocks.stone, 'L', "slimeball"));
			GameRegistry.addRecipe(new ItemStack(mechanicalDusterBlock), "GMG", "SFS", "SSS", 'G', BCItems.IRON_GEAR, 'M', Items.gold_ingot, 'F', itemGrindingWheel, 'S', Blocks.stone);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(semiAutomaticDusterBlock), "GMG", "PLP", "SSS", 'G', BCItems.IRON_GEAR, 'M', Items.gold_ingot, 'P', BCItems.PIPE_ITEMS_GOLD, 'L', "slimeball", 'S', Blocks.stone));
			GameRegistry.addRecipe(new ItemStack(kineticDusterBlock), "GGG", "P P", "IDI", 'G', Blocks.glass, 'P', BCItems.PIPE_ITEMS_GOLD, 'I', BCItems.GOLD_GEAR, 'D', BCItems.DIAMOND_GEAR);
			GameRegistry.addRecipe(new ItemStack(itemGrindingWheel), "FFF", "FGF", "FFF", 'F', Items.flint, 'G', BCItems.STONE_GEAR);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemIronWireUnhardened, 2), "DDD", 'D', "dustIron"));
			GameRegistry.addSmelting(itemIronWireUnhardened, new ItemStack(itemIronWire, 2), 0.5f);
			GameRegistry.addRecipe(new ItemStack(basicCoilBlock), "WWW", "WIW", "WWW", 'W', itemIronWire, 'I', Items.iron_ingot);
			GameRegistry.addRecipe(new ItemStack(heatedFurnaceBlock), "III", "IFI", "III", 'I', Items.iron_ingot, 'F', Blocks.furnace);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(goldWireUnhardened, 2), "DDD", 'D', "dustGold"));
			GameRegistry.addSmelting(goldWireUnhardened, new ItemStack(goldWire, 2), 0.5f);
			GameRegistry.addRecipe(new ItemStack(lavaCoilBlock), "WWW", "WIW", "WWW", 'W', goldWire, 'I', Items.iron_ingot);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamondWireUnhardened, 2), "DDD", 'D', "dustDiamond"));
			GameRegistry.addSmelting(diamondWireUnhardened, new ItemStack(diamondWire, 2), 0.5f);
			GameRegistry.addRecipe(new ItemStack(kineticCoil), "WWW", "WIW", "WWW", 'W', diamondWire, 'I', Items.iron_ingot);
			GameRegistry.addRecipe(new ItemStack(kebT1), "IBI", "PBP", "IBI", 'I', Items.iron_ingot, 'B', powerCapsuleTier1, 'P', BCItems.PIPE_POWER_GOLD);
			GameRegistry.addRecipe(new ItemStack(kebT2), "III", "PBP", "III", 'I', Items.iron_ingot, 'B', powerCapsuleTier2, 'P', BCItems.PIPE_POWER_GOLD);
			GameRegistry.addRecipe(new ItemStack(kebT3Core), "DBD", "PBP", "DBD", 'D', Items.diamond, 'B', powerCapsuleTier3, 'P', BCItems.PIPE_POWER_DIAMOND);
			GameRegistry.addRecipe(new ItemStack(kebT3Plating), "PGP", "GGG", "III", 'P', BCItems.PIPE_POWER_DIAMOND, 'G', Items.gold_ingot, 'I', Items.iron_ingot);
		}
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
	}
}
