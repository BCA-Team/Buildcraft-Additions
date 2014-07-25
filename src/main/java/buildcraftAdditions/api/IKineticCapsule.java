package buildcraftAdditions.api;

import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface IKineticCapsule {

    public void decreaseEnergy(ItemStack stack, double energy);

    public void increaseEnergy(ItemStack stack, double energy);

    public double getEnergy(ItemStack stack);

    public void setEnergy (ItemStack stack, double energy);

    public int getCapacity();
}
