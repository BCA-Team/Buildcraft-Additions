package buildcraftAdditions.ModIntegration.waila;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.reference.enums.EnumMachineUpgrades;
import buildcraftAdditions.tileEntities.TileRefinery;

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
			currenttip.add("Not part of a multiblock");
		} else {
			if (refinery.master == null && (!refinery.isMaster())) {
				refinery.findMaster();
				if (refinery.master == null)
					return currenttip;
			}
			TileRefinery master = refinery;
			if (!refinery.isMaster())
				master = refinery.master;
			currenttip.add("Current heat: " + master.currentHeat);
			if (!master.getOutput().equals("")) {
				currenttip.add("Required heat: " + master.requiredHeat);
				currenttip.add("Refining " + master.getInput() + " into " + master.getOutput());
			}
			String upgrades = "";

			if (!refinery.getIntalledUpgrades().isEmpty()) {
				for (EnumMachineUpgrades upgrade : refinery.getIntalledUpgrades())
					upgrades += SpecialChars.getRenderString("BCA.upgradeRenderer", String.valueOf(upgrade.ordinal()));
			}
			currenttip.add(upgrades);
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
