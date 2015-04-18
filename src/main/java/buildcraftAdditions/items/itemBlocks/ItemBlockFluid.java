package buildcraftAdditions.items.itemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemBlockFluid extends ItemBlock {

	private final FluidStack fluidStack;

	public ItemBlockFluid(Block block) {
		super(block);
		if (block instanceof IFluidBlock) {
			Fluid fluid = ((IFluidBlock) block).getFluid();
			if (fluid != null)
				fluidStack = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
			else fluidStack = null;
		} else fluidStack = null;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return fluidStack != null ? fluidStack.getLocalizedName() : super.getItemStackDisplayName(stack);
	}
}
