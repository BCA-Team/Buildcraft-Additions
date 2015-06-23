package buildcraftAdditions.listeners;

import buildcraft.api.tools.IToolWrench;
import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.core.VersionCheck;
import buildcraftAdditions.items.dust.ItemConverter;
import buildcraftAdditions.reference.BlockLoader;
import buildcraftAdditions.reference.ItemLoader;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.utils.fluids.RefineryRecipeConverter;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eureka.api.EurekaAPI;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EventListener {

	public static class FML {

		@SubscribeEvent
		public void playerLogin(PlayerLoggedInEvent event) {
			if (VersionCheck.newerVersionAvailable && event != null) {
				event.player.addChatComponentMessage(new ChatComponentTranslation("updatenotification", VersionCheck.newerVersionNumber));
				if (!ConfigurationHandler.shouldPrintChangelog)
					return;
				event.player.addChatComponentMessage(new ChatComponentText(Utils.localize("changelogNotification") + ": "));
				for (String s : VersionCheck.changelog)
					event.player.addChatComponentMessage(new ChatComponentText("- " + s));
			}
		}

		@SubscribeEvent
		public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.modID.equalsIgnoreCase(Variables.MOD.ID))
				ConfigurationHandler.readConfig();
		}

		@SubscribeEvent
		public void onCrafted(PlayerEvent.ItemCraftedEvent event) {
			if (event.crafting != null && event.crafting.getItem() != null && event.craftMatrix != null) {
				if (event.crafting.getItem().isItemTool(event.crafting))
					EurekaAPI.API.makeProgress(Variables.Eureka.KineticToolKey, event.player);
			}
		}
	}

	public static class Forge {

		@SubscribeEvent
		public void onInteraction(PlayerInteractEvent event) {
			if (!event.world.isRemote) {
				Block block = event.world.getBlock(event.x, event.y, event.z);
				if (block == BlockLoader.kebT1) {
					if (event.entityPlayer != null && event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem() != null && ((event.entityPlayer.getCurrentEquippedItem().getItem() instanceof IToolWrench && ((IToolWrench) event.entityPlayer.getCurrentEquippedItem().getItem()).canWrench(event.entityPlayer, event.x, event.y, event.z)) || event.entityPlayer.getCurrentEquippedItem().getItem().getToolClasses(event.entityPlayer.getCurrentEquippedItem()).contains("wrench")) && event.entityPlayer.isSneaking()) {
						TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
						if (tile != null) {
							NBTTagCompound tag = new NBTTagCompound();
							tile.writeToNBT(tag);
							ItemStack stack = new ItemStack(BlockLoader.kebT1, 1, event.world.getBlockMetadata(event.x, event.y, event.z));
							tag.removeTag("x");
							tag.removeTag("y");
							tag.removeTag("z");
							tag.removeTag("id");
							stack.stackTagCompound = tag;
							event.world.setBlockToAir(event.x, event.y, event.z);
							if (event.entityPlayer.getCurrentEquippedItem().getItem() instanceof IToolWrench)
								((IToolWrench) event.entityPlayer.getCurrentEquippedItem().getItem()).wrenchUsed(event.entityPlayer, event.x, event.y, event.z);
							if (!event.entityPlayer.isSwingInProgress)
								event.entityPlayer.swingItem();
							Utils.dropItemstackAtEntity(event.entityPlayer, stack);
						}
					}
				} else if (event.entityPlayer != null && event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK && event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem() == ItemLoader.machineConfigurator) {
					TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
					if (tile != null && tile instanceof IUpgradableMachine)
						((IUpgradableMachine) tile).removeUpgrade();
				}
			}
		}

		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void textures(TextureStitchEvent.Post event) {
			if (ConfigurationHandler.forceEnableBCRefinery)
				return;
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
