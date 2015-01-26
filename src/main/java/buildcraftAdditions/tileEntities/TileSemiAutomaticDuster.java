package buildcraftAdditions.tileEntities;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.networking.MessageByteBuff;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileDusterWithConfigurableOutput;

import eureka.api.EurekaKnowledge;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileSemiAutomaticDuster extends TileDusterWithConfigurableOutput {
	private CustomInventory inventory = new CustomInventory("semiAutomaticDuster", 1, 1, this);

	public TileSemiAutomaticDuster() {
		super(Variables.DustT2Key1);
	}

	@Override
	public void makeProgress(EntityPlayer player, String key) {
		EurekaKnowledge.makeProgress(player, key, 1);
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
		if (!worldObj.isRemote)
			PacketHandler.instance.sendToAll(new MessageByteBuff(this));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		inventory.readNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		inventory.writeNBT(tag);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if (configuration.canReceive(ForgeDirection.getOrientation(side)))
			return new int[]{0};
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return configuration.canReceive(ForgeDirection.getOrientation(side));
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return configuration.canSend(ForgeDirection.getOrientation(side));
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf = super.writeToByteBuff(buf);
		int id = getStackInSlot(0) == null ? -1 : Item.getIdFromItem(getStackInSlot(0).getItem());
		int meta = getStackInSlot(0) == null ? -1 : getStackInSlot(0).getItemDamage();
		buf.writeInt(id);
		buf.writeInt(meta);
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		buf = super.readFromByteBuff(buf);
		int id = buf.readInt();
		int meta = buf.readInt();
		ItemStack stack = id == -1 ? null : new ItemStack(Item.getItemById(id), 1, meta);
		setInventorySlotContents(0, stack);
		return buf;
	}
}
