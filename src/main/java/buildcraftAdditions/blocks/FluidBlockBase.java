package buildcraftAdditions.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class FluidBlockBase extends BlockFluidClassic {
	Fluid fluid;

	public FluidBlockBase(Fluid fluid) {
		super(fluid, Material.water);
		this.fluid = fluid;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0 || side == 1)
			return fluid.getStillIcon();
		return fluid.getFlowingIcon();
	}
}
