package buildcraftAdditions.tileEntities;

import java.util.Set;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.api.configurableOutput.EnumPriority;
import buildcraftAdditions.api.configurableOutput.EnumSideStatus;
import buildcraftAdditions.api.configurableOutput.IConfigurableOutput;
import buildcraftAdditions.api.configurableOutput.SideConfiguration;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.reference.enums.EnumMachineUpgrades;
import buildcraftAdditions.tileEntities.Bases.TileBase;
import buildcraftAdditions.tileEntities.Bases.TileCoilBase;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;
import buildcraftAdditions.tileEntities.varHelpers.Upgrades;
import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileHeatedFurnace extends TileBase implements ISidedInventory, IUpgradableMachine, IConfigurableOutput {
	public final TileCoilBase[] coils;
	private final CustomInventory inventory = new CustomInventory("HeatedFurnace", 2, 64, this);
	private final Upgrades upgrades = new Upgrades(1).whitelistUpgrade(EnumMachineUpgrades.AUTO_OUTPUT); //for now only allow that one as the others don't work yet
	public int progress;
	public boolean isCooking, shouldUpdateCoils;
	private SideConfiguration configuration = new SideConfiguration(EnumSideStatus.BOTH);

	public TileHeatedFurnace() {
		super(Variables.SyncIDs.HEATED_FURNACE.ordinal());
		progress = 0;
		isCooking = false;
		coils = new TileCoilBase[6];
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote)
			return;
		if (upgrades.hasUpgrade(EnumMachineUpgrades.AUTO_OUTPUT))
			output();
		if (inventory.getStackInSlot(0) == null && !isCooking)
			return;
		if (shouldUpdateCoils) {
			updateCoils();
			shouldUpdateCoils = false;
		}
		if (canCook()) {
			if (!isCooking) {
				for (TileCoilBase coil : coils) {
					if (coil != null) {
						coil.startHeating();
						if (coil.isBurning()) {
							isCooking = true;
						}
					}
				}
				if (isCooking)
					sync();
			}
			if (progress > 0)
				isCooking = true;
			if (progress >= ConfigurationHandler.heatedFurnaceHeatRequired) {
				ItemStack inputStack = getStackInSlot(0);
				ItemStack outputStack = getStackInSlot(1);
				ItemStack result = getResult(inputStack);
				if (outputStack == null) {
					setInventorySlotContents(1, result.copy());
				} else {
					outputStack.stackSize += result.stackSize;
				}
				if (inputStack.stackSize <= 1)
					setInventorySlotContents(0, null);
				else
					inputStack.stackSize--;
				progress = 0;
			} else {
				for (TileCoilBase coil : coils)
					if (coil != null) {
						progress += coil.getIncrement();
					}
			}
		} else {
			stop();
		}
	}

	public void stop() {
		isCooking = false;
		progress = 0;
		sync();
		for (TileCoilBase coil : coils) {
			if (coil != null)
				coil.stopHeating();
		}
	}

	@Override
	public void sync() {
		super.sync();
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
	}

	private void output() {
		if (getStackInSlot(1) == null)
			return;
		setInventorySlotContents(1, Utils.outputStack(new Location(this), getStackInSlot(1), configuration));
	}

	public boolean canCook() {
		ItemStack stack0 = getStackInSlot(0);
		ItemStack stack1 = getStackInSlot(1);
		if (stack0 == null || stack0.getItem() == null || stack0.stackSize <= 0)
			return false;
		ItemStack result = getResult(stack0);
		if (result == null || result.getItem() == null || result.stackSize <= 0)
			return false;
		return Utils.areItemStacksMergeableStrict(stack1, result);
	}

	public ItemStack getResult(ItemStack stack) {
		return FurnaceRecipes.smelting().getSmeltingResult(stack);
	}

	public void updateCoils() {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			Location location = new Location(worldObj, xCoord, yCoord, zCoord).move(direction);
			TileEntity entity = location.getTileEntity();
			if (entity instanceof TileCoilBase)
				coils[direction.ordinal()] = (TileCoilBase) entity;
		}
		isCooking = false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		inventory.readFromNBT(nbtTagCompound);
		progress = nbtTagCompound.getInteger("progress");
		isCooking = nbtTagCompound.getBoolean("isCooking");
		shouldUpdateCoils = true;
		configuration.readFromNBT(nbtTagCompound);
		upgrades.readFromNBT(nbtTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		inventory.writeToNBT(nbtTagCompound);
		nbtTagCompound.setInteger("progress", progress);
		nbtTagCompound.setBoolean("isCooking", isCooking);
		configuration.writeToNBT(nbtTagCompound);
		upgrades.writeToNBT(nbtTagCompound);
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
		return inventory.getStackInSlot(slot);
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
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return getResult(stack) != null && slot == 0;
	}

	public int getScaledProgress() {
		return (progress * 23) / ConfigurationHandler.heatedFurnaceHeatRequired + 1;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return Utils.createSlotArray(0, 2);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return slot == 0 && getStatus(ForgeDirection.getOrientation(side)).canReceive();
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 1 && getStatus(ForgeDirection.getOrientation(side)).canSend();
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		buf.writeInt(progress);
		buf.writeBoolean(isCooking);
		upgrades.writeToByteBuff(buf);
		configuration.writeToByteBuff(buf);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		progress = buf.readInt();
		isCooking = buf.readBoolean();
		upgrades.readFromByteBuff(buf);
		configuration.readFromByteBuff(buf);
	}

	@Override
	public boolean canAcceptUpgrade(EnumMachineUpgrades upgrade) {
		return upgrades.canInstallUpgrade(upgrade);
	}

	@Override
	public void installUpgrade(EnumMachineUpgrades upgrade) {
		upgrades.installUpgrade(upgrade);
	}

	@Override
	public Set<EnumMachineUpgrades> getInstalledUpgrades() {
		return upgrades.getUpgrades();
	}

	@Override
	public void removeUpgrade() {
		EnumMachineUpgrades upgrade = upgrades.removeUpgrade();
		if (upgrade == null)
			return;
		ItemStack stack = upgrade.getItemStack();
		Utils.dropItemstack(worldObj, xCoord, yCoord, zCoord, stack);
	}

	@Override
	public SideConfiguration getSideConfiguration() {
		return configuration;
	}

	@Override
	public void setSideConfiguration(SideConfiguration configuration) {
		this.configuration = configuration;
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
	public EnumPriority getPriority(ForgeDirection side) {
		return configuration.getPriority(side);
	}

	@Override
	public void changePriority(ForgeDirection side) {
		configuration.changePriority(side);
	}
}
