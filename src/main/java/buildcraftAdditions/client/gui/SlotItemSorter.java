package buildcraftAdditions.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import buildcraft.core.ItemList;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class SlotItemSorter extends SlotPhantom {

	public SlotItemSorter(IInventory inv, int slot, int x, int y) {
		super(inv, slot, x, y);
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return getHasStack() && getStack().getItem() instanceof ItemList;
	}

	@Override
	public ItemStack onLeftClick(EntityPlayer player) {
		ItemStack playerStack = player.inventory.getItemStack();
		if (playerStack != null && playerStack.getItem() instanceof ItemList) {
			putStack(playerStack.copy());
			player.inventory.setItemStack(null);
			onSlotChanged();
			return null;
		}
		if (getHasStack() && getStack().getItem() instanceof ItemList) {
			if (playerStack == null) {
				player.inventory.setItemStack(getStack().copy());
				putStack(null);
			} else {
				ItemStack tempStack = getStack();
				putStack(playerStack.copy());
				player.inventory.setItemStack(tempStack.copy());
			}
			onSlotChanged();
			return null;
		}
		return super.onLeftClick(player);
	}

	@Override
	public ItemStack onRightClick(EntityPlayer player) {
		ItemStack playerStack = player.inventory.getItemStack();
		if (getHasStack() && getStack().getItem() instanceof ItemList) {
			if (playerStack == null)
				player.inventory.setItemStack(getStack().copy());
			else
				player.inventory.addItemStackToInventory(getStack().copy());
			putStack(null);
			onSlotChanged();
		}
		return super.onRightClick(player);
	}
}
