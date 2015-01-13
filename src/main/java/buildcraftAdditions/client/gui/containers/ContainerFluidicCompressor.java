package buildcraftAdditions.client.gui.containers;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import cpw.mods.fml.common.network.NetworkRegistry;

import buildcraftAdditions.networking.MessageByteBuff;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;

public class ContainerFluidicCompressor extends ContainerBase {

	IInventory playerIInventory;
	TileFluidicCompressor fluidicCompressor;

	public ContainerFluidicCompressor(InventoryPlayer inventory, TileFluidicCompressor tile) {
		super();
		playerIInventory = inventory;
		fluidicCompressor = tile;
		PacketHandler.instance.sendToAllAround(new MessageByteBuff(fluidicCompressor), new NetworkRegistry.TargetPoint(fluidicCompressor.getWorldObj().provider.dimensionId, fluidicCompressor.xCoord, fluidicCompressor.yCoord, fluidicCompressor.zCoord, 5));

		this.addSlotToContainer(new Slot(tile, 0, 89, 31));
		this.addSlotToContainer(new Slot(tile, 1, 126, 35));

		for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex) {
			for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex) {
				this.addSlotToContainer(new Slot(inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
			}
		}
		for (int hotbbarIndex = 0; hotbbarIndex < 9; ++hotbbarIndex) {
			this.addSlotToContainer(new Slot(inventory, hotbbarIndex, 8 + hotbbarIndex * 18, 142));
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return fluidicCompressor.isUseableByPlayer(entityPlayer);
	}

}
