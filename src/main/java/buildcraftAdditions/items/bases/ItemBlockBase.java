package buildcraftAdditions.items.bases;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemBlockBase extends ItemBlock {

	public ItemBlockBase(Block block, String name, String texture, String registryName, CreativeTabs tab) {
		super(block);
		setUnlocalizedName(name);
		setTextureName(Variables.MOD.ID + ":" + texture);
		setCreativeTab(tab);
	}
}
