package buildcraftAdditions.client.gui.containers;

import net.minecraft.entity.player.InventoryPlayer;

import buildcraftAdditions.client.gui.SlotItemSorter;
import buildcraftAdditions.client.gui.SlotPhantom;
import buildcraftAdditions.tileEntities.TileItemSorter;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerItemSorter extends ContainerBase<TileItemSorter> {

	public ContainerItemSorter(InventoryPlayer inventoryPlayer, TileItemSorter tile) {
		super(inventoryPlayer, tile);
		for (int i = 0; i < 8; i++)
			addSorterSlotColumn(26 + i * 18, 18, 1 + i * 6);
		addPlayerInventory(8, 160);
		setCanShift(false);
	}

	public void addSorterSlotColumn(int x, int y, int id) {
		for (int i = 0; i < 6; i++) {
			addSlotToContainer(new SlotItemSorter(inventory, id, x, y + i * 18));
			id++;
		}
	}
}
