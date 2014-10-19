package buildcraftAdditions.client.gui;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import buildcraftAdditions.tileEntities.TileChargingStation;

public class ContainerChargingStation extends Container {
	IInventory playerIInventory;
	TileChargingStation chargingStation;

	public ContainerChargingStation(InventoryPlayer inventory, TileChargingStation tile) {
		super();
		playerIInventory = inventory;
		chargingStation = tile;

		this.addSlotToContainer(new Slot(tile, 0, 84, 28));

		for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex) {
			for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex) {
				this.addSlotToContainer(new Slot(inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 71 + inventoryRowIndex * 18));
			}
		}
		for (int hotbbarIndex = 0; hotbbarIndex < 9; ++hotbbarIndex) {
			this.addSlotToContainer(new Slot(inventory, hotbbarIndex, 8 + hotbbarIndex * 18, 129));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return chargingStation.isUseableByPlayer(var1);
	}


}
