package buildcraftAdditions.entities;

import buildcraftAdditions.Inventories.CustomInventory;
import buildcraftAdditions.core.Variables;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
    public CustomInventory inventory = new CustomInventory("Duster", 1, 1, this);

    public TileBasicDuster(){
    }


    public void makeProgress(){
        if (getStackInSlot(0) != null && getDust(getStackInSlot(0)).isEmpty()) {
            progress++;
            if (progress == 5) {
                dust();
                progress = 0;
            }
        } else {
            progress = 0;
        }
    }

    public void dust(){
        if (worldObj.isRemote)
            return;
        String metal = getDust(getStackInSlot(0));
        ItemStack outputStack;
        if (metal.equals("dustRedstone")){
            outputStack = new ItemStack(OreDictionary.getOres(metal).get(0).getItem(), 6);
        } else if(metal.equals("dustCoal")) {
            outputStack = new ItemStack(Items.coal, 6);
        } else if(metal.equals("dustLapis")) {
            outputStack = new ItemStack(Items.dye, 6, 4);
        } else {
            outputStack = new ItemStack(OreDictionary.getOres(metal).get(0).getItem(), 2);
        }
        setInventorySlotContents(0, null);
        float f1 = 0.7F;
        double d = (worldObj.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
        double d1 = (worldObj.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
        double d2 = (worldObj.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
        EntityItem itemToDrop = new EntityItem(worldObj, xCoord + d, yCoord + d1, zCoord + d2, outputStack);
        itemToDrop.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(itemToDrop);
    }

    public String getDust(ItemStack stack){
        String metal = stack.getItem().getUnlocalizedName();
        String toReturn  ="";
        metal  = metal.replaceAll("tile.ore", "");
        if (Variables.metals.contains(metal))
            toReturn = "dust" + metal;
        return toReturn;
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
        inventory.openInventory();
    }

    @Override
    public void closeInventory() {
        inventory.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public void updateEntity() {

    }
}
