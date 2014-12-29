package buildcraftAdditions.networking;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.tileEntities.TileRefinery;

import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageRefinery implements IMessage, IMessageHandler<MessageRefinery, IMessage> {
	public int x, y, z, requiredHeat, lastRequiredHeat, currentHeat, energyCost;
	public boolean valve;

	public MessageRefinery() {
	}

	public MessageRefinery(TileRefinery refinery) {
		x = refinery.xCoord;
		y = refinery.yCoord;
		z = refinery.zCoord;
		valve = refinery.valve;
		currentHeat = refinery.currentHeat;
		lastRequiredHeat = refinery.lastRequiredHeat;
		energyCost = refinery.energyCost;
		requiredHeat = refinery.requiredHeat;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		valve = buf.readBoolean();
		currentHeat = buf.readInt();
		lastRequiredHeat = buf.readInt();
		energyCost = buf.readInt();
		requiredHeat = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBoolean(valve);
		buf.writeInt(currentHeat);
		buf.writeInt(lastRequiredHeat);
		buf.writeInt(energyCost);
		buf.writeInt(requiredHeat);
	}

	@Override
	public IMessage onMessage(MessageRefinery message, MessageContext ctx) {
		TileEntity entity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
		if (entity instanceof TileRefinery) {
			TileRefinery refinery = (TileRefinery) entity;
			refinery.valve = message.valve;
			refinery.energyCost = message.energyCost;
			refinery.currentHeat = message.currentHeat;
			refinery.lastRequiredHeat = message.lastRequiredHeat;
			refinery.requiredHeat = message.requiredHeat;
		}
		return null;
	}
}
