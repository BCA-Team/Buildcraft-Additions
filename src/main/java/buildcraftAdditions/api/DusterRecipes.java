package buildcraftAdditions.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DusterRecipes {

    private static final DusterRecipes dusting = new DusterRecipes();

    private HashMap<ItemStack, ItemStack> dustingList = new HashMap<ItemStack, ItemStack>();

    private DusterRecipes() {

    }

    public static DusterRecipes dusting() {
        return dusting;
    }

    public void addDusterRecipe(ItemStack input, ItemStack output) {
        input.stackSize = 1;
        dustingList.put(input.copy(), output.copy());
    }

    public ItemStack getDustingResult(ItemStack input) {
        if (input != null) {
            for (Entry<ItemStack, ItemStack> entry : dusting.dustingList.entrySet()) {
                if (areItemStacksSameTypeCrafting(input, entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public boolean hasDustingResult(ItemStack input) {
        return getDustingResult(input) != null;
    }

    private boolean areItemStacksSameTypeCrafting(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == stack1.getItemDamage());
    }
}
