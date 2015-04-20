package buildcraftAdditions.networking;

import io.netty.buffer.ByteBuf;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.listeners.FlightTracker;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageFlightSync implements IMessage, IMessageHandler<MessageFlightSync, IMessage> {
	public boolean wantsToFly, wantsToMove;

	public MessageFlightSync() {
	}

	public MessageFlightSync(boolean wantsToFly, boolean wantsToMove) {
		this.wantsToFly = wantsToFly;
		this.wantsToMove = wantsToMove;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		wantsToFly = buf.readBoolean();
		wantsToMove = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(wantsToFly);
		buf.writeBoolean(wantsToMove);
	}

	@Override
	public IMessage onMessage(MessageFlightSync message, MessageContext ctx) {
		FlightTracker.setJumping(ctx.getServerHandler().playerEntity, message.wantsToFly);
		FlightTracker.setMoving(ctx.getServerHandler().playerEntity, message.wantsToMove);
		return null;
	}
}
