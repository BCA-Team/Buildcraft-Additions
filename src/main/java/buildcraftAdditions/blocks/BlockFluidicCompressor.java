package buildcraftAdditions.blocks;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.utils.Utils;

public class BlockFluidicCompressor extends BlockRotationBase {

	@SideOnly(Side.CLIENT)
	private IIcon textureFront, textureBack, textureTop, textureBottom, textureSide;

	public BlockFluidicCompressor() {
		super("blockFluidicCompressor", "fluidicCompressor/", false);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileFluidicCompressor();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.isSneaking())
			return false;

		if (!world.isRemote)
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.FLUIDIC_COMPRESSOR.ordinal(), world, x, y, z);

		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		world.setBlockMetadataWithNotify(x, y, z, Utils.get2dOrientation(entity).getOpposite().ordinal(), 3);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileFluidicCompressor)
			((TileFluidicCompressor) tile).fill = true;
	}
}
