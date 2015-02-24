package buildcraftAdditions.networking;

import io.netty.buffer.ByteBuf;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.items.Tools.ItemPortableLaser;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessagePortableLaserModeChange implements IMessage, IMessageHandler<MessagePortableLaserModeChange, IMessage> {

	private boolean blockMode;

	public MessagePortableLaserModeChange() {

	}

	public MessagePortableLaserModeChange(boolean blockMode) {
		this.blockMode = blockMode;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		blockMode = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(blockMode);
	}

	@Override
	public IMessage onMessage(MessagePortableLaserModeChange message, MessageContext ctx) {
		ItemStack stack = ctx.getServerHandler().playerEntity.getHeldItem();
		if (stack != null && stack.getItem() instanceof ItemPortableLaser)
			stack.setItemDamage(message.blockMode ? 0 : 1);
		return null;
	}

}
