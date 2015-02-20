package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.NetworkRegistry;

import buildcraftAdditions.api.networking.ISyncronizedTile;
import buildcraftAdditions.api.networking.MessageByteBuff;
import buildcraftAdditions.networking.PacketHandler;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileBase extends TileEntity implements ISyncronizedTile {
	public int timer;
	public int networkRange = 15;

	@Override
	public void updateEntity() {
		if (timer <= 0) {
			sync();
			timer = 20;
		} else
			timer--;
	}

	public void sync() {
		if (!worldObj.isRemote) {
			PacketHandler.instance.sendToAllAround(new MessageByteBuff(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, getX(), getY(), getZ(), 20));
		}
	}

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
