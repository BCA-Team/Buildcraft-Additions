package buildcraftAdditions.blocks.multiBlocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.multiBlocks.IMaster;
import buildcraftAdditions.multiBlocks.MultiBlockPatern;
import buildcraftAdditions.multiBlocks.TileMultiBlockSlave;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MulitBlockBase extends Block implements ITileEntityProvider {
	public char identifier;
	public MultiBlockPatern patern;

	@Override
	public boolean hasTileEntity(int metadata) {
		return super.hasTileEntity(metadata);
	}

	public MulitBlockBase(char identifier, MultiBlockPatern patern) {
		super(Material.iron);
		setHardness(4f);
		setHarvestLevel(null, 0);
		this.identifier = identifier;
		this.patern = patern;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (world.getBlockMetadata(x, y, z) == 0) {
			patern.checkPatern(world, x, y, z);
			world.scheduleBlockUpdate(x, y, z, this, 1);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		world.scheduleBlockUpdate(x, y, z, this, 80);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		TileEntity slave = world.getTileEntity(x, y, z);
		if (slave != null && slave instanceof TileMultiBlockSlave) {
			((TileMultiBlockSlave) slave).openGui();
			return true;
		}
		IMaster master = (IMaster) world.getTileEntity(x, y, z);
		if (master != null) {
			master.openGui();
			return true;
		}
		return false;
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		TileMultiBlockSlave slave = (TileMultiBlockSlave) world.getTileEntity(x, y, z);
		if (slave != null)
			slave.destroyMultiblock();
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}
}
