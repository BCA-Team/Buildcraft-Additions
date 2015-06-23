package buildcraftAdditions.items.dust;

import net.minecraft.item.ItemStack;

import buildcraftAdditions.api.item.dust.IDust;
import buildcraftAdditions.api.item.dust.IDustType;
import buildcraftAdditions.reference.ItemLoader;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Dust implements IDust {

	private final String name;
	private final int meta, colorMultiplier;
	private final IDustType dustType;

	public Dust(int meta, String name, int colorMultiplier, IDustType dustType) {
		this.meta = meta;
		this.name = name;
		this.colorMultiplier = colorMultiplier;
		this.dustType = dustType;
	}

	@Override
	public int getMeta() {
		return meta;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getColorMultiplier() {
		return colorMultiplier;
	}

	@Override
	public IDustType getDustType() {
		return dustType;
	}

	@Override
	public ItemStack getDustStack() {
		return new ItemStack(ItemLoader.dust, 1, getMeta());
	}
}
