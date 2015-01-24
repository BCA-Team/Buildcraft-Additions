package buildcraftAdditions.items;

import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemBucketBCA extends ItemBucket {

	private final FluidStack fluid;

	public ItemBucketBCA(Fluid fluid) {
		super(fluid.getBlock());
		this.fluid = new FluidStack(fluid.getID(), FluidContainerRegistry.BUCKET_VOLUME);
		setContainerItem(Items.bucket);
		setCreativeTab(BuildcraftAdditions.bcadditions);
		setUnlocalizedName("bcaBucket." + fluid.getName());
		setTextureName("bcadditions:bucket." + fluid.getName());
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return Utils.localizeFormatted("item.bcaBucket.name", fluid.getLocalizedName());
	}
}
