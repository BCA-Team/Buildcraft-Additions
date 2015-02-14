package buildcraftAdditions.client.gui;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.inventories.InventoryKineticMultiTool;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class SlotKineticMultiTool extends Slot {

	public SlotKineticMultiTool(InventoryKineticMultiTool inventoryKineticMultiTool, int id, int x, int y) {
		super(inventoryKineticMultiTool, id, x, y);
	}

	@Override
	public void onSlotChange(ItemStack stack1, ItemStack stack2) {
		super.onSlotChange(stack1, stack2);
		save();
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		save();
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem;
	}

	private void save() {
		if (inventory != null && inventory instanceof InventoryKineticMultiTool)
			((InventoryKineticMultiTool) inventory).writeToNBT();
	}
}
