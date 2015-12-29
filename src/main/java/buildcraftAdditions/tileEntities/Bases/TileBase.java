package buildcraftAdditions.tileEntities.Bases;

import buildcraftAdditions.api.networking.ISynchronizedTile;
import buildcraftAdditions.api.networking.MessageByteBuff;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.reference.Variables;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileBase extends TileEntity implements ISynchronizedTile {

	protected final int IDENTIFIER;

	public TileBase(int identifier) {
		IDENTIFIER = identifier;
	}

	public void sync() {
		if (!worldObj.isRemote) {
			PacketHandler.instance.sendToAllAround(new MessageByteBuff(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, getX(), getY(), getZ(), Variables.NETWORK_RANGE));
		}
	}

	public void syncToPlayer(EntityPlayerMP player) {
		if (!worldObj.isRemote)
			PacketHandler.instance.sendTo(new MessageByteBuff(this), player);
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
	public int getIdentifier() {
		return IDENTIFIER;
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
