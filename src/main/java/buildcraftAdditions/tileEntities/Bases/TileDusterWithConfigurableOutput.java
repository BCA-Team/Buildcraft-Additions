package buildcraftAdditions.tileEntities.Bases;

import io.netty.buffer.ByteBuf;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;

import buildcraftAdditions.api.configurableOutput.EnumPriority;
import buildcraftAdditions.api.configurableOutput.EnumSideStatus;
import buildcraftAdditions.api.configurableOutput.IConfigurableOutput;
import buildcraftAdditions.api.configurableOutput.SideConfiguration;
import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileDusterWithConfigurableOutput extends TileBaseDuster implements IConfigurableOutput, IPipeConnection {

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
	public void writeToByteBuff(ByteBuf buf) {
		super.writeToByteBuff(buf);
		configuration.writeToByteBuff(buf);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		super.readFromByteBuff(buf);
		configuration.readFromByteBuff(buf);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return configuration.canReceive(ForgeDirection.getOrientation(side)) ? new int[]{0} : new int[0];
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
		Utils.outputStack(new Location(this), output, configuration);
		if (output != null && output.stackSize > 0 && output.getItem() != null)
			Utils.dropItemstack(worldObj, xCoord, yCoord, zCoord, output);
		setInventorySlotContents(0, null);
	}

	@Override
	public SideConfiguration getSideConfiguration() {
		return configuration;
	}

	@Override
	public void setSideConfiguration(SideConfiguration configuration) {
		this.configuration.load(configuration);
	}

	@Override
	public ConnectOverride overridePipeConnection(IPipeTile.PipeType type, ForgeDirection with) {
		EnumSideStatus status = getStatus(with);
		return type == IPipeTile.PipeType.ITEM && (status.canSend() || status.canReceive()) ? ConnectOverride.CONNECT : ConnectOverride.DEFAULT;
	}
}
