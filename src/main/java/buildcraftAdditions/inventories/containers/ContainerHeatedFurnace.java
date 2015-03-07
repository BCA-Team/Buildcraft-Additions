package buildcraftAdditions.inventories.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

import buildcraftAdditions.tileEntities.TileHeatedFurnace;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerHeatedFurnace extends ContainerBase<TileHeatedFurnace> {

	public ContainerHeatedFurnace(InventoryPlayer inventoryPlayer, TileHeatedFurnace tile) {
		super(inventoryPlayer, tile);
		addSlotToContainer(new Slot(tile, 0, 56, 34));
		addSlotToContainer(new SlotFurnace(inventoryPlayer.player, tile, 1, 116, 34));
		addPlayerInventory(8, 84);
	}

}
