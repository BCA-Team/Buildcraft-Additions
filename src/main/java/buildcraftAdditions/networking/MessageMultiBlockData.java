package buildcraftAdditions.networking;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import buildcraftAdditions.multiBlocks.IMultiBlockTile;

import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageMultiBlockData implements IMessage, IMessageHandler<MessageMultiBlockData, IMessage> {
	public int masterX, masterY, masterZ, rotationIndex, x, y, z;
	public boolean isMaster, partOfMultiBlock;

	public MessageMultiBlockData() {
	}

	public MessageMultiBlockData(IMultiBlockTile multiblock, int x, int y, int z) {
		masterX = multiblock.getMasterX();
		masterY = multiblock.getMasterY();
		masterZ = multiblock.getMasterZ();
		rotationIndex = multiblock.getRotationIndex();
		isMaster = multiblock.isMaster();
		partOfMultiBlock = multiblock.isPartOfMultiblock();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		masterX = buf.readInt();
		masterY = buf.readInt();
		masterZ = buf.readInt();
		rotationIndex = buf.readInt();
		isMaster = buf.readBoolean();
		partOfMultiBlock = buf.readBoolean();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(masterX);
		buf.writeInt(masterY);
		buf.writeInt(masterZ);
		buf.writeInt(rotationIndex);
		buf.writeBoolean(isMaster);
		buf.writeBoolean(partOfMultiBlock);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	@Override
	public IMessage onMessage(MessageMultiBlockData message, MessageContext ctx) {
		TileEntity entity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
		if (entity instanceof IMultiBlockTile) {
			IMultiBlockTile multiBlock = (IMultiBlockTile) entity;
			multiBlock.setMasterX(message.masterX);
			multiBlock.setMasterY(message.masterY);
			multiBlock.setMasterZ(message.masterZ);
			multiBlock.setRotationIndex(message.rotationIndex);
			multiBlock.setIsMaster(message.isMaster);
			multiBlock.setPartOfMultiBlock(message.partOfMultiBlock);
		}
		return null;
	}
}
