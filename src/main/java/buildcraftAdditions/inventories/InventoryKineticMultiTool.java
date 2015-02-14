package buildcraftAdditions.inventories;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import net.minecraftforge.common.util.Constants;

import cofh.api.energy.IEnergyContainerItem;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class InventoryKineticMultiTool extends InventoryBasic {

	private final ItemStack tool;

	public InventoryKineticMultiTool(ItemStack tool) {
		super("gui.kineticMultiTool", false, 3);
		this.tool = tool;
		readFromNBT();
	}

	public void readFromNBT() {
		if (tool != null && tool.stackTagCompound != null && tool.stackTagCompound.hasKey("Inventory", Constants.NBT.TAG_COMPOUND)) {
			NBTTagCompound inventoryTag = tool.stackTagCompound.getCompoundTag("Inventory");
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
		if (tool != null) {
			if (tool.stackTagCompound == null)
				tool.stackTagCompound = new NBTTagCompound();
			tool.stackTagCompound.setTag("Inventory", inventoryTag);
		}
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem;
	}

	public ItemStack getTool() {
		return tool;
	}
}
