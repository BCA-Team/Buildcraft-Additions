package buildcraftAdditions.ModIntegration.nei;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.items.ItemCanister;
import buildcraftAdditions.reference.ItemsAndBlocks;

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

        API.addItemListEntry(ItemsAndBlocks.kebT1.createCreativeKEB());

        API.addItemListEntry(new ItemStack(ItemsAndBlocks.powerCapsuleTier1));
        API.addItemListEntry(new ItemStack(ItemsAndBlocks.powerCapsuleTier2));
        API.addItemListEntry(new ItemStack(ItemsAndBlocks.powerCapsuleTier3));
        API.addItemListEntry(ItemsAndBlocks.powerCapsuleTier1.createdFilledBattery());
        API.addItemListEntry(ItemsAndBlocks.powerCapsuleTier2.createdFilledBattery());
        API.addItemListEntry(ItemsAndBlocks.powerCapsuleTier3.createdFilledBattery());

        API.addItemListEntry(new ItemStack(ItemsAndBlocks.ironCanister));
        API.addItemListEntry(new ItemStack(ItemsAndBlocks.goldCanister));
        API.addItemListEntry(new ItemStack(ItemsAndBlocks.diamondCanister));
        addFullCanisters(ItemsAndBlocks.ironCanister);
        addFullCanisters(ItemsAndBlocks.goldCanister);
        addFullCanisters(ItemsAndBlocks.diamondCanister);
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
