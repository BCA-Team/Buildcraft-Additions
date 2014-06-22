package buildcraftAdditions.blocks;

import buildcraftAdditions.entities.TileBasicCoil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockBasicCoil extends BlockCoilBase {

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileBasicCoil();
    }
}
