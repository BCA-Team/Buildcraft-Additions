package buildcraftAdditions.api;

import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftAdditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftAdditions.wordpress.com/wiki/licensing-stuff/
 */
public interface IKineticCapsule {

    /**
     * decrease the amount of stored energy
     * @param stack the itemstack to decrease
     * @param energy the amount to decrease
     */
    public void decreaseEnergy(ItemStack stack, double energy);

    /**
     * increase the amount of stored energy
     * @param stack the itemstack to increase
     * @param energy the amount to increase
     */
    public void increaseEnergy(ItemStack stack, double energy);

    /**
     * @param stack the itemstack from where to get the amount of energy
     * @return the amount of energy stored in the specified stack
     */
    public double getEnergy(ItemStack stack);

    /**
     * Overrides the amount of stored energy inside the specified stack
     * @param stack the stack to override energy on
     * @param energy new amoung of energy stored
     */
    public void setEnergy (ItemStack stack, double energy);

    /**
     * @return the max energy that can be stored
     */
    public int getCapacity();
}
