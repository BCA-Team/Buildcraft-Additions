package buildcraftAdditions.tileEntities;

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

import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.tileEntities.Bases.TileMachineBase;
import buildcraftAdditions.tileEntities.interfaces.IWidgetListener;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.utils.fluids.Tank;

import io.netty.buffer.ByteBuf;

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
		super(800);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote)
			return;
		ItemStack itemstack = inventory.getStackInSlot(0);
		if (itemstack != null) {
			IFluidContainerItem item = null;
			Item itemInSlot = itemstack.getItem();
			if (itemInSlot instanceof IFluidContainerItem) {
				item = (IFluidContainerItem) itemstack.getItem();
			}
			if (item != null) {
				int amount = 100;
				if (fill && !tank.isEmpty()) {
					if (tank.getFluid().amount < 100)
						amount = tank.getFluid().amount;
					if (energy >= amount) {
						drain(ForgeDirection.UNKNOWN, item.fill(itemstack, new FluidStack(tank.getFluid(), amount), true), true);
						energy -= amount;
						FluidStack fluid = Utils.getFluidStackFromItemStack(itemstack);
						if (fluid != null) {
							if (getProgress() == 16) {
								if (inventory.getStackInSlot(1) == null) {
									inventory.setInventorySlotContents(1, itemstack);
									inventory.setInventorySlotContents(0, null);
								} else if (inventory.getStackInSlot(1).getItem() == inventory.getStackInSlot(0).getItem() && inventory.getStackInSlot(1).stackSize < 4) {
									inventory.getStackInSlot(1).stackSize++;
									inventory.setInventorySlotContents(0, null);
								}
							}
						}
					}
				} else {
					amount = 50;
					if (!fill && !tank.isFull() && Utils.getFluidStackFromItemStack(itemstack) != null) {
						if (!tank.isEmpty()) {
							if ((tank.getCapacity() - tank.getFluid().amount) < 1000) {
								amount = tank.getCapacity() - tank.getFluid().amount;
							}
						}
						if (amount > Utils.getFluidStackFromItemStack(itemstack).amount) {
							amount = Utils.getFluidStackFromItemStack(itemstack).amount;
						}
						fill(ForgeDirection.UNKNOWN, item.drain(itemstack, amount, true), true);
						if (getProgress() >= 16) {
							itemstack.getTagCompound().removeTag("Fluid");
							if (inventory.getStackInSlot(1) == null) {
								inventory.setInventorySlotContents(1, itemstack);
								inventory.setInventorySlotContents(0, null);
							} else if (inventory.getStackInSlot(1).getItem() == inventory.getStackInSlot(0).getItem() && inventory.getStackInSlot(1).stackSize < 4) {
								inventory.getStackInSlot(1).stackSize++;
								inventory.setInventorySlotContents(0, null);
							}
						}
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
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
				&& entityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D,
				zCoord + 0.5D) <= 64.0D;
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
	public boolean isItemValidForSlot(int slotid, ItemStack itemStack) {
		if (itemStack == null)
			return false;
		Item item = itemStack.getItem();
		return slotid == 0 && item instanceof IFluidContainerItem;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
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
		ItemStack itemstack = inventory.getStackInSlot(0);
		if (itemstack == null)
			return 0;
		Item item = itemstack.getItem();
		if (!(item instanceof IFluidContainerItem))
			return 0;
		FluidStack fluidstack = Utils.getFluidStackFromItemStack(itemstack);
		IFluidContainerItem canister = (IFluidContainerItem) itemstack.getItem();
		if (fluidstack == null) {
			if (fill) {
				return 0;
			} else {
				return 0;
			}
		}
		int capacity = canister.getCapacity(itemstack);
		if (fill)
			return (fluidstack.amount * 16) / capacity;
		return ((capacity - fluidstack.amount) * 16) / capacity;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return Utils.createSlotArray(0, 2);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return side != 0 && isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return (slot == 1);
	}

	public int getEnergyStored() {
		return energy;
	}

	public int getFluidStored() {
		if (tank.getFluid() != null) {
			return tank.getFluid().amount;
		}
		return 0;
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		super.writeToByteBuff(buf);
		tank.writeToByteBuff(buf);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		super.readFromByteBuff(buf);
		tank.readFromByteBuff(buf);
	}

	@Override
	public void onWidgetPressed(int id, int value) {
		fill = value == 1;
	}
}
