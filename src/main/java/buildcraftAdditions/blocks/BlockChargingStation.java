package buildcraftAdditions.blocks;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraft.core.IItemPipe;
import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.core.Utils;
import buildcraftAdditions.core.Variables;
import buildcraftAdditions.entities.TileChargingStation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockChargingStation extends BlockContainer {

    public IIcon textureFront;
    public IIcon textureBack;
    public IIcon textureLeft;
    public IIcon textureRight;
    public IIcon textureTop;
    public IIcon textureBottom;

	public BlockChargingStation() {
		super(Material.iron);
		setHardness(5F);
		setResistance(10F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileChargingStation();
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
			entityplayer.openGui(BuildcraftAdditions.instance, Variables.GuiChargingStation, world, x, y, z);

		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, stack);

		ForgeDirection orientation = Utils.get2dOrientation(entityliving);
		world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal(), 1);
        return;
	}

    @Override
    public IIcon getIcon(int i, int j) {
        // If no metadata is set, then this is an icon.
        if (j == 0 && i == 3)
            return textureFront;

        if (i == j && i > 1)
            return textureFront;

        switch (i) {
            case 0:
                return textureBottom;
            case 1:
                return textureBottom;
            default:
                return textureBack;
        }
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        textureFront = par1IconRegister.registerIcon("bcadditions:charger_front");
        textureTop = par1IconRegister.registerIcon("bcadditions:charger_top");
        textureBack = par1IconRegister.registerIcon("bcadditions:charger_back");
        textureBottom = par1IconRegister.registerIcon("bcadditions:charger_bottom");
        textureLeft = par1IconRegister.registerIcon("bcadditions:charger_leftSide");
        textureRight = par1IconRegister.registerIcon("bcadditions:charger_rightSide");
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta){
        TileChargingStation station = (TileChargingStation) world.getTileEntity(x, y, z);
        station.openInventory();
            float f1 = 0.7F;
            double d = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
            double d1 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
            double d2 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
            ItemStack stack = station.getStackInSlot(0);
            if (stack != null) {
                station.setInventorySlotContents(0, null);
                EntityItem itemToDrop = new EntityItem(world, x + d, y + d1, z + d2, stack);
                itemToDrop.delayBeforeCanPickup = 10;

                world.spawnEntityInWorld(itemToDrop);
            }
        }

}
