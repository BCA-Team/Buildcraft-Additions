package buildcraftAdditions.entities;

import buildcraft.core.TileBuildCraft;
import buildcraftAdditions.Inventories.CustomInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileHeatedFurnace extends TileBuildCraft implements IInventory {
    private final CustomInventory inventory = new CustomInventory("HeatedFurnace", 2, 64);
    private int ticksToCook;

    public TileHeatedFurnace(){
        ticksToCook = 100;
    }

    @Override
    public void updateEntity(){
        if (inventory.getStackInSlot(0) != null) {
            if (ticksToCook == 0) {
                ItemStack inputStack = getStackInSlot(0);
                ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(inputStack);
                if (inventory.getStackInSlot(1) == null){
                    inventory.setInventorySlotContents(1, result.copy());
                } else{
                    inventory.getStackInSlot(1).stackSize += result.stackSize;
                }
                inventory.getStackInSlot(0).stackSize--;
                ticksToCook = 100;
            }
            ticksToCook--;
        }
    }


    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        NBTTagCompound inventoryTag = new NBTTagCompound();
        inventory.writeNBT(inventoryTag);
        tag.setTag("Inventory", inventoryTag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        inventory.readNBT(tag.getCompoundTag("Inventory"));
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
        inventory.openInventory();
    }

    @Override
    public void closeInventory() {
        inventory.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return FurnaceRecipes.smelting().getSmeltingResult(stack) != null;
    }
}
