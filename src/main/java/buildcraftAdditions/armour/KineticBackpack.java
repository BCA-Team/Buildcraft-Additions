package buildcraftAdditions.armour;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.BuildcraftAdditions;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class KineticBackpack extends ItemArmor implements IEnergyContainerItem {

	public KineticBackpack() {
		super(ArmorMaterial.IRON, BuildcraftAdditions.proxy.addArmor("kineticBackpack"), 1);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null && stack.getItem() instanceof IEnergyContainerItem) {
			((IEnergyContainerItem) stack.getItem()).receiveEnergy(player.getCurrentEquippedItem(), 20000, false);
		}
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		tagTest(container);
		return container.stackTagCompound.getInteger("energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		tagTest(container);
		return container.stackTagCompound.getInteger("maxEnergy");
	}

	private void tagTest(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("maxEnergy", 0);
			stack.stackTagCompound.setInteger("energy", 0);
		}
	}
}
