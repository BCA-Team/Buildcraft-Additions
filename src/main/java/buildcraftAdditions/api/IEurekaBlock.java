package buildcraftAdditions.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface IEurekaBlock {

    /**
     * Used to check if the player can use this or not, call the eureka system here for the check or do your own check
     */
    boolean isAllowed(EntityPlayer player);

    /**
     * Use this to specify what items get droped when a 'dumb' player tries to use it
     */
    ItemStack[] getComponents();

    /**
     * The message 'dumb' players get when there machine falls appart
     */
    String getMessage();
}
