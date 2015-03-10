package buildcraftAdditions.networking;

import io.netty.buffer.ByteBuf;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageSelfDestruct implements IMessage, IMessageHandler<MessageSelfDestruct, IMessage> {
	public int x, y, z;

	public MessageSelfDestruct() {
	}

	public MessageSelfDestruct(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	@Override
	public IMessage onMessage(MessageSelfDestruct message, MessageContext ctx) {
		TileEntity entity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
		if (entity instanceof TileKineticEnergyBufferBase)
			((TileKineticEnergyBufferBase) entity).activateSelfDestruct();
		return null;
	}
}
