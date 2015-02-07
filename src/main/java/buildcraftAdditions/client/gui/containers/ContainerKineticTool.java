package buildcraftAdditions.client.gui.containers;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import buildcraftAdditions.items.BatteryBase;
import buildcraftAdditions.items.Tools.ItemKineticTool;

public class ContainerKineticTool extends ContainerBase<IInventory> {

	private final ItemKineticTool tool;
	private final ItemStack stack;
	private final EntityPlayer player;

	public ContainerKineticTool(InventoryPlayer inventoryPlayer, ItemKineticTool tool, IInventory toolInventory, ItemStack stack, EntityPlayer player) {
		super(inventoryPlayer, toolInventory);
		this.tool = tool;
		this.stack = stack;
		this.player = player;

		toolInventory.openInventory();
		addSlotToContainer(new Slot(toolInventory, 0, 60, 29));
		addSlotToContainer(new Slot(toolInventory, 1, 78, 29));
		addSlotToContainer(new Slot(toolInventory, 2, 96, 29));
		addPlayerInventory(8, 71);
	}


	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {

		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			stack = itemstack1.copy();

			if (slotIndex < 3) {
				if (!mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 0, 3, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == stack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return stack;
	}

	@Override
	public boolean mergeItemStack(ItemStack inputStack, int par2, int par3, boolean doMerge) {
		if (!(inputStack.getItem() instanceof BatteryBase))
			return false;
		boolean merged = false;
		int k = par2;

		if (doMerge) {
			k = par3 - 1;
		}

		Slot slot;
		ItemStack itemstack1;

		if (inputStack.isStackable()) {
			while (inputStack.stackSize > 0 && (!doMerge && k < par3 || doMerge && k >= par2)) {
				slot = (Slot) inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 != null && itemstack1.getItem() == inputStack.getItem()
						&& (!inputStack.getHasSubtypes() || inputStack.getItemDamage() == itemstack1.getItemDamage())
						&& ItemStack.areItemStackTagsEqual(inputStack, itemstack1)) {
					int l = itemstack1.stackSize + inputStack.stackSize;

					if (l <= inputStack.getMaxStackSize()) {
						inputStack.stackSize = 0;
						itemstack1.stackSize = l;
						slot.onSlotChanged();
						merged = true;
					} else if (itemstack1.stackSize < inputStack.getMaxStackSize()) {
						inputStack.stackSize -= inputStack.getMaxStackSize() - itemstack1.stackSize;
						itemstack1.stackSize = inputStack.getMaxStackSize();
						slot.onSlotChanged();
						merged = true;
					}
				}

				if (doMerge) {
					--k;
				} else {
					++k;
				}
			}
		}

		if (inputStack.stackSize > 0) {
			if (doMerge) {
				k = par3 - 1;
			} else {
				k = par2;
			}

			while (!doMerge && k < par3 || doMerge && k >= par2) {
				slot = (Slot) inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 == null) {
					slot.putStack(inputStack.copy());
					slot.onSlotChanged();
					inputStack.stackSize = 0;
					merged = true;
					break;
				}

				if (doMerge) {
					--k;
				} else {
					++k;
				}
			}
		}

		return merged;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		tool.readBateries(stack);
	}

}
