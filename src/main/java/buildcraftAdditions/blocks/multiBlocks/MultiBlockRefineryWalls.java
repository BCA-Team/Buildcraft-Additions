package buildcraftAdditions.blocks.multiBlocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.multiBlocks.MultiBlockPaternRefinery;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileRefinery;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockRefineryWalls extends MultiBlockBase {

	public MultiBlockRefineryWalls() {
		super("blockRefineryWalls", Variables.Identifiers.REFINERY_WALLS, new MultiBlockPaternRefinery(), "heaterPlating", "heaterSidesMultiblock", "refinerywals");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileRefinery();
	}
}
