package buildcraftAdditions.reference;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;

import buildcraftAdditions.compat.buildcraft.BCItems;
import buildcraftAdditions.compat.buildcraft.recipe.BCAIntegrationRecipe;
import buildcraftAdditions.compat.buildcraft.recipe.KineticToolUpgradeHandler;
import buildcraftAdditions.compat.buildcraft.recipe.ToolCoreRecipe;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.items.ItemCanister;
import buildcraftAdditions.items.ItemMachineConfigurator;
import buildcraftAdditions.items.ItemMachineUpgrade;
import buildcraftAdditions.items.ItemPoweredBase;
import buildcraftAdditions.items.ItemStickBCA;
import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.items.Tools.ItemPipeColoringTool;
import buildcraftAdditions.items.Tools.ItemPortableLaser;
import buildcraftAdditions.items.Tools.ItemToolUpgrade;
import buildcraftAdditions.items.bases.ItemBase;
import buildcraftAdditions.items.dust.ItemDust;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemLoader {
	public static ItemCanister ironCanister;
	public static ItemCanister goldCanister;
	public static ItemCanister diamondCanister;
	public static Item powerCapsuleTier1;
	public static Item powerCapsuleTier2;
	public static Item powerCapsuleTier3;
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
	public static ItemBase toolCore;
	public static ItemToolUpgrade toolUpgradeHoe;
	public static ItemToolUpgrade toolUpgradeDigger;
	public static ItemToolUpgrade toolUpgradeDrill;
	public static ItemToolUpgrade toolUpgradeChainsaw;
	public static ItemToolUpgrade toolUpgradeArea;
	public static ItemToolUpgrade toolUpgradeSilky;
	public static ItemToolUpgrade toolUpgradeFortune1;
	public static ItemToolUpgrade toolUpgradeFortune2;
	public static ItemToolUpgrade toolUpgradeFortune3;
	public static Item grindingWheel;
	public static Item itemIronWireUnhardened;
	public static Item itemIronWire;
	public static Item goldWireUnhardened;
	public static Item goldWire;
	public static Item diamondWireUnhardened;
	public static Item diamondWire;
	public static Item kineticMultiTool;
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
	public static Item spring;
	public static Item lightPlating;
	public static Item thruster;
	public static Item fluxConductor;
	public static Item fluxDisperser;


	public static void loadItems() {
		//canisters
		ironCanister = new ItemCanister("ironCanister", 2000);
		goldCanister = new ItemCanister("goldCanister", 8000);
		diamondCanister = new ItemCanister("diamondCanister", 64000);

		//power capsules
		powerCapsuleTier1 = new ItemPoweredBase("powerCapsuleTier1", 100000, 1024);
		powerCapsuleTier2 = new ItemPoweredBase("powerCapsuleTier2", 300000, 4096);
		powerCapsuleTier3 = new ItemPoweredBase("powerCapsuleTier3", 1000000, 16384);

		//sticks
		ironStick = new ItemStickBCA("Iron");
		goldStick = new ItemStickBCA("Gold");
		diamondStick = new ItemStickBCA("Diamond");
		emeraldStick = new ItemStickBCA("Emerald");
		netherStarStick = new ItemStickBCA("NetherStar");
		quartzStick = new ItemStickBCA("Quartz");
		enderStick = new ItemStickBCA("Ender");
		redstoneStick = new ItemStickBCA("Redstone");
		glowstoneStick = new ItemStickBCA("Glowstone");
		slimeStick = new ItemStickBCA("Slime");
		blazeStick = new ItemStickBCA("Blaze");

		//tool upgrades
		toolUpgradeHoe = new ItemToolUpgrade("Hoe", "upgrades/hoe");
		toolUpgradeDigger = new ItemToolUpgrade("Digger", "upgrades/digger");
		toolUpgradeDrill = new ItemToolUpgrade("Drill", "upgrades/drill");
		toolUpgradeChainsaw = new ItemToolUpgrade("Chainsaw", "upgrades/chainsaw");
		toolUpgradeArea = new ItemToolUpgrade("Area", "upgrades/area");
		toolUpgradeSilky = new ItemToolUpgrade("Silky", "upgrades/silky");
		toolUpgradeFortune1 = new ItemToolUpgrade("Fortune1", "upgrades/fortune1");
		toolUpgradeFortune2 = new ItemToolUpgrade("Fortune2", "upgrades/fortune2");
		toolUpgradeFortune3 = new ItemToolUpgrade("Fortune3", "upgrades/fortune3");

		//tools
		kineticMultiTool = new ItemKineticMultiTool();
		machineConfigurator = new ItemMachineConfigurator();
		pipeColoringTool = new ItemPipeColoringTool();
		portableLaser = new ItemPortableLaser();

		//crafting components
		toolCore = new ItemBase("toolCore");
		grindingWheel = new ItemBase("grindingWheel");
		itemIronWireUnhardened = new ItemBase("wireIronUnhardened");
		itemIronWire = new ItemBase("wireIron");
		goldWireUnhardened = new ItemBase("wireGoldUnhardened");
		goldWire = new ItemBase("wireGold");
		diamondWireUnhardened = new ItemBase("wireDiamondUnhardened");
		diamondWire = new ItemBase("wireDiamond");
		heatPlatingRaw = new ItemBase("heatPlatingRaw");
		heatPlating = new ItemBase("heatPlating");
		blankUpgrade = new ItemBase("baseUpgrade", "upgrades/base", "blankUpgrade");
		conductivePlateRaw = new ItemBase("conductivePlateRaw");
		conductivePlate = new ItemBase("conductivePlate");
		spring = new ItemBase("spring", "components/spring");
		thruster = new ItemBase("thruster", "components/thruster");
		lightPlating = new ItemBase("lightPlating", "components/light plating");
		fluxConductor = new ItemBase("fluxConductor", "components/Flux_Conductor");
		fluxDisperser = new ItemBase("fluxDisperser", "components/Flux_Disperser");


		//other
		dust = new ItemDust();
		upgrade = new ItemMachineUpgrade();
		gildedRedMetalIngot = new ItemBase("gildedRedMetalIngot", "gildedRedMetalIngot", "ingotGildedRedMetal");
		OreDictionary.registerOre("ingotGildedRedMetal", gildedRedMetalIngot);


	}

	public static void addRecipes() {

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(grindingWheel), "FFF", "FGF", "FFF", 'F', Items.flint, 'G', "gearStone"));
		if (ConfigurationHandler.enabled("MultiTools")) {
			addStickRecipe(ItemLoader.ironStick, 1000, "ingotIron");
			addStickRecipe(ItemLoader.goldStick, 2000, "ingotGold");
			addStickRecipe(ItemLoader.diamondStick, 3000, "stickGold", "gemDiamond");
			addStickRecipe(ItemLoader.netherStarStick, 10000, "stickEmerald", Items.nether_star);
			addStickRecipe(ItemLoader.quartzStick, 2000, "stickIron", "gemQuartz");
			addStickRecipe(ItemLoader.enderStick, 5000, "stickGold", Items.ender_pearl);
			addStickRecipe(ItemLoader.redstoneStick, 3000, "stickIron", "dustRedstone");
			addStickRecipe(ItemLoader.glowstoneStick, 3000, "stickIron", "dustGlowstone");
			addStickRecipe(ItemLoader.slimeStick, 2000, "stickIron", "slimeball");
			addStickRecipe(ItemLoader.blazeStick, 4000, "stickQuartz", Items.blaze_rod);
			addAssemblyRecipe("kineticTool", ItemLoader.kineticMultiTool, 8000, "gemDiamond", "gemDiamond", "gemDiamond", "stickIron", ItemLoader.toolCore);
			addUpgradeRecipe(ItemLoader.toolUpgradeChainsaw);
			addUpgradeRecipe(ItemLoader.toolUpgradeDrill);
			addUpgradeRecipe(ItemLoader.toolUpgradeDigger);
			addUpgradeRecipe(ItemLoader.toolUpgradeHoe);
			BuildcraftRecipeRegistry.integrationTable.addRecipe(new ToolCoreRecipe());
			KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("drill", false, toolUpgradeDrill));
			KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("digger", false, toolUpgradeDigger));
			KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("chainsaw", false, toolUpgradeChainsaw));
			KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("hoe", false, toolUpgradeHoe));
		}

		if (ConfigurationHandler.enabled("MultiToolsArea")) {
			KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("area", false, toolUpgradeArea));
			addUpgradeRecipe(ItemLoader.toolUpgradeArea, new ItemStack(Blocks.sticky_piston), new ItemStack(Items.ender_pearl), "ingotGold");
		}

		if (ConfigurationHandler.enabled("MultiToolsSilky")) {
			KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("silky", false, toolUpgradeSilky));
			addUpgradeRecipe(ItemLoader.toolUpgradeSilky, new ItemStack(Items.string, 3), "slimeball", "ingotGold");
		}

		if (ConfigurationHandler.enabled("MultiToolsFortune")) {
			addUpgradeRecipe(ItemLoader.toolUpgradeFortune1, new ItemStack(Items.string, 3), "gemLapis", "blockLapis", "ingotGold");
			addUpgradeRecipe(ItemLoader.toolUpgradeFortune2, new ItemStack(ItemLoader.toolUpgradeFortune1), "gemDiamond", "blockLapis", "ingotGold");
			addUpgradeRecipe(ItemLoader.toolUpgradeFortune3, new ItemStack(ItemLoader.toolUpgradeFortune2), "gemEmerald", "blockLapis", "ingotGold");
			KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("fortune1", false, toolUpgradeFortune1));
			KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("fortune2", false, toolUpgradeFortune2));
			KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("fortune3", false, toolUpgradeFortune3));
		}

		if (ConfigurationHandler.enabled("FluidCanisters")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.ironCanister, 4), "PIP", "IGI", "PIP", 'P', BCItems.SEALANT, 'I', "ingotIron", 'G', "paneGlass"));
			GameRegistry.addRecipe(new ItemStack(ItemLoader.goldCanister), "PGP", "GIG", "PGP", 'P', BCItems.SEALANT, 'G', Items.gold_ingot, 'I', ironCanister);
			GameRegistry.addRecipe(new ItemStack(ItemLoader.diamondCanister), "PDP", "DGD", "PDP", 'P', BCItems.SEALANT, 'D', Items.diamond, 'G', goldCanister);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockLoader.fluidicCompressorBlock), "GFG", "PCP", "IGI", 'G', "ingotGold", 'F', fluxConductor, 'C', goldCanister, 'I', "ingotIron", 'P', Blocks.piston));

			if (ConfigurationHandler.enabled("PortableLaser"))
				addAssemblyRecipe("portableLaser", portableLaser, 8000, "blockGlass", "gemDiamond", "stickBlaze", "stickBlaze", BCItems.LASER, toolCore);

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heatPlatingRaw, 2), "DD", "DD", 'D', "dustIron"));
			GameRegistry.addSmelting(new ItemStack(heatPlatingRaw), new ItemStack(heatPlating), 0);

			GameRegistry.addRecipe(new ItemStack(toolUpgradeChainsaw), "U", 'U', toolUpgradeDigger);
			GameRegistry.addRecipe(new ItemStack(toolUpgradeDigger), "U", 'U', toolUpgradeDrill);
			GameRegistry.addRecipe(new ItemStack(toolUpgradeDrill), "U", 'U', toolUpgradeHoe);
			GameRegistry.addRecipe(new ItemStack(toolUpgradeHoe), "U", 'U', toolUpgradeChainsaw);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(machineConfigurator), "RIB", " W ", "YIY", 'B', "dyeBlue", 'I', "ingotIron", 'R', "dyeRed", 'W', BCItems.WRENCH, 'Y', "dyeYellow"));
		}

		KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("goldStick", true, goldStick));
		KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("diamondStick", true, diamondStick));
		KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("emeraldStick", true, emeraldStick));
		KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("netherStarStick", true, netherStarStick));
		KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("quartzStick", true, quartzStick));
		KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("enderStick", true, enderStick));
		KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("redstoneStick", true, redstoneStick));
		KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("glowstoneStick", true, glowstoneStick));
		KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("slimeStick", true, slimeStick));
		KineticToolUpgradeHandler.addRecipe(new BCAIntegrationRecipe("blazeStick", true, blazeStick));

		if (ConfigurationHandler.enabled("ColoringTool"))
			GameRegistry.addRecipe(new ShapedOreRecipe(pipeColoringTool, "  S", " C ", "W  ", 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), 'S', "stickIron", 'C', powerCapsuleTier1));

		GameRegistry.addRecipe(new ItemStack(blankUpgrade, 2), "GGG", "GPG", "GGG", 'G', Items.gold_ingot, 'P', heatPlating);

		GameRegistry.addSmelting(conductivePlateRaw, new ItemStack(conductivePlate), 0.5f);
		GameRegistry.addRecipe(new ItemStack(thruster), "LCL", "LPL", "I I", 'L', lightPlating, 'C', powerCapsuleTier2, 'P', conductivePlate, 'I', Items.iron_ingot);
		GameRegistry.addRecipe(new ItemStack(lightPlating, 2), "CH", "HC", 'C', conductivePlate, 'H', heatPlating);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spring), "III", "I I", "III", 'I', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fluxConductor), "GIG", "GRG", "GIG", 'G', "ingotGold", 'I', "ingotIron", 'R', "dustRedstone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fluxDisperser), "IGI", "IRI", "IGI", 'G', "ingotGold", 'I', "ingotIron", 'R', "dustRedstone"));

		BuildcraftRecipeRegistry.integrationTable.addRecipe(new KineticToolUpgradeHandler());
	}

	private static void addUpgradeRecipe(ItemToolUpgrade upgrade, Object... inputs) {
		if (inputs != null) {
			Object[] inputs2 = new Object[inputs.length + 1];
			inputs2[0] = new ItemStack(ItemLoader.toolCore);
			System.arraycopy(inputs, 0, inputs2, 1, inputs2.length - 1);
			addAssemblyRecipe("toolUpgrade" + upgrade.getType(), upgrade, 1000, inputs2);
		}
	}

	private static void addUpgradeRecipe(ItemToolUpgrade upgrade) {
		addAssemblyRecipe("toolUpgrade" + upgrade.getType(), upgrade, 1000, ItemLoader.toolCore, "ingotIron", "ingotIron", "ingotIron", "ingotGold", "ingotGold");
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
