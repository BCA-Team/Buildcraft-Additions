package buildcraftAdditions.entities;

import buildcraftAdditions.Inventories.CustomInventory;
import buildcraftAdditions.core.Variables;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileBasicDuster extends TileBase implements IInventory {
    public int progress;
    private static CustomInventory inventory = new CustomInventory("Duster", 1, 1);

    public TileBasicDuster(){
    }


    public void makeProgress(){
        progress++;
        if (progress == 5){
            dust();
            progress = 0;
        }
    }

    public void dust(){
        if (worldObj.isRemote)
            return;
        String metal = getStackInSlot(0).getUnlocalizedName();
        metal  = metal.replaceAll("tile.ore", "");
        if (Variables.metals.contains(metal)) {
            metal = "dust" + metal;
            ItemStack outputStack = new ItemStack(OreDictionary.getOres(metal).get(0).getItem(), 2);
            setInventorySlotContents(0, null);
            float f1 = 0.7F;
            double d = (worldObj.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
            double d1 = (worldObj.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
            double d2 = (worldObj.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
            EntityItem itemToDrop = new EntityItem(worldObj, xCoord + d, yCoord + 2 + d1, zCoord + d2, outputStack);
            itemToDrop.delayBeforeCanPickup = 10;
            worldObj.spawnEntityInWorld(itemToDrop);
        }
    }


    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        inventory.readNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        inventory.writeNBT(tag);
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
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
                && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D,
                zCoord + 0.5D) <= 64.0D;
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

    @Override
    public void updateEntity() {

    }
}
