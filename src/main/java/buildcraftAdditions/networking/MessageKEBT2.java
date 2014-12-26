package buildcraftAdditions.networking;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.tileEntities.TileKEBT2;
import buildcraftAdditions.utils.EnumSideStatus;

import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageKEBT2 implements IMessage, IMessageHandler<MessageKEBT2, IMessage> {
	public int x, y, z, energy, energyState, length;
	public String owner;
	public EnumSideStatus configuration[];

	public MessageKEBT2() {}

	public MessageKEBT2(TileKEBT2 keb) {
		x = keb.xCoord;
		y = keb.yCoord;
		z = keb.zCoord;
		configuration = new EnumSideStatus[6];
		configuration = keb.configuration;
		energy = keb.energy;
		energyState = keb.energyState;
		owner = keb.owner;
		length = owner.length();
	}


	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		configuration = new EnumSideStatus[6];
		for (int teller = 0; teller < 6; teller++)
			configuration[teller] = EnumSideStatus.values()[buf.readInt()];
		energy = buf.readInt();
		energyState = buf.readInt();
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
		for (int teller = 0; teller < 6; teller++)
			buf.writeInt(configuration[teller].ordinal());
		buf.writeInt(energy);
		buf.writeInt(energyState);
		buf.writeInt(length);
		char[] letters = owner.toCharArray();
		for (char letter: letters) {
			buf.writeChar(letter);
		}

	}

	@Override
	public IMessage onMessage(MessageKEBT2 message, MessageContext ctx) {
		TileEntity entity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
		if (entity != null && entity instanceof TileKEBT2) {
			TileKEBT2 keb = (TileKEBT2) entity;
			keb.configuration = message.configuration;
			keb.energy = message.energy;
			keb.energyState = message.energyState;
			keb.owner = message.owner;
		}
		return null;
	}
}
