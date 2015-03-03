package buildcraftAdditions.ModIntegration.waila;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.api.recipe.duster.IDusterRecipe;
import buildcraftAdditions.tileEntities.Bases.TileBaseDuster;
import buildcraftAdditions.utils.Utils;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DusterDataProvider implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if (accessor.getNBTData() != null && accessor.getTileEntity() != null && accessor.getTileEntity() instanceof TileBaseDuster) {
			NBTTagCompound tag = accessor.getNBTData();
			TileBaseDuster duster = (TileBaseDuster) accessor.getTileEntity();
			ItemStack input = duster.getStackInSlot(0);
			if (input != null && input.getItem() != null) {
				IDusterRecipe recipe = duster.getRecipe();
				if (recipe != null) {
					ItemStack output = recipe.getOutput(input);
					if (output != null && output.getItem() != null) {
						if (config.getConfig(WailaIntegration.GRAPHICAL_DUSTER_PROGRESS_CONFIG_KEY)) {
							String s = "";
							s += SpecialChars.getRenderString("waila.stack", input.getItem() instanceof ItemBlock ? "0" : "1", input.getItem() instanceof ItemBlock ? Block.blockRegistry.getNameForObject(((ItemBlock) input.getItem()).field_150939_a) : Item.itemRegistry.getNameForObject(input.getItem()), "" + input.stackSize, "" + input.getItemDamage());
							s += SpecialChars.getRenderString("waila.progress", "" + tag.getInteger("progress"), "100");
							s += SpecialChars.getRenderString("waila.stack", output.getItem() instanceof ItemBlock ? "0" : "1", output.getItem() instanceof ItemBlock ? Block.blockRegistry.getNameForObject(((ItemBlock) output.getItem()).field_150939_a) : Item.itemRegistry.getNameForObject(output.getItem()), "" + output.stackSize, "" + output.getItemDamage());
							currenttip.add(s);
						}
						if (config.getConfig(WailaIntegration.TEXTUAL_DUSTER_PROGRESS_CONFIG_KEY)) {
							currenttip.add(Utils.localizeFormatted("waila.duster.recipe", input.getDisplayName(), output.getDisplayName()));
							currenttip.add(Utils.localize("waila.duster.progress") + " : " + tag.getInteger("progress") + " %");
						}
					}
				}
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
		if (te != null && te instanceof TileBaseDuster)
			tag.setInteger("progress", (int) (((TileBaseDuster) te).getProgress() * 100));
		return tag;
	}
}
