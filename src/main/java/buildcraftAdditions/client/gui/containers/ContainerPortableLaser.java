package buildcraftAdditions.client.gui.containers;

import net.minecraft.entity.player.InventoryPlayer;

import buildcraftAdditions.client.gui.SlotBattery;
import buildcraftAdditions.inventories.InventoryPortableLaser;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerPortableLaser extends ContainerBase<InventoryPortableLaser> {

	public ContainerPortableLaser(InventoryPlayer inventoryPlayer, InventoryPortableLaser inventory) {
		super(inventoryPlayer, inventory);
		addSlotToContainer(new SlotBattery(inventory, 0, 60, 29));
		addSlotToContainer(new SlotBattery(inventory, 1, 78, 29));
		addSlotToContainer(new SlotBattery(inventory, 2, 96, 29));
		addPlayerInventory(8, 71);
	}

}
