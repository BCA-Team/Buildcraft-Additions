package buildcraftAdditions.compat.eureka.drophandlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import buildcraftAdditions.inventories.InventoryPortableLaser;
import buildcraftAdditions.items.Tools.ItemPortableLaser;
import buildcraftAdditions.reference.ItemLoader;

import eureka.api.IDropHandler;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class PortableLaserDropHandler implements IDropHandler {
	@Override
	public boolean handles(ItemStack stack) {
		return stack != null && stack.getItem() instanceof ItemPortableLaser;
	}

	@Override
	public List<ItemStack> getDrops(ItemStack stack) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(Blocks.glass));
		list.add(new ItemStack(Items.diamond));
		list.add(new ItemStack(ItemLoader.blazeStick, 2));
		InventoryPortableLaser inv = new InventoryPortableLaser(stack);
		for (int t = 0; t < inv.getSizeInventory(); t++) {
			list.add(inv.getStackInSlot(t));
		}
		return list;
	}
}
