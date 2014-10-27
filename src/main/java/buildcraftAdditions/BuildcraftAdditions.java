package buildcraftAdditions;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import buildcraft.api.gates.ITrigger;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;

import buildcraftAdditions.api.DusterRecipes;
import buildcraftAdditions.blocks.BlockBCKinesisPipeWood;
import buildcraftAdditions.blocks.BlockBCKinisisPipeCobble;
import buildcraftAdditions.blocks.BlockBasicCoil;
import buildcraftAdditions.blocks.BlockBasicDuster;
import buildcraftAdditions.blocks.BlockChargingStation;
import buildcraftAdditions.blocks.BlockFluidicCompressor;
import buildcraftAdditions.blocks.BlockHeatedFurnace;
import buildcraftAdditions.blocks.BlockKineticCoil;
import buildcraftAdditions.blocks.BlockKineticDuster;
import buildcraftAdditions.blocks.BlockLavaCoil;
import buildcraftAdditions.blocks.BlockMechanicalDuster;
import buildcraftAdditions.blocks.BlockSemiAutomaticDuster;
import buildcraftAdditions.client.gui.GuiHandler;
import buildcraftAdditions.config.ConfigurationHandeler;
import buildcraftAdditions.core.EventListener;
import buildcraftAdditions.core.Logger;
import buildcraftAdditions.core.ModIntegration;
import buildcraftAdditions.items.ItemBase;
import buildcraftAdditions.items.ItemCanister;
import buildcraftAdditions.items.ItemDust;
import buildcraftAdditions.items.ItemPowerCapsuleTier1;
import buildcraftAdditions.items.ItemPowerCapsuleTier2;
import buildcraftAdditions.items.ItemPowerCapsuleTier3;
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
import buildcraftAdditions.networking.PacketHandeler;
import buildcraftAdditions.proxy.CommonProxy;
import buildcraftAdditions.tileEntities.TileBasicCoil;
import buildcraftAdditions.tileEntities.TileBasicDuster;
import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;
import buildcraftAdditions.tileEntities.TileKineticCoil;
import buildcraftAdditions.tileEntities.TileKineticDuster;
import buildcraftAdditions.tileEntities.TileLavaCoil;
import buildcraftAdditions.tileEntities.TileMechanicalDuster;
import buildcraftAdditions.tileEntities.TileSemiAutomaticDuster;
import buildcraftAdditions.triggers.TriggerCanisterRequested;
import buildcraftAdditions.triggers.TriggerDoneCharging;
import buildcraftAdditions.triggers.TriggerHasEmptyCanister;
import buildcraftAdditions.triggers.TriggerHasFullCanister;
import buildcraftAdditions.triggers.TriggerReadyToCharge;
import buildcraftAdditions.utils.BCItems;
import buildcraftAdditions.variables.Variables;
import buildcraftAdditions.villager.ComponentPowerPlant;
import buildcraftAdditions.villager.PowerPlantCreationHandeler;
import buildcraftAdditions.villager.VillagerTradeHandler;


import eureka.api.EurekaInfo;
import eureka.api.EurekaRegistry;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */


@Mod(modid = "bcadditions", name = "Buildcraft Additions", version = "@MODVERSION@", guiFactory = "buildcraftAdditions.config.GuiFactory", dependencies = "after:BuildCraft|Energy;required-after:eureka", acceptedMinecraftVersions = "1.7.10")
public class BuildcraftAdditions {

	public static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/villagers/Engineer.png");
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
	public static Item itemDust;
	public static Item itemGrindingWheel;
	public static Item itemIronWireUnhardened;
	public static Item itemIronWire;
	public static Item goldWireUnhardened;
	public static Item goldWire;
	public static Item diamondWireUnhardened;
	public static Item diamondWire;
	public static ItemKineticTool kineticTool;
	public static ITrigger triggerCanAcceptCanister = new TriggerCanisterRequested();
	public static ITrigger triggerHasEmptyCanister = new TriggerHasEmptyCanister();
	public static ITrigger triggerhasFullCanister = new TriggerHasFullCanister();
	public static ITrigger triggerDoneCharging = new TriggerDoneCharging();
	public static ITrigger triggerReadyToCharge = new TriggerReadyToCharge();
	@Mod.Instance("bcadditions")
	public static BuildcraftAdditions instance;

