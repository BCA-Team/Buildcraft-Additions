package buildcraftAdditions.blocks;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftAdditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftAdditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraft.core.IItemPipe;
import buildcraft.energy.TileEngine;
import buildcraftAdditions.tileEntities.TileAmplifiedEngine;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockEngine extends buildcraft.energy.BlockEngine {

    public BlockEngine(){
        super();
        setBlockName("engineBlock");
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileAmplifiedEngine();
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List itemList) {
        itemList.add(new ItemStack(this, 1, 0));
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int side, float par7, float par8, float par9) {

        TileEntity tile = world.getTileEntity(i, j, k);

        // REMOVED DUE TO CREATIVE ENGINE REQUIREMENTS - dmillerw
        // Drop through if the player is sneaking
//		if (player.isSneaking()) {
//			return false;
//		}

        // Do not open guis when having a pipe in hand
        if (player.getCurrentEquippedItem() != null) {
            if (player.getCurrentEquippedItem().getItem() instanceof IItemPipe) {
                return false;
            }
        }

        if (tile instanceof TileEngine) {
            return ((TileEngine) tile).onBlockActivated(player, ForgeDirection.getOrientation(side));
        }

        return false;
    }
}
