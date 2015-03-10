package buildcraftAdditions.items.itemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import buildcraftAdditions.blocks.FluidBlockBase;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemBlockFluid extends ItemBlock {

	public ItemBlockFluid(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (field_150939_a instanceof FluidBlockBase) {
			return ((FluidBlockBase) field_150939_a).getFluidStack().getLocalizedName();
		}
		return super.getItemStackDisplayName(stack);
	}
}
