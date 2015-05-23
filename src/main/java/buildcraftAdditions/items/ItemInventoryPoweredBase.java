package buildcraftAdditions.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.inventories.InventoryItem;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class ItemInventoryPoweredBase extends ItemPoweredBase {

	public ItemInventoryPoweredBase(String name) {
		this(name, name);
	}

	public ItemInventoryPoweredBase(String name, String gameregistryName) {
		super(name, gameregistryName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		list.add(new ItemStack(item));
	}

	public abstract InventoryItem getInventory(ItemStack stack);

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		InventoryItem inv = getInventory(container);
		int received = 0;
		if (inv != null) {
			for (int i = 0; i < inv.getSizeInventory() && maxReceive - received > 0; i++) {
				ItemStack stack = inv.getStackInSlot(i);
				if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
					IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
					received += item.receiveEnergy(stack, maxReceive - received, simulate);
				}
			}
			inv.closeInventory();
		}
		return received;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		InventoryItem inv = getInventory(container);
		int extracted = 0;
		if (inv != null) {
			for (int i = 0; i < inv.getSizeInventory() && maxExtract - extracted > 0; i++) {
				ItemStack stack = inv.getStackInSlot(i);
				if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
					IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
					extracted += item.extractEnergy(stack, maxExtract - extracted, simulate);
				}
			}
			inv.closeInventory();
		}
		return extracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		InventoryItem inv = getInventory(container);
		int stored = 0;
		if (inv != null) {
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack stack = inv.getStackInSlot(i);
				if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
					IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
					stored += item.getEnergyStored(stack);
				}
			}
		}
		return stored;
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		InventoryItem inv = getInventory(container);
		int maxStored = 0;
		if (inv != null) {
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack stack = inv.getStackInSlot(i);
				if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
					IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
					maxStored += item.getMaxEnergyStored(stack);
				}
			}
		}
		return maxStored;
	}

}
