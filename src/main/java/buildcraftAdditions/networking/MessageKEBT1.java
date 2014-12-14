package buildcraftAdditions.networking;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.tileEntities.TileKineticEnergyBufferTier1;
import buildcraftAdditions.utils.EnumSideStatus;

import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageKEBT1 implements IMessage, IMessageHandler<MessageKEBT1, IMessage> {
	public int x, y, z, energy, length;
	public String owner;
	public EnumSideStatus configuration[];

	public MessageKEBT1(){}

	public MessageKEBT1(TileKineticEnergyBufferTier1 keb) {
		x = keb.xCoord;
		y = keb.yCoord;
		z = keb.zCoord;
		energy = keb.energy;
		configuration = new EnumSideStatus[6];
		configuration = keb.configuration;
		owner = keb.owner;
		length = owner.length();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		energy = buf.readInt();
		configuration = new EnumSideStatus[6];
		for (int teller = 0; teller < 6; teller++)
			configuration[teller] = EnumSideStatus.values()[buf.readInt()];
		length = buf.readInt();
		owner = "";
		for (int teller = 0; teller < length; teller++)
			owner += buf.readChar();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(energy);
		for (int teller = 0; teller < 6; teller++)
			buf.writeInt(configuration[teller].ordinal());
		buf.writeInt(length);
		char[] letters = owner.toCharArray();
		for (char letter: letters) {
			buf.writeChar(letter);
		}
	}

	@Override
	public IMessage onMessage(MessageKEBT1 message, MessageContext ctx) {
		TileEntity entity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
		if (entity != null && entity instanceof TileKineticEnergyBufferTier1) {
			TileKineticEnergyBufferTier1 keb = (TileKineticEnergyBufferTier1) entity;
			keb.energy = message.energy;
			keb.configuration = message.configuration;
			keb.owner = message.owner;
		}
		return null;
	}
}
