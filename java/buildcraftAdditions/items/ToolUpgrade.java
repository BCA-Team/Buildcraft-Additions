package buildcraftAdditions.items;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraftAdditions.core.BuildcraftAdditions;
import net.minecraft.item.Item;

public abstract class ToolUpgrade extends Item {

    public ToolUpgrade(){
        this.setMaxStackSize(16);
        this.setCreativeTab(BuildcraftAdditions.bcadditions);
    }

    public abstract String getType();
}
