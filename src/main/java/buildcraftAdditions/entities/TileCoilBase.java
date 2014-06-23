package buildcraftAdditions.entities;

import net.minecraft.tileentity.TileEntity;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileCoilBase extends TileEntity {
    public boolean shouldHeat;

    public void startHeating(){
        shouldHeat = true;
    }

    public void stopHeating(){
        shouldHeat = false;
    }

    public abstract int getIncrement();

}