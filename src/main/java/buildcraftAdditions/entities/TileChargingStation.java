package buildcraftAdditions.entities;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraft.api.gates.IOverrideDefaultTriggers;
import buildcraft.api.gates.ITrigger;
import buildcraftAdditions.BuildcraftAdditions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import buildcraft.api.mj.MjBattery;
import buildcraft.core.TileBuildCraft;
import buildcraft.core.inventory.SimpleInventory;
import buildcraftAdditions.items.BatteryBase;

import java.util.LinkedList;

public class TileChargingStation extends TileBuildCraft implements IInventory, IOverrideDefaultTriggers {
	
	@MjBattery(maxCapacity=10000, maxReceivedPerCycle = 100)
	double energy = 0;
	private final SimpleInventory inventory = new SimpleInventory(1, "ChargingStation", 1);
	
	public TileChargingStation(){
	}
	
	@Override
	public void updateEntity() {
		int charge = 100;
		ItemStack stack = inventory.getStackInSlot(0);
		if (stack==null)
			return;
		if (!(stack.getItem() instanceof BatteryBase))
			return;
		BatteryBase battery = (BatteryBase) stack.getItem();
		if (battery.getEnergy(stack) + charge > battery.getCapacity())
			charge = battery.getCapacity() - (int) battery.getEnergy(stack);
		if (energy < charge)
			charge = (int) energy;
		battery.increaseEnergy(stack, charge);
		energy -= charge;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		inventory.readFromNBT(nbtTagCompound);
		energy = nbtTagCompound.getDouble("energy");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		inventory.writeToNBT(nbtTagCompound);
		nbtTagCompound.setDouble("energy", energy);
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
	public boolean isItemValidForSlot(int var1, ItemStack stack) {
		return stack.getItem() instanceof BatteryBase;
	}

	public double getProgress() {
		ItemStack stack = inventory.getStackInSlot(0);
		if (stack != null)
			if (stack.getItem() instanceof BatteryBase){
				BatteryBase battery = (BatteryBase) stack.getItem();
				return battery.getEnergy(stack) / battery.getCapacity();
			}
		return 0;
	}

	public int getToolEnergy() {
		ItemStack stack = inventory.getStackInSlot(0);
		if (stack != null)
			if (stack.getItem() instanceof BatteryBase)
				return (int) stack.stackTagCompound.getDouble("energy");
		return 0;
	}
	
	public int getToolMaxEnergy(){
		ItemStack stack = inventory.getStackInSlot(0);
		if (stack != null)
			if (stack.getItem() instanceof BatteryBase) {
				BatteryBase battery = (BatteryBase) stack.getItem();
				return battery.getCapacity();
			}
		return 0;
	}

    @Override
    public LinkedList<ITrigger> getTriggers() {
        LinkedList<ITrigger> list = new LinkedList<ITrigger>();
        list.add(BuildcraftAdditions.triggerDoneCharging);
        list.add(BuildcraftAdditions.triggerReadyToCharge);
        return list;
    }
}
