package buildcraftAdditions.villager;

import java.util.Random;

import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftEnergy;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class VillagerTradeHandler implements IVillageTradeHandler {

    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
    {
    	//this is where the custom villager trades are specified
        recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 1), null, new ItemStack(BuildCraftCore.stoneGearItem, 1)));
        recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 2), null, new ItemStack(BuildCraftCore.ironGearItem, 1)));
        recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 4), null, new ItemStack(BuildCraftCore.goldGearItem, 1)));
        recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 8), null, new ItemStack(BuildCraftCore.diamondGearItem, 1)));
    }

}