package buildcraftAdditions.multiBlocks;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.reference.BlockLoader;
import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.RotationUtils;

/**
 * Dev use only, builds the specified pattern for visual representation
 */
public class TestingPatern {

	public static void build(Location location) {
		HashMap<String, Block> blocks = new HashMap<String, Block>();
		ForgeDirection directions[] = new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.UP, ForgeDirection.UP, ForgeDirection.UP, ForgeDirection.UP,
				ForgeDirection.SOUTH, ForgeDirection.DOWN, ForgeDirection.DOWN, ForgeDirection.DOWN, ForgeDirection.DOWN, ForgeDirection.WEST, ForgeDirection.UP, ForgeDirection.NORTH,
				ForgeDirection.UP, ForgeDirection.SOUTH, ForgeDirection.UP, ForgeDirection.NORTH, ForgeDirection.UP, ForgeDirection.SOUTH, ForgeDirection.SOUTH, ForgeDirection.DOWN,
				ForgeDirection.DOWN, ForgeDirection.DOWN, ForgeDirection.DOWN, ForgeDirection.EAST, ForgeDirection.UP, ForgeDirection.UP, ForgeDirection.UP, ForgeDirection.UP,
				ForgeDirection.EAST, ForgeDirection.DOWN, ForgeDirection.DOWN, ForgeDirection.DOWN, ForgeDirection.DOWN, ForgeDirection.NORTH, ForgeDirection.UP, ForgeDirection.UP,
				ForgeDirection.UP, ForgeDirection.UP, ForgeDirection.NORTH, ForgeDirection.DOWN, ForgeDirection.DOWN, ForgeDirection.DOWN, ForgeDirection.DOWN};
		int length = directions.length;
		String identifiers[] = new String[length];
		int rotation = 3;
		directions = RotationUtils.rotateDirections(rotation, directions);
		Arrays.fill(identifiers, "walls");
		blocks.put("walls", BlockLoader.coolingTowerWalls);
		blocks.put("valve", BlockLoader.coolingTowerValve);
		blocks.put("air", Blocks.air);
		identifiers[3] = "valve";
		identifiers[6] = "valve";
		for (int t = 7; t < 10; t++)
			identifiers[t] = "air";
		identifiers[10] = "valve";
		for (int t = 0; t < length; t++) {
			location.move(directions[t]);
			location.setBlock(blocks.get(identifiers[t]));
		}
	}
}
