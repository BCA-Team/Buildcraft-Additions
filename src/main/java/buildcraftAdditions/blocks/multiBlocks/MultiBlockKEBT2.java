package buildcraftAdditions.blocks.multiBlocks;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.utils.MultiBlockPatern;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockKEBT2 extends MulitBlockBase {

	public MultiBlockKEBT2() {
		super('K', new MultiBlockPatern(new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.UP, ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH}, 'K'));
	}
}
