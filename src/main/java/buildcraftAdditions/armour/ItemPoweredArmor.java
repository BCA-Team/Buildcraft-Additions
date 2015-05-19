package buildcraftAdditions.armour;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemArmor;

import buildcraftAdditions.BuildcraftAdditions;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemPoweredArmor extends ItemArmor {

	public ItemPoweredArmor(String name, int slot) {
		super(ArmorMaterial.IRON, BuildcraftAdditions.proxy.addArmor(name), slot);
		setUnlocalizedName(name);
	}

	@Override
	public void registerIcons(IIconRegister register) {
	}

	@Override
	public boolean isDamageable() {
		return false;
	}
}
