package buildcraftAdditions.items.Tools;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */


import net.minecraft.item.ItemSword;

@Deprecated
public abstract class ItemPoweredBase extends ItemSword {

	public ItemPoweredBase() {
		super(ToolMaterial.EMERALD);
	}

}
