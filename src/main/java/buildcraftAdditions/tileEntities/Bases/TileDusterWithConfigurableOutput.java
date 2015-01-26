package buildcraftAdditions.tileEntities.Bases;

import io.netty.buffer.ByteBuf;

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
import buildcraftAdditions.utils.SideConfiguration;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileDusterWithConfigurableOutput extends TileBaseDuster implements IConfigurableOutput {

	protected final SideConfiguration configuration = new SideConfiguration();

	public TileDusterWithConfigurableOutput(String key) {
		super(key);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		configuration.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		configuration.writeToNBT(tag);
	}

	@Override
	public EnumSideStatus getStatus(ForgeDirection side) {
		return configuration.getStatus(side);
	}

	@Override
	public void changeStatus(ForgeDirection side) {
		configuration.changeStatus(side);
	}

	@Override
	public void setSideConfiguration(SideConfiguration configuration) {
		this.configuration.load(configuration);
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		configuration.writeToByteBuff(buf);
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		configuration.readFromByteBuff(buf);
		return buf;
	}

	@Override
	public EnumPriority getPriority(ForgeDirection side) {
		return configuration.getPriority(side);
	}

	@Override
	public void changePriority(ForgeDirection side) {
		configuration.changePriority(side);
	}

	@Override
	public void dust() {
		ItemStack output = BCARecipeManager.duster.getRecipe(getStackInSlot(0)).getOutput(getStackInSlot(0));

		for (EnumPriority priority : EnumPriority.values()) {

			//first try to put it intro a pipe
			for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
				if (configuration.getPriority(direction) != priority)
					continue;
				if (!configuration.canSend(direction))
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
				if (configuration.getPriority(direction) != priority)
					continue;
				if (!configuration.canSend(direction))
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
		}

		//drop it on the ground
		if (output != null)
			Utils.dropItemstack(worldObj, xCoord, yCoord, zCoord, output);

		setInventorySlotContents(0, null);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public SideConfiguration getSideConfiguration() {
		return configuration;
	}
}
