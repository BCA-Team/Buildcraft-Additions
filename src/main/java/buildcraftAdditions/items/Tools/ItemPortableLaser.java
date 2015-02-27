package buildcraftAdditions.items.Tools;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.entities.EntityLaserShot;
import buildcraftAdditions.inventories.InventoryPortableLaser;
import buildcraftAdditions.items.ItemBase;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemPortableLaser extends ItemBase implements IEnergyContainerItem {

	public ItemPortableLaser() {
		super("portableLaser");
		setNoRepair();
		setMaxStackSize(1);
		setFull3D();
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return Integer.MAX_VALUE;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && player.isSneaking()) {
			MovingObjectPosition mop = getMovingObjectPositionFromPlayer(world, player, true);
			if (mop == null || mop.typeOfHit == MovingObjectPosition.MovingObjectType.MISS || mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
				player.openGui(BuildcraftAdditions.instance, Variables.Gui.PORTABLE_LASER.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
				return stack;
			}
		}
		if (getEnergyStored(stack) > 0)
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int count) {
		float j = getMaxItemUseDuration(stack) - count;
		float f = j / 20;
		f = (f * f + f * 2) / 3;
		if (f < 0.1)
			return;
		if (f > 1)
			f = 1;
		f *= 2;
		if (getEnergyStored(stack) < (int) (f * ConfigurationHandler.portableLaserPowerUse))
			return;
		extractEnergy(stack, (int) (f * ConfigurationHandler.portableLaserPowerUse), false);
		world.playSoundAtEntity(player, "random.bow", 1, 1 / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.25F);
		if (!world.isRemote)
			world.spawnEntityInWorld(new EntityLaserShot(world, player, f));
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		float j = getMaxItemUseDuration(stack) - (count + 1);
		float f = j / 20;
		f = (f * f + f * 2) / 3;
		if (f < 0.1)
			return;
		if (f > 1)
			f = 1;
		f *= 2;
		if (getEnergyStored(stack) < f * ConfigurationHandler.portableLaserPowerUse)
			player.stopUsingItem();
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getMaxEnergyStored(stack) > 0;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		double maxEnergy = getMaxEnergyStored(stack);
		if (maxEnergy <= 0)
			return 1;
		return (maxEnergy - getEnergyStored(stack)) / maxEnergy;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible) {
		list.add(Utils.localizeFormatted("rf.info", getEnergyStored(stack), getMaxEnergyStored(stack)));
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		InventoryPortableLaser inv = new InventoryPortableLaser(container);
		int received = 0;
		for (int i = 0; i < inv.getSizeInventory() && maxReceive - received > 0; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
				received += item.receiveEnergy(stack, maxReceive - received, simulate);
			}
		}
		inv.writeToNBT();
		return received;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		InventoryPortableLaser inv = new InventoryPortableLaser(container);
		int extracted = 0;
		for (int i = 0; i < inv.getSizeInventory() && maxExtract - extracted > 0; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
				extracted += item.extractEnergy(stack, maxExtract - extracted, simulate);
			}
		}
		inv.writeToNBT();
		return extracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		InventoryPortableLaser inv = new InventoryPortableLaser(container);
		int stored = 0;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
				stored += item.getEnergyStored(stack);
			}
		}
		inv.writeToNBT();
		return stored;
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		InventoryPortableLaser inv = new InventoryPortableLaser(container);
		int maxStored = 0;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.getItem() != null && stack.getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
				maxStored += item.getMaxEnergyStored(stack);
			}
		}
		inv.writeToNBT();
		return maxStored;
	}

}
