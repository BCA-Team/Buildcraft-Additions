package buildcraftAdditions.items.itemBlocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import net.minecraftforge.common.util.Constants;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.PlayerUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemBlockKEB extends ItemBlock implements IEnergyContainerItem {

	public ItemBlockKEB(Block block) {
		super(block);
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
		list.add(Utils.localizeFormatted("rf.info", getEnergyStored(stack), getMaxEnergyStored(stack)));
		if (stack.hasTagCompound())
			list.add("" + EnumChatFormatting.GRAY + EnumChatFormatting.ITALIC + Utils.localize("configured"));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getEnergyStored(stack) > 0 && stack.hasTagCompound() && (!stack.getTagCompound().hasKey("creative") || !stack.getTagCompound().getBoolean("creative"));
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		double maxEnergy = getMaxEnergyStored(stack);
		if (maxEnergy <= 0)
			return 1;
		return (maxEnergy - getEnergyStored(stack)) / maxEnergy;
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		if (!container.hasTagCompound() || !container.getTagCompound().hasKey("energy", Constants.NBT.TAG_INT))
			return 0;
		int current = container.getTagCompound().getInteger("energy");
		int received = Math.min(3000000 - current, maxReceive);
		if (!simulate)
			container.getTagCompound().setInteger("energy", current + received);
		return received;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		if (!container.hasTagCompound() || !container.getTagCompound().hasKey("energy", Constants.NBT.TAG_INT))
			return 0;
		int current = container.getTagCompound().getInteger("energy");
		int extracted = Math.min(current, maxExtract);
		if (!simulate)
			container.getTagCompound().setInteger("energy", current - extracted);
		return extracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		if (!container.hasTagCompound() || !container.getTagCompound().hasKey("energy", Constants.NBT.TAG_INT))
			return 0;

		return container.getTagCompound().getInteger("energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		if (!container.hasTagCompound())
			return 0;

		return 3000000;
	}
}
