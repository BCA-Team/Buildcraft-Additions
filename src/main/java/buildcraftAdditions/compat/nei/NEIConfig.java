package buildcraftAdditions.compat.nei;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import buildcraftAdditions.api.item.BCAItemManager;
import buildcraftAdditions.api.item.dust.IDust;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.TemplateRecipeHandler;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		registerRecipeHandler(new DustingRecipeHandler());
		registerRecipeHandler(new RecipeHandlerCoolingTower());
		API.hideItem(GameRegistry.findItemStack(Variables.MOD.ID, "kebT2DisplayItem", 1));
		API.hideItem(GameRegistry.findItemStack(Variables.MOD.ID, "kebT3DisplayItem", 1));
		API.hideItem(new ItemStack(ItemsAndBlocks.kinesisPipeWood));
		API.hideItem(new ItemStack(ItemsAndBlocks.kinisisPipeStone));
		API.hideItem(new ItemStack(ItemsAndBlocks.backpackStandGhost));
		for (IDust dust : BCAItemManager.dusts.getDusts())
			if (dust != null && dust.getName() != null)
				API.hideItem(GameRegistry.findItemStack(Variables.MOD.ID, "converter" + dust.getName().toLowerCase(), 1));
	}

	public void registerRecipeHandler(TemplateRecipeHandler handler) {
		API.registerRecipeHandler(handler);
		API.registerUsageHandler(handler);
	}

	@Override
	public String getName() {
		return "Buildcraft Additions";
	}

	@Override
	public String getVersion() {
		return "@MODVERSION@";
	}
}
