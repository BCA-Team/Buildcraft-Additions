package buildcraftAdditions.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.BuildCraftCore;
import buildcraft.core.IItemPipe;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.tileEntities.TileMechanicalDuster;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.variables.Variables;


import eureka.api.EurekaKnowledge;
import eureka.api.interfaces.IEurekaBlock;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockMechanicalDuster extends BlockBase implements IEurekaBlock {
    public IIcon front, sides, bottom;
    public IIcon top[] = new IIcon[4];

    public BlockMechanicalDuster() {
        super(Material.iron);
    }

    @Override
    public boolean isAllowed(EntityPlayer player) {
        return EurekaKnowledge.isFinished(player, Variables.DustT2Key1);
    }

    @Override
    public ItemStack[] getComponents() {
        return new ItemStack[]{new ItemStack(BuildCraftCore.ironGearItem, 2), new ItemStack(Items.gold_ingot, 1), new ItemStack(BuildcraftAdditions.itemGrindingWheel, 1), new ItemStack(Blocks.stone, 5)};
    }

    @Override
    public String getMessage() {
        return Utils.localize("eureka.missingKnowledge");
    }

	@Override
	public boolean breakOnInteraction() {
		return true;
	}

	@Override
    public TileEntity createNewTileEntity(World world, int getal) {
        return new TileMechanicalDuster();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
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
        TileMechanicalDuster duster = (TileMechanicalDuster) world.getTileEntity(x, y, z);
	    duster.player = player;
        if (duster.getStackInSlot(0) == null && player.getCurrentEquippedItem() != null) {
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
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
        super.onBlockPlacedBy(world, i, j, k, entityliving, stack);

        ForgeDirection orientation = Utils.get2dOrientation(entityliving);
        world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal(), 1);

    }

	@SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side){
        int meta = access.getBlockMetadata(x, y, z);
        if (meta == 0 && side == 3)
            return front;

        if (side == meta && meta > 1) {
            return front;
        }

        if (side == 1){
            TileMechanicalDuster duster = (TileMechanicalDuster) access.getTileEntity(x, y, z);
            return top[duster.progressStage];
        }

        switch (side) {
            case 0:
                return bottom;
            default:
                return sides;
        }
    }

	@SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        // If no metadata is set, then this is an icon.
        if (meta == 0 && side == 3)
            return front;

        if (side == meta && meta > 1)
            return front;


            switch (side) {
                case 0:
                    return bottom;
                case 1:
                    return top[0];
            }
        return sides;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        front = par1IconRegister.registerIcon("bcadditions:dusterMechanicalFront");
        sides = par1IconRegister.registerIcon("bcadditions:dusterMechanicalSides");
        bottom = par1IconRegister.registerIcon("bcadditions:dusterMechanicalBottom");
        top[0] = par1IconRegister.registerIcon("bcadditions:dusterMechanicalTop0");
        top[1] = par1IconRegister.registerIcon("bcadditions:dusterMechanicalTop1");
        top[2] = par1IconRegister.registerIcon("bcadditions:dusterMechanicalTop2");
        top[3] = par1IconRegister.registerIcon("bcadditions:dusterMechanicalTop3");
    }
}
