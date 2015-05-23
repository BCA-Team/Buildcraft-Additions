package buildcraftAdditions.blocks.multiBlocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileCoolingTower;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockCoolingTowerWalls extends MultiBlockBase {

	public MultiBlockCoolingTowerWalls() {
		super("blockCoolingTowerWalls", Variables.Identifiers.COOLING_TOWER_WALLS, Variables.Paterns.COOLING_TOWER, "coolingTowerPlating", "coolingTowerFormed", "blockCoolingTowerWalls)");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileCoolingTower();
	}
}
