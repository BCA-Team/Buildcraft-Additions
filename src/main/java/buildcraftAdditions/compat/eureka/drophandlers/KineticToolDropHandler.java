package buildcraftAdditions.compat.eureka.drophandlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import buildcraftAdditions.inventories.InventoryKineticMultiTool;
import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.reference.ItemLoader;

import eureka.api.IDropHandler;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class KineticToolDropHandler implements IDropHandler {
	@Override
	public boolean handles(ItemStack stack) {
		return stack != null && stack.getItem() instanceof ItemKineticMultiTool;
	}

	@Override
	public List<ItemStack> getDrops(ItemStack stack) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(Items.diamond, 3));
		list.add(new ItemStack(ItemLoader.ironStick));
		list.add(new ItemStack(ItemLoader.toolCore));
		InventoryKineticMultiTool inventory = new InventoryKineticMultiTool(stack);
		for (int t = 0; t < inventory.getSizeInventory(); t++) {
			if (inventory.getStackInSlot(t) != null)
				list.add(inventory.getStackInSlot(t));
		}
		list.addAll(ItemKineticMultiTool.getInstalledUpgrades(stack));
		return list;
	}
}
