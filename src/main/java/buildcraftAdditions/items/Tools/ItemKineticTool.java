package buildcraftAdditions.items.Tools;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cofh.api.energy.IEnergyContainerItem;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@Deprecated
public class ItemKineticTool extends ItemPoweredBase implements IEnergyContainerItem {

	public ItemKineticTool() {
		super();
		setUnlocalizedName("kineticMultiTool");
		setMaxStackSize(1);
	}

	@Override
	public boolean isItemTool(ItemStack stack) {
		return false;
	}


	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		return 0;
	}

	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean visible) {
		list.add("Deprecated item, please place this in a crafting grid to convert this to the new kinetic multi-tool.");
		list.add("All upgrades and the inventory will be saved.");
		list.add("My apologies for this and any issues this might have caused");
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.none;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return HashMultimap.create();
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
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
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return 0;
	}
}
