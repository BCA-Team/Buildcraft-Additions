package buildcraftAdditions.blocks;

import buildcraftAdditions.api.IEurekaBlock;
import buildcraftAdditions.entities.TileKineticDuster;
import buildcraftAdditions.utils.Utils;
import net.minecraft.block.material.Material;
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
public class BlockKineticDuster extends BlockBase implements IEurekaBlock {

    public BlockKineticDuster() {
        super(Material.iron);
    }

    @Override
    public boolean isAllowed(EntityPlayer player) {
        return false;
    }

    @Override
    public ItemStack[] getComponents() {
        return new ItemStack[0];
    }

    @Override
    public String getMessage() {
        return Utils.localize("eureka.missingKnowledge");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int getal) {
        return new TileKineticDuster();
    }
}
