package buildcraftAdditions.inventories.containers;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import buildcraftAdditions.inventories.slots.SlotOutput;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;

public class ContainerFluidicCompressor extends ContainerBase<TileFluidicCompressor> {

	private boolean fill;
	private int fluidID, fluidAmount;

	public ContainerFluidicCompressor(InventoryPlayer inventoryPlayer, TileFluidicCompressor tile) {
		super(inventoryPlayer, tile);
		addSlotToContainer(new Slot(tile, 0, 89, 39) {

			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack != null && stack.getItem() != null && (stack.getItem() instanceof IFluidContainerItem || FluidContainerRegistry.isContainer(stack));
			}
		});
		addSlotToContainer(new SlotOutput(tile, 1, 126, 43));
		addPlayerInventory(8, 103);
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, inventory.fill ? 1 : 0);
		crafting.sendProgressBarUpdate(this, 1, inventory.tank.getFluidAmount() > 0 ? inventory.tank.getFluid().getFluidID() : -1);
		crafting.sendProgressBarUpdate(this, 2, inventory.tank.getFluidAmount());
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (crafters != null) {
			for (Object o : crafters) {
				if (o != null && o instanceof ICrafting) {
					ICrafting crafting = (ICrafting) o;
					if (fill != inventory.fill)
						crafting.sendProgressBarUpdate(this, 0, inventory.fill ? 1 : 0);
					if (fluidID != (inventory.tank.getFluidAmount() > 0 ? inventory.tank.getFluid().getFluidID() : -1))
						crafting.sendProgressBarUpdate(this, 1, inventory.tank.getFluidAmount() > 0 ? inventory.tank.getFluid().getFluidID() : -1);
					if (fluidAmount != inventory.tank.getFluidAmount())
						crafting.sendProgressBarUpdate(this, 2, inventory.tank.getFluidAmount());
				}
			}
		}
		fill = inventory.fill;
		fluidID = inventory.tank.getFluidAmount() > 0 ? inventory.tank.getFluid().getFluidID() : -1;
		fluidAmount = inventory.tank.getFluidAmount();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		super.updateProgressBar(id, value);
		switch (id) {
			case 0:
				inventory.fill = value != 0;
				break;
			case 1:
				if (value >= 0)
					inventory.tank.setFluid(new FluidStack(FluidRegistry.getFluid(value), inventory.tank.getFluidAmount()));
				else
					inventory.tank.setFluid(null);
				break;
			case 2:
				if (value > 0 && inventory.tank.getFluid() != null)
					inventory.tank.setFluid(new FluidStack(inventory.tank.getFluid(), value));
				else
					inventory.tank.setFluid(null);
				break;
			default:
				break;
		}
	}

}
