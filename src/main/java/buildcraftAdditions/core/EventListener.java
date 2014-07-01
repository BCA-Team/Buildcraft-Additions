package buildcraftAdditions.core;

import buildcraftAdditions.utils.Eureka;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.util.ChatComponentText;

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
}
