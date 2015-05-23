package buildcraftAdditions.items.bases;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

public class ItemBase extends Item {

	private final String name;

	public ItemBase(String name) {
		this(name, name);
	}

	public ItemBase(String name, String texture) {
		this(name, name, texture, BuildcraftAdditions.bcadditions);
	}

	public ItemBase(String name, String registryName, String texture, CreativeTabs tab) {
		this.name = name;
		setUnlocalizedName(name);
		setTextureName(Variables.MOD.ID + ":" + texture);
		setCreativeTab(tab);
		GameRegistry.registerItem(this, registryName);

	}

	public ItemBase(String name, String texture, CreativeTabs tab) {
		this(name, name, texture, tab);
	}

	public ItemBase(String name, String texture, String gameRegistryName) {
		this(name, texture, gameRegistryName, BuildcraftAdditions.bcadditions);
	}

	public String getName() {
		return name;
	}

}
