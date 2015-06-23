package buildcraftAdditions.networking;

import io.netty.buffer.ByteBuf;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by AEnterprise
 */
public class MessageToggleBoots implements IMessage, IMessageHandler<MessageToggleBoots, IMessage> {

	public MessageToggleBoots() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {

	}

	@Override
	public void toBytes(ByteBuf buf) {

	}

	@Override
	public IMessage onMessage(MessageToggleBoots message, MessageContext ctx) {
		ctx.getServerHandler().playerEntity.getCurrentArmor(0).stackTagCompound.setBoolean("enabled", !ctx.getServerHandler().playerEntity.getCurrentArmor(0).stackTagCompound.getBoolean("enabled"));
		return null;
	}
}
