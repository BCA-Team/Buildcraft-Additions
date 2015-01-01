package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.tileentity.TileEntity;

import buildcraftAdditions.networking.ISyncronizedTile;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileBase extends TileEntity implements ISyncronizedTile {

	@Override
	public abstract void updateEntity();

	@Override
	public int getX() {
		return xCoord;
	}

	@Override
	public int getY() {
		return yCoord;
	}

	@Override
	public int getZ() {
		return zCoord;
	}
}
