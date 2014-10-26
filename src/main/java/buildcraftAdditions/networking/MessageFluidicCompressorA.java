package buildcraftAdditions.networking;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.tileEntities.TileFluidicCompressor;


import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageFluidicCompressorA implements IMessage, IMessageHandler<MessageFluidicCompressorA, IMessage> {
	public int x, y, z;
	public boolean fill;

	public MessageFluidicCompressorA() {
	}

	public MessageFluidicCompressorA(boolean fill, TileFluidicCompressor compressor) {
		this.fill = fill;
		this.x = compressor.xCoord;
		this.y = compressor.yCoord;
		this.z = compressor.zCoord;
	}



	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		fill = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBoolean(fill);
	}

	@Override
	public IMessage onMessage(MessageFluidicCompressorA message, MessageContext ctx) {
		TileFluidicCompressor compressor = (TileFluidicCompressor) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
		if (compressor != null)
			compressor.fill = message.fill;
		return null;
	}

}
