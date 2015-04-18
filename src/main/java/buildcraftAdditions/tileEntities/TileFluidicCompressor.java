package buildcraftAdditions.tileEntities;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileMachineBase;
import buildcraftAdditions.tileEntities.interfaces.IWidgetListener;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.utils.fluids.Tank;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileFluidicCompressor extends TileMachineBase implements ISidedInventory, IFluidHandler, IWidgetListener {

	public final Tank tank = new Tank(FluidContainerRegistry.BUCKET_VOLUME * 10, this, "Tank");
	private final CustomInventory inventory = new CustomInventory("FluidicCompressor", 2, 1, this);
	public boolean fill;

	public TileFluidicCompressor() {
		super(ConfigurationHandler.capacityFluidicCompressor, ConfigurationHandler.maxTransferFluidicCompressor, Variables.SyncIDs.FLUIDIC_COMPRESSOR.ordinal());
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote)
			return;
		ItemStack stack = inventory.getStackInSlot(0);
		if (stack != null) {
			Item stackItem = stack.getItem();
			if (stackItem instanceof IFluidContainerItem) {
				IFluidContainerItem iFluidContainerItem = (IFluidContainerItem) stackItem;
				if (fill) {
					if (!tank.isEmpty()) {
						int amount = 128;
						if (tank.getFluidAmount() < amount)
							amount = tank.getFluidAmount();
						if (energy >= amount) {
							drain(ForgeDirection.UNKNOWN, iFluidContainerItem.fill(stack, new FluidStack(tank.getFluid(), amount), true), true);
							energy -= amount;
						}
					}
				} else {
					FluidStack contained = iFluidContainerItem.getFluid(stack);
					if (!fill && !tank.isFull() && contained != null && contained.amount > 0) {
						int amount = 64;
						if (tank.getFreeSpace() < amount)
							amount = tank.getFreeSpace();
						if (amount > contained.amount)
							amount = contained.amount;
						iFluidContainerItem.drain(stack, fill(ForgeDirection.UNKNOWN, new FluidStack(contained, amount), true), true);
					}
				}
			} else if (FluidContainerRegistry.isContainer(stack)) {
				if (fill) {
					if (!tank.isEmpty()) {
						int amount = FluidContainerRegistry.getContainerCapacity(tank.getFluid(), stack);
						if (amount > 0 && energy >= amount && tank.getFluidAmount() >= amount) {
							ItemStack filledContainer = FluidContainerRegistry.fillFluidContainer(new FluidStack(tank.getFluid(), amount), stack);
							if (filledContainer != null && filledContainer.getItem() != null && filledContainer.stackSize > 0) {
								energy -= amount;
								drain(ForgeDirection.UNKNOWN, amount, true);
								inventory.setInventorySlotContents(0, filledContainer.copy());
							}
						}
					}
				} else {
					FluidStack contained = FluidContainerRegistry.getFluidForFilledItem(stack);
					if (contained != null && contained.amount > 0 && tank.getFreeSpace() >= contained.amount) {
						if (fill(ForgeDirection.UNKNOWN, contained, false) == contained.amount) {
							fill(ForgeDirection.UNKNOWN, contained, true);
							ItemStack drainedContainer = FluidContainerRegistry.drainFluidContainer(stack);
							if (drainedContainer != null && drainedContainer.getItem() != null && drainedContainer.stackSize > 0)
								inventory.setInventorySlotContents(0, drainedContainer.copy());
						}
					}
				}
			}

			if (getProgress() >= 16) {
				stack = getStackInSlot(0);
				if (stack != null) {
					ItemStack outputStack = getStackInSlot(1);
					if (outputStack == null || outputStack.getItem() == null || outputStack.stackSize <= 0) {
						ItemStack copyStack = stack.copy();
						copyStack.stackSize = 1;
						inventory.setInventorySlotContents(1, copyStack);
						inventory.decrStackSize(0, 1);
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		fill = nbtTagCompound.getBoolean("fill");
		inventory.readFromNBT(nbtTagCompound);
		if (nbtTagCompound.hasKey("tank", Constants.NBT.TAG_COMPOUND))
			tank.readFromNBT(nbtTagCompound.getCompoundTag("tank"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setBoolean("fill", fill);
		inventory.writeToNBT(nbtTagCompound);
		nbtTagCompound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public int getSizeInventory() {
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slotId) {
		return inventory.getStackInSlot(slotId);
	}

	@Override
	public ItemStack decrStackSize(int slotId, int count) {
		return inventory.decrStackSize(slotId, count);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return inventory.getStackInSlotOnClosing(var1);
	}

	@Override
	public void setInventorySlotContents(int slotId, ItemStack itemstack) {
		inventory.setInventorySlotContents(slotId, itemstack);
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
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
	}

	@Override
	public void openInventory() {
		inventory.openInventory();
	}

	@Override
	public void closeInventory() {
		inventory.openInventory();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return stack != null && stack.getItem() != null && slot == 0 && (stack.getItem() instanceof IFluidContainerItem || FluidContainerRegistry.isContainer(stack));
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return tank.getFluidType() == fluid;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{new FluidTankInfo(tank)};
	}

	public FluidStack getFluid() {
		return tank.getFluid();
	}

	public int getProgress() {
		ItemStack stack = inventory.getStackInSlot(0);
		if (stack == null)
			return 0;
		Item item = stack.getItem();
		FluidStack fluidstack = null;
		int capacity = 0;
		if (item instanceof IFluidContainerItem) {
			IFluidContainerItem iFluidContainerItem = (IFluidContainerItem) stack.getItem();
			fluidstack = iFluidContainerItem.getFluid(stack);
			capacity = iFluidContainerItem.getCapacity(stack);
		} else if (FluidContainerRegistry.isContainer(stack)) {
			fluidstack = FluidContainerRegistry.getFluidForFilledItem(stack);
			capacity = FluidContainerRegistry.getContainerCapacity(fill ? tank.getFluid() : fluidstack, stack);
		}

		if (fluidstack == null || capacity <= 0) {
			if (fill)
				return 0;
			return 16;
		}
		if (fill)
			return (int) ((fluidstack.amount * 16D) / capacity);
		return (int) (((capacity - fluidstack.amount) * 16D) / capacity);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return Utils.createSlotArray(0, 2);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return slot == 0 && isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 1;
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		super.writeToByteBuff(buf);
		buf.writeBoolean(fill);
		tank.writeToByteBuff(buf);
		inventory.writeToByteBuff(buf);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		super.readFromByteBuff(buf);
		fill = buf.readBoolean();
		tank.readFromByteBuff(buf);
		inventory.readFromByteBuff(buf);
	}

	@Override
	public void onWidgetPressed(int id, int value) {
		fill = value == 1;
	}
}
