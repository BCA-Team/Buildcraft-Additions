package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;

import buildcraftAdditions.core.Logger;
import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKEBT3 extends TileKineticEnergyBufferBase implements IMultiBlockTile {

	public TileKEBT3() {
		super(0, 0, 0, 0, 3);
	}

	@Override
	public void makeMaster() {
		Logger.info("KEB T3 master at " + xCoord + ", " + yCoord + ", " + zCoord);
	}

	@Override
	public void invalidateMultiblock() {

	}

	@Override
	public void onBlockActivated(EntityPlayer player) {

	}

	@Override
	public void formMultiblock(int masterX, int masterY, int masterZ) {
		Logger.info("KEB T3 slave at " + xCoord + ", " + yCoord + ", " + zCoord);
	}

	@Override
	public void invalidateBlock() {

	}

	@Override
	public void sync() {

	}
}
