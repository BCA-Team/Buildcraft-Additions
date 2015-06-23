package buildcraftAdditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import buildcraftAdditions.reference.BlockLoader;
import buildcraftAdditions.tileEntities.TileBackpackStand;
import buildcraftAdditions.utils.Raytracing;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockBackpackStand extends BlockRotationBase {
	public IIcon icon;

	public BlockBackpackStand() {
		super("backpackStand", "", false, "backpackStand");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileBackpackStand();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (world.getBlock(x, y + 1, z) != BlockLoader.backpackStandGhost)
				return true;
			TileEntity entity = world.getTileEntity(x, y, z);
			if (entity != null && entity instanceof TileBackpackStand) {
				((TileBackpackStand) entity).onBlockActivated(hitX, hitY, hitZ, world.getBlockMetadata(x, y, z), player);
			}
		}

		return true;
	}


	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		MovingObjectPosition mop = world.rayTraceBlocks(Raytracing.getCorrectedHeadVec(player), Raytracing.getEndVector(player));
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null && entity instanceof TileBackpackStand)
			((TileBackpackStand) entity).removeCapsule(player, world.getBlockMetadata(x, y, z), mop.hitVec.xCoord - mop.blockX, mop.hitVec.yCoord - mop.blockY, mop.hitVec.zCoord - mop.blockZ);

	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		world.setBlock(x, y + 1, z, BlockLoader.backpackStandGhost);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isBlockNormalCube() {
		return false;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null && entity instanceof TileBackpackStand) {
			TileBackpackStand stand = (TileBackpackStand) entity;
			Utils.dropItemstack(world, x, y, z, stand.inventory.getStackInSlot(0));
			stand.inventory.setInventorySlotContents(0, null);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && world.isAirBlock(x, y + 1, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (world.getBlock(x, y + 1, z) != BlockLoader.backpackStandGhost) {
			world.setBlockToAir(x, y, z);
			if (!world.isRemote)
				dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		}
	}
}
