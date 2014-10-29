package buildcraftAdditions.utils;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.common.util.ForgeDirection;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Location {
	public int x, y, z;
	public IBlockAccess world;

	public Location(IBlockAccess world, int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}

	public Location move (ForgeDirection direction) {
		return move(direction, 1);
	}

	public Location move(ForgeDirection direction, int blocks){
		x += (direction.offsetX * blocks);
		y += (direction.offsetY * blocks);
		z += direction.offsetZ;
		return this;
	}

	public TileEntity getTileEntity() {
		return world.getTileEntity(x, y, z);
	}

	public Block getBlock() {
		return world.getBlock(x, y, z);
	}
}
