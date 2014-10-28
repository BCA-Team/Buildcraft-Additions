package buildcraftAdditions.tileEntities;

import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKineticEnergyBufferTier1 extends TileKineticEnergyBufferBase {
	public int energyState, lastEnergyState;

	public TileKineticEnergyBufferTier1() {
		super(30000, 1000, 1000, 10);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		energyState = (energy * 9) / maxEnergy;
		if (lastEnergyState != energyState)
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, energyState, 2);
		lastEnergyState = energyState;
	}
}
