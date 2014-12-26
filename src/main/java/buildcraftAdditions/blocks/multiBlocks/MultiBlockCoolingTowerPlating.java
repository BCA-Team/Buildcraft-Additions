package buildcraftAdditions.blocks.multiBlocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.reference.Variables;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockCoolingTowerPlating extends MultiBlockBase {

	public MultiBlockCoolingTowerPlating() {
		super(Variables.Identifiers.COOLING_TOWER_WALLS, null, "coolingtowerplating");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}
}
