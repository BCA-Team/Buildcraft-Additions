package buildcraftAdditions.blocks.multiBlocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.multiBlocks.MultiBlockPatern;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MulitBlockBase extends BlockContainer {
	public char identifier;
	public MultiBlockPatern patern;


	public MulitBlockBase(char identifier, MultiBlockPatern patern) {
		super(Material.iron);
		setHardness(4f);
		setHarvestLevel(null, 0);
		this.identifier = identifier;
		this.patern = patern;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		world.scheduleBlockUpdate(x, y, z, this, 80);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return null;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (world.getBlockMetadata(x, y, z) == 0) {
			patern.checkPatern(world, x, y, z);
			world.scheduleBlockUpdate(x, y, z, this, 1);
		}
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return metadata == 1;
	}



	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null && entity instanceof IMultiBlockTile) {
			return ((IMultiBlockTile) entity).onBlockActivated(player);
		}
		return false;
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null && entity instanceof IMultiBlockTile)
			((IMultiBlockTile) entity).invalidateMultiblock();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}
}
