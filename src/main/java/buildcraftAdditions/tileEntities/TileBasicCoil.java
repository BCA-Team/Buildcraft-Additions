package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileCoilBase;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileBasicCoil extends TileCoilBase implements IInventory {
	private final CustomInventory inventory = new CustomInventory("BasicCoil", 1, 64, this);

	public TileBasicCoil() {
		super(Variables.SyncIDs.BASIC_COIL.ordinal());
		burnTime = 0;
		fullBurnTime = 0;
		shouldHeat = false;
		burning = false;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (burnTime == 0) {
			burning = false;
			if (shouldHeat)
				burn();
		}
		if (burning)
			burnTime--;
		if (burning && increment < ConfigurationHandler.basicCoilHeat)
			increment++;
		if (!burning && increment > 0)
			increment--;
	}

	public void burn() {
		ItemStack stack = getStackInSlot(0);
		if (stack != null && stack.getItem() != null && stack.stackSize > 0) {
			burnTime = getBurnTime(stack);
			fullBurnTime = burnTime;
			stack.stackSize--;
			if (stack.stackSize <= 0)
				setInventorySlotContents(0, stack.getItem().getContainerItem(stack));
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
		return (burnTime * ConfigurationHandler.basicCoilHeat) / fullBurnTime;
	}
}
