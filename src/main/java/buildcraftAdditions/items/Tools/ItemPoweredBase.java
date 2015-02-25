package buildcraftAdditions.items.Tools;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */


import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.core.InventoryTool;
import buildcraftAdditions.items.BatteryBase;
import buildcraftAdditions.utils.Utils;

@Deprecated
public abstract class ItemPoweredBase extends ItemSword {

	public int x, y, z;
	public World world;
	public int storageB1, storageB2, storageB3;
	public int energyB1, energyB2, energyB3;
	EntityPlayer player;

	public ItemPoweredBase() {
		super(ToolMaterial.EMERALD);
	}

	public static IInventory getInventory(EntityPlayer player) {
		ItemStack tool;
		IInventory toolInventory = null;
		tool = player.getCurrentEquippedItem();

		if (tool != null && tool.getItem() instanceof ItemPoweredBase) {
			toolInventory = new InventoryTool(tool);
		}

		return toolInventory;
	}

	public static IInventory getInventory(ItemStack stack) {
		IInventory toolInventory = null;

		if (stack != null && stack.getItem() instanceof ItemPoweredBase) {
			toolInventory = new InventoryTool(stack);
		}

		return toolInventory;
	}

	@Override
	public boolean isItemTool(ItemStack stack) {
		return true;
	}

	public void decreaseEnergy(ItemStack stack, int energy) {
		if (energyB1 - energy <= 0) {
			energy -= energyB1;
			energyB1 = 0;
		}
		if (energyB1 > energy) {
			energyB1 -= energy;
			energy = 0;
		}
		if (energyB2 - energy <= 0) {
			energy -= energyB1;
			energyB2 = 0;
		}
		if (energyB2 > energy) {
			energyB2 -= energy;
			energy = 0;
		}
		if (energyB3 - energy <= 0) {
			energy -= energyB3;
			energyB3 = 0;
		}
		if (energyB3 > energy) {
			energyB3 -= energy;
		}
		writeBateries(stack);
		readBateries(stack);
	}

	public int getEnergy() {
		return energyB1 + energyB2 + energyB3;
	}

	public int getCapacity() {
		return storageB1 + storageB2 + storageB3;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = player.worldObj;
		this.player = player;
		readBateries(stack);
		if (getEnergy() <= world.getBlock(x, y, z).getBlockHardness(world, x, y, z)) {
			player.addChatComponentMessage(new ChatComponentText(Utils.localize("kineticTool.outOfPower")));
			return true;
		}
		return false;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
		decreaseEnergy(stack, (int) (block.getBlockHardness(world, x, y, z) * (ConfigurationHandler.powerDifficultyModifiers[world.difficultySetting.getDifficultyId()]) * ConfigurationHandler.basePowerModifier));

		return false;
	}

	public void readBateries(ItemStack stack) {
		IInventory inventory = getInventory(stack);
		inventory.openInventory();
		IEnergyContainerItem battery;
		ItemStack batteryStack = inventory.getStackInSlot(0);
		storageB1 = 0;
		storageB2 = 0;
		storageB3 = 0;
		energyB1 = 0;
		energyB2 = 0;
		energyB3 = 0;
		if (batteryStack != null && batteryStack.getItem() instanceof IEnergyContainerItem) {
			battery = (IEnergyContainerItem) batteryStack.getItem();
			storageB1 = battery.getMaxEnergyStored(batteryStack);
			energyB1 = battery.getEnergyStored(batteryStack);
		}
		batteryStack = inventory.getStackInSlot(1);
		if (batteryStack != null && batteryStack.getItem() instanceof IEnergyContainerItem) {
			battery = (IEnergyContainerItem) batteryStack.getItem();
			storageB2 += battery.getMaxEnergyStored(batteryStack);
			energyB2 = battery.getEnergyStored(batteryStack);
		}
		batteryStack = inventory.getStackInSlot(2);
		if (batteryStack != null && batteryStack.getItem() instanceof IEnergyContainerItem) {
			battery = (IEnergyContainerItem) batteryStack.getItem();
			storageB3 = battery.getMaxEnergyStored(batteryStack);
			energyB3 = battery.getEnergyStored(batteryStack);
		}
		stack.getItem().setMaxDamage(storageB1 + storageB2 + storageB3);
		stack.getItem().setDamage(stack, storageB1 + storageB2 + storageB3 - energyB1 - energyB2 - energyB3);
	}

	public void writeBateries(ItemStack stack) {
		IInventory inventory = getInventory(stack);
		inventory.openInventory();
		BatteryBase battery = null;
		ItemStack batteryStack = inventory.getStackInSlot(0);
		if (batteryStack != null) {
			battery = (BatteryBase) batteryStack.getItem();
			battery.setEnergy(batteryStack, energyB1);
		}
		batteryStack = inventory.getStackInSlot(1);
		if (batteryStack != null) {
			battery = (BatteryBase) batteryStack.getItem();
			battery.setEnergy(batteryStack, energyB2);
		}
		batteryStack = inventory.getStackInSlot(2);
		if (batteryStack != null) {
			battery = (BatteryBase) batteryStack.getItem();
			battery.setEnergy(batteryStack, energyB3);
		}
	}


}
