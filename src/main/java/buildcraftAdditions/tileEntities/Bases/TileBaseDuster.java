package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.network.ByteBufUtils;

import buildcraftAdditions.api.networking.MessageByteBuff;
import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.networking.PacketHandler;

import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileBaseDuster extends TileBase implements ISidedInventory {

	public final CustomInventory inventory = new CustomInventory("Duster", 1, 1, this);
	protected final String key;
	public int progress;

	public TileBaseDuster(String key) {
		this.key = key;
	}

	public void makeProgress(EntityPlayer player) {
		if (BCARecipeManager.duster.getRecipe(getStackInSlot(0)) != null) {
			progress++;
			if (progress >= 8) {
				dust();
				progress = 0;
				makeEurekaProgress(player);
			}
		}
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	public abstract void dust();

	public void makeEurekaProgress(EntityPlayer player) {
		//if (!Strings.isNullOrEmpty(key))
		//EurekaKnowledge.makeProgress(player, key, 1);
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf = buf.writeInt(progress);
		ByteBufUtils.writeItemStack(buf, getStackInSlot(0));
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		progress = buf.readInt();
		setInventorySlotContents(0, ByteBufUtils.readItemStack(buf));
		return buf;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("progress", progress);
		inventory.writeNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		progress = tag.getInteger("progress");
		inventory.readNBT(tag);
	}

	@Override
	public void markDirty() {
		super.markDirty();
		if (!worldObj.isRemote)
			PacketHandler.instance.sendToAll(new MessageByteBuff(this));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
		markDirty();
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
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return false;
	}
}
