package buildcraftAdditions.ModIntegration.nei;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.api.item.BCAItemManager;
import buildcraftAdditions.api.item.dust.IDust;
import buildcraftAdditions.items.ItemCanister;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		DustingRecipeHandler dustingRecipeHandler = new DustingRecipeHandler();
		API.registerRecipeHandler(dustingRecipeHandler);
		API.registerUsageHandler(dustingRecipeHandler);
        API.hideItem(GameRegistry.findItemStack(Variables.MOD.ID, "test", 1));
        API.hideItem(new ItemStack(ItemsAndBlocks.kinesisPipeWood));
        API.hideItem(new ItemStack(ItemsAndBlocks.kinisisPipeStone));
        for (IDust dust : BCAItemManager.dusts.getDusts()) {
            if (dust == null)
                continue;
            String name = dust.getName().toLowerCase();
            API.hideItem(GameRegistry.findItemStack(Variables.MOD.ID, "converter" + name, 1));
        }
	}

    public void addFullCanisters(ItemCanister canister) {
        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values())
            API.addItemListEntry(canister.getFilledItemStack(new FluidStack(fluid, canister.getCapacity())));
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
