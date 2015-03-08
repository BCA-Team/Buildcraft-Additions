package buildcraftAdditions.networking;

import io.netty.buffer.ByteBuf;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.api.configurableOutput.IConfigurableOutput;
import buildcraftAdditions.api.configurableOutput.SideConfiguration;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageConfiguration implements IMessage, IMessageHandler<MessageConfiguration, IMessage> {

	private final SideConfiguration configuration;
	private int x, y, z;

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
		TileEntity tile = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
		if (tile != null && tile instanceof IConfigurableOutput) {
			((IConfigurableOutput) tile).setSideConfiguration(message.configuration);
			ctx.getServerHandler().playerEntity.worldObj.notifyBlocksOfNeighborChange(message.x, message.y, message.z, ctx.getServerHandler().playerEntity.worldObj.getBlock(message.x, message.y, message.z));
		}
		return null;
	}
}
