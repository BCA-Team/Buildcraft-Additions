package buildcraftAdditions.Utils;

import buildcraftAdditions.Variables.Variables;
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
        initKey(tag, Variables.DustT1Progress, 0);
        initKey(tag, Variables.DustT1Finished, false);
        initKey(tag, Variables.DustT2Progress1, 0);
        initKey(tag, Variables.DustT2Finished1, false);
        initKey(tag, Variables.DustT2Progress2, 0);
        initKey(tag, Variables.DustT2Finished2, false);
        initKey(tag, Variables.CoilT1Progress, 0);
        initKey(tag, Variables.CoilT1Finished, false);
        initKey(tag, Variables.CoilT2Progress, 0);
        initKey(tag, Variables.CoilT2Finished, false);

    }

    private static NBTTagCompound getTag (EntityPlayer player){
        return (NBTTagCompound) player.getEntityData().getTag(EntityPlayer.PERSISTED_NBT_TAG);
    }

    public static int getProgress(EntityPlayer player, String key){
        return getTag(player).getInteger(key);
    }

    public static boolean isUnlocked(EntityPlayer player, String key){
        return getTag(player).getBoolean(key);
    }

    public static void makeProgress (EntityPlayer player, String key){
        if (player.worldObj.isRemote)
            return;
        int progress = getProgress(player, key);
        NBTTagCompound tag = getTag(player);
        if (progress < 100){
            setKey(tag, key, progress + 1);
        }
        if (progress == 100){
            key = key.replaceAll("Progress", "Finished");
            if (!isUnlocked(player, key)) {
                setKey(tag, key, true);
                String message = Utils.localize("eureka." + key);
                player.addChatMessage(new ChatComponentText(Utils.localize("Eureka")));
                player.addChatComponentMessage(new ChatComponentText(message));
            }
        }
    }

    private static void initKey(NBTTagCompound tag, String key, int integer){
        if (!tag.hasKey(key))
            setKey(tag, key, integer);
    }

    private static void initKey(NBTTagCompound tag, String key, boolean bool){
        if (!tag.hasKey(key))
            setKey(tag, key, bool);
    }

    private static void setKey(NBTTagCompound tag, String key, int integer){
        tag.setInteger(key, integer);
    }

    private static void setKey(NBTTagCompound tag, String key, boolean bool){
        tag.setBoolean(key, bool);

    }
}
