package buildcraftAdditions.networking;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;


import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageKEBConfiguration implements IMessage, IMessageHandler<MessageKEBConfiguration, IMessage> {
	public int x, y, z, configuration[];

	public MessageKEBConfiguration(){}

	public MessageKEBConfiguration(TileKineticEnergyBufferBase keb) {
		x= keb.xCoord;
		y = keb.yCoord;
		z = keb.zCoord;
		configuration = new int[6];
		configuration = keb.configuration;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		configuration = new int[6];
		for (int teller = 0; teller < 6; teller++)
			configuration[teller] = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		for (int teller = 0; teller < 6; teller++)
			buf.writeInt(configuration[teller]);
	}

	@Override
	public IMessage onMessage(MessageKEBConfiguration message, MessageContext ctx) {
		TileEntity entity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
		if (entity != null && entity instanceof TileKineticEnergyBufferBase) {
			TileKineticEnergyBufferBase keb = (TileKineticEnergyBufferBase) entity;
			keb.configuration = message.configuration;
		}
		return null;
	}
}