	@SidedProxy(clientSide = "buildcraftAdditions.proxy.ClientProxy", serverSide = "buildcraftAdditions.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs bcadditions = new CreativeTabs("BuildcraftAdditions") {

		@Override
		public Item getTabIconItem() {
			return new ItemStack(fluidicCompressorBlock, 1).getItem();
		}

	};

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		Logger.initiallize();
		ConfigurationHandeler.init(event.getSuggestedConfigurationFile());
		PacketHandeler.init();

		ironCanister = new ItemCanister("ironCanister", 1000);
		GameRegistry.registerItem(ironCanister, "ironCanister");

		goldCanister = new ItemCanister("goldCanister", 4000);
		GameRegistry.registerItem(goldCanister, "goldCanister");

		diamondCanister = new ItemCanister("diamondCanister", 16000);
		GameRegistry.registerItem(diamondCanister, "diamondCanister");

		powerCapsuleTier1 = new ItemPowerCapsuleTier1();
		GameRegistry.registerItem(powerCapsuleTier1, "powerCapsuleTier1");

		powerCapsuleTier2 = new ItemPowerCapsuleTier2();
		GameRegistry.registerItem(powerCapsuleTier2, "powerCapsuleTier2");

		powerCapsuleTier3 = new ItemPowerCapsuleTier3();
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
		GameRegistry.registerBlock(kinesisPipeWood, "kinesisPipeWood");

