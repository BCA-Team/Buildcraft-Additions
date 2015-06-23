package buildcraftAdditions.creative;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.reference.BlockLoader;
import buildcraftAdditions.reference.ItemLoader;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TabBCAdditions extends CreativeTabs {

	public TabBCAdditions() {
		super("BuildcraftAdditions");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Item.getItemFromBlock(BlockLoader.fluidicCompressorBlock);
	}

	@Override
	public void displayAllReleventItems(List list) {
		super.displayAllReleventItems(list);
		list.add(new ItemStack(ItemLoader.ironCanister));
		list.add(new ItemStack(ItemLoader.goldCanister));
		list.add(new ItemStack(ItemLoader.diamondCanister));
	}
}