package buildcraftAdditions.utils;

import buildcraftAdditions.api.EurekaRegistry;
import buildcraftAdditions.variables.Variables;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Eureka {

    public static void init(EntityPlayer player){
        if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)){
            player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
        }
        NBTTagCompound tag = getTag(player);
        for (String key : EurekaRegistry.getKeys())
            initKey(tag, key);

    }

    private static NBTTagCompound getTag (EntityPlayer player){
        return (NBTTagCompound) player.getEntityData().getTag(EntityPlayer.PERSISTED_NBT_TAG);
    }

    public static int getProgress(EntityPlayer player, String key){
        return getTag(player).getInteger(key + "Progress");
    }

    public static boolean isUnlocked(EntityPlayer player, String key){
        return getTag(player).getBoolean(key + "Finished");
    }

    public static void makeProgress (EntityPlayer player, String key){
        if (player.worldObj.isRemote)
            return;
        int progress = getProgress(player, key);
        NBTTagCompound tag = getTag(player);
        if (progress < 100){
            setKey(tag, key + "Progress", progress + 1);
        }
        if (progress == 100){
            key = key + "Finished";
            if (!isUnlocked(player, key)) {
                setKey(tag, key, true);
                String message = Utils.localize("eureka." + key);
                player.addChatMessage(new ChatComponentText(Utils.localize("Eureka")));
                player.addChatComponentMessage(new ChatComponentText(message));
            }
        }
    }

    private static void initKey(NBTTagCompound tag, String key){
        if (!tag.hasKey(key + "Progress")){
            setKey(tag, key + "Progress", 0);
            setKey(tag, key + "Finished", false);
        }
    }

    private static void setKey(NBTTagCompound tag, String key, int integer){
        tag.setInteger(key, integer);
    }

    private static void setKey(NBTTagCompound tag, String key, boolean bool){
        tag.setBoolean(key, bool);

    }
}
