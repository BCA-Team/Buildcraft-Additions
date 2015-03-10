package buildcraftAdditions.multiBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.RotationUtils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockPaternKEBT2 extends MultiBlockPatern {

	public MultiBlockPaternKEBT2() {
		super(new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.WEST,
				ForgeDirection.UP, ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.SOUTH}, Variables.Identifiers.KEBT2);
	}

	@Override
	public void checkPatern(World world, int x, int y, int z) {
		if (isPaternValid(world, x, y, z, 0)) {
			rotatedDirections = RotationUtils.rotateDirections(0, directions);
			Location location = new Location(world, x, y, z);
			for (ForgeDirection direction : rotatedDirections) {
				location.move(direction);
				if (location.getBlock().getMaterial() != Material.air) {
					location.setMetadata(1);
					IMultiBlockTile slave = (IMultiBlockTile) location.getTileEntity();
					slave.formMultiblock(x, y, z, 0);
				}
			}
			letNeighboursKnow(world, x, y, z, 0);
			addMaster(world, x, y, z, 0);
		}
	}
}
