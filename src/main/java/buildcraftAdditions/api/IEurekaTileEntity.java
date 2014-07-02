package buildcraftAdditions.api;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface IEurekaTileEntity {

    void makeProgress(EntityPlayer player, String key);
}
