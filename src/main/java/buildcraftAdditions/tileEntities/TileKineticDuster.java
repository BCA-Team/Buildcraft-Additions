package buildcraftAdditions.tileEntities;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.network.NetworkRegistry;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.power.ILaserTarget;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.networking.MessageByteBuff;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.tileEntities.Bases.TileDusterWithConfigurableOutput;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKineticDuster extends TileDusterWithConfigurableOutput implements ILaserTarget {
	public int progress, progressStage, oldProgressStage;
	private CustomInventory inventory = new CustomInventory("KineticDuster", 1, 1, this);

	public TileKineticDuster() {
		super("");
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
			PacketHandler.instance.sendToAllAround(new MessageByteBuff(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 30));
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
	public boolean requiresLaserEnergy() {
		return BCARecipeManager.duster.getRecipe(getStackInSlot(0)) != null;
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf = super.writeToByteBuff(buf);
		buf.writeInt(progressStage);
		int id = getStackInSlot(0) == null ? -1 : Item.getIdFromItem(getStackInSlot(0).getItem());
		int meta = getStackInSlot(0) == null ? -1 : getStackInSlot(0).getItemDamage();
		buf.writeInt(id);
		buf.writeInt(meta);
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		buf = super.readFromByteBuff(buf);
		progressStage = buf.readInt();
		int id = buf.readInt();
		int meta = buf.readInt();
		ItemStack stack = id == -1 ? null : new ItemStack(Item.getItemById(id), 1, meta);
		setInventorySlotContents(0, stack);
		return buf;
	}

	@Override
	public void receiveLaserEnergy(int energy) {
		progress += energy;
		oldProgressStage = progressStage;
		if (progress > 20000) {
			progress = 0;
			progressStage = 0;
			dust();
		}
		if (progress > 5000)
			progressStage = 1;
		if (progress > 10000)
			progressStage = 2;
		if (progress > 15000)
			progressStage = 3;

		if (progressStage != oldProgressStage)
			PacketHandler.instance.sendToAllAround(new MessageByteBuff(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 30));
	}

	@Override
	public boolean isInvalidTarget() {
		return false;
	}

	@Override
	public double getXCoord() {
		return xCoord;
	}

	@Override
	public double getYCoord() {
		return yCoord;
	}

	@Override
	public double getZCoord() {
		return zCoord;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		inventory.readNBT(tag);
		tag.setInteger("progress", progress);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		inventory.writeNBT(tag);
		progress = tag.getInteger("progress");
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
}
