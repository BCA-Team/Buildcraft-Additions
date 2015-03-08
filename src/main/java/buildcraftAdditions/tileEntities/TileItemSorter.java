package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.core.EnumColor;
import buildcraft.api.items.IList;
import buildcraft.api.transport.IInjectable;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;

import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.tileEntities.Bases.TileBase;
import buildcraftAdditions.tileEntities.interfaces.IWidgetListener;

import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileItemSorter extends TileBase implements ISidedInventory, IPipeConnection, IWidgetListener {

	private final CustomInventory inventory = new CustomInventory("ItemSorter", 49, 64, this);
	public byte[] colors = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	private ForgeDirection rotation = ForgeDirection.UP;
	private boolean reloadRotation = false;

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (reloadRotation) {
			setRotation(ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord)));
			reloadRotation = false;
		}
		ItemStack stack = inventory.getStackInSlot(0);
		if (stack == null)
			return;
		TileEntity outputTile = getTileFromDirection(getExitSide());
		if (outputTile == null || !(outputTile instanceof IInjectable))
			return;
		IInjectable injectable = (IInjectable) outputTile;
		if (!injectable.canInjectItems(getExitSide().getOpposite()))
			return;
		EnumColor color = null;
		for (int i = 1; i < inventory.getSizeInventory() - 1; i++) {
			if (areStacksEqual(inventory.getStackInSlot(i), stack)) {
				color = EnumColor.values()[15 - colors[1 + (i - 1) / 6]];
				break;
			}
		}
		if (color == null)
			color = EnumColor.values()[15 - colors[0]];
		injectable.injectItem(stack, true, getExitSide().getOpposite(), color);
		setInventorySlotContents(0, null);
		markDirty();
	}

	private boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
		return stack1 != null && stack1.getItem() instanceof IList && ((IList) stack1.getItem()).matches(stack1, stack2) || stack1 == null && stack2 == null || !(stack1 == null || stack2 == null) && stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage() && !(stack1.stackTagCompound == null && stack2.stackTagCompound != null) && (stack1.stackTagCompound == null || stack1.stackTagCompound.equals(stack2.stackTagCompound));
	}

	private TileEntity getTileFromDirection(ForgeDirection dir) {
		return worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
	}

	public ForgeDirection getRotation() {
		return rotation;
	}

	public void setRotation(ForgeDirection dir) {
		rotation = dir;
		if (worldObj != null) {
			updateBlock();
			notifyNeighborBlockUpdate();
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, dir.ordinal(), 3);
		}
	}

	public ForgeDirection getEnterSide() {
		return rotation.getOpposite();
	}

	public ForgeDirection getExitSide() {
		return rotation;
	}

	protected void updateBlock() {
		if (!worldObj.isRemote)
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	protected void notifyNeighborBlockUpdate() {
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
	}

	protected void updateRender() {
		if (worldObj.isRemote)
			worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
	}

	@Override
	public void onWidgetPressed(int id, int value) {
		colors[id] = (byte) value;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		rotation = ForgeDirection.getOrientation(tag.getByte("Rotation"));
		colors = tag.getByteArray("Colors");
		inventory.readFromNBT(tag);
		if (tag.hasKey("reloadRotation"))
			reloadRotation = true;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setByte("Rotation", (byte) rotation.ordinal());
		tag.setByteArray("Colors", colors);
		inventory.writeToNBT(tag);
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		buf.writeByte(rotation.ordinal());
		buf.writeBytes(colors);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		rotation = ForgeDirection.getOrientation(buf.readByte());
		buf.readBytes(colors);
		updateRender();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == getEnterSide().ordinal() ? new int[]{0} : new int[0];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return side == getEnterSide().ordinal() && slot == 0;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return false;
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
		return inventory.isUseableByPlayer(player);
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
	public ConnectOverride overridePipeConnection(IPipeTile.PipeType type, ForgeDirection side) {
		if ((side == getExitSide() || side == getEnterSide()) && type.equals(IPipeTile.PipeType.ITEM))
			return ConnectOverride.CONNECT;
		return ConnectOverride.DISCONNECT;
	}
}
