package buildcraftAdditions.networking;

import buildcraftAdditions.tileEntities.TileSemiAutomaticDuster;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftAdditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftAdditions.wordpress.com/wiki/licensing-stuff/
 */
public class MessageSemiAutomaticDuster implements IMessage, IMessageHandler<MessageSemiAutomaticDuster, IMessage> {
    public int x, y, z, id, meta;

    public MessageSemiAutomaticDuster(){

    }

    public MessageSemiAutomaticDuster(int x, int y, int z, ItemStack stack){
        this.x = x;
        this.y = y;
        this.z = z;
        if (stack != null) {
            this.id = Item.getIdFromItem(stack.getItem());
            this.meta = stack.getItemDamage();
        } else {
            this.id = 0;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.id = buf.readInt();
        this.meta = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.id);
        buf.writeInt(this.meta);
    }

    @Override
    public IMessage onMessage(MessageSemiAutomaticDuster message, MessageContext ctx) {
        TileSemiAutomaticDuster duster = (TileSemiAutomaticDuster) FMLClientHandler.instance().getWorldClient().getTileEntity(message.x, message.y, message.z);
        if (duster == null)
            return null;
        if (message.id != 0) {
            duster.setInventorySlotContents(0, new ItemStack(Item.getItemById(message.id), message.meta));
        } else{
            duster.setInventorySlotContents(0, null);
        }
        return null;
    }
}
