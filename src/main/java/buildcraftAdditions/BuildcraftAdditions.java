package buildcraftAdditions;

import java.util.ArrayList;

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
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.api.DusterRecipes;
import buildcraftAdditions.client.gui.GuiHandler;
import buildcraftAdditions.config.ConfigurationHandeler;
import buildcraftAdditions.core.EventListener;
import buildcraftAdditions.core.Logger;
import buildcraftAdditions.core.ModIntegration;
import buildcraftAdditions.items.ItemDust;
import buildcraftAdditions.networking.PacketHandeler;
import buildcraftAdditions.proxy.CommonProxy;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.TrigersAndActions;
import buildcraftAdditions.utils.SpecialListMananger;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */


@Mod(modid = "bcadditions", name = "Buildcraft Additions", version = "@MODVERSION@", guiFactory = "buildcraftAdditions.config.GuiFactory", dependencies = "after:BuildCraft|Energy;required-after:eureka;required-after:Forge@[10.13.2.1230,)", acceptedMinecraftVersions = "1.7.10")
public class BuildcraftAdditions {


	@Mod.Instance("bcadditions")
	public static BuildcraftAdditions instance;

	@SidedProxy(clientSide = "buildcraftAdditions.proxy.ClientProxy", serverSide = "buildcraftAdditions.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs bcadditions = new CreativeTabs("BuildcraftAdditions") {

		@Override
		public Item getTabIconItem() {
			return new ItemStack(ItemsAndBlocks.fluidicCompressorBlock, 1).getItem();
		}

	};

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Logger.initiallize();
		ConfigurationHandeler.init(event.getSuggestedConfigurationFile());
		PacketHandeler.init();
		ItemsAndBlocks.init();
		TrigersAndActions.register();
		SpecialListMananger.init();

		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.redstone_ore), new ItemStack(Items.redstone, 6));
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.coal_ore), new ItemStack(Items.coal, 2));
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.lapis_ore), new ItemStack(Items.dye, 6, 4));
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.quartz_ore), new ItemStack(Items.quartz, 2));
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.stone), new ItemStack(Blocks.gravel));
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.sand));
	}

	@Mod.EventHandler
	public void doneLoading(FMLLoadCompleteEvent event) {
		ItemsAndBlocks.addRecepies();

		Item itemDust = new ItemDust(Integer.parseInt("13ECFC", 16)).setUnlocalizedName("dustDiamond");
		GameRegistry.registerItem(itemDust, "dustDiamond");
		OreDictionary.registerOre("dustDiamond", itemDust);
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Blocks.diamond_ore), new ItemStack(itemDust, 2));
		DusterRecipes.dusting().addDusterRecipe(new ItemStack(Items.diamond), new ItemStack(itemDust, 1));
		DusterRecipes.dusting().addDusterRecipe(Items.blaze_rod, new ItemStack(Items.blaze_powder, 4));

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
		addDusts("Cobalt", 0x0064FF);

		if (Loader.isModLoaded("Railcraft"))
			ModIntegration.railcraftIntegration();
		if (Loader.isModLoaded("Metallurgy"))
			ModIntegration.metallurgyMetals();

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		FMLCommonHandler.instance().bus().register(new EventListener.FML());
		MinecraftForge.EVENT_BUS.register(new EventListener.Forge());
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		ModIntegration.eurekaResearch();
	}

	public void addDusts(String metalName, int color) {
		Item itemDust;
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
			GameRegistry.addSmelting(itemDust, OreDictionary.getOres("ingot" + metalName).get(0).copy(), 0);
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
			DusterRecipes.dusting().addDusterRecipe(stack.copy(), new ItemStack(itemDust, 2));
		list = OreDictionary.getOres("ingot" + metalName);
		for (ItemStack stack : list)
			DusterRecipes.dusting().addDusterRecipe(stack.copy(), new ItemStack(itemDust, 1));
	}
}

