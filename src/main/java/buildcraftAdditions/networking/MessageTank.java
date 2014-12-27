package buildcraftAdditions.networking;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.utils.ITankHolder;
import buildcraftAdditions.utils.Tank;

import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageTank implements IMessage, IMessageHandler<MessageTank, IMessage> {
	public int x, y, z, fluidID[], fluidAmount[], numberOfTanks;

	public MessageTank() {
	}

	public MessageTank(ITankHolder tankHolder, int x, int y, int z) {
		Tank[] tanks = tankHolder.getTanks();
		this.x = x;
		this.y = y;
		this.z = z;
		numberOfTanks = tanks.length;
		fluidID = new int[numberOfTanks];
		fluidAmount = new int[numberOfTanks];
		for (int t = 0; t < numberOfTanks; t++) {
			FluidStack fluidStack = tanks[t].getFluid();
			if (fluidStack != null && fluidStack.amount != 0) {
				fluidID[t] = fluidStack.fluidID;
				fluidAmount[t] = fluidStack.amount;
			} else {
				fluidID[t] = -1;
				fluidAmount[t] = 0;
			}
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		numberOfTanks = buf.readInt();
		fluidID = new int[numberOfTanks];
		fluidAmount = new int[numberOfTanks];
		for (int t = 0; t < numberOfTanks; t++) {
			fluidID[t] = buf.readInt();
			fluidAmount[t] = buf.readInt();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(numberOfTanks);
		for (int t = 0; t < numberOfTanks; t++) {
			buf.writeInt(fluidID[t]);
			buf.writeInt(fluidAmount[t]);
		}
	}

	@Override
	public IMessage onMessage(MessageTank message, MessageContext ctx) {
		TileEntity entity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
		if (entity instanceof ITankHolder) {
			Tank tanks[] = ((ITankHolder) entity).getTanks();
			for (int t = 0; t < message.numberOfTanks; t++) {
				FluidStack fluidStack = null;
				if (message.fluidID[t] != -1)
					fluidStack = new FluidStack(message.fluidID[t], message.fluidAmount[t]);
				tanks[t].setFluid(fluidStack);
			}
		}
		return null;
	}
}
