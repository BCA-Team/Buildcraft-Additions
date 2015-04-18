package buildcraftAdditions.inventories.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.tileEntities.TileRefinery;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerRefinery extends ContainerBase<TileRefinery> {

	private int currentHeat, requiredHeat, energyCost, fluidIDInput, fluidAmountInput, fluidIDOutput, fluidAmountOutput;

	public ContainerRefinery(InventoryPlayer inventoryPlayer, TileRefinery tile) {
		super(inventoryPlayer, tile);
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, inventory.currentHeat);
		crafting.sendProgressBarUpdate(this, 1, inventory.requiredHeat);
		crafting.sendProgressBarUpdate(this, 2, inventory.energyCost);
		crafting.sendProgressBarUpdate(this, 3, inventory.input.getFluidAmount() > 0 ? inventory.input.getFluid().getFluidID() : -1);
		crafting.sendProgressBarUpdate(this, 4, inventory.input.getFluidAmount());
		crafting.sendProgressBarUpdate(this, 5, inventory.output.getFluidAmount() > 0 ? inventory.output.getFluid().getFluidID() : -1);
		crafting.sendProgressBarUpdate(this, 6, inventory.output.getFluidAmount());
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (crafters != null) {
			for (Object o : crafters) {
				if (o != null && o instanceof ICrafting) {
					ICrafting crafting = (ICrafting) o;
					if (currentHeat != inventory.currentHeat)
						crafting.sendProgressBarUpdate(this, 0, inventory.currentHeat);
					if (requiredHeat != inventory.requiredHeat)
						crafting.sendProgressBarUpdate(this, 1, inventory.requiredHeat);
					if (energyCost != inventory.energyCost)
						crafting.sendProgressBarUpdate(this, 2, inventory.energyCost);
					if (fluidIDInput != (inventory.input.getFluidAmount() > 0 ? inventory.input.getFluid().getFluidID() : -1))
						crafting.sendProgressBarUpdate(this, 3, inventory.input.getFluidAmount() > 0 ? inventory.input.getFluid().getFluidID() : -1);
					if (fluidAmountInput != inventory.input.getFluidAmount())
						crafting.sendProgressBarUpdate(this, 4, inventory.input.getFluidAmount());
					if (fluidIDOutput != (inventory.output.getFluidAmount() > 0 ? inventory.output.getFluid().getFluidID() : -1))
						crafting.sendProgressBarUpdate(this, 5, inventory.output.getFluidAmount() > 0 ? inventory.output.getFluid().getFluidID() : -1);
					if (fluidAmountOutput != inventory.output.getFluidAmount())
						crafting.sendProgressBarUpdate(this, 6, inventory.output.getFluidAmount());
				}
			}
		}
		currentHeat = inventory.currentHeat;
		requiredHeat = inventory.requiredHeat;
		energyCost = inventory.energyCost;
		fluidIDInput = inventory.input.getFluidAmount() > 0 ? inventory.input.getFluid().getFluidID() : -1;
		fluidAmountInput = inventory.input.getFluidAmount();
		fluidIDOutput = inventory.output.getFluidAmount() > 0 ? inventory.output.getFluid().getFluidID() : -1;
		fluidAmountOutput = inventory.output.getFluidAmount();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		super.updateProgressBar(id, value);
		switch (id) {
			case 0:
				inventory.currentHeat = value;
				break;
			case 1:
				inventory.requiredHeat = value;
				break;
			case 2:
				inventory.energyCost = value;
				break;
			case 3:
				if (value >= 0)
					inventory.input.setFluid(new FluidStack(FluidRegistry.getFluid(value), inventory.input.getFluidAmount()));
				else
					inventory.input.setFluid(null);
				break;
			case 4:
				if (value > 0 && inventory.input.getFluid() != null)
					inventory.input.setFluid(new FluidStack(inventory.input.getFluid(), value));
				else
					inventory.input.setFluid(null);
				break;
			case 5:
				if (value >= 0)
					inventory.output.setFluid(new FluidStack(FluidRegistry.getFluid(value), inventory.output.getFluidAmount()));
				else
					inventory.output.setFluid(null);
				break;
			case 6:
				if (value > 0 && inventory.output.getFluid() != null)
					inventory.output.setFluid(new FluidStack(inventory.output.getFluid(), value));
				else
					inventory.output.setFluid(null);
				break;
			default:
				break;
		}
	}


}
