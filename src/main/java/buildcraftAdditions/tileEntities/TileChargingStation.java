package buildcraftAdditions.tileEntities;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.FMLCommonHandler;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.tileEntities.Bases.TileMachineBase;

public class TileChargingStation extends TileMachineBase implements IInventory {

	private final CustomInventory inventory = new CustomInventory("ChargingStation",1 , 1, this);

	public TileChargingStation() {
		super (1000);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		int charge = 6000;
		if (charge > energy)
			charge = energy;
		if (charge > 0) {
			if (getRequiredEnergy() > 0) {
				ItemStack stack = getStackInSlot(0);
				IEnergyContainerItem containerItem = (IEnergyContainerItem) stack.getItem();
				energy -= containerItem.receiveEnergy(stack, charge, false);
				setInventorySlotContents(0, stack);
			}
		}
	}

	public int getRequiredEnergy() {
		ItemStack stack = getStackInSlot(0);
		if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
			IEnergyContainerItem containerItem = (IEnergyContainerItem) stack.getItem();
			return containerItem.getMaxEnergyStored(stack) - containerItem.getEnergyStored(stack);
		}

		return 0;
	}

	@Override
	public boolean canUpdate() {
		return !FMLCommonHandler.instance().getEffectiveSide().isClient();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		inventory.readNBT(nbtTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		inventory.writeNBT(nbtTagCompound);
	}

	@Override
	public int getSizeInventory() {
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return inventory.getStackInSlot(var1);
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		return inventory.decrStackSize(var1, var2);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return inventory.getStackInSlotOnClosing(var1);
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		inventory.setInventorySlotContents(var1, var2);
	}

	@Override
	public String getInventoryName() {
		return inventory.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return inventory.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && var1.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack.getItem() instanceof IEnergyContainerItem;
	}

	public double getProgress() {
		ItemStack stack = inventory.getStackInSlot(0);
		if (stack != null)
			if (stack.getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem battery = (IEnergyContainerItem) stack.getItem();
				return battery.getEnergyStored(stack) / battery.getMaxEnergyStored(stack);
			}
		return 0;
	}
}
