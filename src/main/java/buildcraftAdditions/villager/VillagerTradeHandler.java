package buildcraftAdditions.villager;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import java.util.Random;

import buildcraftAdditions.BuildcraftAdditions;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class VillagerTradeHandler implements IVillageTradeHandler {

    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
    {
    	//canisters
        recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 1), null, new ItemStack(BuildcraftAdditions.ironCanister, 2)));
        recipeList.add(new MerchantRecipe(new ItemStack(BuildcraftAdditions.ironCanister, 1), new ItemStack(Items.emerald, 1), new ItemStack(BuildcraftAdditions.goldCanister, 1)));
        recipeList.add(new MerchantRecipe(new ItemStack(BuildcraftAdditions.goldCanister, 1), new ItemStack(Items.emerald, 4), new ItemStack(BuildcraftAdditions.diamondCanister, 1)));

        //kinetic capsules
        recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 2), null, new ItemStack(BuildcraftAdditions.powerCapsuleTier1, 1)));
        recipeList.add(new MerchantRecipe(new ItemStack(BuildcraftAdditions.powerCapsuleTier1, 1), new ItemStack(Items.emerald, 3), new ItemStack(BuildcraftAdditions.powerCapsuleTier2, 1)));
        recipeList.add(new MerchantRecipe(new ItemStack(BuildcraftAdditions.powerCapsuleTier2, 1), new ItemStack(Items.emerald, 10), new ItemStack(BuildcraftAdditions.powerCapsuleTier3, 1)));

        //emerald stick
        recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 15), null, new ItemStack(BuildcraftAdditions.emeraldStick, 1)));
    }

}