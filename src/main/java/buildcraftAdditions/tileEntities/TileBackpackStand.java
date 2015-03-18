package buildcraftAdditions.tileEntities;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.tileEntities.Bases.TileBase;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileBackpackStand extends TileBase implements IInventory {
	private CustomInventory inventory = new CustomInventory("backpackStand", 1, 1, this);

	public void onBlockActivated(float hitX, float hitY, float hitZ, int rotation, EntityPlayer player) {
		int capsule = -1;
		switch (ForgeDirection.getOrientation(rotation)) {
			case NORTH:
				if (hitY >= 1.1 && hitY <= 1.3) {
					if (hitX >= 0.58 && hitX <= 0.81)
						capsule = 0;
					else if (hitX >= 0.18 && hitX <= 0.41)
						capsule = 1;
				} else if (hitY >= 0.78 && hitY <= 1) {
					if (hitX >= 0.58 && hitX <= 0.81)
						capsule = 2;
					else if (hitX >= 0.18 && hitX <= 0.41)
						capsule = 3;
				}
				break;

			case EAST:
				if (hitY >= 1.1 && hitY <= 1.3) {
					if (hitZ >= 0.58 && hitZ <= 0.81)
						capsule = 0;
					else if (hitZ >= 0.18 && hitZ <= 0.41)
						capsule = 1;
				} else if (hitY >= 0.78 && hitY <= 1) {
					if (hitZ >= 0.58 && hitZ <= 0.81)
						capsule = 2;
					else if (hitZ >= 0.18 && hitZ <= 0.41)
						capsule = 3;
				}
				break;

			case SOUTH:
				if (hitY >= 1.1 && hitY <= 1.3) {
					if (hitX >= 0.58 && hitX <= 0.81)
						capsule = 1;
					else if (hitX >= 0.18 && hitX <= 0.41)
						capsule = 0;
				} else if (hitY >= 0.78 && hitY <= 1) {
					if (hitX >= 0.58 && hitX <= 0.81)
						capsule = 3;
					else if (hitX >= 0.18 && hitX <= 0.41)
						capsule = 2;
				}
				break;

			case WEST:
				if (hitY >= 1.1 && hitY <= 1.3) {
					if (hitZ >= 0.58 && hitZ <= 0.81)
						capsule = 1;
					else if (hitZ >= 0.18 && hitZ <= 0.41)
						capsule = 0;
				} else if (hitY >= 0.78 && hitY <= 1) {
					if (hitZ >= 0.58 && hitZ <= 0.81)
						capsule = 3;
					else if (hitZ >= 0.18 && hitZ <= 0.41)
						capsule = 2;
				}
				break;
		}
		player.addChatComponentMessage(new ChatComponentText(String.format("%s, %s, %s, %s, %s", hitX, hitY, hitZ, ForgeDirection.getOrientation(rotation).name(), capsule)));
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		inventory.writeToByteBuff(buf);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		inventory.readFromByteBuff(buf);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord - 2, zCoord - 2, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
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
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}

	@Override
	public void openInventory() {
		inventory.openInventory();
	}

	@Override
	public void closeInventory() {
		inventory.closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
}
