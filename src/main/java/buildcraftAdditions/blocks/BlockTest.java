package buildcraftAdditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.multiBlocks.TestingPatern;
import buildcraftAdditions.utils.Location;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockTest extends Block {

	public BlockTest() {
		super(Material.iron);
		setBlockName("testBlock");
		setBlockTextureName("bcadditions:testBlock");
		setCreativeTab(BuildcraftAdditions.bcadditions);
		GameRegistry.registerBlock(this, "testingBlock");
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TestingPatern.build(new Location(world, x, y, z));
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
	}
}
