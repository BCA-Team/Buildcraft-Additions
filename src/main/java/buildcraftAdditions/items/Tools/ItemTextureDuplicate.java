package buildcraftAdditions.items.Tools;

import net.minecraft.item.ItemBucket;
import net.minecraft.util.IIcon;

import net.minecraftforge.fluids.Fluid;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemTextureDuplicate extends ItemBucket {
	public IIcon texture;
	public String name;

	public ItemTextureDuplicate(String name, IIcon icon, Fluid fluid) {
		super(fluid.getBlock());
		this.texture = icon;
		this.name = name;
	}

	@Override
	public IIcon getIconFromDamage(int damage) {
		return texture;
	}

	@Override
	public String getUnlocalizedName() {
		return name;
	}
}
