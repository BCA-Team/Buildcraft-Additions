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

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.IFluidContainerItem;

import buildcraftAdditions.inventories.slots.SlotOutput;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;

public class ContainerFluidicCompressor extends ContainerBase<TileFluidicCompressor> {

	public ContainerFluidicCompressor(InventoryPlayer inventoryPlayer, TileFluidicCompressor tile) {
		super(inventoryPlayer, tile);
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
