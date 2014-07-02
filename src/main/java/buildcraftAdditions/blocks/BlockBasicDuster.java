package buildcraftAdditions.blocks;

import buildcraft.core.IItemPipe;
import buildcraftAdditions.entities.TileBasicDuster;
import buildcraftAdditions.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockBasicDuster extends BlockBase {

    public BlockBasicDuster() {
        super(Material.iron);
        setHardness(5F);
        setResistance(10F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int variable) {
        return new TileBasicDuster();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
        if (player.isSneaking())
            return false;
        if (player.getCurrentEquippedItem() != null) {
            if (player.getCurrentEquippedItem().getItem() instanceof IItemPipe)
                return false;
        }
        TileBasicDuster duster = (TileBasicDuster) world.getTileEntity(x, y, z);
            if (duster != null && duster.getStackInSlot(0) == null && player.getCurrentEquippedItem() != null) {
                ItemStack stack = player.getCurrentEquippedItem().copy();
                stack.stackSize = 1;
                duster.setInventorySlotContents(0, stack);
                player.getCurrentEquippedItem().stackSize--;
                if (player.getCurrentEquippedItem().stackSize <= 0)
                    player.setCurrentItemOrArmor(0, null);
            } else {
                if (duster.getStackInSlot(0) != null){
                    if (!world.isRemote)
                        Utils.dropItemstack(world, x, y, z, duster.getStackInSlot(0));
                    duster.setInventorySlotContents(0, null);
            }
        }
        world.markBlockForUpdate(x, y, z);
        return true;
    }

    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float hit){
        if (entity instanceof EntityPlayer) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileBasicDuster)
                ((TileBasicDuster) tileEntity).makeProgress((EntityPlayer) entity);
        }
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }
}
