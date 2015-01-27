package buildcraftAdditions.ModIntegration.Buildcraft;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.ShapedOreRecipe;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.ModIntegration.Buildcraft.Triggers.Triggers;
import buildcraftAdditions.items.Tools.ToolCoreRecipe;
import buildcraftAdditions.items.Tools.UpgradeRecipeDiamondStick;
import buildcraftAdditions.items.Tools.UpgradeRecipeDrillHead;
import buildcraftAdditions.items.Tools.UpgradeRecipeEmeraldStick;
import buildcraftAdditions.items.Tools.UpgradeRecipeExcavationAttachment;
import buildcraftAdditions.items.Tools.UpgradeRecipeGoldStick;
import buildcraftAdditions.items.Tools.UpgradeRecipeSawBlade;
import buildcraftAdditions.items.Tools.UpgradeRecipeTiller;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.utils.BCItems;
import buildcraftAdditions.utils.RefineryRecipeConverter;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BuildcraftIntegration {

	public static void integrate() {
		Triggers.register();
		addBCRecipes();
		RefineryRecipeConverter.doYourThing();
		BuildcraftAdditions.proxy.addPowerplant();
	}

	private static void addBCRecipes() {
		BuildcraftRecipeRegistry.assemblyTable.addRecipe("ironStick", 1000, new ItemStack(ItemsAndBlocks.ironStick), Items.iron_ingot);
		BuildcraftRecipeRegistry.assemblyTable.addRecipe("goldStick", 2000, new ItemStack(ItemsAndBlocks.goldStick), new ItemStack(Items.gold_ingot, 4));
		BuildcraftRecipeRegistry.assemblyTable.addRecipe("diamondStick", 3000, new ItemStack(ItemsAndBlocks.diamondStick), new ItemStack(Items.diamond, 2));
		BuildcraftRecipeRegistry.assemblyTable.addRecipe("kineticTool", 8000, new ItemStack(ItemsAndBlocks.kineticTool), new ItemStack(Items.diamond, 3), ItemsAndBlocks.ironStick, ItemsAndBlocks.toolCore);
		BuildcraftRecipeRegistry.assemblyTable.addRecipe("toolUpgradeChainsaw", 1000, new ItemStack(ItemsAndBlocks.toolUpgradeChainsaw), ItemsAndBlocks.toolCore, new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.gold_ingot, 2));
		BuildcraftRecipeRegistry.assemblyTable.addRecipe("toolUpgradeDrill", 1000, new ItemStack(ItemsAndBlocks.toolUpgradeDrill), ItemsAndBlocks.toolCore, new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.gold_ingot, 2));
		BuildcraftRecipeRegistry.assemblyTable.addRecipe("toolUpgradeDigger", 1000, new ItemStack(ItemsAndBlocks.toolUpgradeDigger), ItemsAndBlocks.toolCore, new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.gold_ingot, 2));
		BuildcraftRecipeRegistry.assemblyTable.addRecipe("toolUpgradeHoe", 1000, new ItemStack(ItemsAndBlocks.toolUpgradeHoe), ItemsAndBlocks.toolCore, new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.gold_ingot, 2));
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new ToolCoreRecipe());
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeDrillHead());
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeExcavationAttachment());
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeSawBlade());
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeTiller());
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeGoldStick());
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeDiamondStick());
		BuildcraftRecipeRegistry.integrationTable.addRecipe(new UpgradeRecipeEmeraldStick());

		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.ironCanister, 4), "PIP", "IGI", "PIP", 'P', BCItems.SEALANT, 'I', Items.iron_ingot, 'G', Blocks.glass_pane);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.goldCanister), "PGP", "GIG", "PGP", 'P', BCItems.SEALANT, 'G', Items.gold_ingot, 'I', ItemsAndBlocks.ironCanister);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.diamondCanister), "PDP", "DGD", "PDP", 'P', BCItems.SEALANT, 'D', Items.diamond, 'G', ItemsAndBlocks.goldCanister);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.fluidicCompressorBlock), "IFI", "PGP", "IMI", 'I', BCItems.IRON_GEAR, 'F', BCItems.FLOODGATE, 'P', Blocks.piston, 'G', ItemsAndBlocks.goldCanister, 'M', BCItems.PUMP);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.chargingStationBlock), "I I", "WKW", "I I", 'I', BCItems.IRON_GEAR, 'W', BCItems.PIPE_POWER_WOOD, 'K', ItemsAndBlocks.powerCapsuleTier2);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.powerCapsuleTier1), "IGI", "IRI", "IGI", 'I', Items.iron_ingot, 'G', Items.gold_ingot, 'R', Blocks.redstone_block);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.powerCapsuleTier2), "GDG", "GPG", "GDG", 'G', Items.gold_ingot, 'D', Items.diamond, 'P', ItemsAndBlocks.powerCapsuleTier1);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.powerCapsuleTier3), "DED", "DPD", "DED", 'D', Items.diamond, 'E', Items.emerald, 'P', ItemsAndBlocks.powerCapsuleTier2);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.basicDusterBlock), "GIG", "SLS", "SSS", 'G', BCItems.STONE_GEAR, 'I', Items.iron_ingot, 'S', Blocks.stone, 'L', "slimeball"));
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.mechanicalDusterBlock), "GMG", "SFS", "SSS", 'G', BCItems.IRON_GEAR, 'M', Items.gold_ingot, 'F', ItemsAndBlocks.itemGrindingWheel, 'S', Blocks.stone);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.semiAutomaticDusterBlock), "GMG", "PLP", "SSS", 'G', BCItems.IRON_GEAR, 'M', Items.gold_ingot, 'P', BCItems.PIPE_ITEMS_GOLD, 'L', "slimeball", 'S', Blocks.stone));
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.kineticDusterBlock), "GGG", "P P", "IDI", 'G', Blocks.glass, 'P', BCItems.PIPE_ITEMS_GOLD, 'I', BCItems.GOLD_GEAR, 'D', BCItems.DIAMOND_GEAR);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.itemGrindingWheel), "FFF", "FGF", "FFF", 'F', Items.flint, 'G', BCItems.STONE_GEAR);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.itemIronWireUnhardened, 2), "DDD", 'D', "dustIron"));
		GameRegistry.addSmelting(ItemsAndBlocks.itemIronWireUnhardened, new ItemStack(ItemsAndBlocks.itemIronWire, 2), 0.5f);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.basicCoilBlock), "WWW", "WIW", "WWW", 'W', ItemsAndBlocks.itemIronWire, 'I', Items.iron_ingot);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.heatedFurnaceBlock), "III", "IFI", "III", 'I', Items.iron_ingot, 'F', Blocks.furnace);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.goldWireUnhardened, 2), "DDD", 'D', "dustGold"));
		GameRegistry.addSmelting(ItemsAndBlocks.goldWireUnhardened, new ItemStack(ItemsAndBlocks.goldWire, 2), 0.5f);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.lavaCoilBlock), "WWW", "WIW", "WWW", 'W', ItemsAndBlocks.goldWire, 'I', Items.iron_ingot);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.diamondWireUnhardened, 2), "DDD", 'D', "dustDiamond"));
		GameRegistry.addSmelting(ItemsAndBlocks.diamondWireUnhardened, new ItemStack(ItemsAndBlocks.diamondWire, 2), 0.5f);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.kineticCoil), "WWW", "WIW", "WWW", 'W', ItemsAndBlocks.diamondWire, 'I', Items.iron_ingot);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.kebT1), "IBI", "PBP", "IBI", 'I', Items.iron_ingot, 'B', ItemsAndBlocks.powerCapsuleTier1, 'P', BCItems.PIPE_POWER_GOLD);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.kebT2), "III", "PBP", "III", 'I', Items.iron_ingot, 'B', ItemsAndBlocks.powerCapsuleTier2, 'P', BCItems.PIPE_POWER_GOLD);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.kebT3Core), "DBD", "PBP", "DBD", 'D', Items.diamond, 'B', ItemsAndBlocks.powerCapsuleTier3, 'P', BCItems.PIPE_POWER_DIAMOND);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.kebT3Plating), "PGP", "GGG", "III", 'P', BCItems.PIPE_POWER_DIAMOND, 'G', Items.gold_ingot, 'I', Items.iron_ingot);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.heatPlatingRaw, 2), "DD", "DD", 'D', "dustIron"));
		GameRegistry.addSmelting(new ItemStack(ItemsAndBlocks.heatPlatingRaw), new ItemStack(ItemsAndBlocks.heatPlating), 0);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.refineryWalls, 10), "PPP", "PDP", "PPP", 'P', ItemsAndBlocks.heatPlating, 'D', "dustDiamond"));
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.refineryValve, 4), " P ", "PBP", " P ", 'P', ItemsAndBlocks.heatPlating, 'B', Blocks.iron_bars);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.coolingTowerWalls, 10), "PPP", "PDP", "PPP", 'P', ItemsAndBlocks.heatPlating, 'D', Items.redstone);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.coolingTowerValve), "V", 'V', ItemsAndBlocks.refineryValve);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.toolUpgradeChainsaw), "U", 'U', ItemsAndBlocks.toolUpgradeDigger);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.toolUpgradeDigger), "U", 'U', ItemsAndBlocks.toolUpgradeDrill);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.toolUpgradeDrill), "U", 'U', ItemsAndBlocks.toolUpgradeHoe);
		GameRegistry.addRecipe(new ItemStack(ItemsAndBlocks.toolUpgradeHoe), "U", 'U', ItemsAndBlocks.toolUpgradeChainsaw);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsAndBlocks.machineConfigurator), "BIR", " W ", "YIY", 'B', "dyeBlue", 'I', "ingotIron", 'R', "dyeRed", 'W', BCItems.WRENCH, 'Y', "dyeYellow"));

		//remove BC refinery recipe
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
