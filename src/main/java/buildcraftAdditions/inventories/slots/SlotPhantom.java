package buildcraftAdditions.inventories.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class SlotPhantom extends Slot {

	public SlotPhantom(IInventory inv, int slot, int x, int y) {
		super(inv, slot, x, y);
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}

	public ItemStack onLeftClick(EntityPlayer player) {
		ItemStack playerStack = player.inventory.getItemStack();
		if (playerStack != null && isItemValid(playerStack)) {
			ItemStack phantomStack = playerStack.copy();
			phantomStack.stackSize = 1;
			putStack(phantomStack);
			onSlotChanged();
		}
		return null;
	}

	public ItemStack onRightClick(EntityPlayer player) {
		if (getHasStack()) {
			putStack(null);
			onSlotChanged();
		}
		return null;
	}

	public ItemStack onMiddleClick(EntityPlayer player) {
		return null;
	}

	public ItemStack onClick(int mouseButton, EntityPlayer player) {
		switch (mouseButton) {
			case 0:
				return onLeftClick(player);
			case 1:
				return onRightClick(player);
			case 2:
				return onMiddleClick(player);
			default:
				return null;
		}
	}
}
