package buildcraftAdditions.core;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.FluidRegistry;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.RefineryRecepieConverter;
import buildcraftAdditions.utils.Utils;

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
				if (!ConfigurationHandler.shouldPrintChangelog)
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
				ConfigurationHandler.readConfig();
		}

		@SubscribeEvent
		public void onCrafted(PlayerEvent.ItemCraftedEvent event) {
			if (event.crafting.getItem().isItemTool(event.crafting))
				EurekaKnowledge.makeProgress(event.player, Variables.KineticToolKey, 1);
		}
	}

	public static class Forge {

		@SubscribeEvent
		public void onGettingAchievement(AchievementEvent event) {
			//unlock basic duster
			EurekaKnowledge.makeProgress(event.entityPlayer, Variables.DustT0Key, 1);
		}

		@SubscribeEvent
		public void onInteraction(PlayerInteractEvent event){
			Block block = event.world.getBlock(event.x, event.y, event.z);
			if (block != ItemsAndBlocks.kebT1)
				return;
			if (event.entityPlayer != null && event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem().getToolClasses(event.entityPlayer.getCurrentEquippedItem()).contains("wrench") && event.entityPlayer.isSneaking() && event.world.getTileEntity(event.x, event.y, event.z) != null) {
				NBTTagCompound tag = new NBTTagCompound();
				event.world.getTileEntity(event.x, event.y, event.z).writeToNBT(tag);
				ItemStack stack = new ItemStack(ItemsAndBlocks.kebT1, 1, event.world.getBlockMetadata(event.x, event.y, event.z));
				stack.stackTagCompound = tag;
				Utils.dropItemstack(event.world, event.x, event.y, event.z, stack);
				event.world.setBlockToAir(event.x, event.y, event.z);
				event.world.removeTileEntity(event.x, event.y, event.z);
			}
		}

		@SubscribeEvent
		public void textures(TextureStitchEvent.Post event) {
			if (event.map.getTextureType() == 0) {
				for (int t = 0; t < RefineryRecepieConverter.inputs.length; t++) {
					if (RefineryRecepieConverter.inputs[t] != null && RefineryRecepieConverter.outputs[t] != null)
						BuildcraftAdditions.proxy.cloneFluidTextures(FluidRegistry.getFluid(RefineryRecepieConverter.outputs[t].getFluid().getName()), RefineryRecepieConverter.gas[t].getFluid());
				}
			}
		}
	}
}
