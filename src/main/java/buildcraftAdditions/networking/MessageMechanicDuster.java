package buildcraftAdditions.networking;

import buildcraftAdditions.entities.TileMechanicalDuster;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageMechanicDuster implements IMessage, IMessageHandler<MessageMechanicDuster, IMessage> {
    public int x, y, z, progressStage, id, meta;

    public MessageMechanicDuster(){

    }

    public MessageMechanicDuster(int x, int y, int z, int progressStage, ItemStack stack){
        this.x = x;
        this.y = y;
        this.z = z;
        this.progressStage = progressStage;
        if (stack == null){
            id = 0;
        } else {
            id = Item.getIdFromItem(stack.getItem());
            meta = stack.getItemDamage();
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.progressStage = buf.readInt();
        this.id = buf.readInt();
        this.meta = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(progressStage);
        buf.writeInt(id);
        buf.writeInt(meta);
    }

    @Override
    public IMessage onMessage(MessageMechanicDuster message, MessageContext ctx) {
        TileEntity entity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
        if (entity instanceof TileMechanicalDuster){
            TileMechanicalDuster duster = (TileMechanicalDuster) entity;
            if (message.id == 0) {
                duster.setInventorySlotContents(0, null);
            } else {
                duster.setInventorySlotContents(0, new ItemStack(Item.getItemById(message.id), 1, message.meta));
            }
            duster.progressStage = message.progressStage;
        }
        return null;
    }
}
