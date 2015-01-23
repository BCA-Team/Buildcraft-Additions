package buildcraftAdditions.items;

import net.minecraft.item.ItemBucket;

import net.minecraftforge.fluids.Fluid;

import buildcraftAdditions.BuildcraftAdditions;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemBucketBCA extends ItemBucket {

	public ItemBucketBCA(Fluid fluid) {
		super(fluid.getBlock());
		setCreativeTab(BuildcraftAdditions.bcadditions);
		setUnlocalizedName("bucket." + fluid.getUnlocalizedName());
		setTextureName("bcadditions:bucket." + fluid.getName());
	}

}
