package buildcraftAdditions.networking;

import io.netty.buffer.ByteBuf;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.utils.IConfigurableOutput;
import buildcraftAdditions.utils.SideConfiguration;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageConfiguration implements IMessage, IMessageHandler<MessageConfiguration, IMessage> {

	private int x, y, z;
	private SideConfiguration configuration;

	public MessageConfiguration() {
		configuration = new SideConfiguration();
	}

	public MessageConfiguration(IConfigurableOutput configurableOutput) {
		this();
		x = configurableOutput.getX();
		y = configurableOutput.getY();
		z = configurableOutput.getZ();
		configuration.load(configurableOutput);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		configuration.readFromByteBuff(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		configuration.writeToByteBuff(buf);
	}

	@Override
	public IMessage onMessage(MessageConfiguration message, MessageContext ctx) {
		TileEntity entity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
		if (entity != null && entity instanceof IConfigurableOutput) {
			((IConfigurableOutput) entity).setSideConfiguration(message.configuration);
		}
		return null;
	}
}
