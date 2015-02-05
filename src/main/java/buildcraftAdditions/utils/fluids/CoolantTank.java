package buildcraftAdditions.utils.fluids;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fluids.FluidStack;

import buildcraft.energy.fuels.CoolantManager;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class CoolantTank extends RestrictedTank {

	public CoolantTank(String name, int capacity, TileEntity tile) {
		super(name, capacity, tile, new IFluidAcceptor() {
			@Override
			public boolean accepts(FluidStack fluidStack) {
				return CoolantManager.INSTANCE.getCoolant(fluidStack.getFluid()) != null;
			}

			@Override
			public String getDescription() {
				return "Only accepts fluids that are registered as coolants in the BC api";
			}
		});
	}
}
