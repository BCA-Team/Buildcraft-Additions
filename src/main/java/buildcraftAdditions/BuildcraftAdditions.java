package buildcraftAdditions;

import java.util.Iterator;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;

import buildcraftAdditions.ModIntegration.ModIntegration;
import buildcraftAdditions.ModIntegration.imc.IMCHandler;
import buildcraftAdditions.ModIntegration.imc.IMCSender;
import buildcraftAdditions.api.item.BCAItemManager;
import buildcraftAdditions.api.item.dust.IDust;
import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.client.gui.gui.GuiHandler;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.core.BucketHandler;
import buildcraftAdditions.core.EventListener;
import buildcraftAdditions.core.Logger;
import buildcraftAdditions.creative.TabBCAdditions;
import buildcraftAdditions.creative.TabCanisters;
import buildcraftAdditions.creative.TabDusts;
import buildcraftAdditions.items.dust.DustManager;
import buildcraftAdditions.items.dust.DustTypes;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.proxy.CommonProxy;
import buildcraftAdditions.recipe.duster.DusterRecipeManager;
import buildcraftAdditions.recipe.refinery.CoolingTowerRecipeManager;
import buildcraftAdditions.recipe.refinery.RefineryRecipeManager;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.SpecialListMananger;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@Mod(modid = Variables.MOD.ID, name = Variables.MOD.NAME, version = "@MODVERSION@", guiFactory = "buildcraftAdditions.config.GuiFactory", dependencies = "after:BuildCraft|Energy;before:zCraftingManager;required-after:Forge@[10.13.2.1230,)", acceptedMinecraftVersions = "1.7.10")
public class BuildcraftAdditions {

	@Mod.Instance(Variables.MOD.ID)
	public static BuildcraftAdditions instance;

	@SidedProxy(clientSide = "buildcraftAdditions.proxy.ClientProxy", serverSide = "buildcraftAdditions.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs bcadditions = new TabBCAdditions();
	public static CreativeTabs bcaCannisters = new TabCanisters();
	public static CreativeTabs bcaDusts = new TabDusts();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Logger.initiallize();
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		PacketHandler.init();
		ItemsAndBlocks.init();
		proxy.registerEntities();
		SpecialListMananger.init();

		BCARecipeManager.duster = new DusterRecipeManager();
		BCARecipeManager.cooling = new CoolingTowerRecipeManager();
		BCARecipeManager.refinery = new RefineryRecipeManager();

		BCAItemManager.dusts = new DustManager();
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		FMLCommonHandler.instance().bus().register(new EventListener.FML());
		MinecraftForge.EVENT_BUS.register(new EventListener.Forge());
		BucketHandler.register();
		IMCSender.sendMessages();
		ItemsAndBlocks.registerTileEntities();

		int meta = 1;
		BCAItemManager.dusts.addDust(meta++, "Iron", 0xD2CEC9, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Gold", 0xF8DF17, DustTypes.METAL_DUST);
		BCAItemManager.dusts.addDust(meta++, "Diamond", 0x13ECFC, DustTypes.GEM_DUST);

		BCARecipeManager.duster.addRecipe("oreRedstone", new ItemStack(Items.redstone, 6));
		BCARecipeManager.duster.addRecipe("oreCoal", new ItemStack(Items.coal, 2));
		BCARecipeManager.duster.addRecipe("oreLapis", new ItemStack(Items.dye, 6, 4));
		BCARecipeManager.duster.addRecipe("oreQuartz", new ItemStack(Items.quartz, 2));
		BCARecipeManager.duster.addRecipe("stone", new ItemStack(Blocks.gravel));
		BCARecipeManager.duster.addRecipe("cobblestone", new ItemStack(Blocks.sand));
		BCARecipeManager.duster.addRecipe("oreDiamond", new ItemStack(Items.diamond, 2));
		BCARecipeManager.duster.addRecipe("oreEmerald", new ItemStack(Items.emerald, 2));
		BCARecipeManager.duster.addRecipe(new ItemStack(Items.blaze_rod), new ItemStack(Items.blaze_powder, 4));

		ModIntegration.integrateInit();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}

	@Mod.EventHandler
	public void doneLoading(FMLLoadCompleteEvent event) {
		ModIntegration.integrate();
		IMCHandler.handleIMC(FMLInterModComms.fetchRuntimeMessages(this));
		for (IDust dust : BCAItemManager.dusts.getDusts())
			if (dust != null)
				dust.getDustType().register(dust.getMeta(), dust.getName(), dust.getDustStack());
	}

	@Mod.EventHandler
	public void onIMC(FMLInterModComms.IMCEvent event) {
		IMCHandler.handleIMC(event.getMessages());
	}

	@Mod.EventHandler
	public void remap(FMLMissingMappingsEvent event) {
		Iterator<? extends IDust> iterator = BCAItemManager.dusts.getDusts().iterator();
		for (FMLMissingMappingsEvent.MissingMapping mapping : event.get()) {
			if (mapping.name.toLowerCase().contains("fuelgas")) {
				if (mapping.type == GameRegistry.Type.ITEM)
					mapping.remap(Item.getItemFromBlock(FluidRegistry.getFluid("fuelgas").getBlock()));
				if (mapping.type == GameRegistry.Type.BLOCK)
					mapping.remap(FluidRegistry.getFluid("fuelgas").getBlock());
				continue;
			} else if (mapping.name.toLowerCase().contains("bioethanolgas") && Loader.isModLoaded("Forestry")) {
				if (mapping.type == GameRegistry.Type.ITEM)
					mapping.remap(Item.getItemFromBlock(FluidRegistry.getFluid("bioethanolgas").getBlock()));
				else if (mapping.type == GameRegistry.Type.BLOCK)
					mapping.remap(FluidRegistry.getFluid("bioethanolgas").getBlock());
				continue;
			}
			if (!ConfigurationHandler.shouldRegisterDusts)
				return;
			while (iterator.hasNext()) {
				IDust dust = iterator.next();
				if (dust == null)
					continue;
				String name = dust.getName().toLowerCase();
				if (mapping.name.toLowerCase().contains(name)) {
					mapping.remap(GameRegistry.findItem(Variables.MOD.ID, "converter" + name));
					iterator.remove();
					continue;
				}
			}
		}
	}
}
