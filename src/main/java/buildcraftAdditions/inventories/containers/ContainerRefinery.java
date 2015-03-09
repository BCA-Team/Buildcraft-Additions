package buildcraftAdditions.inventories.containers;

import net.minecraft.entity.player.InventoryPlayer;

import buildcraftAdditions.tileEntities.TileRefinery;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerRefinery extends ContainerBase<TileRefinery> {

	public ContainerRefinery(InventoryPlayer inventoryPlayer, TileRefinery tile) {
		super(inventoryPlayer, tile);
	}
}