package buildcraftAdditions.inventories;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import net.minecraftforge.common.util.Constants;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class InventoryItem extends InventoryBasic {

	protected final ItemStack stack;

	public InventoryItem(ItemStack stack, String name, int size) {
		super(stack.hasDisplayName() ? stack.getDisplayName() : name, stack.hasDisplayName(), size);
		this.stack = stack;
		openInventory();
	}

	public InventoryItem(ItemStack stack) {
		super("", true, 0);
		this.stack = stack;
		openInventory();
	}

	@Override
	public void markDirty() {
		super.markDirty();
		writeToNBT();
	}

	@Override
	public void openInventory() {
		readFromNBT();
	}

	@Override
	public void closeInventory() {
		writeToNBT();
	}

	public void readFromNBT() {
		if (stack != null && stack.stackTagCompound != null && stack.stackTagCompound.hasKey("Inventory", Constants.NBT.TAG_COMPOUND)) {
			NBTTagCompound inventoryTag = stack.stackTagCompound.getCompoundTag("Inventory");
			if (inventoryTag != null && inventoryTag.hasKey("Items", Constants.NBT.TAG_LIST)) {
				NBTTagList itemList = inventoryTag.getTagList("Items", Constants.NBT.TAG_COMPOUND);
				if (itemList != null) {
					for (int i = 0; i < itemList.tagCount(); i++) {
						NBTTagCompound slotTag = itemList.getCompoundTagAt(i);
						if (slotTag.hasKey("Slot", Constants.NBT.TAG_BYTE)) {
							int slot = slotTag.getByte("Slot") & 0xFF;
							if (slot >= 0 && slot < getSizeInventory()) {
								setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(slotTag));
							}
						}
					}
				}
			}
		}
	}

	public void writeToNBT() {
		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack stack = getStackInSlot(i);
			if (stack != null && stack.stackSize > 0) {
				NBTTagCompound slotTag = new NBTTagCompound();
				slotTag.setByte("Slot", (byte) (i & 0xFF));
				stack.writeToNBT(slotTag);
				itemList.appendTag(slotTag);
			}
		}
		NBTTagCompound inventoryTag = new NBTTagCompound();
		inventoryTag.setTag("Items", itemList);
		if (stack != null) {
			if (stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setTag("Inventory", inventoryTag);
		}
	}

	public ItemStack getStack() {
		return stack;
	}
}
