package buildcraftAdditions.networking;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.tileEntities.Bases.TileCoilBase;


import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageCoilStatus implements IMessage, IMessageHandler<MessageCoilStatus, IMessage> {
	public int x, y, z;
	public boolean shouldHeat;

	public MessageCoilStatus() {

	}

	public MessageCoilStatus(TileCoilBase coil){
		x = coil.xCoord;
		y = coil.yCoord;
		z = coil.zCoord;
		shouldHeat = coil.shouldHeat;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		shouldHeat = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBoolean(shouldHeat);
	}

	@Override
	public IMessage onMessage(MessageCoilStatus message, MessageContext messageContext) {
		TileCoilBase coil =  (TileCoilBase) FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
		if (coil != null)
			coil.shouldHeat = message.shouldHeat;
		return null;
	}
}
