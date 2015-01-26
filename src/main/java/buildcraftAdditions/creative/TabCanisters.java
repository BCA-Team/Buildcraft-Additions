package buildcraftAdditions.creative;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.reference.ItemsAndBlocks;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TabCanisters extends CreativeTabs {

	public TabCanisters() {
		super("bcaCanisters");
		setBackgroundImageName("item_search.png");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() {
		return ItemsAndBlocks.diamondCanister.getFilledItemStack(new FluidStack(FluidRegistry.getFluid("fuel"), ItemsAndBlocks.diamondCanister.getCapacity()));
	}

	@Override
	public boolean hasSearchBar() {
		return true;
	}
}