		kinisisPipeCobble = new BlockBCKinisisPipeCobble();
		GameRegistry.registerBlock(kinisisPipeCobble, "kinesisPipeCobble");

		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.redstone_ore), new ItemStack(Items.redstone, 6));
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.coal_ore), new ItemStack(Items.coal, 2));
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.lapis_ore), new ItemStack(Items.dye, 6, 4));
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.quartz_ore), new ItemStack(Items.quartz, 2));
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.stone), new ItemStack(Blocks.gravel));
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.sand));
	}

	@Mod.EventHandler
	public void doneLoading(FMLLoadCompleteEvent event) {

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
		}

		itemDust = new ItemDust(Integer.parseInt("13ECFC", 16)).setUnlocalizedName("dustDiamond");
		GameRegistry.registerItem(itemDust, "dustDiamond");
		OreDictionary.registerOre("dustDiamond", itemDust);
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.diamond_ore), new ItemStack(itemDust, 2));
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Items.diamond), new ItemStack(itemDust, 1));

		addDusts("Iron", 0xD2CEC9);
		addDusts("Gold", 0xF8DF17);
		addDusts("Copper", 0xBF5E1F);
		addDusts("Lead", 0x808096);
		addDusts("Nickel", 0xBAB0A4);
		addDusts("Platinum", 0xABCDEF);
		addDusts("Silver", 0xB3B3B3);
		addDusts("Tin", 0xF2F2F2);

		//TiC metals
		addDusts("Aluminum", 0xEDEDED);
		addDusts("Ardite", 0xF28900);
		addDusts("Steel", 0x878787);
		addDusts("Manyullyn", 0xAB7EE3);

		if (Loader.isModLoaded("Railcraft"))
			ModIntegration.railcraftIntegration();

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {

		FMLCommonHandler.instance().bus().register(new EventListener.FML());
		MinecraftForge.EVENT_BUS.register(new EventListener.Forge());


		VillagerRegistry.instance().registerVillagerId(457);
		VillagerRegistry.instance().registerVillagerSkin(457, texture);
		VillagerRegistry.instance().registerVillageTradeHandler(457, new VillagerTradeHandler());
		VillagerRegistry.instance().registerVillageCreationHandler(new PowerPlantCreationHandeler());
		try {
			MapGenStructureIO.func_143031_a(ComponentPowerPlant.class, "bcadditions:powerplant");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {

		proxy.registerRenderers();
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

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		EurekaRegistry.registerCategory("BCA", new ItemStack(kineticDusterBlock));

		EurekaRegistry.register(new EurekaInfo(Variables.DustT0Key, "BCA", 1, new ItemStack(basicDusterBlock)));
		EurekaRegistry.registerDrops(Variables.DustT0Key, new ItemStack(eureka.utils.BCItems.STONE_GEAR, 2), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.stone, 5), new ItemStack(Items.slime_ball));
		EurekaRegistry.bindToKey(basicDusterBlock, Variables.DustT0Key);

		EurekaRegistry.register(new EurekaInfo(Variables.DustT1Key, "BCA", 20, new ItemStack(semiAutomaticDusterBlock), Variables.DustT0Key));
		EurekaRegistry.registerDrops(Variables.DustT1Key, new ItemStack(BCItems.PIPE_ITEMS_GOLD, 2), new ItemStack(Items.gold_ingot), new ItemStack(eureka.utils.BCItems.PIPE_ITEMS_GOLD, 2), new ItemStack(Blocks.stone, 3));
		EurekaRegistry.bindToKey(semiAutomaticDusterBlock, Variables.DustT1Key);

		EurekaRegistry.register(new EurekaInfo(Variables.DustT2Key1, "BCA", 40, new ItemStack(mechanicalDusterBlock), Variables.DustT1Key));
		EurekaRegistry.registerDrops(Variables.DustT2Key1, new ItemStack(BCItems.IRON_GEAR, 2), new ItemStack(Items.gold_ingot, 1), new ItemStack(itemGrindingWheel, 1), new ItemStack(Blocks.stone, 5));
		EurekaRegistry.bindToKey(mechanicalDusterBlock, Variables.DustT2Key1);

		EurekaRegistry.register(new EurekaInfo(Variables.DustT2Key2, "BCA", 20, new ItemStack(kineticDusterBlock), Variables.DustT2Key1));
		EurekaRegistry.registerDrops(Variables.DustT2Key2, new ItemStack(Blocks.glass, 3), new ItemStack(BCItems.PIPE_ITEMS_GOLD, 2), new ItemStack(BCItems.GOLD_GEAR, 2), new ItemStack(BCItems.DIAMOND_GEAR));
		EurekaRegistry.bindToKey(kineticDusterBlock, Variables.DustT2Key2);

		EurekaRegistry.register(new EurekaInfo(Variables.KineticToolKey, "BCA", 25, new ItemStack(kineticTool)));
		EurekaRegistry.registerDrops(Variables.KineticToolKey, new ItemStack(Items.diamond, 3), new ItemStack(ironStick), new ItemStack(toolCore));
		EurekaRegistry.bindToKey(kineticTool, Variables.KineticToolKey);

		EurekaRegistry.register(new EurekaInfo("heatedFurnace", "BCA", 5, new ItemStack(heatedFurnaceBlock)));
		EurekaRegistry.registerDrops("heatedFurnace", new ItemStack(Blocks.furnace), new ItemStack(Items.iron_ingot, 8));
		EurekaRegistry.addPlaceBlockProgress(Blocks.furnace, "heatedFurnace");
		EurekaRegistry.bindToKey(heatedFurnaceBlock, "heatedFurnace");

		EurekaRegistry.register(new EurekaInfo("basicCoil", "BCA", 1, new ItemStack(basicCoilBlock), "heatedFurnace"));
		EurekaRegistry.registerDrops("basicCoil", new ItemStack(Items.iron_ingot), new ItemStack(itemIronWire, 8));
		EurekaRegistry.addPlaceBlockProgress(heatedFurnaceBlock, "basicCoil");
		EurekaRegistry.bindToKey(basicCoilBlock, "basicCoil");

		EurekaRegistry.register(new EurekaInfo("lavaCoil", "BCA", 5, new ItemStack(lavaCoilBlock), "basicCoil"));
		EurekaRegistry.registerDrops("lavaCoil", new ItemStack(Items.iron_ingot), new ItemStack(goldWire, 8));
		EurekaRegistry.addPlaceBlockProgress(basicCoilBlock, "lavaCoil");
		EurekaRegistry.bindToKey(lavaCoilBlock, "lavaCoil");

		EurekaRegistry.register(new EurekaInfo("kineticCoil", "BCA", 4, new ItemStack(kineticCoil), "lavaCoil"));
		EurekaRegistry.registerDrops("kineticCoil", new ItemStack(Items.iron_ingot), new ItemStack(diamondWire, 8));
		EurekaRegistry.addPlaceBlockProgress(lavaCoilBlock, "kineticCoil");
		EurekaRegistry.bindToKey(kineticCoil, "kineticCoil");
	}

	public void addDusts(String metalName, int color) {
		ArrayList<ItemStack> list;
		list = OreDictionary.getOres("ingot" + metalName);
		if (list.isEmpty())
			return;
		list = OreDictionary.getOres("ore" + metalName);
		if (list.isEmpty())
			return;
		if (ConfigurationHandeler.shouldRegisterDusts) {
			itemDust = new ItemDust(color).setUnlocalizedName("dust" + metalName);
			GameRegistry.registerItem(itemDust, "dust" + metalName);
			OreDictionary.registerOre("dust" + metalName, itemDust);
			GameRegistry.addSmelting(itemDust, OreDictionary.getOres("ingot" + metalName).get(0), 0);
		} else {
			ArrayList<ItemStack> tempList = OreDictionary.getOres("dust" + metalName);
			if (tempList.isEmpty())
				return;
			ItemStack stack = tempList.get(0);
			if (stack == null)
				return;
			itemDust = stack.getItem();
		}
		for (ItemStack stack : list)
			DusterRecipes.dusting().addDusterRecipe(stack, new ItemStack(itemDust, 2));
		list = OreDictionary.getOres("ingot" + metalName);
		for (ItemStack stack : list)
			DusterRecipes.dusting().addDusterRecipe(stack, new ItemStack(itemDust, 1));
	}


	@Mod.EventHandler
	public void initialize(FMLPreInitializationEvent evt) {
		fluidicCompressorBlock = new BlockFluidicCompressor();
		fluidicCompressorBlock.setBlockName("blockFluidicCompressor").setCreativeTab(bcadditions);
		GameRegistry.registerBlock(fluidicCompressorBlock, "blockFluidicCompressor");

		chargingStationBlock = new BlockChargingStation();
		chargingStationBlock.setBlockName("blockChargingStation").setCreativeTab(bcadditions);
		GameRegistry.registerBlock(chargingStationBlock, "blockChargingStation");

		heatedFurnaceBlock = new BlockHeatedFurnace();
		heatedFurnaceBlock.setBlockName("blockHeatedFurnace").setCreativeTab(bcadditions);
		GameRegistry.registerBlock(heatedFurnaceBlock, "blockHeatedFurnace");

		basicCoilBlock = new BlockBasicCoil();
		basicCoilBlock.setBlockName("blockCoilBasic").setCreativeTab(bcadditions);
		GameRegistry.registerBlock(basicCoilBlock, "blockCoilBasic");

		lavaCoilBlock = new BlockLavaCoil();
		lavaCoilBlock.setBlockName("blockCoilLava").setCreativeTab(bcadditions);
		GameRegistry.registerBlock(lavaCoilBlock, "blockCoilLava");

		basicDusterBlock = new BlockBasicDuster();
		basicDusterBlock.setBlockName("blockDusterBasic").setCreativeTab(bcadditions);
		GameRegistry.registerBlock(basicDusterBlock, "blockDusterBasic");

		semiAutomaticDusterBlock = new BlockSemiAutomaticDuster();
		semiAutomaticDusterBlock.setBlockName("blockDusterSemiAutomatic").setCreativeTab(bcadditions);
		GameRegistry.registerBlock(semiAutomaticDusterBlock, "blockDusterSemiAutomatic");

		mechanicalDusterBlock = new BlockMechanicalDuster();
		mechanicalDusterBlock.setBlockName("blockDusterMechanical").setCreativeTab(bcadditions);
		GameRegistry.registerBlock(mechanicalDusterBlock, "blockDusterMechanical");

		kineticDusterBlock = new BlockKineticDuster();
		kineticDusterBlock.setBlockName("blockDusterKinetic").setCreativeTab(bcadditions);
		GameRegistry.registerBlock(kineticDusterBlock, "blockDusterKinetic");

		kineticCoil = new BlockKineticCoil();
		kineticCoil.setBlockName("blockCoilKinetic").setCreativeTab(bcadditions);
		GameRegistry.registerBlock(kineticCoil, "blockCoilKinetic");
	}

}

