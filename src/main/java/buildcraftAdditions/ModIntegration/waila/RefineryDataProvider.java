package buildcraftAdditions.ModIntegration.waila;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.tileEntities.TileRefinery;
import buildcraftAdditions.utils.Utils;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RefineryDataProvider implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileRefinery refinery = (TileRefinery) accessor.getTileEntity();
		if (!refinery.isPartOfMultiblock()) {
			currenttip.add(Utils.localize("waila.refinery.noMultiBlock"));
		} else {
			if (refinery.master == null && (!refinery.isMaster())) {
				refinery.findMaster();
				if (refinery.master == null)
					return currenttip;
			}
			TileRefinery master = refinery;
			if (!refinery.isMaster())
				master = refinery.master;
			currenttip.add(Utils.localizeFormatted("waila.refinery.currentHeat", master.currentHeat));
			if (!master.getOutput().equals("")) {
				currenttip.add(Utils.localizeFormatted("waila.refinery.requiredHeat", master.requiredHeat));
				currenttip.add(Utils.localizeFormatted("waila.refinery.processing", master.getInput(), master.getOutput()));
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
		if (te != null)
			te.writeToNBT(tag);
		return tag;
	}
}
