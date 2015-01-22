package buildcraftAdditions.client.gui.containers;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import cpw.mods.fml.common.network.NetworkRegistry;

import buildcraftAdditions.networking.MessageByteBuff;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;

public class ContainerFluidicCompressor extends ContainerBase<TileFluidicCompressor> {

	public ContainerFluidicCompressor(InventoryPlayer inventoryPlayer, TileFluidicCompressor tile) {
		super(inventoryPlayer, tile);
		PacketHandler.instance.sendToAllAround(new MessageByteBuff(tile), new NetworkRegistry.TargetPoint(tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord, 5));
		addSlotToContainer(new Slot(tile, 0, 89, 31));
		addSlotToContainer(new Slot(tile, 1, 126, 35));
		addPlayerInventory(8, 87);
	}

}
