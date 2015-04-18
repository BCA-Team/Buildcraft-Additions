package buildcraftAdditions.tileEntities.Bases;

import com.google.common.base.Strings;

import io.netty.buffer.ByteBuf;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.api.recipe.duster.IDusterRecipe;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.inventories.CustomInventory;

import eureka.api.EurekaAPI;

/**
 * Copyright (c) 2014-2015, AEnterprise
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

	@Override
	public boolean canUpdate() {
		return false;
	}

	public abstract void dust();

	public abstract double getProgress();

	public IDusterRecipe getRecipe() {
		return BCARecipeManager.duster.getRecipe(getStackInSlot(0));
	}

	protected void spawnDustingParticles() {
		if (!ConfigurationHandler.dusterParticles)
			return;
		if (!worldObj.isRemote && worldObj instanceof WorldServer) {
			WorldServer worldServer = (WorldServer) worldObj;
			ItemStack stack = getStackInSlot(0);
			if (stack != null && stack.getItem() != null && stack.stackSize > 0) {
				Item item = stack.getItem();
				String s = null;
				double offsetY = 0;
				if (item instanceof ItemBlock) {
					s = "blockcrack_" + Block.getIdFromBlock(((ItemBlock) item).field_150939_a) + "_" + stack.getItemDamage();
					offsetY = 0.1;
				} else {
					s = "iconcrack_" + Item.getIdFromItem(item);
					if (stack.getHasSubtypes())
						s += "_" + stack.getItemDamage();
					offsetY = 0.5;
				}
				offsetY += getParticleOffset();
				if (!Strings.isNullOrEmpty(s))
					worldServer.func_147487_a(s, xCoord + .5, yCoord + offsetY, zCoord + .5, ConfigurationHandler.particleCount, 0, 0, 0, 0.075);
			}
		}
	}

	protected double getParticleOffset() {
		return 1;
	}

	public void makeEurekaProgress(EntityPlayer player) {
		if (!Strings.isNullOrEmpty(key))
			EurekaAPI.API.makeProgress(key, player);
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		buf.writeInt(progress);
		inventory.writeToByteBuff(buf);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		progress = buf.readInt();
		inventory.readFromByteBuff(buf);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("progress", progress);
		inventory.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		progress = tag.getInteger("progress");
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
