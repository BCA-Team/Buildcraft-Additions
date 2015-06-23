package buildcraftAdditions.items.itemBlocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import net.minecraftforge.common.util.Constants;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.items.bases.ItemBlockBase;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.PlayerUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemBlockKEB extends ItemBlockBase implements IEnergyContainerItem {

	public ItemBlockKEB(Block block) {
		super(block, "", "", "", BuildcraftAdditions.bcadditions);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (PlayerUtils.playerMatches(Variables.UUIDs.CORJAANTJE, BuildcraftAdditions.proxy.getClientPlayer())) {
			if (stack.stackTagCompound != null && stack.stackTagCompound.getBoolean("creative"))
				return "Creative Kebab Extreme Bakery";
			return "Kebab Extreme Bakery";
		}
		if (stack.stackTagCompound != null && stack.stackTagCompound.getBoolean("creative"))
			return Utils.localize("tile.blockKEBT1Creative.name");
		return super.getItemStackDisplayName(stack);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible) {
		list.add(Utils.getRFInfoTooltip(getEnergyStored(stack), getMaxEnergyStored(stack)));
		if (stack.stackTagCompound != null) {
			list.add("" + EnumChatFormatting.GRAY + EnumChatFormatting.ITALIC + Utils.localize("configured"));
			if (stack.stackTagCompound.getBoolean("creative"))
				list.add(Utils.localize("tooltip.creativeOnly"));
		}
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getMaxEnergyStored(stack) > 0 && getEnergyStored(stack) > 0 && (stack.stackTagCompound == null || !stack.stackTagCompound.hasKey("creative", Constants.NBT.TAG_BYTE) || !stack.getTagCompound().getBoolean("creative"));
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		double maxEnergy = getMaxEnergyStored(stack);
		if (maxEnergy <= 0)
			return 1;
		return (maxEnergy - getEnergyStored(stack)) / maxEnergy;
	}

	public int getMaxTransfer() {
		return ConfigurationHandler.maxTransferKEBTier1;
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		if (container.stackTagCompound == null)
			container.stackTagCompound = new NBTTagCompound();
		int energy = container.stackTagCompound.getInteger("energy");
		int energyReceived = Math.min(getMaxEnergyStored(container) - energy, Math.min(getMaxTransfer(), maxReceive));
		if (!simulate) {
			energy += energyReceived;
			container.stackTagCompound.setInteger("energy", energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("energy", Constants.NBT.TAG_INT))
			return 0;
		int energy = container.stackTagCompound.getInteger("energy");
		int energyExtracted = Math.min(energy, Math.min(getMaxTransfer(), maxExtract));
		if (!simulate) {
			energy -= energyExtracted;
			container.stackTagCompound.setInteger("energy", energy);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("energy", Constants.NBT.TAG_INT))
			return 0;
		return container.stackTagCompound.getInteger("energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return ConfigurationHandler.capacityKEBTier1;
	}
}
