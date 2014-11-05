package buildcraftAdditions.blocks.multiBlocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.multiBlocks.MultiBlockPaternKEBT2;
import buildcraftAdditions.tileEntities.TileKEBT2;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockKEBT2 extends MulitBlockBase {

	public MultiBlockKEBT2() {
		super('K', new MultiBlockPaternKEBT2());
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileKEBT2();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
}
