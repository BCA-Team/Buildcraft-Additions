package buildcraftAdditions.entities;

import buildcraft.core.TileBuildCraft;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileCoilBase extends TileBuildCraft {
    public boolean shouldHeat;
    public boolean burning;

    public void startHeating(){
        shouldHeat = true;
    }

    public void stopHeating(){
        shouldHeat = false;
    }

    public abstract int getIncrement();

    public boolean isBurning(){
        return burning;
    }

}
