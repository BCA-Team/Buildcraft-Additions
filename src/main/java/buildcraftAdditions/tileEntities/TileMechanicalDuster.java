package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyReceiver;

import buildcraftAdditions.api.DusterRecipes;
import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.networking.MessageByteBuff;
import buildcraftAdditions.networking.PacketHandeler;
import buildcraftAdditions.tileEntities.Bases.TileBaseDuster;
import buildcraftAdditions.utils.Utils;

import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileMechanicalDuster extends TileBaseDuster implements IEnergyReceiver {
	public int progress, progressStage, oldProgressStage, energy, maxEnergy;
	public EntityPlayer player;
	private CustomInventory inventory = new CustomInventory("mechanicalDuster", 1, 1, this);

	public TileMechanicalDuster() {
		super("dusterTier2-2");
		maxEnergy = 2000;
	}


	@Override
	public void updateEntity() {
		if (energy >= 4) {
			if (getStackInSlot(0) != null && DusterRecipes.dusting().hasDustingResult(getStackInSlot(0))) {
				progress++;
				energy -= 4;
				oldProgressStage = progressStage;
				if (progress > 25)
					progressStage = 1;
				if (progress > 50)
					progressStage = 2;
				if (progress > 75)
					progressStage = 3;
				if (progress >= 100) {
					dust();
					if (player != null)
						makeProgress(player, "dusterTier2-2");
					progress = 0;
					progressStage = 0;
				}


			} else {
				progress = 0;
				progressStage = 0;
			}
		}
		if (oldProgressStage != progressStage) {
			TargetPoint point = new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 30);
			PacketHandeler.instance.sendToAllAround(new MessageByteBuff(this), point);
			oldProgressStage = progressStage;
		}
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
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		inventory.writeNBT(tag);
		tag.setInteger("progress", progress);
		tag.setInteger("energy", energy);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		inventory.readNBT(tag);
		progress = tag.getInteger("progress");
		energy = tag.getInteger("energy");
	}

	@Override
	public void dust() {
		Utils.dropItemstack(worldObj, xCoord, yCoord, zCoord, DusterRecipes.dusting().getDustingResult(getStackInSlot(0)));
		setInventorySlotContents(0, null);
		if (!worldObj.isRemote)
			PacketHandeler.instance.sendToAllAround(new MessageByteBuff(this), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 30));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		int energyRecieved = maxReceive;
		if (energyRecieved > maxEnergy - energy)
			energyRecieved = maxReceive - energy;
		if (!simulate)
			energy += energyRecieved;
		return energyRecieved;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energy;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return maxEnergy;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return true;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return false;
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

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf = super.writeToByteBuff(buf);
		int id = getStackInSlot(0) == null ? -1 : Item.getIdFromItem(getStackInSlot(0).getItem());
		int meta = getStackInSlot(0) == null ? -1 : getStackInSlot(0).getItemDamage();
		buf.writeInt(id);
		buf.writeInt(meta);
		return buf;
	}
}

