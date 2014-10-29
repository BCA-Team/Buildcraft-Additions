package buildcraftAdditions.multiBlocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.tileEntities.TileKEBT2;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockPaternKEBT2 extends MultiBlockPatern {

	public MultiBlockPaternKEBT2() {
		super(new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.UP, ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH}, 'K');
	}

	@Override
	public void addMaster(World world, int x, int y, int z) {
		TileEntity entity = new TileKEBT2(x, y, z);
		world.setTileEntity(x, y, z, entity);
	}
}
