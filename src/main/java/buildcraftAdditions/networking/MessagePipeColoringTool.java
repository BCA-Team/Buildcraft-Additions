package buildcraftAdditions.networking;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.items.ItemPipeColoringTool;

import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessagePipeColoringTool implements IMessage, IMessageHandler<MessagePipeColoringTool, IMessage> {

	public byte color;
	public boolean sortMode;

	public MessagePipeColoringTool() {

	}

	public MessagePipeColoringTool(byte color, boolean sortMode) {
		this.color = color;
		this.sortMode = sortMode;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		color = buf.readByte();
		sortMode = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(color);
		buf.writeBoolean(sortMode);
	}

	@Override
	public IMessage onMessage(MessagePipeColoringTool message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;

		if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPipeColoringTool) {
			ItemStack stack = player.getHeldItem();
			if (message.color >= 0 && message.color <= 15)
				stack.setItemDamage(message.color);
			if (!stack.hasTagCompound())
				stack.stackTagCompound = new NBTTagCompound();
			stack.getTagCompound().setBoolean("SortMode", message.sortMode);
		}

		return null;
	}
}
