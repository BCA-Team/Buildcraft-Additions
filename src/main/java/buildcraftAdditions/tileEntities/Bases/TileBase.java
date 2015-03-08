package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.NetworkRegistry;

import buildcraftAdditions.api.networking.ISynchronizedTile;
import buildcraftAdditions.api.networking.MessageByteBuff;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileBase extends TileEntity implements ISynchronizedTile {

	public int timer;

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
			PacketHandler.instance.sendToAllAround(new MessageByteBuff(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, getX(), getY(), getZ(), Variables.NETWORK_RANGE));
		}
	}

	@Override
	public void markDirty() {
		super.markDirty();
		if (worldObj != null) {
			sync();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, Byte.MAX_VALUE, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}
}
