package buildcraftAdditions.entities;

import buildcraftAdditions.Inventories.CustomInventory;
import buildcraftAdditions.networking.MessageTileBasicDuster;
import buildcraftAdditions.networking.PacketHandeler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

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
    public ArrayList<String> metals;

    public TileBasicDuster(){
        metals = new ArrayList<String>();
        addMetals();
    }

    public void addMetals(){
        metals.add("Iron");
    }

    public void makeProgress(){
        progress++;
        if (progress == 5){
            dust();
            progress = 0;
        }
    }

    public void dust(){
        String metal = getStackInSlot(0).getUnlocalizedName();
        metal = metal;
        metal  = metal.replaceAll("tile.ore", "");
        if (metals.contains(metal)) {
            metal = "dust" + metal;
            ItemStack outputStack = OreDictionary.getOres(metal).get(0);
            float f1 = 0.7F;
            double d = (worldObj.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
            double d1 = (worldObj.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
            double d2 = (worldObj.rand.nextFloat() * f1) + (1.0F - f1) * 0.5D;
            EntityItem itemToDrop = new EntityItem(worldObj, xCoord + d, yCoord + d1, zCoord + d2, outputStack);
            itemToDrop.delayBeforeCanPickup = 10;
            worldObj.spawnEntityInWorld(itemToDrop);
        }
    }

    @Override
    public Packet getDescriptionPacket(){
        return PacketHandeler.instance.getPacketFrom(new MessageTileBasicDuster(this));
    }

    @Override
    public void updateEntity() {


    }

    @Override
    public void readFromNBT(NBTTagCompound tag){

    }

    @Override
    public void writeToNBT(NBTTagCompound tag){

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
}
