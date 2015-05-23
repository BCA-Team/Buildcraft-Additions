package buildcraftAdditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class BlockBase extends BlockContainer {
	protected final String name;

	public BlockBase(String name) {
		this(Material.iron, name, name, name, BuildcraftAdditions.bcadditions, null);
	}

	public BlockBase(Material material, String name) {
		this(material, name, name, name, BuildcraftAdditions.bcadditions, null);
	}

	public BlockBase(String name, String texture, String gameRegistryName) {
		this(Material.iron, name, texture, gameRegistryName, BuildcraftAdditions.bcadditions, null);
	}

	public BlockBase(Material material, String name, String texture, String gameRegistryName, CreativeTabs tab, Class<? extends ItemBlock> clas) {
		super(material);
		this.name = name;
		setBlockName(name);
		setBlockTextureName("bcadditions:" + texture);
		setHardness(5F);
		setResistance(10F);
		setCreativeTab(tab);
		if (clas == null) {
			GameRegistry.registerBlock(this, gameRegistryName);
		} else {
			GameRegistry.registerBlock(this, clas, gameRegistryName);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		Utils.dropInventory(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}
}
