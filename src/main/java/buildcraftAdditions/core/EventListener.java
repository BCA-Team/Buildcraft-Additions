package buildcraftAdditions.core;

import net.minecraft.util.ChatComponentText;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

import net.minecraftforge.event.entity.player.AchievementEvent;

import buildcraftAdditions.config.ConfigurationHandeler;
import buildcraftAdditions.variables.Variables;


import eureka.api.EurekaKnowledge;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EventListener {

	public static class FML {

		@SubscribeEvent
		public void playerLogin(PlayerLoggedInEvent event) {
			//version check stuff
			if (VersionCheck.newerVersionAvailable && event != null) {
				event.player.addChatComponentMessage(new ChatComponentText("There is a newer version of Buildcraft Additions available (" + VersionCheck.newerVersionNumber + ") Please consider updating"));
				if (!ConfigurationHandeler.shouldPrintChangelog)
					return;
				event.player.addChatComponentMessage(new ChatComponentText("Changelog: "));
				for (int t = 0; t < VersionCheck.numLines; t++) {

					event.player.addChatComponentMessage(new ChatComponentText("- " + VersionCheck.changelog[t]));
				}
			}

		}

		@SubscribeEvent
		public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.modID.equals("bcadditions"))
				ConfigurationHandeler.readConfig();
		}

		@SubscribeEvent
		public void onCrafted(PlayerEvent.ItemCraftedEvent event) {
			if (event.crafting.getItem().isItemTool(event.crafting))
				EurekaKnowledge.makeProgress(event.player, Variables.KineticToolKey);
		}
	}

	public static class Forge {

		@SubscribeEvent
		public void onGettingAchievement(AchievementEvent event) {
			//unlock basic duster
			EurekaKnowledge.makeProgress(event.entityPlayer, Variables.DustT0Key);
		}
	}
}
