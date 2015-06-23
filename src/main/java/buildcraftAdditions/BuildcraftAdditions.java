package buildcraftAdditions;

import buildcraftAdditions.api.item.BCAItemManager;
import buildcraftAdditions.api.item.dust.IDust;
import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.compat.ModuleManager;
import buildcraftAdditions.compat.imc.IMCHandler;
import buildcraftAdditions.compat.imc.IMCSender;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.core.GuiHandler;
import buildcraftAdditions.core.SpecialListMananger;
import buildcraftAdditions.creative.TabBCAdditions;
import buildcraftAdditions.creative.TabCanisters;
import buildcraftAdditions.creative.TabDusts;
import buildcraftAdditions.items.dust.DustManager;
import buildcraftAdditions.items.dust.DustTypes;
import buildcraftAdditions.listeners.EventListener;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.proxy.CommonProxy;
import buildcraftAdditions.recipe.duster.DusterRecipeManager;
import buildcraftAdditions.recipe.refinery.CoolingTowerRecipeManager;
import buildcraftAdditions.recipe.refinery.RefineryRecipeManager;
import buildcraftAdditions.reference.ArmorLoader;
import buildcraftAdditions.reference.BlockLoader;
import buildcraftAdditions.reference.ItemLoader;
import buildcraftAdditions.reference.Variables;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@Mod(modid = Variables.MOD.ID, name = Variables.MOD.NAME, version = "@MODVERSION@", guiFactory = "buildcraftAdditions.config.GuiFactory", dependencies = "required-after:BuildCraft|Core;required-after:eureka;before:zCraftingManager;required-after:Forge@[10.13.2.1236,);", acceptedMinecraftVersions = "1.7.10")
public class BuildcraftAdditions {

	public static final CreativeTabs bcadditions = new TabBCAdditions();
	public static final CreativeTabs bcaCannisters = new TabCanisters();
	public static final CreativeTabs bcaDusts = new TabDusts();

	@Mod.Instance(Variables.MOD.ID)
	public static BuildcraftAdditions instance;
	@SidedProxy(clientSide = "buildcraftAdditions.proxy.ClientProxy", serverSide = "buildcraftAdditions.proxy.CommonProxy")
	public static CommonProxy proxy;

	public ModuleManager manager = new ModuleManager();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		manager.setupModules();

		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		PacketHandler.init();
		BlockLoader.loadBlocks();
		ItemLoader.loadItems();
		ArmorLoader.loadArmor();

		proxy.registerEntities();
		SpecialListMananger.init();

		BCARecipeManager.duster = new DusterRecipeManager();
		BCARecipeManager.cooling = new CoolingTowerRecipeManager();
		BCARecipeManager.refinery = new RefineryRecipeManager();

		BCAItemManager.dusts = new DustManager();

		OreDictionary.registerOre("rodBlaze", Items.blaze_rod);
		OreDictionary.registerOre("dustBlaze", Items.blaze_powder);
		OreDictionary.registerOre("materialSnowball", Items.snowball);
		OreDictionary.registerOre("blockSand", Blocks.sand);
		OreDictionary.registerOre("blockSoulSand", Blocks.soul_sand);

		manager.preInit(event);
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, GuiHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(new EventListener.FML());
		MinecraftForge.EVENT_BUS.register(new EventListener.Forge());
		proxy.addListeners();
		IMCSender.sendMessages();
		BlockLoader.registerTileEntities();

