package buildcraftAdditions.networking;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.tileEntities.TileKEBT3;


import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageKEBT3 implements IMessage, IMessageHandler<MessageKEBT3, IMessage> {
	public int x, y, z, energy, configuration[], masterX, masterY, masterZ, energyState, length;
	public boolean partOfMultiBlock, isMaster;
	public String owner;

	public MessageKEBT3() {}

	public MessageKEBT3(TileKEBT3 keb) {
		x = keb.xCoord;
		y = keb.yCoord;
		z = keb.zCoord;
		configuration = new int[6];
		configuration = keb.configuration;
		partOfMultiBlock = keb.partOfMultiBlock;
		isMaster = keb.isMaster;
		energy = keb.energy;
		masterX = keb.masterX;
		masterY = keb.masterY;
		masterZ = keb.masterZ;
		energyState = keb.energyState;
		owner = keb.owner;
		length = owner.length();
	}


	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		configuration = new int[6];
		for (int teller = 0; teller < 6; teller++)
			configuration[teller] = buf.readInt();
		partOfMultiBlock = buf.readBoolean();
		isMaster = buf.readBoolean();
		energy = buf.readInt();
		masterX = buf.readInt();
		masterY = buf.readInt();
		masterZ = buf.readInt();
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
			buf.writeInt(configuration[teller]);
		buf.writeBoolean(partOfMultiBlock);
		buf.writeBoolean(isMaster);
		buf.writeInt(energy);
		buf.writeInt(masterX);
		buf.writeInt(masterY);
		buf.writeInt(masterZ);
		buf.writeInt(energyState);
		buf.writeInt(length);
		char[] letters = owner.toCharArray();
		for (char letter: letters) {
			buf.writeChar(letter);
		}

	}

	@Override
	public IMessage onMessage(MessageKEBT3 message, MessageContext ctx) {
		TileEntity entity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
		if (entity != null && entity instanceof TileKEBT3) {
			TileKEBT3 keb = (TileKEBT3) entity;
			keb.configuration = message.configuration;
			keb.partOfMultiBlock = message.partOfMultiBlock;
			keb.isMaster = message.isMaster;
			keb.energy = message.energy;
			keb.masterX = message.masterX;
			keb.masterY = message.masterY;
			keb.masterZ = message.masterZ;
			keb.energyState = message.energyState;
			keb.owner = message.owner;
		}
		return null;
	}
}
