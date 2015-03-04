package buildcraftAdditions.armour;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import cofh.api.energy.IEnergyContainerItem;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.client.models.BackPackModel;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemKineticBackpack extends ItemArmor implements IEnergyContainerItem {

	public ItemKineticBackpack() {
		super(ArmorMaterial.IRON, BuildcraftAdditions.proxy.addArmor("kineticBackpack"), 1);
		setTextureName("bcadditions:kineticBackpack");
		setUnlocalizedName("kineticBackpack");
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		setMaxEnergy(itemStack, 1000000); //this is temporal until i have the armourstand thingy
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null && stack.getItem() instanceof IEnergyContainerItem) {
			IEnergyContainerItem eci = (IEnergyContainerItem) stack.getItem();
			int transfer = Math.min(eci.receiveEnergy(player.getCurrentEquippedItem(), getMaxEnergyStored(itemStack), true), 20000);
			eci.receiveEnergy(player.getCurrentEquippedItem(), transfer, false);
			extractEnergy(itemStack, transfer, player.capabilities.isCreativeMode); //don't use power if player is in creative
		}
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		int recieved = maxReceive;
		if (recieved > getMaxEnergyStored(container) - getEnergyStored(container))
			recieved = getMaxEnergyStored(container) - getEnergyStored(container);
		if (!simulate)
			setEnergy(container, getEnergyStored(container) + recieved);
		return recieved;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		int max = Math.min(getMaxEnergyStored(container), maxExtract);
		if (!simulate)
			setEnergy(container, getEnergyStored(container) - max);
		return max;
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

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean sneaking) {
		list.add(Utils.localizeFormatted("rf.info", getEnergyStored(stack), getMaxEnergyStored(stack)));
	}

	private void tagTest(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("maxEnergy", 0);
			stack.stackTagCompound.setInteger("energy", 0);
		}
	}

	private void setEnergy(ItemStack stack, int energy) {
		tagTest(stack);
		stack.stackTagCompound.setInteger("energy", energy);
	}

	private void setMaxEnergy(ItemStack stack, int maxEnergy) {
		tagTest(stack);
		stack.stackTagCompound.setInteger("maxEnergy", maxEnergy);
	}

	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return new BackPackModel();
	}
}
