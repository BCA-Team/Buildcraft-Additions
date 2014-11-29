package buildcraftAdditions.multiBlocks;

import net.minecraftforge.common.util.ForgeDirection;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockPaternKEBT3 extends MultiBlockPatern {

	public MultiBlockPaternKEBT3() {
		super(new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.SOUTH,
				ForgeDirection.WEST, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.UP,
				ForgeDirection.EAST, ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.WEST,
				ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.DOWN, ForgeDirection.DOWN,
				ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.WEST,
				ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.NORTH}, 'W');
		identifiers[17] = 'C';
	}
}
