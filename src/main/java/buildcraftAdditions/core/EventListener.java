package buildcraftAdditions.core;

import buildcraftAdditions.api.IEurekaBlock;
import buildcraftAdditions.api.IEurekaItem;
import buildcraftAdditions.utils.Eureka;
import buildcraftAdditions.utils.Utils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EventListener  {

    public static class FML {
        @SubscribeEvent
        public void playerLogin (PlayerLoggedInEvent event){
            //version check stuff
            if (VersionCheck.newerVersionAvailable && event != null){
                event.player.addChatComponentMessage(new ChatComponentText("There is a newer version of Buildcraft Additions available (" + VersionCheck.newerVersionNumber + ") Please consider updating"));
                if (!Configuration.shouldPrintChangelog)
                    return;
                event.player.addChatComponentMessage(new ChatComponentText("Changelog: "));
                for (int t = 0; t < VersionCheck.numLines; t++){

                    event.player.addChatComponentMessage(new ChatComponentText("- " + VersionCheck.changelog[t]));
                }
            }
            //initialize player knowledge if needed
            Eureka.init(event.player);

        }
    }

    public static class Forge{
        @SubscribeEvent
        public void onPlyerUsesBlock(PlayerInteractEvent event) {
            if (event == null)
                return;
            Block block = event.world.getBlock(event.x, event.y, event.z);
            if (block instanceof IEurekaBlock) {
                IEurekaBlock eurekaBlock = (IEurekaBlock) block;
                World world = event.world;
                if (!world.isRemote) {
                    if (!eurekaBlock.isAllowed(event.entityPlayer)) {
                        ItemStack[] stackArray = eurekaBlock.getComponents();
                        for (ItemStack stack : stackArray)
                            Utils.dropItemstack(world, event.x, event.y, event.z, stack);
                        if (!world.isRemote)
                            world.setBlock(event.x, event.y, event.z, Blocks.air);
                        world.markBlockForUpdate(event.x, event.y, event.z);
                        event.entityPlayer.addChatComponentMessage(new ChatComponentText(((IEurekaBlock) block).getMessage()));

                    }
                }
            }
        }

        @SubscribeEvent
        public void onItemUse(PlayerUseItemEvent event){
            if (event== null)
                return;
            Item item = event.item.getItem();
            if (item instanceof IEurekaItem){
                IEurekaItem eurekaItem = (IEurekaItem) item;
                if (!eurekaItem.isAllowed(event.entityPlayer)) {
                    event.setCanceled(true);
                    if (event.entityPlayer != null)
                        event.entityPlayer.addChatComponentMessage(new ChatComponentText(eurekaItem.getMessage()));
                }
            }
        }
    }
}
