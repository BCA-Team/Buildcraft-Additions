package buildcraftAdditions.items;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;

public class BatteryBase extends Item implements IEnergyContainerItem {
	protected int maxEnergy, maxExtract, maxInput, tier;
	private String texture;
	public IIcon icon;

	public BatteryBase(int maxEnergy, int maxExtract, int maxInput, int tier, String texture) {
		this.maxStackSize = 1;
		setCreativeTab(BuildcraftAdditions.bcadditions);
		this.maxEnergy = maxEnergy;
		this.maxExtract = maxExtract;
		this.maxInput = maxInput;
		this.tier = tier;
		this.texture = texture;
	}

	public void setEnergy(ItemStack stack, int energy) {
		if (stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setInteger("energy", energy);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return (((double) maxEnergy - getEnergyStored(stack))) / maxEnergy;
	}

	@SuppressWarnings("unchecked")
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible) {
		list.add(Integer.toString(getEnergyStored(stack)) + "/" + Integer.toString(maxEnergy) + " RF");
	}

	@Override
	public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
		int energy = getEnergyStored(stack);
		int recieved = maxReceive;
		if (recieved > maxEnergy - energy)
			recieved = maxEnergy - energy;
		if (recieved > maxReceive)
			recieved = maxReceive;
		if (!simulate)
			energy += recieved;
		setEnergy(stack, energy);
		return recieved;
	}

	@Override
	public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
		int energy = getEnergyStored(stack);
		int extracted = maxExtract;
		if (extracted > energy)
			extracted = energy;
		if (extracted > maxExtract)
			extracted = maxExtract;
		if (!simulate)
			energy -= extracted;
		setEnergy(stack, energy);
		return extracted;
	}

	@Override
	public int getEnergyStored(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("energy", 0);
		}
		return stack.stackTagCompound.getInteger("energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack stack) {
		return maxEnergy;
	}

	public String getType() {
		return "(Tier " + tier + "): ";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		icon = par1IconRegister.registerIcon(Variables.MOD.ID + ":" + texture);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		return icon;
	}

	public ItemStack createdFilledBattery() {
		ItemStack stack = new ItemStack(this);
		if (stack.getTagCompound() == null)
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("energy", maxEnergy);
		return stack;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		super.getSubItems(item, tab, list);
		list.add(createdFilledBattery());
	}
}
