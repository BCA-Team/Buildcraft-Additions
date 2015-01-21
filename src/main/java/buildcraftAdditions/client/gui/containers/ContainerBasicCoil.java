package buildcraftAdditions.client.gui.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import buildcraftAdditions.tileEntities.TileBasicCoil;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerBasicCoil extends ContainerBase {

	public ContainerBasicCoil(InventoryPlayer inventoryPlayer, TileBasicCoil tile) {
		super(inventoryPlayer, tile);
		addSlotToContainer(new Slot(tile, 0, 78, 43));
		addPlayerInventory(8, 84);
	}

}
