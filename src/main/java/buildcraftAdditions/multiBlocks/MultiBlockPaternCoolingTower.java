package buildcraftAdditions.multiBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileCoolingTower;
import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.RotationUtils;
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
		identifiers[16] = Variables.Identifiers.COOLING_TOWER_VALVE;
		identifiers[41] = Variables.Identifiers.COOLING_TOWER_VALVE;
		for (int t = 42; t < 45; t++)
			identifiers[t] = '\n';
		identifiers[45] = Variables.Identifiers.COOLING_TOWER_VALVE;
	}

	@Override
	public void checkPatern(World world, int x, int y, int z) {
		int rotation = 0;
		boolean valid = isPaternValid(world, x, y, z);
		for (int t = 0; t < 4; t++) {
			if (isPaternValid(world, x, y, z, t)) {
				rotation = t;
				valid = true;
			}
		}
		if (valid) {
			rotatedDirections = RotationUtils.rotateDirections(directions, rotation);
			Location location = new Location(world, x, y, z);
			int t = 0;
			for (ForgeDirection direction : rotatedDirections) {
				location.move(direction);
				if (location.getBlock().getMaterial() != Material.air) {
					location.setMetadata(1);
					IMultiBlockTile slave = (IMultiBlockTile) location.getTileEntity();
					slave.formMultiblock(x, y, z, rotation);
					TileCoolingTower tower = (TileCoolingTower) location.getTileEntity();
					if (t == 16)
						tower.tank = 2;
					if (t == 41)
						tower.tank = 1;
					if (t == 45)
						tower.tank = 0;
				}
				t++;
			}
			letNeighboursKnow(world, x, y, z, rotation);
			addMaster(world, x, y, z, rotation);
		}
	}
}
