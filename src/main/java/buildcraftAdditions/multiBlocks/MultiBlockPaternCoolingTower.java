package buildcraftAdditions.multiBlocks;

import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.reference.Variables;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockPaternCoolingTower extends MultiBlockPatern {

	public MultiBlockPaternCoolingTower() {
		super(new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.EAST,
				ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.WEST, ForgeDirection.UP,
				ForgeDirection.EAST, ForgeDirection.EAST, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.WEST,
				ForgeDirection.WEST, ForgeDirection.SOUTH, ForgeDirection.UP, ForgeDirection.SOUTH, ForgeDirection.EAST,
				ForgeDirection.EAST, ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.WEST, ForgeDirection.WEST,
				ForgeDirection.UP, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.EAST,
				ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.WEST, ForgeDirection.UP, ForgeDirection.WEST,
				ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.EAST, ForgeDirection.NORTH,
				ForgeDirection.NORTH, ForgeDirection.WEST, ForgeDirection.SOUTH, ForgeDirection.DOWN, ForgeDirection.DOWN,
				ForgeDirection.DOWN, ForgeDirection.DOWN}, Variables.Identifiers.COOLING_TOWER_WALLS);
		identifiers[41] = Variables.Identifiers.COOLING_TOWER_VALVE;
		for (int t = 42; t < 45; t++)
			identifiers[t] = '\n';
		identifiers[45] = Variables.Identifiers.COOLING_TOWER_VALVE;
	}

	@Override
	public void checkPatern(World world, int x, int y, int z) {
		super.checkPatern(world, x, y, z);
	}
}
