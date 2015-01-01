package buildcraftAdditions.networking;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageByteBuff implements IMessage, IMessageHandler<MessageByteBuff, IMessage> {
	public ISyncronizedTile object;
	public int x, y, z;

	public MessageByteBuff() {
	}

	public MessageByteBuff(ISyncronizedTile tile) {
		this.object = tile;
		x = tile.getX();
		y = tile.getY();
		z = tile.getZ();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		TileEntity entity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(x, y, z);
		if (entity instanceof ISyncronizedTile) {
			((ISyncronizedTile) entity).readFromByteBuff(buf);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		object.writeToByteBuff(buf);
	}

	@Override
	public IMessage onMessage(MessageByteBuff message, MessageContext ctx) {
		return null;
	}
}
