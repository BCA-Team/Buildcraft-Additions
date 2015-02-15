package buildcraftAdditions.core;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import buildcraftAdditions.items.BatteryBase;
import buildcraftAdditions.items.Tools.ItemPoweredBase;

@Deprecated
public class InventoryTool extends InventoryBasic {

	ItemStack tool;
	boolean reading;

	public InventoryTool(ItemStack stack) {
		super("Tool Inventory", false, 3);
		tool = stack;
		if (!hasInventory()) {
			createInventory();
		}
	}

	public static boolean isInventoryEmpty(IInventory inventory) {
		for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
			if (inventory.getStackInSlot(slot) != null) {
				return false;
			}
		}
		return true;
	}

	private boolean hasInventory() {
		return tool.stackTagCompound != null && tool.stackTagCompound.getTag("Inventory") != null;
	}

	private void createInventory() {
		writeToNBT();
	}

	protected void writeToNBT() {
		NBTTagList itemList = new NBTTagList();
		for (int t = 0; t < getSizeInventory(); t++) {
			if (getStackInSlot(t) != null) {
				NBTTagCompound slotContent = new NBTTagCompound();
				slotContent.setByte("Slot", (byte) t);
				getStackInSlot(t).writeToNBT(slotContent);
				itemList.appendTag(slotContent);
			}
		}

		NBTTagCompound inventory = new NBTTagCompound();
		inventory.setTag("Items", itemList);
		if (tool.stackTagCompound == null)
			tool.stackTagCompound = new NBTTagCompound();
		tool.stackTagCompound.setTag("Inventory", inventory);
	}

	@Override
	public void markDirty() {
		super.markDirty();
		if (!reading) {
			saveInventory();
		}

	}

	public void saveInventory() {
		writeToNBT();
		setNBT();
	}

	protected void setNBT() {
		if (tool != null && tool.getItem() instanceof ItemPoweredBase) {
			tool.setTagCompound(tool.getTagCompound());
		}
	}

	@Override
	public void openInventory() {
		loadInventory();
	}

	public void loadInventory() {
		readFromNBT();
	}

	@Override
	public void closeInventory() {
		saveInventory();
	}

	protected void readFromNBT() {
		reading = true;

		NBTTagList itemList = (NBTTagList) ((NBTTagCompound) tool.stackTagCompound.getTag("Inventory")).getTag("Items");
		for (int t = 0; t < itemList.tagCount(); t++) {
			NBTTagCompound slotEntry = itemList.getCompoundTagAt(t);
			int j = slotEntry.getByte("Slot") & 0xff;
			if (j >= 0 && j < getSizeInventory()) {
				setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(slotEntry));
			}
		}
		reading = false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack.getItem() instanceof BatteryBase;
	}

}
