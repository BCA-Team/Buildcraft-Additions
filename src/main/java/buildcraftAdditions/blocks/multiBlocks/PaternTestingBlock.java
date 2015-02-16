package buildcraftAdditions.blocks.multiBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import buildcraftAdditions.multiBlocks.TestingPatern;
import buildcraftAdditions.utils.Location;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class PaternTestingBlock extends Block {

	public PaternTestingBlock() {
		super(Material.iron);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TestingPatern.build(new Location(world, x, y, z));
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
	}
}
