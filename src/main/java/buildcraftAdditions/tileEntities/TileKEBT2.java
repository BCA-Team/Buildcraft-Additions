package buildcraftAdditions.tileEntities;

import buildcraftAdditions.core.Logger;
import buildcraftAdditions.multiBlocks.IMaster;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKEBT2 extends TileKineticEnergyBufferBase implements IMaster {

	public TileKEBT2(int x, int y, int z) {
		super(500000, 10000, 10000, 6);
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		Logger.info("TILE KEB TIER 2 CREATED AT " + xCoord + ", " + yCoord + ", " + zCoord);
	}

	@Override
	public void openGui() {
		Logger.info("OPENING GUI");
	}

	@Override
	public void destroyMultiblock() {
		Logger.info("DESTROYING MULTIBLOCK");
	}
}
