package buildcraftAdditions.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.Constants;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.items.bases.ItemBase;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemPoweredBase extends ItemBase implements IEnergyContainerItem {

	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;

	public ItemPoweredBase(String name, String gameRegistryName) {
		super(name, name, gameRegistryName);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}

	public ItemPoweredBase(String name, String gameregistryName, int capacity) {
		this(name, gameregistryName, capacity, capacity, capacity);
	}

	public ItemPoweredBase(String name, int capacity, int maxTransfer) {
		this(name, name, capacity, maxTransfer, maxTransfer);
	}

	public ItemPoweredBase(String name, String gameRegistryName, int capacity, int maxReceive, int maxExtract) {
		this(name, gameRegistryName);
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}

	public int getMaxReceive() {
		return maxReceive;
	}

	public int getMaxExtract() {
		return maxExtract;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getMaxEnergyStored(stack) > 0;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		double maxEnergy = getMaxEnergyStored(stack);
		if (maxEnergy <= 0)
			return 1;
		return (maxEnergy - getEnergyStored(stack)) / maxEnergy;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedTooltips) {
		list.add(Utils.getRFInfoTooltip(getEnergyStored(stack), getMaxEnergyStored(stack)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		list.add(new ItemStack(item));
		ItemStack filled = new ItemStack(item);
		filled.stackTagCompound = new NBTTagCompound();
		filled.stackTagCompound.setInteger("energy", getMaxEnergyStored(filled));
		list.add(filled);
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		if (container.stackTagCompound == null)
			container.stackTagCompound = new NBTTagCompound();
		int energy = container.stackTagCompound.getInteger("energy");
		int energyReceived = Math.min(getMaxEnergyStored(container) - energy, Math.min(getMaxReceive(), maxReceive));
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
		int energyExtracted = Math.min(energy, Math.min(getMaxExtract(), maxExtract));
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
		return capacity;
	}

}
