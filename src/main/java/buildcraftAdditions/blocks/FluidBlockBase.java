package buildcraftAdditions.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

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
		setBlockName(fluid.getUnlocalizedName());
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0 || side == 1)
			return fluid.getStillIcon();
		return fluid.getFlowingIcon();
	}

	@Override
	public int getRenderColor(int p_149741_1_) {
		return fluid.getColor();
	}

	@Override
	public int getBlockColor() {
		return fluid.getColor();
	}

	@Override
	public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
		return fluid.getColor();
	}
}
