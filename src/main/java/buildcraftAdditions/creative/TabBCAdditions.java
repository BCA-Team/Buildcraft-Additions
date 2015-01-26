package buildcraftAdditions.creative;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.reference.ItemsAndBlocks;

/**
 * Copyright (c) 2014, AEnterprise
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
		return Item.getItemFromBlock(ItemsAndBlocks.fluidicCompressorBlock);
	}

}
