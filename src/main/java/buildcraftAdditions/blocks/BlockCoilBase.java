package buildcraftAdditions.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class BlockCoilBase extends BlockContainer {

    public BlockCoilBase() {
        super(Material.iron);
        this.setBlockBounds(2F/10F, 0, 2F/10F, 8F/10F, 1, 8F/10F);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

}