		int meta = 1;
		BCAItemManager.dusts.addDust(meta++, "Iron", 0xD2CEC9, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Gold", 0xF8DF17, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta, "Diamond", 0x13ECFC, DustTypes.GEM_DUST);
		meta = 55;
		BCAItemManager.dusts.addDust(meta++, "Emerald", 0x00B038, DustTypes.GEM_DUST);
		BCAItemManager.dusts.addDust(meta++, "Coal", 0x1B1B1B, new DustTypes.SimpleDustAlwaysValid(new ItemStack(Items.coal)));
		BCAItemManager.dusts.addDust(meta++, "Charcoal", 0x53493A, new DustTypes.SimpleDustAlwaysValid(new ItemStack(Items.coal, 1, 1)));
		BCAItemManager.dusts.addDust(meta++, "Obsidian", 0x171124, new DustTypes.SimpleDustAlwaysValid(new ItemStack(Blocks.obsidian), 4));
		BCAItemManager.dusts.addDust(meta++, "EnderPearl", 0x105E51, new DustTypes.SimpleDustAlwaysValid(new ItemStack(Items.ender_pearl)));
		BCAItemManager.dusts.addDust(meta, "NetherQuartz", 0xDBCCBF, new DustTypes.SimpleDustAlwaysValid(new ItemStack(Items.coal, 1, 1)));
		meta = 83;
		BCAItemManager.dusts.addDust(meta, "GildedRedMetal", 0xFF6E1B, new DustTypes.DustAlwaysValid());

		BCARecipeManager.duster.addRecipe("oreRedstone", new ItemStack(Items.redstone, 10));
		BCARecipeManager.duster.addRecipe("oreCoal", new ItemStack(Items.coal, 2));
		BCARecipeManager.duster.addRecipe("oreLapis", new ItemStack(Items.dye, 12, 4));
		BCARecipeManager.duster.addRecipe("oreQuartz", new ItemStack(Items.quartz, 2));
		BCARecipeManager.duster.addRecipe("stone", new ItemStack(Blocks.gravel));
		BCARecipeManager.duster.addRecipe("cobblestone", new ItemStack(Blocks.sand));
		BCARecipeManager.duster.addRecipe("oreDiamond", new ItemStack(Items.diamond, 2));
		BCARecipeManager.duster.addRecipe("oreEmerald", new ItemStack(Items.emerald, 2));
		BCARecipeManager.duster.addRecipe("rodBlaze", new ItemStack(Items.blaze_powder, 4));

		manager.init(event);
		BlockLoader.addRecipes();
		ItemLoader.addRecipes();
		ArmorLoader.addRecipes();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		manager.postInit(event);
	}

	@Mod.EventHandler
	public void doneLoading(FMLLoadCompleteEvent event) {
		IMCHandler.handleIMC(FMLInterModComms.fetchRuntimeMessages(this));
		manager.doneLoadingEvent(event);
		for (IDust dust : BCAItemManager.dusts.getDusts())
			if (dust != null)
				dust.getDustType().register(dust.getMeta(), dust.getName(), dust.getDustStack());
		if (OreDictionary.getOres("dustGold").isEmpty() || OreDictionary.getOres("dustIron").isEmpty()) {
			GameRegistry.addRecipe(new ShapelessOreRecipe(ItemLoader.gildedRedMetalIngot, "ingotGold", "ingotGold", "ingotGold", "ingotIron", "ingotIron", "dustRedstone"));
			GameRegistry.addRecipe(new ShapedOreRecipe(ItemLoader.conductivePlateRaw, "DD", "DD", 'D', "ingotGildedRedMetal"));
		} else {
			ItemStack dust = BCAItemManager.dusts.getDust("GildedRedMetal").getDustStack().copy();
			dust.stackSize = 6;
			GameRegistry.addRecipe(new ShapelessOreRecipe(dust, "dustGold", "dustGold", "dustGold", "dustIron", "dustIron", "dustRedstone"));
			GameRegistry.addRecipe(new ShapedOreRecipe(ItemLoader.conductivePlateRaw, "DD", "DD", 'D', "dustGildedRedMetal"));
		}
	}

	@Mod.EventHandler
	public void onIMC(FMLInterModComms.IMCEvent event) {
		IMCHandler.handleIMC(event.getMessages());
	}

	@Mod.EventHandler
	public void remap(FMLMissingMappingsEvent event) {
		for (FMLMissingMappingsEvent.MissingMapping mapping : event.get()) {
			if (mapping.name.equals(Variables.MOD.ID + ":condictuvePlateRaw")) {
				mapping.remap(ItemLoader.conductivePlateRaw);
			} else if (mapping.name.equals(Variables.MOD.ID + ":PowerCapsuleTier1")) {
				mapping.remap(ItemLoader.powerCapsuleTier1);
			} else if (mapping.name.equals(Variables.MOD.ID + ":backpackstandGhost")) {
				mapping.remap(BlockLoader.backpackStandGhost);
			} else if (mapping.name.equals(Variables.MOD.ID + ":blankUpgrade")) {
				mapping.remap(ItemLoader.blankUpgrade);
			} else if (mapping.name.equals(Variables.MOD.ID + ":condictuvePlate")) {
				mapping.remap(ItemLoader.conductivePlate);
			} else if (mapping.name.equals(Variables.MOD.ID + ":toolUpgradeChaisaw")) {
				mapping.remap(ItemLoader.toolUpgradeChainsaw);
			} else if (mapping.name.equals(Variables.MOD.ID + ":ingotGildedRedMetal")) {
				mapping.remap(ItemLoader.gildedRedMetalIngot);
			} else if (mapping.name.equals(Variables.MOD.ID + ":itemKineticMultiTool")) {
				mapping.remap(ItemLoader.kineticMultiTool);
			} else if (mapping.name.equals(Variables.MOD.ID + ":robotDebugTool")) {
				mapping.ignore();
			} else if (mapping.name.contains("converter")) {
				mapping.ignore();
			}
		}
	}
}
