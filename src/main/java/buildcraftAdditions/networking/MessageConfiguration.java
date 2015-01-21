package buildcraftAdditions.networking;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.utils.EnumPriority;
import buildcraftAdditions.utils.EnumSideStatus;
import buildcraftAdditions.utils.IConfigurableOutput;

import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageConfiguration implements IMessage, IMessageHandler<MessageConfiguration, IMessage> {
	public int x, y, z;
	public EnumSideStatus configuration[];
	public EnumPriority priorities[];

	public MessageConfiguration() {
	}

	public MessageConfiguration(IConfigurableOutput configurableOutput) {
		x = configurableOutput.getX();
		y = configurableOutput.getY();
		z = configurableOutput.getZ();
		configuration = new EnumSideStatus[6];
		priorities = new EnumPriority[6];
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			configuration[direction.ordinal()] = configurableOutput.getStatus(direction);
			priorities[direction.ordinal()] = configurableOutput.getPriority(direction);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		configuration = new EnumSideStatus[6];
		priorities = new EnumPriority[6];
		for (int teller = 0; teller < 6; teller++) {
			configuration[teller] = EnumSideStatus.values()[buf.readInt()];
			priorities[teller] = EnumPriority.PRIORITIES[buf.readInt()];
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		for (int teller = 0; teller < 6; teller++) {
			buf.writeInt(configuration[teller].ordinal());
			buf.writeInt(priorities[teller].ordinal());
		}
	}

	@Override
	public IMessage onMessage(MessageConfiguration message, MessageContext ctx) {
		TileEntity entity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
		if (entity != null && entity instanceof IConfigurableOutput) {
			IConfigurableOutput configurableOutput = (IConfigurableOutput) entity;
			configurableOutput.overrideConfiguration(message.configuration);
			configurableOutput.overridePriority(message.priorities);
		}
		return null;
	}
}
