package buildcraftAdditions.ModIntegration.Buildcraft;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import buildcraft.api.blueprints.BuilderAPI;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.silicon.ItemRedstoneChipset;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.ModIntegration.Buildcraft.Triggers.Triggers;
import buildcraftAdditions.ModIntegration.Buildcraft.schematics.SchematicBCABase;
import buildcraftAdditions.ModIntegration.Buildcraft.schematics.SchematicMulitblock;
import buildcraftAdditions.ModIntegration.Buildcraft.schematics.SchematicRotatableBCABlock;
import buildcraftAdditions.ModIntegration.Buildcraft.schematics.SchematicSorter;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.items.ItemBase;
import buildcraftAdditions.items.Tools.ItemToolUpgrade;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.reference.enums.EnumMachineUpgrades;
import buildcraftAdditions.tileEntities.TileItemSorter;
import buildcraftAdditions.utils.BCItems;
import buildcraftAdditions.utils.fluids.RefineryRecipeConverter;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BuildcraftIntegration {

	public static void integrate() {
		RefineryRecipeConverter.doYourThing();
		Triggers.register();
		addBCRecipes();
		BuildcraftAdditions.proxy.addPowerplant();
	}

	public static void registerSchematics() {
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.kebT1, SchematicBCABase.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.kineticCoil, SchematicBCABase.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.kineticDusterBlock, SchematicBCABase.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.lavaCoilBlock, SchematicBCABase.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.basicCoilBlock, SchematicBCABase.class);

		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.basicDusterBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.heatedFurnaceBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.chargingStationBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.fluidicCompressorBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.mechanicalDusterBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.semiAutomaticDusterBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);

		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.refineryWalls, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.refineryValve, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.coolingTowerWalls, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.coolingTowerValve, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.kebT2, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.kebT3Core, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.kebT3Plating, SchematicMulitblock.class);

		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.itemSorter, SchematicSorter.class);

		StripesHandler.register();
	}

	private static void addBCRecipes() {
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
			addStickRecipe(ItemsAndBlocks.boneStick, 2000, "stickIron", Items.bone);
			addStickRecipe(ItemsAndBlocks.flintStick, 2000, "stickIron", Items.flint);
			addStickRecipe(ItemsAndBlocks.blazeStick, 4000, "stickQuartz", Items.blaze_rod);
			addAssemblyRecipe("kineticTool", ItemsAndBlocks.itemKineticMultiTool, 8000, "gemDiamond", "gemDiamond", "gemDiamond", "stickIron", ItemsAndBlocks.toolCore);
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeChainsaw);
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeDrill);
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeDigger);
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeHoe);
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new ToolCoreRecipe());
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("drill"));
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("digger"));
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("chainsaw"));
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("hoe"));
		}
		if (ConfigurationHandler.enabled("MultiToolsArea")) {
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("area"));
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeArea, new ItemStack(Blocks.sticky_piston), new ItemStack(Items.ender_pearl), "ingotGold");
		}
		if (ConfigurationHandler.enabled("MultiToolsSilky")) {
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("silky"));
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeSilky, new ItemStack(Items.string, 3), "slimeball", "ingotGold");
		}

		if (ConfigurationHandler.enabled("MultiToolksFortune")) {
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeFortune1, new ItemStack(Items.string, 3), "gemLapis", "blockLapis", "ingotGold");
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeFortune2, new ItemStack(ItemsAndBlocks.toolUpgradeFortune1), "gemDiamond", "blockLapis", "ingotGold");
			addUpgradeRecipe(ItemsAndBlocks.toolUpgradeFortune3, new ItemStack(ItemsAndBlocks.toolUpgradeFortune2), "gemEmerald", "blockLapis", "ingotGold");
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("fortune1"));
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("fortune2"));
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeUpgrade("fortune3"));
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
		if (ConfigurationHandler.enabled("HeatedFurnace")) {
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.heatedFurnaceBlock), "III", "IFI", "III", 'I', Items.iron_ingot, 'F', Blocks.furnace);
		}
		if (ConfigurationHandler.enabled("KineticEnergyBuffer")) {
			GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.kebT1), "IBI", "PBP", "IBI", 'I', Items.iron_ingot, 'B', ItemsAndBlocks.powerCapsuleTier1, 'P', BCItems.PIPE_POWER_GOLD);
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
		}


		addAssemblyRecipe("portableLaser", ItemsAndBlocks.portableLaser, 8000, "blockGlass", "gemDiamond", "stickBlaze", "stickBlaze", BCItems.LASER, ItemsAndBlocks.toolCore);


		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("goldStick", "stickGold", null, ItemRedstoneChipset.Chipset.IRON));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("diamondStick", "stickDiamond", "goldStick", ItemRedstoneChipset.Chipset.GOLD));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("emeraldStick", "stickEmerald", "diamondStick", ItemRedstoneChipset.Chipset.DIAMOND));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("netherStarStick", "stickNetherStar", "emeraldStick", ItemRedstoneChipset.Chipset.EMERALD, ItemRedstoneChipset.Chipset.PULSATING));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("quartzStick", "stickQuartz", "glowstoneStick", ItemRedstoneChipset.Chipset.IRON));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("enderStick", "stickEnder", "goldStick", ItemRedstoneChipset.Chipset.COMP));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("redstoneStick", "stickRedstone", null, ItemRedstoneChipset.Chipset.IRON));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("glowstoneStick", "stickGlowstone", "redstoneStick", ItemRedstoneChipset.Chipset.RED));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("slimeStick", "stickSlime", "redstoneStick", ItemRedstoneChipset.Chipset.RED));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("boneStick", "stickBone", "redstoneStick", ItemRedstoneChipset.Chipset.RED));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("flintStick", "stickFlint", "redstoneStick", ItemRedstoneChipset.Chipset.RED));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeStick("blazeStick", "stickBlaze", "redstoneStick", ItemRedstoneChipset.Chipset.RED));

		GameRegistry.addShapelessRecipe(new ItemStack(ItemsAndBlocks.itemKineticMultiTool), new ItemStack(ItemsAndBlocks.kineticTool, 1, OreDictionary.WILDCARD_VALUE));


		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.heatPlatingRaw, 2), "DD", "DD", 'D', "dustIron"));
		GameRegistry.addSmelting(new ItemStack(ItemsAndBlocks.heatPlatingRaw), new ItemStack(ItemsAndBlocks.heatPlating), 0);


		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.toolUpgradeChainsaw), "U", 'U', ItemsAndBlocks.toolUpgradeDigger);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.toolUpgradeDigger), "U", 'U', ItemsAndBlocks.toolUpgradeDrill);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.toolUpgradeDrill), "U", 'U', ItemsAndBlocks.toolUpgradeHoe);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.toolUpgradeHoe), "U", 'U', ItemsAndBlocks.toolUpgradeChainsaw);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.machineConfigurator), "BIR", " W ", "YIY", 'B', "dyeBlue", 'I', "ingotIron", 'R', "dyeRed", 'W', BCItems.WRENCH, 'Y', "dyeYellow"));
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.itemSorter), "SUS", "PCP", "SGS", 'S', Blocks.stone, 'U', EnumMachineUpgrades.AUTO_OUTPUT.getItemStack(), 'P', BCItems.PIPE_ITEMS_LAPIS, 'C', Blocks.chest, 'G', BCItems.PIPE_ITEMS_GOLD);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.pipeColoringTool), "  S", " S ", "W  ", 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), 'S', Items.stick);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.blankUpgrade, 2), "GGG", "GPG", "GGG", 'G', Items.gold_ingot, 'P', ItemsAndBlocks.heatPlating);


		//remove BC refinery recipe
		if (ConfigurationHandler.enabled("MultiBlockRefining")) {
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

	public static void addItemSorter() {
		GameRegistry.registerBlock(ItemsAndBlocks.itemSorter, "blockItemSorter");
		GameRegistry.registerTileEntity(TileItemSorter.class, "ItemSorter");
	}

	private static void addUpgradeRecipe(ItemToolUpgrade upgrade, Object... inputs) {
		Object[] inputs2 = new Object[(inputs != null ? inputs.length : 0) + 1];
		inputs2[0] = new ItemStack(ItemsAndBlocks.toolCore);
		for (int i = 1; i < inputs2.length; i++)
			inputs2[i] = inputs[i - 1];
		addAssemblyRecipe("toolUpgrade" + upgrade.getType(), upgrade, 1000, inputs2);
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
}
