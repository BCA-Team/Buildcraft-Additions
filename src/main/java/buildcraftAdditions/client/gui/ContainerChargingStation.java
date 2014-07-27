package buildcraftAdditions.client.gui;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraft.core.gui.BuildCraftContainer;
import buildcraft.core.gui.slots.SlotValidated;
import buildcraftAdditions.tileEntities.TileChargingStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerChargingStation extends BuildCraftContainer{
	IInventory playerIInventory;
	TileChargingStation chargingStation;

	public ContainerChargingStation(InventoryPlayer inventory, TileChargingStation tile) {
		super(tile.getSizeInventory());
		playerIInventory = inventory;
		chargingStation = tile;
		
		this.addSlotToContainer(new SlotValidated(tile, 0, 84, 28));
		
		for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 71 + inventoryRowIndex * 18));
            }
        }
		for (int hotbbarIndex = 0; hotbbarIndex < 9; ++hotbbarIndex)
        {
            this.addSlotToContainer(new Slot(inventory, hotbbarIndex, 8 + hotbbarIndex * 18, 129));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return chargingStation.isUseableByPlayer(var1);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		chargingStation.sendNetworkUpdate();
	}

}
