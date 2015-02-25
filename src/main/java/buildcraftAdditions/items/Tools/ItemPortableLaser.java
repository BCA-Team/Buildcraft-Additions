package buildcraftAdditions.items.Tools;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && player.isSneaking())
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.PORTABLE_LASER.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
		if (!player.isSneaking() && getEnergyStored(stack) >= ConfigurationHandler.portableLaserPowerUse) {
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote) {
				extractEnergy(stack, ConfigurationHandler.portableLaserPowerUse, false);
				world.spawnEntityInWorld(new EntityLaserShot(player.worldObj, player));
			}
		}
		return stack;
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
