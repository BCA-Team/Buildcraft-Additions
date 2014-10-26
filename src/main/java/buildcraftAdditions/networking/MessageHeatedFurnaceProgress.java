package buildcraftAdditions.networking;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.tileEntities.TileHeatedFurnace;


import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageHeatedFurnaceProgress implements IMessage, IMessageHandler<MessageHeatedFurnaceProgress, IMessage> {
	public int x, y, z, progress;

	public MessageHeatedFurnaceProgress(){}

	public MessageHeatedFurnaceProgress(TileHeatedFurnace furnace){
		x = furnace.xCoord;
		y = furnace.yCoord;
		z = furnace.zCoord;
		progress = furnace.progress;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		progress = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(progress);
	}

	@Override
	public IMessage onMessage(MessageHeatedFurnaceProgress message, MessageContext ctx) {
		TileHeatedFurnace furnace = (TileHeatedFurnace) FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
		if (furnace != null)
			furnace.progress = message.progress;
		return null;
	}
}
