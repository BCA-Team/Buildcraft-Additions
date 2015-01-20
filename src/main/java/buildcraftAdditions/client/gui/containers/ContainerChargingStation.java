package buildcraftAdditions.client.gui.containers;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import buildcraftAdditions.tileEntities.TileChargingStation;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerChargingStation extends ContainerBase {

	private final TileChargingStation chargingStation;

	public ContainerChargingStation(InventoryPlayer inventory, TileChargingStation tile) {
		super();
		chargingStation = tile;

		addSlotToContainer(new Slot(tile, 0, 80, 30));

		addPlayerInventory(inventory, 8, 71);
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return chargingStation.isUseableByPlayer(var1);
	}


}
