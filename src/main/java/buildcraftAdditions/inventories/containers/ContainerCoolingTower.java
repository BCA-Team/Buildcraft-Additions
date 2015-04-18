package buildcraftAdditions.inventories.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.tileEntities.TileCoolingTower;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerCoolingTower extends ContainerBase<TileCoolingTower> {

	private int heat, fluidIDInput, fluidAmountInput, fluidIDOutput, fluidAmountOutput, fluidIDCoolant, fluidAmountCoolant;

	public ContainerCoolingTower(InventoryPlayer inventoryPlayer, TileCoolingTower tile) {
		super(inventoryPlayer, tile);
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, MathHelper.floor_float(inventory.heat * 32));
		crafting.sendProgressBarUpdate(this, 1, inventory.input.getFluidAmount() > 0 ? inventory.input.getFluid().getFluidID() : -1);
		crafting.sendProgressBarUpdate(this, 2, inventory.input.getFluidAmount());
		crafting.sendProgressBarUpdate(this, 3, inventory.output.getFluidAmount() > 0 ? inventory.output.getFluid().getFluidID() : -1);
		crafting.sendProgressBarUpdate(this, 4, inventory.output.getFluidAmount());
		crafting.sendProgressBarUpdate(this, 5, inventory.coolant.getFluidAmount() > 0 ? inventory.coolant.getFluid().getFluidID() : -1);
		crafting.sendProgressBarUpdate(this, 6, inventory.coolant.getFluidAmount());
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		super.detectAndSendChanges();
		if (crafters != null) {
			for (Object o : crafters) {
				if (o != null && o instanceof ICrafting) {
					ICrafting crafting = (ICrafting) o;
					if (heat != MathHelper.floor_float(inventory.heat * 32))
						crafting.sendProgressBarUpdate(this, 0, MathHelper.floor_float(inventory.heat * 32));
					if (fluidIDInput != (inventory.input.getFluidAmount() > 0 ? inventory.input.getFluid().getFluidID() : -1))
						crafting.sendProgressBarUpdate(this, 1, inventory.input.getFluidAmount() > 0 ? inventory.input.getFluid().getFluidID() : -1);
					if (fluidAmountInput != inventory.input.getFluidAmount())
						crafting.sendProgressBarUpdate(this, 2, inventory.input.getFluidAmount());
					if (fluidIDOutput != (inventory.output.getFluidAmount() > 0 ? inventory.output.getFluid().getFluidID() : -1))
						crafting.sendProgressBarUpdate(this, 3, inventory.output.getFluidAmount() > 0 ? inventory.output.getFluid().getFluidID() : -1);
					if (fluidAmountOutput != inventory.output.getFluidAmount())
						crafting.sendProgressBarUpdate(this, 4, inventory.output.getFluidAmount());
					if (fluidIDCoolant != (inventory.coolant.getFluidAmount() > 0 ? inventory.coolant.getFluid().getFluidID() : -1))
						crafting.sendProgressBarUpdate(this, 5, inventory.coolant.getFluidAmount() > 0 ? inventory.coolant.getFluid().getFluidID() : -1);
					if (fluidAmountCoolant != inventory.coolant.getFluidAmount())
						crafting.sendProgressBarUpdate(this, 6, inventory.coolant.getFluidAmount());
				}
			}
		}
		heat = MathHelper.floor_float(inventory.heat * 32);
		fluidIDInput = inventory.input.getFluidAmount() > 0 ? inventory.input.getFluid().getFluidID() : -1;
		fluidAmountInput = inventory.input.getFluidAmount();
		fluidIDOutput = inventory.output.getFluidAmount() > 0 ? inventory.output.getFluid().getFluidID() : -1;
		fluidAmountOutput = inventory.output.getFluidAmount();
		fluidIDCoolant = inventory.coolant.getFluidAmount() > 0 ? inventory.coolant.getFluid().getFluidID() : -1;
		fluidAmountCoolant = inventory.coolant.getFluidAmount();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		super.updateProgressBar(id, value);
		switch (id) {
			case 0:
				inventory.heat = value / 32F;
				break;
			case 1:
				if (value >= 0)
					inventory.input.setFluid(new FluidStack(FluidRegistry.getFluid(value), inventory.input.getFluidAmount()));
				else
					inventory.input.setFluid(null);
				break;
			case 2:
				if (value > 0 && inventory.input.getFluid() != null)
					inventory.input.setFluid(new FluidStack(inventory.input.getFluid(), value));
				else
					inventory.input.setFluid(null);
				break;
			case 3:
				if (value >= 0)
					inventory.output.setFluid(new FluidStack(FluidRegistry.getFluid(value), inventory.output.getFluidAmount()));
				else
					inventory.output.setFluid(null);
				break;
			case 4:
				if (value > 0 && inventory.output.getFluid() != null)
					inventory.output.setFluid(new FluidStack(inventory.output.getFluid(), value));
				else
					inventory.output.setFluid(null);
				break;
			case 5:
				if (value >= 0)
					inventory.coolant.setFluid(new FluidStack(FluidRegistry.getFluid(value), inventory.coolant.getFluidAmount()));
				else
					inventory.coolant.setFluid(null);
				break;
			case 6:
				if (value > 0 && inventory.coolant.getFluid() != null)
					inventory.coolant.setFluid(new FluidStack(inventory.coolant.getFluid(), value));
				else
					inventory.coolant.setFluid(null);
				break;
			default:
				break;
		}
	}

}
