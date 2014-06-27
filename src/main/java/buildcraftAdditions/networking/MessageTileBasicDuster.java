package buildcraftAdditions.networking;

import buildcraftAdditions.entities.TileBasicDuster;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageTileBasicDuster implements IMessage, IMessageHandler<MessageTileBasicDuster, IMessage> {
    public TileBasicDuster duster;
    public int x, y, z;


    public MessageTileBasicDuster(){

    }

    public MessageTileBasicDuster(TileBasicDuster duster){

    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(duster.xCoord);

    }

    @Override
    public IMessage onMessage(MessageTileBasicDuster message, MessageContext ctx) {
        return null;
    }
}
