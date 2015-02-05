package buildcraftAdditions.utils;

import net.minecraftforge.fluids.FluidStack;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface IFluidAcceptor {

	boolean accepts(FluidStack fluidStack);

	String getDescription();

}
