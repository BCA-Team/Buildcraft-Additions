package buildcraftAdditions.blocks;

import buildcraftAdditions.entities.Bases.TileBase;
import buildcraftAdditions.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class BlockBase extends BlockContainer {

    public BlockBase(Material material) {
        super(material);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta){
        TileBase entity = (TileBase) world.getTileEntity(x, y, z);
        if (entity != null) {
            entity.openInventory();

            for (int t = 0; t < entity.getSizeInventory(); t++) {

                ItemStack stack = entity.getStackInSlot(t);
                if (stack != null) {
                    entity.setInventorySlotContents(t, null);
                    Utils.dropItemstack(world, x, y, z, stack);
                }
            }
            entity.closeInventory();
        }
        super.breakBlock(world, x, y, z, block, meta);
    }
}
