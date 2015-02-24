package buildcraftAdditions.inventories;

import net.minecraft.item.ItemStack;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.items.Tools.ItemPortableLaser;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class InventoryKineticMultiTool extends InventoryItem {

	public InventoryKineticMultiTool(ItemStack stack) {
		super(stack, "gui.kineticMultiTool", 3);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem && !(stack.getItem() instanceof ItemKineticMultiTool) && !(stack.getItem() instanceof ItemPortableLaser);
	}

}
