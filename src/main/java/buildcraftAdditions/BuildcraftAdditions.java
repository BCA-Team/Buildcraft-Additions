package buildcraftAdditions;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.ModIntegration.ModIntegration;
import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.client.gui.gui.GuiHandler;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.core.EventListener;
import buildcraftAdditions.core.Logger;
import buildcraftAdditions.creative.TabBCAdditions;
import buildcraftAdditions.creative.TabCanisters;
import buildcraftAdditions.creative.TabDusts;
import buildcraftAdditions.items.ItemDust;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.proxy.CommonProxy;
import buildcraftAdditions.recipe.duster.DusterRecipeManager;
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
@Mod(modid = Variables.MOD.ID, name = Variables.MOD.NAME, version = "@MODVERSION@", guiFactory = "buildcraftAdditions.config.GuiFactory", dependencies = "after:BuildCraft|Energy;required-after:eureka;required-after:Forge@[10.13.2.1230,)", acceptedMinecraftVersions = "1.7.10")
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
		SpecialListMananger.init();

		Item dustDiamond = new ItemDust(0x13ECFC).setUnlocalizedName("dustDiamond");
		GameRegistry.registerItem(dustDiamond, "dustDiamond");
		OreDictionary.registerOre("dustDiamond", dustDiamond);
		Item dustEmerald = new ItemDust(0x17DD62).setUnlocalizedName("dustEmerald");
		GameRegistry.registerItem(dustEmerald, "dustEmerald");
		OreDictionary.registerOre("dustEmerald", dustEmerald);

		BCARecipeManager.duster = new DusterRecipeManager();

		BCARecipeManager.duster.addRecipe("oreRedstone", new ItemStack(Items.redstone, 6));
		BCARecipeManager.duster.addRecipe("oreCoal", new ItemStack(Items.coal, 2));
		BCARecipeManager.duster.addRecipe("oreLapis", new ItemStack(Items.dye, 6, 4));
		BCARecipeManager.duster.addRecipe("oreQuartz", new ItemStack(Items.quartz, 2));
		BCARecipeManager.duster.addRecipe("stone", new ItemStack(Blocks.gravel));
		BCARecipeManager.duster.addRecipe("cobblestone", new ItemStack(Blocks.sand));
		BCARecipeManager.duster.addRecipe("oreDiamond", new ItemStack(dustDiamond, 2));
		BCARecipeManager.duster.addRecipe("gemDiamond", new ItemStack(dustDiamond));
		BCARecipeManager.duster.addRecipe("oreEmerald", new ItemStack(dustEmerald, 2));
		BCARecipeManager.duster.addRecipe("gemEmerald", new ItemStack(dustEmerald));
		BCARecipeManager.duster.addRecipe(new ItemStack(Items.blaze_rod), new ItemStack(Items.blaze_powder, 4));
	}

	@Mod.EventHandler
	public void doneLoading(FMLLoadCompleteEvent event) {
		ItemsAndBlocks.addRecipes();

		addDusts("Iron", 0xD2CEC9);
		addDusts("Gold", 0xF8DF17);

		ModIntegration.integrate();
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		FMLCommonHandler.instance().bus().register(new EventListener.FML());
		MinecraftForge.EVENT_BUS.register(new EventListener.Forge());
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
		if (ConfigurationHandler.shouldRegisterDusts) {
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
			BCARecipeManager.duster.addRecipe(stack.copy(), new ItemStack(itemDust, 2));
		list = OreDictionary.getOres("ingot" + metalName);
		for (ItemStack stack : list)
			BCARecipeManager.duster.addRecipe(stack.copy(), new ItemStack(itemDust, 1));
	}
}

