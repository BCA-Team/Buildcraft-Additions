package buildcraftAdditions.entities;

import buildcraft.core.TileBuildCraft;
import buildcraftAdditions.Inventories.CustomInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileHeatedFurnace extends TileBuildCraft implements IInventory {
    private final CustomInventory inventory = new CustomInventory("HeatedFurnace", 2, 64);
    public int progress;
    public boolean isCooking;
    public TileCoilBase[] coils;

    public TileHeatedFurnace(){
        progress = 0;
        isCooking = false;
        coils = new TileCoilBase[6];
    }

    @Override
    public void updateEntity(){
        if (canCook()) {
            isCooking = true;
            if (progress >= 10000) {
                ItemStack inputStack = getStackInSlot(0);
                ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(inputStack);
                if (getStackInSlot(1) == null){
                    setInventorySlotContents(1, result.copy());
                } else{
                    getStackInSlot(1).stackSize += result.stackSize;
                }
                getStackInSlot(0).stackSize--;
                if (getStackInSlot(0).stackSize == 0)
                    setInventorySlotContents(0, null);
                progress = 0;
            } else {
                for (TileCoilBase coil: coils)
                    if(coil != null)
                        progress += coil.getHeat();
            }
        } else {
            isCooking = false;
            progress = 0;
        }
    }

    public boolean canCook(){
        ItemStack stack0 = getStackInSlot(0);
        ItemStack stack1 = getStackInSlot(1);
        if (stack0 == null)
            return false;
        ItemStack result = getResult(stack0);
        return stack1 == null || (result.getItem() == stack1.getItem() && result.stackSize + stack1.stackSize <= result.getMaxStackSize());
    }

    public ItemStack getResult(ItemStack stack){
        return FurnaceRecipes.smelting().getSmeltingResult(stack);
    }

    public void updateCoils(){
        TileEntity entity = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
        if (entity instanceof TileCoilBase) {
            coils[0] = (TileCoilBase) entity;
        } else {
            coils[0] = null;
        }
        entity = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
        if (entity instanceof TileCoilBase) {
            coils[1] = (TileCoilBase) entity;
        } else {
            coils[1] = null;
        }
        entity = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
        if (entity instanceof TileCoilBase) {
            coils[2] = (TileCoilBase) entity;
        } else {
            coils[2] = null;
        }
        entity = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
        if (entity instanceof TileCoilBase) {
            coils[3] = (TileCoilBase) entity;
        } else {
            coils[3] = null;
        }
        entity = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
        if (entity instanceof TileCoilBase) {
            coils[4] = (TileCoilBase) entity;
        } else {
            coils[4] = null;
        }
        entity = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
        if (entity instanceof TileCoilBase) {
            coils[5] = (TileCoilBase) entity;
        } else {
            coils[5] = null;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        NBTTagCompound inventoryTag = new NBTTagCompound();
        inventory.writeNBT(inventoryTag);
        tag.setTag("Inventory", inventoryTag);
        tag.setInteger("progress", progress);
        tag.setBoolean("isCooking", isCooking);
        for (int t=0; t < 6; t++)
            tag.setTag("coil" + t, writeCoilToNBT(coils[t]));
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        inventory.readNBT(tag.getCompoundTag("Inventory"));
        progress = tag.getInteger("progress");
        isCooking = tag.getBoolean("isCooking");
        for (int t=0; t < 6; t++)
            coils[t] = readCoilFromNBT(tag.getCompoundTag("coil" + t));
    }

    public NBTTagCompound writeCoilToNBT(TileCoilBase coil){
        NBTTagCompound tag = new NBTTagCompound();
        if (coil == null){
            tag.setBoolean("validCoil", false);
        } else{
            tag.setBoolean("validCoil", true);
            tag.setInteger("x", coil.xCoord);
            tag.setInteger("y", coil.yCoord);
            tag.setInteger("z", coil.zCoord);
        }
        return tag;
    }

    public TileCoilBase readCoilFromNBT(NBTTagCompound tag){
        if (!tag.getBoolean("validCoil"))
            return null;
        return (TileCoilBase) worldObj.getTileEntity(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
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
        return getResult(stack) != null;
    }
}
