package buildcraftAdditions.utils;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.blocks.multiBlocks.MulitBlockBase;
import buildcraftAdditions.core.Logger;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockPatern {
	public ForgeDirection directions[];
	public char identifier;

	public MultiBlockPatern(ForgeDirection directions[], char identifier) {
			this.directions = directions;
			this.identifier = identifier;
	}

	public void checkPatern(Location start) {
		Location location = start;
		for (ForgeDirection direction: directions) {
			location.move(direction);
			if (!(location.getBlock() instanceof MulitBlockBase))
				return;
			MulitBlockBase block = (MulitBlockBase) location.getBlock();
			if (!(block.identifier == identifier))
				return;
		}
		Logger.info("VALID MULTIBLOCK DETECTED");
	}
}
