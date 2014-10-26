package buildcraftAdditions.networking;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.tileEntities.TileFluidicCompressor;


import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageFluidicCompressorC implements IMessage, IMessageHandler<MessageFluidicCompressorC, IMessage> {
	public int x, y, z, fluidID, amount;

	public MessageFluidicCompressorC(){}

	public MessageFluidicCompressorC(TileFluidicCompressor compressor){
		x = compressor.xCoord;
		y = compressor.yCoord;
		z = compressor.zCoord;
		FluidStack fluid = compressor.getFluid();
		if (fluid != null && fluid.amount > 0){
			fluidID = fluid.fluidID;
			amount = fluid.amount;
		} else {
			fluidID = 0;
			amount = 0;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		fluidID = buf.readInt();
		amount = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(fluidID);
		buf.writeInt(amount);
	}

	@Override
	public IMessage onMessage(MessageFluidicCompressorC message, MessageContext ctx) {
		TileFluidicCompressor compressor = (TileFluidicCompressor) FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
		if (compressor != null) {
			FluidStack fluid = null;
			if (message.amount > 0)
				fluid = new FluidStack(message.fluidID, message.amount);
			compressor.tank.setFluid(fluid);
		}
		return null;
	}
}
