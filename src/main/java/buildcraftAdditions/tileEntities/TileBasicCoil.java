package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

import buildcraft.api.core.NetworkData;
import buildcraft.core.inventory.SimpleInventory;

import buildcraftAdditions.tileEntities.Bases.TileCoilBase;


/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileBasicCoil extends TileCoilBase implements IInventory {
	@NetworkData
	private final SimpleInventory inventory = new SimpleInventory(1, "BasicCoil", 64);

	public TileBasicCoil() {
		burnTime = 0;
		fullBurnTime = 0;
		shouldHeat = false;
		burning = false;
	}

	@Override
	public void updateEntity() {
		if (burnTime == 0) {
			burning = false;
			if (shouldHeat)
				burn();
		}
		if (burning)
			burnTime--;
		if (burning && increment < 16)
			increment++;
		if (!burning && increment > 0)
			increment--;
	}

	public void burn() {
		if (getStackInSlot(0) != null) {
			burnTime = getBurnTime(getStackInSlot(0));
			fullBurnTime = burnTime;
			decrStackSize(0, 1);
			burning = true;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		inventory.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		inventory.readFromNBT(tag);
	}

	@Override
	public int getSizeInventory() {
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return inventory.decrStackSize(slot, amount);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return inventory.getStackInSlotOnClosing(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory.setInventorySlotContents(slot, stack);
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
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return getBurnTime(stack) != 0;
	}

	public int getBurnTime(ItemStack stack) {
		return TileEntityFurnace.getItemBurnTime(stack) * 5;
	}

	public int getBurnIconHeight() {
		if (fullBurnTime == 0)
			return 0;
		return (burnTime * 16) / fullBurnTime;
	}
}
