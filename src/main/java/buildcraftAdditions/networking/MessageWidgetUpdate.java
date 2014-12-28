/**
 * Copyright (c) 2014, big_Xplosion and the UniversalTeam
 *
 * UniversalTeam's mods are distributed under the terms of GNU LGPL v3.0
 * Please check the contents of the license located in
 * https://www.gnu.org/licenses/lgpl.html
 *
 * @author big_Xplosion
 */
package buildcraftAdditions.networking;

import buildcraftAdditions.tileEntities.implement.IWidgetListener;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class MessageWidgetUpdate implements IMessage, IMessageHandler<MessageWidgetUpdate, IMessage> {

	public int x;
	public int y;
	public int z;
	public int id;
	public int value;

	public MessageWidgetUpdate() {

	}

	public MessageWidgetUpdate(TileEntity tile, int id, int value) {
		this.x = tile.xCoord;
		this.y = tile.yCoord;
		this.z = tile.zCoord;
		this.id = id;
		this.value = value;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		id = buf.readInt();
		value = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(id);
		buf.writeInt(value);
	}

	@Override
	public IMessage onMessage(MessageWidgetUpdate message, MessageContext ctx) {
		TileEntity tile = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);

		if (tile instanceof IWidgetListener)
			((IWidgetListener) tile).onWidgetPressed(message.id, message.value);
		return null;
	}
}
