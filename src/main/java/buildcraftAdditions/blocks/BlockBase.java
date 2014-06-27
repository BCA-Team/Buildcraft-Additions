package buildcraftAdditions.blocks;

import buildcraftAdditions.entities.TileBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
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
        TileBase tile = (TileBase) world.getTileEntity(x, y, z);
        tile.openInventory();
        float f1 = 0.7F;
        for (int t = 0; t < tile.getSizeInventory(); t++){
            double d = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
            double d1 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
            double d2 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
            ItemStack stack = tile.getStackInSlot(t);
            if (stack != null) {
                tile.setInventorySlotContents(t, null);
                EntityItem itemToDrop = new EntityItem(world, x + d, y + d1, z + d2, stack);
                itemToDrop.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(itemToDrop);
            }
        }
        tile.closeInventory();
        super.breakBlock(world, x, y, z, block, meta);
    }
}
