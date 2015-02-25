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
public class InventoryPortableLaser extends InventoryItem {

	public InventoryPortableLaser(ItemStack stack) {
		super(stack, "gui.portableLaser", 3);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem && !(stack.getItem() instanceof ItemPortableLaser) && !(stack.getItem() instanceof ItemKineticMultiTool);
	}

}
