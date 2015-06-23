package buildcraftAdditions.tileEntities;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.armour.ItemKineticBackpack;
import buildcraftAdditions.inventories.CustomInventory;
import buildcraftAdditions.reference.ArmorLoader;
import buildcraftAdditions.reference.ItemLoader;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileBase;
import buildcraftAdditions.utils.Utils;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileBackpackStand extends TileBase {
	public CustomInventory inventory = new CustomInventory("backpackStand", 1, 1, this);

	public TileBackpackStand() {
		super(Variables.SyncIDs.BACKPACK_STAND.ordinal());
	}

	public void onBlockActivated(float hitX, float hitY, float hitZ, int rotation, EntityPlayer player) {
		if (inventory.getStackInSlot(0) == null) {
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ArmorLoader.kineticBackpack) {
				inventory.setInventorySlotContents(0, player.getCurrentEquippedItem());
				player.destroyCurrentEquippedItem();
			}
		} else {
			if (player.getCurrentEquippedItem() == null && player.isSneaking()) {
				Utils.dropItemstackAtEntity(player, inventory.getStackInSlot(0));
				inventory.setInventorySlotContents(0, null);
				return;
			}
			int capsuleSlot = getSlot(rotation, hitX, hitY, hitZ);
			if (capsuleSlot == -1)
				return;
			ItemStack playerStack = player.getCurrentEquippedItem();
			if (playerStack == null)
				return;
			if (playerStack.getItem() == ItemLoader.powerCapsuleTier1 || playerStack.getItem() == ItemLoader.powerCapsuleTier2 || playerStack.getItem() == ItemLoader.powerCapsuleTier3) {
				ItemStack bStack = inventory.getStackInSlot(0);
				ItemKineticBackpack backpack = (ItemKineticBackpack) bStack.getItem();
				if (backpack.getInstalledCapsule(bStack, capsuleSlot) == 0) {
					backpack.installCapsule(bStack, capsuleSlot, playerStack);
					player.destroyCurrentEquippedItem();
				}
			}

		}
		sync();
	}

	public void removeCapsule(EntityPlayer player, int rotation, double hitX, double hitY, double hitZ) {
		if (inventory.getStackInSlot(0) == null)
			return;
		ItemStack bStack = inventory.getStackInSlot(0);
		ItemKineticBackpack backpack = (ItemKineticBackpack) bStack.getItem();
		int slot = getSlot(rotation, hitX, hitY, hitZ);
		if (backpack.getInstalledCapsule(bStack, slot) > 0)
			Utils.dropItemstacksAtEntity(player, Utils.getDropsForCapsule(backpack.removeCapsule(bStack, slot)));
	}

	private int getSlot(int rotation, double hitX, double hitY, double hitZ) {
		switch (ForgeDirection.getOrientation(rotation)) {
			case NORTH:
				if (hitY >= 1.1 && hitY <= 1.3) {
					if (hitX >= 0.58 && hitX <= 0.81)
						return 0;
					else if (hitX >= 0.18 && hitX <= 0.41)
						return 1;
				} else if (hitY >= 0.78 && hitY <= 1) {
					if (hitX >= 0.58 && hitX <= 0.81)
						return 2;
					else if (hitX >= 0.18 && hitX <= 0.41)
						return 3;
				}
				break;

			case EAST:
				if (hitY >= 1.1 && hitY <= 1.3) {
					if (hitZ >= 0.58 && hitZ <= 0.81)
						return 0;
					else if (hitZ >= 0.18 && hitZ <= 0.41)
						return 1;
				} else if (hitY >= 0.78 && hitY <= 1) {
					if (hitZ >= 0.58 && hitZ <= 0.81)
						return 2;
					else if (hitZ >= 0.18 && hitZ <= 0.41)
						return 3;
				}
				break;

			case SOUTH:
				if (hitY >= 1.1 && hitY <= 1.3) {
					if (hitX >= 0.58 && hitX <= 0.81)
						return 1;
					else if (hitX >= 0.18 && hitX <= 0.41)
						return 0;
				} else if (hitY >= 0.78 && hitY <= 1) {
					if (hitX >= 0.58 && hitX <= 0.81)
						return 3;
					else if (hitX >= 0.18 && hitX <= 0.41)
						return 2;
				}
				break;

			case WEST:
				if (hitY >= 1.1 && hitY <= 1.3) {
					if (hitZ >= 0.58 && hitZ <= 0.81)
						return 1;
					else if (hitZ >= 0.18 && hitZ <= 0.41)
						return 0;
				} else if (hitY >= 0.78 && hitY <= 1) {
					if (hitZ >= 0.58 && hitZ <= 0.81)
						return 3;
					else if (hitZ >= 0.18 && hitZ <= 0.41)
						return 2;
				}
				break;
		}
		return -1;
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
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		inventory.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		inventory.writeToNBT(tag);
	}
}
