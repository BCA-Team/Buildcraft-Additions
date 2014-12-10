package buildcraftAdditions.networking;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.minecraftforge.fluids.FluidStack;

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
	public int x, y, z, masterX, masterY, masterZ, rotationIndex, fluidIDinput, fluidAmountInput, fluidIDoutput, fluidAmountOutput;
	public boolean isMaster, partOfMultiblock;

	public MessageRefinery() {
	}

	public MessageRefinery(TileRefinery refinery) {
		x = refinery.xCoord;
		y = refinery.yCoord;
		z = refinery.zCoord;
		masterX = refinery.masterX;
		masterY = refinery.masterY;
		masterZ = refinery.masterZ;
		isMaster = refinery.isMaster;
		partOfMultiblock = refinery.partOfMultiBlock;
		rotationIndex = refinery.rotationIndex;
		if (refinery.input.getFluid() == null) {
			fluidIDinput = -1;
		} else {
			fluidIDinput = refinery.input.getFluid().fluidID;
			fluidAmountInput = refinery.input.getFluid().amount;
		}
		if (refinery.output.getFluid() == null) {
			fluidIDoutput = -1;
		} else {
			fluidIDoutput = refinery.output.getFluid().fluidID;
			fluidAmountOutput = refinery.output.getFluid().amount;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		masterX = buf.readInt();
		masterY = buf.readInt();
		masterZ = buf.readInt();
		isMaster = buf.readBoolean();
		partOfMultiblock = buf.readBoolean();
		rotationIndex = buf.readInt();
		fluidIDinput = buf.readInt();
		fluidAmountInput = buf.readInt();
		fluidIDoutput = buf.readInt();
		fluidAmountOutput = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(masterX);
		buf.writeInt(masterY);
		buf.writeInt(masterZ);
		buf.writeBoolean(isMaster);
		buf.writeBoolean(partOfMultiblock);
		buf.writeInt(rotationIndex);
		buf.writeInt(fluidIDinput);
		buf.writeInt(fluidAmountInput);
		buf.writeInt(fluidIDoutput);
		buf.writeInt(fluidAmountOutput);
	}

	@Override
	public IMessage onMessage(MessageRefinery message, MessageContext ctx) {
		TileEntity entity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
		if (entity instanceof TileRefinery) {
			TileRefinery refinery = (TileRefinery) entity;
			refinery.masterX = message.masterX;
			refinery.masterY = message.masterY;
			refinery.masterZ = message.masterZ;
			refinery.isMaster = message.isMaster;
			refinery.partOfMultiBlock = message.partOfMultiblock;
			refinery.rotationIndex = message.rotationIndex;
			FluidStack stack;
			if (message.fluidIDinput == -1)
				stack = null;
			else {
				stack = new FluidStack(message.fluidIDinput, message.fluidAmountInput);
			}
			refinery.input.setFluid(stack);

			if (message.fluidIDoutput == -1)
				stack = null;
			else {
				stack = new FluidStack(message.fluidIDoutput, message.fluidAmountOutput);
			}
			refinery.output.setFluid(stack);
		}
		return null;
	}
}
