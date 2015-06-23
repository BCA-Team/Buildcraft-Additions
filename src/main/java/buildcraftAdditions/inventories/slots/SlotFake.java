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
public class SlotFake extends Slot {

	public SlotFake(IInventory inventory, int x, int y, int id) {
		super(inventory, x, y, id);
	}

	@Override
	public void onSlotChange(ItemStack stack, ItemStack stack2) {

	}

	@Override
	protected void onCrafting(ItemStack stack, int stack2) {

	}

	@Override
	protected void onCrafting(ItemStack stack) {

	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {

	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack getStack() {
		return null;
	}

	@Override
	public boolean getHasStack() {
		return false;
	}

	@Override
	public void putStack(ItemStack stack) {

	}

	@Override
	public void onSlotChanged() {

	}

	@Override
	public int getSlotStackLimit() {
		return 64;
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		return null;
	}

	@Override
	public boolean isSlotInInventory(IInventory inventory, int slot) {
		return true;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}


	@Override
	public int getSlotIndex() {
		return 0;
	}
}
