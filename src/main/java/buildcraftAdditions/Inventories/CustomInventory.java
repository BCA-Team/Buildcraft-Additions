package buildcraftAdditions.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the itemStacks of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class CustomInventory implements IInventory {

    private final ItemStack[] itemStacks;
    private final String name;
    private final int stackLimit;
    private TileEntity entity;

    public CustomInventory(String name, int slots, int stackLimit, TileEntity entity){
        this.name = name;
        this.stackLimit = stackLimit;
        itemStacks = new ItemStack[slots];
        this.entity = entity;
    }

    public void readNBT(NBTTagCompound tag){
        NBTTagList nbttaglist = tag.getTagList("ItemStacks", Constants.NBT.TAG_COMPOUND);
        for (int t = 0; t < nbttaglist.tagCount(); t++) {
            NBTTagCompound slot = nbttaglist.getCompoundTagAt(t);
            int index;
            if (slot.hasKey("index")) {
                index = slot.getInteger("index");
            } else {
                index = slot.getByte("Slot");
            }
            if (index >= 0 && index < itemStacks.length) {
                setInventorySlotContents(index, ItemStack.loadItemStackFromNBT(slot));
            }
        }
    }

    public void writeNBT(NBTTagCompound tag) {
        NBTTagList list = new NBTTagList();
        for (byte t = 0; t < itemStacks.length; t++) {
            if (itemStacks[t] != null && itemStacks[t].stackSize > 0) {
                NBTTagCompound slot = new NBTTagCompound();
                list.appendTag(slot);
                slot.setByte("Slot", t);
                itemStacks[t].writeToNBT(slot);
            }
        }
        tag.setTag("ItemStacks", list);
    }

    @Override
    public int getSizeInventory() {
        return itemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return itemStacks[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (slot < itemStacks.length && itemStacks[slot] != null) {
            if (itemStacks[slot].stackSize > amount) {
                ItemStack result = itemStacks[slot].splitStack(amount);
                return result;
            }
            ItemStack stack = itemStacks[slot];
            setInventorySlotContents(slot, null);
            return stack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.itemStacks[slot] == null) {
            return null;
        }

        ItemStack stack = this.itemStacks[slot];
        setInventorySlotContents(slot, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        itemStacks[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
        markDirty();
    }

    @Override
    public String getInventoryName() {
        if (name != null)
            return name;
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return stackLimit;
    }

    @Override
    public void markDirty() {
        entity.markDirty();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }
}
