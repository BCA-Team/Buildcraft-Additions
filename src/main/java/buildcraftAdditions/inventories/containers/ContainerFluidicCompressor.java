package buildcraftAdditions.inventories.containers;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.network.NetworkRegistry;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.IFluidContainerItem;

import buildcraftAdditions.api.networking.MessageByteBuff;
import buildcraftAdditions.inventories.slots.SlotOutput;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;

public class ContainerFluidicCompressor extends ContainerBase<TileFluidicCompressor> {

	public ContainerFluidicCompressor(InventoryPlayer inventoryPlayer, TileFluidicCompressor tile) {
		super(inventoryPlayer, tile);
		PacketHandler.instance.sendToAllAround(new MessageByteBuff(tile), new NetworkRegistry.TargetPoint(tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord, Variables.NETWORK_RANGE));
		addSlotToContainer(new Slot(tile, 0, 89, 39) {

			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack != null && stack.getItem() != null && (stack.getItem() instanceof IFluidContainerItem || FluidContainerRegistry.isContainer(stack));
			}

			@Override
			public int getSlotStackLimit() {
				return 1;
			}
		});
		addSlotToContainer(new SlotOutput(tile, 1, 126, 43));
		addPlayerInventory(8, 103);
	}

}
