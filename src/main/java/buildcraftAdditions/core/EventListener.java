package buildcraftAdditions.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.FluidRegistry;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.items.dust.ItemConverter;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.RefineryRecipeConverter;
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
				event.player.addChatComponentMessage(new ChatComponentText(Utils.localize("changelogNotification") + ": "));
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
		public void onItemTooltip(ItemTooltipEvent event) {
			if (event.itemStack != null && event.itemStack.getItem() == Item.getItemFromBlock(ItemsAndBlocks.kebT1) && event.itemStack.stackTagCompound != null)
				event.toolTip.add("" + EnumChatFormatting.GRAY + EnumChatFormatting.ITALIC + Utils.localize("configured"));
		}

		@SubscribeEvent
		public void onGettingAchievement(AchievementEvent event) {
			//unlock basic duster
			EurekaKnowledge.makeProgress(event.entityPlayer, Variables.DustT0Key, 1);
		}

		@SubscribeEvent
		public void onInteraction(PlayerInteractEvent event) {
			Block block = event.world.getBlock(event.x, event.y, event.z);
			if (block != ItemsAndBlocks.kebT1)
				return;
			if (event.entityPlayer != null && event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem().getToolClasses(event.entityPlayer.getCurrentEquippedItem()).contains("wrench") && event.entityPlayer.isSneaking()) {
				TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
				if (tile != null) {
					NBTTagCompound tag = new NBTTagCompound();
					tile.writeToNBT(tag);
					ItemStack stack = new ItemStack(ItemsAndBlocks.kebT1, 1, event.world.getBlockMetadata(event.x, event.y, event.z));
					tag.removeTag("x");
					tag.removeTag("y");
					tag.removeTag("z");
					tag.removeTag("id");
					stack.stackTagCompound = tag;
					event.world.removeTileEntity(event.x, event.y, event.z);
					event.world.setBlockToAir(event.x, event.y, event.z);
					if (event.world.isRemote)
						event.entityPlayer.swingItem();
					Utils.dropItemstack(event.world, event.x, event.y, event.z, stack);
				}
			}
		}

		@SideOnly(Side.CLIENT)
		@SubscribeEvent
		public void textures(TextureStitchEvent.Post event) {
			if (event.map.getTextureType() == 0) {
				for (int t = 0; t < RefineryRecipeConverter.inputs.length; t++) {
					if (RefineryRecipeConverter.inputs[t] != null && RefineryRecipeConverter.outputs[t] != null)
						BuildcraftAdditions.proxy.cloneFluidTextures(FluidRegistry.getFluid(RefineryRecipeConverter.outputs[t].getFluid().getName()), RefineryRecipeConverter.gasses[t].getFluid());
				}
			}
		}

		@SubscribeEvent
		public void onItemDrop(ItemTossEvent event) {
			if (!(event.entityItem == null || event.entityItem.getEntityItem() == null || !(event.entityItem.getEntityItem().getItem() instanceof ItemConverter))) {
				ItemStack stack = ((ItemConverter) event.entityItem.getEntityItem().getItem()).getDust().getDustStack();
				stack.stackSize = event.entityItem.getEntityItem().stackSize;
				event.entityItem.setEntityItemStack(stack);
			}
		}
	}
}
