package buildcraftAdditions.multiBlocks;

import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.blocks.multiBlocks.MulitBlockBase;
import buildcraftAdditions.core.Logger;
import buildcraftAdditions.utils.Location;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class MultiBlockPatern {
	public ForgeDirection directions[];
	public char identifier;

	public MultiBlockPatern(ForgeDirection directions[], char identifier) {
			this.directions = directions;
			this.identifier = identifier;
	}

	public void checkPatern(World world, int x, int y, int z) {
		Location location = new Location(world, x, y, z);
		for (ForgeDirection direction: directions) {
			location.move(direction);
			if (!(location.getBlock() instanceof MulitBlockBase))
				return;
			MulitBlockBase block = (MulitBlockBase) location.getBlock();
			if (!(block.identifier == identifier) || location.getMeatadata() != 0 )
				return;
		}
		Logger.info("VALID MULTIBLOCK DETECTED");
		location = new Location(world, x, y, z);
		for (ForgeDirection direction: directions) {
			location.move(direction);
			location.setMetadata(1);
			location.addTileEntity(new TileMultiBlockSlave(x, y, z, location.x, location.y, location.z));
		}
		addMaster(world, x, y, z);
	}

	public abstract void addMaster (World world, int x, int y, int z);
}
