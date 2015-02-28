package buildcraftAdditions.multiBlocks;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockPaternRefinery extends MultiBlockPatern {

	public MultiBlockPaternRefinery() {
		super(new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.NORTH, ForgeDirection.NORTH,
				ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH,
				ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH,
				ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH,
				ForgeDirection.EAST, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH,
				ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.UP,
				ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH,
				ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.NORTH,
				ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH,
				ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.WEST, ForgeDirection.SOUTH, ForgeDirection.SOUTH,
				ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH,
				ForgeDirection.SOUTH, ForgeDirection.UP, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH,
				ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH,
				ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH,
				ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.EAST,
				ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH,
				ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.NORTH}, Variables.Identifiers.REFINERY_WALLS);
		for (int t = 38; t < 45; t++)
			identifiers[t] = '\n';
		identifiers[37] = Variables.Identifiers.REFINERY_VALVE;
		identifiers[45] = Variables.Identifiers.REFINERY_VALVE;
	}
}
