package buildcraftAdditions.inventories.containers;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.GuiBase;
import buildcraftAdditions.inventories.slots.SlotPhantom;
import buildcraftAdditions.proxy.ClientProxy;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerBase<T> extends Container {

	protected final InventoryPlayer inventoryPlayer;
	protected final T inventory;
	private boolean canShift = true;

	public ContainerBase(InventoryPlayer inventoryPlayer, T inventory) {
		this.inventoryPlayer = inventoryPlayer;
		this.inventory = inventory;
	}

	private static boolean canStacksMerge(ItemStack stack1, ItemStack stack2) {
		return !(stack1 == null || stack2 == null) && stack1.isItemEqual(stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
	}

	public ContainerBase setCanShift(boolean canShift) {
		this.canShift = canShift;
		return this;
	}

	protected void addPlayerInventory(int x, int y) {
		if (inventoryPlayer != null) {
			for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
				for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex)
					addSlotToContainer(new Slot(inventoryPlayer, 9 + inventoryColumnIndex + inventoryRowIndex * 9, x + inventoryColumnIndex * 18, y + inventoryRowIndex * 18));
			for (int hotBarIndex = 0; hotBarIndex < 9; ++hotBarIndex)
				addSlotToContainer(new Slot(inventoryPlayer, hotBarIndex, 8 + hotBarIndex * 18, y + 58));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		int numSlots = inventorySlots.size();
		if (slotIndex < 0 || slotIndex >= numSlots)
			return null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);
		ItemStack originalStack = null;
		if (slot != null && slot.getHasStack()) {
			ItemStack stackInSlot = slot.getStack();
			originalStack = stackInSlot.copy();
			if (slotIndex >= numSlots - 9 * 4 && tryShiftItem(stackInSlot, numSlots)) {
				// NO-OP
			} else if (slotIndex >= numSlots - 9 * 4 && slotIndex < numSlots - 9) {
				if (!shiftItemStack(stackInSlot, numSlots - 9, numSlots)) {
					return null;
				}
			} else if (slotIndex >= numSlots - 9 && slotIndex < numSlots) {
				if (!shiftItemStack(stackInSlot, numSlots - 9 * 4, numSlots - 9)) {
					return null;
				}
			} else if (!shiftItemStack(stackInSlot, numSlots - 9 * 4, numSlots)) {
				return null;
			}
			slot.onSlotChange(stackInSlot, originalStack);
			if (stackInSlot.stackSize <= 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (stackInSlot.stackSize == originalStack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, stackInSlot);
		}
		return originalStack;
	}

	private boolean tryShiftItem(ItemStack stackToShift, int numSlots) {
		for (int machineIndex = 0; machineIndex < numSlots - 9 * 4; machineIndex++) {
			Slot slot = (Slot) inventorySlots.get(machineIndex);
			if (!slot.isItemValid(stackToShift)) {
				continue;
			}
			if (shiftItemStack(stackToShift, machineIndex, machineIndex + 1)) {
				return true;
			}
		}
		return false;
	}

	private boolean shiftItemStack(ItemStack stackToShift, int start, int end) {
		if (!canShift)
			return false;

		boolean changed = false;
		if (stackToShift.isStackable()) {
			for (int slotIndex = start; stackToShift.stackSize > 0 && slotIndex < end; slotIndex++) {
				Slot slot = (Slot) inventorySlots.get(slotIndex);
				ItemStack stackInSlot = slot.getStack();
				if (stackInSlot != null && canStacksMerge(stackInSlot, stackToShift)) {
					int resultingStackSize = stackInSlot.stackSize + stackToShift.stackSize;
					int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
					if (resultingStackSize <= max) {
						stackToShift.stackSize = 0;
						stackInSlot.stackSize = resultingStackSize;
						slot.onSlotChanged();
						changed = true;
					} else if (stackInSlot.stackSize < max) {
						stackToShift.stackSize -= max - stackInSlot.stackSize;
						stackInSlot.stackSize = max;
						slot.onSlotChanged();
						changed = true;
					}
				}
			}
		}
		if (stackToShift.stackSize > 0) {
			for (int slotIndex = start; stackToShift.stackSize > 0 && slotIndex < end; slotIndex++) {
				Slot slot = (Slot) inventorySlots.get(slotIndex);
				ItemStack stackInSlot = slot.getStack();
				if (stackInSlot == null) {
					int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
					stackInSlot = stackToShift.copy();
					stackInSlot.stackSize = Math.min(stackToShift.stackSize, max);
					stackToShift.stackSize -= stackInSlot.stackSize;
					slot.putStack(stackInSlot);
					slot.onSlotChanged();
					changed = true;
				}
			}
		}
		return changed;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return !(inventory instanceof IInventory) || ((IInventory) inventory).isUseableByPlayer(player);
	}

	@Override
	public ItemStack slotClick(int slotNum, int mouseButton, int modifier, EntityPlayer player) {
		if (slotNum < 0 || slotNum >= inventorySlots.size())
			return null;
		Slot slot = (Slot) inventorySlots.get(slotNum);
		if (slot instanceof SlotPhantom)
			return ((SlotPhantom) slot).onClick(mouseButton, player);
		return super.slotClick(slotNum, mouseButton, modifier, player);
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if (player.worldObj != null && !player.worldObj.isRemote && inventory instanceof IInventory)
			((IInventory) inventory).closeInventory();
	}

	@SideOnly(Side.CLIENT)
	protected void redrawOpenGui() {
		GuiScreen gui = FMLClientHandler.instance().getClient().currentScreen;
		if (gui instanceof GuiBase)
			((GuiBase) gui).redraw();
	}
}
