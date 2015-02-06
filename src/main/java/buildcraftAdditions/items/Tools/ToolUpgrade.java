package buildcraftAdditions.items.Tools;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.item.Item;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;

public class ToolUpgrade extends Item {

	private final String type;

	public ToolUpgrade(String upgrade) {
		setMaxStackSize(16);
		setCreativeTab(BuildcraftAdditions.bcadditions);
		setUnlocalizedName("toolUpgrade" + upgrade);
		setTextureName(Variables.MOD.ID + ":" + upgrade + "Upgrade");
		type = upgrade;
	}

	public String getType() {
		return type;
	}

}
