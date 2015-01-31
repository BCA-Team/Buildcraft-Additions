package buildcraftAdditions.tileEntities;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.reference.enums.EnumMachineUpgrades;
import buildcraftAdditions.tileEntities.Bases.TileBase;
import buildcraftAdditions.tileEntities.Bases.TileCoilBase;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;
import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.Utils;

import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileHeatedFurnace extends TileBase implements ISidedInventory, IUpgradableMachine {
	private final CustomInventory inventory = new CustomInventory("HeatedFurnace", 2, 64, this);
	public int progress;
	public boolean isCooking, shouldUpdateCoils;
	public TileCoilBase[] coils;

	public TileHeatedFurnace() {
		progress = 0;
		isCooking = false;
		coils = new TileCoilBase[6];
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote)
			return;
		if (shouldUpdateCoils) {
			updateCoils();
			shouldUpdateCoils = false;
		}
		if (canCook()) {
			if (!isCooking) {
				for (TileCoilBase coil : coils) {
					if (coil != null) {
						coil.startHeating();
						if (coil.isBurning()) {
							isCooking = true;
							doBlockUpdate();
						}
					}
				}
			}
			if (progress > 0)
				isCooking = true;
			if (progress >= ConfigurationHandler.heatedFurnaceHeatRequired) {
				ItemStack inputStack = getStackInSlot(0);
				ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(inputStack);
				if (getStackInSlot(1) == null) {
					setInventorySlotContents(1, result.copy());
				} else {
					getStackInSlot(1).stackSize += result.stackSize;
				}
				if (getStackInSlot(0).stackSize <= 1)
					setInventorySlotContents(0, null);
				else
				getStackInSlot(0).stackSize--;
				progress = 0;
			} else {
				for (TileCoilBase coil : coils)
					if (coil != null) {
						progress += coil.getIncrement();
					}
			}
		} else {
			stop();
		}
	}

	public void stop() {
		isCooking = false;
		doBlockUpdate();
		progress = 0;
		for (TileCoilBase coil : coils) {
			if (coil != null)
				coil.stopHeating();
		}
	}

	public void doBlockUpdate() {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public boolean canCook() {
		ItemStack stack0 = getStackInSlot(0);
		ItemStack stack1 = getStackInSlot(1);
		if (stack0 == null || getResult(stack0) == null)
			return false;
		ItemStack result = getResult(stack0);
		return stack1 == null || (result.getItem() == stack1.getItem() && result.stackSize + stack1.stackSize <= result.getMaxStackSize());
	}

	public ItemStack getResult(ItemStack stack) {
		return FurnaceRecipes.smelting().getSmeltingResult(stack);
	}

	public void updateCoils() {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			Location location = new Location(worldObj, xCoord, yCoord, zCoord).move(direction);
			TileEntity entity = location.getTileEntity();
			if (entity instanceof TileCoilBase)
				coils[direction.ordinal()] = (TileCoilBase) entity;
		}
		isCooking = false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		inventory.readNBT(nbtTagCompound);
		progress = nbtTagCompound.getInteger("progress");
		isCooking = nbtTagCompound.getBoolean("isCooking");
		shouldUpdateCoils = true;

	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		inventory.writeNBT(nbtTagCompound);
		nbtTagCompound.setInteger("progress", progress);
		nbtTagCompound.setBoolean("isCooking", isCooking);
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
		return inventory.getStackInSlot(slot);
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
		return getResult(stack) != null && slot == 0;
	}

	public int getScaledProgress() {
		return (progress * 23) / ConfigurationHandler.heatedFurnaceHeatRequired + 1;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return Utils.createSlotArray(0, 2);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return slot == 0;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 1;
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf.writeInt(progress);
		return null;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		progress = buf.readInt();
		return null;
	}

	@Override
	public boolean canAcceptUpgrade(EnumMachineUpgrades upgrades) {
		return false;
	}

	@Override
	public void installUpgrade(EnumMachineUpgrades upgrade) {

	}

	@Override
	public List<EnumMachineUpgrades> getIntalledUpgrades() {
		return null;
	}
}
