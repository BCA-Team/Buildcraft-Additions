package buildcraftAdditions.blocks;

import buildcraft.core.IItemPipe;
import buildcraftAdditions.BuildcraftAdditions;
import Variables.Variables;
import buildcraftAdditions.entities.TileBasicDuster;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
        super.onBlockActivated(world, x, y, z, entityplayer, par6, par7, par8, par9);

        // Drop through if the player is sneaking
        if (entityplayer.isSneaking())
            return false;

        if (entityplayer.getCurrentEquippedItem() != null) {
            if (entityplayer.getCurrentEquippedItem().getItem() instanceof IItemPipe)
                return false;
        }

        if (!world.isRemote)
            entityplayer.openGui(BuildcraftAdditions.instance, Variables.GuiBasicDuster, world, x, y, z);

        return true;
    }

    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float hit){
        if (entity instanceof EntityPlayer) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileBasicDuster)
                ((TileBasicDuster) tileEntity).makeProgress();
        }
    }
}
