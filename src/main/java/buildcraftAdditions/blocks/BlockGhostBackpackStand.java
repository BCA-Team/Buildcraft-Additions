package buildcraftAdditions.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.tileEntities.TileBackpackStand;
import buildcraftAdditions.utils.Utils;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockGhostBackpackStand extends BlockBase {

	public BlockGhostBackpackStand() {
		super("blockGhost");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntity entity = world.getTileEntity(x, y - 1, z);
			if (entity != null && entity instanceof TileBackpackStand) {
				((TileBackpackStand) entity).onBlockActivated(hitX, hitY + 1, hitZ, world.getBlockMetadata(x, y - 1, z), player);
			} else {
				world.setBlockToAir(x, y, z);
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		Utils.harvestBlock(world, x, y - 1, z, player);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
		world.setBlockToAir(x, y - 1, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (world.getBlock(x, y - 1, z) != ItemsAndBlocks.backpackStand)
			world.setBlockToAir(x, y, z);
	}

	@Override
	public Item getItemDropped(int number, Random random, int anotherNumber) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return new ItemStack(ItemsAndBlocks.backpackStand);
	}
}
