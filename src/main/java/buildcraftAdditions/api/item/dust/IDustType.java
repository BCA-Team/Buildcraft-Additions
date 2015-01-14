package buildcraftAdditions.api.item.dust;

import net.minecraft.item.ItemStack;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface IDustType {

	void register(int meta, String name, ItemStack dust);

	String getName();

	boolean isValid(int meta, String name, ItemStack dust);

}
