package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileBase extends TileEntity implements IInventory {

    @Override
    public abstract void updateEntity();

}
