package buildcraftAdditions.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DusterRecepies {
    public static ArrayList<Item> dusterInput = new ArrayList<Item>(20);
    public static ArrayList<ItemStack> dusterOutput = new ArrayList<ItemStack>(20);

    public static void addDusterRecepie(ItemStack input, ItemStack output){
        dusterInput.add(input.getItem());
        dusterOutput.add(output);
    }

    public static ItemStack getOutput(ItemStack input){
         if (dusterInput.contains(input.getItem()))
            return dusterOutput.get(dusterInput.indexOf(input.getItem())).copy();
        return null;
    }
}
