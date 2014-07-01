package buildcraftAdditions.core;

import buildcraftAdditions.interfaces.IEurekaBlock;
import buildcraftAdditions.utils.Eureka;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EventListener  {

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

    @SubscribeEvent
    public void onBlockPlacedDown(PlayerInteractEvent event){
        if (event == null)
            return;
        Block block = event.world.getBlock(event.x, event.y, event.z);
        if (block instanceof IEurekaBlock){
            IEurekaBlock eurekaBlock = (IEurekaBlock) block;
            if (!eurekaBlock.isAllowed()){
                World world = event.world;
                ItemStack[] stackArray = eurekaBlock.getComponents();
                float f1 = 0.7F;
                double d = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
                double d1 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
                double d2 = (world.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
                for (ItemStack stack : stackArray) {
                    EntityItem itemToDrop = new EntityItem(world, event.x + d, event.y + d1, event.z + d2, stack);
                    itemToDrop.delayBeforeCanPickup = 10;
                    if (!world.isRemote)
                    world.spawnEntityInWorld(itemToDrop);
                }
                if (!world.isRemote)
                world.setBlock(event.x, event.y, event.z, Blocks.air);
                world.markBlockForUpdate(event.x, event.y, event.z);
                if (world.isRemote)
                event.entityPlayer.addChatComponentMessage(new ChatComponentText(((IEurekaBlock) block).getMessage()));
            }
        }
    }
}
