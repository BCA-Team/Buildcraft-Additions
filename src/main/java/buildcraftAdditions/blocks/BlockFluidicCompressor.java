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
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.variables.Variables;
import buildcraftAdditions.entities.TileFluidicCompressor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFluidicCompressor extends BlockContainer {
	
	IIcon textureFront;
	IIcon textureTop;
	IIcon textureSide;
	IIcon textureBack;
	IIcon textureBottom;

	public BlockFluidicCompressor() {
		super(Material.iron);
		setHardness(5F);
		setResistance(10F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileFluidicCompressor();
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
			entityplayer.openGui(BuildcraftAdditions.instance, Variables.GuiFluidicCompressor, world, x, y, z);

		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, stack);

		ForgeDirection orientation = Utils.get2dOrientation(entityliving);
		world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal(), 1);

        TileFluidicCompressor canner = (TileFluidicCompressor) world.getTileEntity(i, j, k);
        canner.fill = true;
	}

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta){
        TileFluidicCompressor compressor = (TileFluidicCompressor) world.getTileEntity(x, y, z);
        compressor.openInventory();
        for (int t = 0; t < 2; t++){
            ItemStack stack = compressor.getStackInSlot(t);
            if (stack != null) {
                compressor.setInventorySlotContents(t, null);
                Utils.dropItemstack(world, x, y, z, stack);
            }
        }
        compressor.closeInventory();
        super.breakBlock(world, x, y, z, block, meta);
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
			return textureTop;
		default:
			return textureSide;
		}
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		textureFront = par1IconRegister.registerIcon("bcadditions:fluidicCompressor_front");
		textureSide = par1IconRegister.registerIcon("bcadditions:fluidicCompressor_sides");
		textureTop = par1IconRegister.registerIcon("bcadditions:fluidicCompressor_top");
		textureBack = par1IconRegister.registerIcon("bcadditions:fluidicCompressor_back");
		textureBottom = par1IconRegister.registerIcon("bcadditions:fluidicCompressor_bottom");
	}
}
