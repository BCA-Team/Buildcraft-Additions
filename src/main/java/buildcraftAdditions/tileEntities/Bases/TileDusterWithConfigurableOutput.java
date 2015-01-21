package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.transport.IPipeTile;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.utils.EnumPriority;
import buildcraftAdditions.utils.EnumSideStatus;
import buildcraftAdditions.utils.IConfigurableOutput;
import buildcraftAdditions.utils.Utils;

import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileDusterWithConfigurableOutput extends TileBaseDuster implements IConfigurableOutput {
	protected EnumSideStatus configuration[];
	public EnumPriority priorities[] = new EnumPriority[6];

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (tag.hasKey("configuration")) {
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				configuration[direction.ordinal()] = Utils.intToStatus(tag.getInteger("configuration" + direction.ordinal()));
				priorities[direction.ordinal()] = EnumPriority.PRIORITIES[tag.getInteger("priority" + direction.ordinal())];
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setBoolean("configuration", true);
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			tag.setInteger("configuration" + direction.ordinal(), Utils.statusToInt(configuration[direction.ordinal()]));
			tag.setInteger("priority" + direction.ordinal(), priorities[direction.ordinal()].ordinal());
		}
	}

	public TileDusterWithConfigurableOutput(String key) {
		super(key);
		configuration = new EnumSideStatus[6];
		for (int teller = 0; teller < 6; teller++) {
			configuration[teller] = EnumSideStatus.BOTH;
			priorities[teller] = EnumPriority.NORMAL;
		}
	}

	@Override
	public EnumSideStatus getStatus(ForgeDirection side) {
		return configuration[side.ordinal()];
	}

	@Override
	public void changeStatus(ForgeDirection side) {
		EnumSideStatus status = configuration[side.ordinal()];
		if (status == EnumSideStatus.INPUT)
			status = EnumSideStatus.OUTPUT;
		else if (status == EnumSideStatus.OUTPUT)
			status = EnumSideStatus.BOTH;
		else if (status == EnumSideStatus.BOTH)
			status = EnumSideStatus.DISSABLED;
		else if (status == EnumSideStatus.DISSABLED)
			status = EnumSideStatus.INPUT;
		configuration[side.ordinal()] = status;
	}

	@Override
	public void overrideConfiguration(EnumSideStatus[] newConfiguration) {
		configuration = newConfiguration;
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			buf.writeInt(Utils.statusToInt(configuration[direction.ordinal()]));
			buf.writeInt(priorities[direction.ordinal()].ordinal());
		}
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			configuration[direction.ordinal()] = Utils.intToStatus(buf.readInt());
			priorities[direction.ordinal()] = EnumPriority.PRIORITIES[buf.readInt()];
		}
		return buf;
	}

	@Override
	public EnumPriority getPriority(ForgeDirection side) {
		return priorities[side.ordinal()];
	}

	@Override
	public void overridePriority(EnumPriority[] newPriorities) {
		priorities = newPriorities;
	}

	@Override
	public void changePriority(ForgeDirection side) {
		priorities[side.ordinal()] = priorities[side.ordinal()].getNextPriority();
	}

	@Override
	public void dust() {
		ItemStack output = BCARecipeManager.duster.getRecipe(getStackInSlot(0)).getOutput(getStackInSlot(0));

		for (EnumPriority priority : EnumPriority.PRIORITIES) {

			//first try to put it intro a pipe
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				if (priorities[direction.ordinal()] != priority)
					continue;
				if (configuration[direction.ordinal()] == EnumSideStatus.DISSABLED || configuration[direction.ordinal()] == EnumSideStatus.INPUT)
					continue;
				int x = xCoord + direction.offsetX;
				int y = yCoord + direction.offsetY;
				int z = zCoord + direction.offsetZ;
				TileEntity entity = worldObj.getTileEntity(x, y, z);
				if (entity instanceof IPipeTile) {
					IPipeTile pipe = (IPipeTile) entity;
					if (output != null && pipe.isPipeConnected(direction.getOpposite()) && pipe.getPipeType() == IPipeTile.PipeType.ITEM) {
						int leftOver = pipe.injectItem(output.copy(), true, direction.getOpposite(), null);
						output.stackSize -= leftOver;
						if (output.stackSize == 0)
							output = null;
					}
				}
			}
			//try to put it intro an inventory
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				if (priorities[direction.ordinal()] != priority)
					continue;
				if (configuration[direction.ordinal()] == EnumSideStatus.DISSABLED || configuration[direction.ordinal()] == EnumSideStatus.INPUT)
					continue;
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
								stack = output.copy();
								stack.stackSize = 0;
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
	}
}
