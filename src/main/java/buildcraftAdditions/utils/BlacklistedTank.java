package buildcraftAdditions.utils;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlacklistedTank extends RestrictedTank {

	public BlacklistedTank(String name, int capacity, TileEntity tile, final Fluid... fluids) {
		super(name, capacity, tile, new IFluidAcceptor() {

			@Override
			public boolean accepts(FluidStack fluidStack) {
				if (fluidStack == null)
					return false;
				Fluid fluid = fluidStack.getFluid();
				if (fluid == null)
					return false;
				if (fluids == null)
					return true;
				for (Fluid f : fluids)
					if (f != null && f.getID() == fluid.getID())
						return false;
				return true;
			}

			@Override
			public String getDescription() {
				ArrayList<String> list = new ArrayList<String>();
				if (fluids != null)
					for (Fluid f : fluids)
						if (f != null)
							list.add(f.getName());
				list.trimToSize();
				return "Blacklist: " + list.toString();
			}

		});
	}
}
