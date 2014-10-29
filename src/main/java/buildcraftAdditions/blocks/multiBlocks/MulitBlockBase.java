package buildcraftAdditions.blocks.multiBlocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.MultiBlockPatern;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MulitBlockBase extends Block {
	public char identifier;
	public boolean isPartOfMultiblock;
	public MultiBlockPatern patern;

	public MulitBlockBase(char identifier, MultiBlockPatern patern) {
		super(Material.iron);
		setHardness(4f);
		setHarvestLevel(null, 0);
		this.identifier = identifier;
		this.patern = patern;
		isPartOfMultiblock = false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		patern.checkPatern(new Location(world, x, y, z));
		world.scheduleBlockUpdate(x, y, z, this, 80);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		world.scheduleBlockUpdate(x, y, z, this, 80);
	}

}
