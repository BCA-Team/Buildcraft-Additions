package buildcraftAdditions.blocks;

import buildcraftAdditions.api.IEurekaBlock;
import buildcraftAdditions.utils.Eureka;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.variables.Variables;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
public class BlockSemiAutomaticDuster extends BlockBase implements IEurekaBlock {

    public BlockSemiAutomaticDuster() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return null;
    }

    @Override
    public boolean isAllowed(EntityPlayer player) {
        return Eureka.isUnlocked(player, Variables.DustT1Key);
    }

    @Override
    public ItemStack[] getComponents() {
        return new ItemStack[]{new ItemStack(Items.gold_ingot, 5)};
    }

    @Override
    public String getMessage() {
        return Utils.localize("eureka.missingKnowledge");
    }
}
