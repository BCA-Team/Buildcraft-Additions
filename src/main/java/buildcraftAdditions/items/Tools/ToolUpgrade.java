package buildcraftAdditions.items.Tools;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.BuildcraftAdditions;

public class ToolUpgrade extends Item {
	public String type;
	public IIcon icon;

	public ToolUpgrade(String upgrade) {
		this.setMaxStackSize(16);
		this.setCreativeTab(BuildcraftAdditions.bcadditions);
		this.setUnlocalizedName("toolUpgrade" + upgrade);
		this.type = upgrade;
	}

	public String getType() {
		return type;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		icon = par1IconRegister.registerIcon("bcadditions:" + getType() + "Upgrade");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		return icon;
	}
}
