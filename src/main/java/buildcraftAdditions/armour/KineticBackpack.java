package buildcraftAdditions.armour;

import net.minecraft.item.ItemArmor;

import buildcraftAdditions.BuildcraftAdditions;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class KineticBackpack extends ItemArmor {

	public KineticBackpack() {
		super(ArmorMaterial.IRON, BuildcraftAdditions.proxy.addArmor("test"), 1);
	}
}
