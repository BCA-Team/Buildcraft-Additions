package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.transport.IPipeTile;

import buildcraftAdditions.api.DusterRecipes;
import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.networking.MessageSemiAutomaticDuster;
import buildcraftAdditions.networking.PacketHandeler;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileDusterWithConfigurableOutput;
import buildcraftAdditions.utils.EnumSideStatus;
import buildcraftAdditions.utils.Utils;

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
	public void dust() {
		ItemStack output = DusterRecipes.dusting().getDustingResult(getStackInSlot(0));

		//first try to put it intro a pipe
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			int x = xCoord + direction.offsetX;
			int y = yCoord + direction.offsetY;
			int z = zCoord + direction.offsetZ;
			TileEntity entity = worldObj.getTileEntity(x, y, z);
			if (entity instanceof IPipeTile) {
				IPipeTile pipe = (IPipeTile) entity;
				if (output != null && pipe.isPipeConnected(direction.getOpposite()) && pipe.getPipeType() == IPipeTile.PipeType.ITEM) {
					int leftOver = pipe.injectItem(output.copy(), true, direction.getOpposite());
					output.stackSize -= leftOver;
					if (output.stackSize == 0)
						output = null;
				}
			}
		}
		//try to put it intro an inventory
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			int x = xCoord + direction.offsetX;
			int y = yCoord + direction.offsetY;
			int z = zCoord + direction.offsetZ;
			TileEntity entity = worldObj.getTileEntity(x, y, z);
			if (entity != null && entity instanceof IInventory) {
				IInventory outputInventory = (IInventory) entity;
				for (int slot = 0; slot < outputInventory.getSizeInventory(); slot++) {
					int stackLimit = outputInventory.getInventoryStackLimit();
					ItemStack testStack = outputInventory.getStackInSlot(slot);
					if (output != null &&
							(testStack == null || (testStack.stackSize + output.stackSize <= testStack.getMaxStackSize() && testStack.getItem() == output.getItem() && testStack.getItemDamage() == output.getItemDamage()))) {
						ItemStack stack = outputInventory.getStackInSlot(slot);
						int toMove;
						if (stack == null) {
							toMove = stackLimit - 1;
							stack = new ItemStack(output.getItem(), 0);
						} else {
							toMove = stackLimit - stack.stackSize;
						}
						if (toMove > output.stackSize)
							toMove = output.stackSize;
						stack.stackSize += toMove;
						output.stackSize -= toMove;
						outputInventory.setInventorySlotContents(slot, stack);
						outputInventory.markDirty();
						if (output.stackSize == 0)
							output = null;
					}
				}

			}
		}

		//drop it on the ground
		if (output != null)
			Utils.dropItemstack(worldObj, xCoord, yCoord, zCoord, output);

		setInventorySlotContents(0, null);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void makeProgress(EntityPlayer player, String key) {
		EurekaKnowledge.makeProgress(player, key, 1);
	}

	@Override
	public void updateEntity() {

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
			PacketHandeler.instance.sendToAll(new MessageSemiAutomaticDuster(xCoord, yCoord, zCoord, stack));
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
		if (configuration[side] == EnumSideStatus.BOTH || configuration[side] == EnumSideStatus.INPUT)
			return new int[]{0};
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return configuration[side] == EnumSideStatus.INPUT || configuration[side] == EnumSideStatus.BOTH;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return configuration[side] == EnumSideStatus.OUTPUT || configuration[side] == EnumSideStatus.BOTH;
	}
}
