package buildcraftAdditions.utils.fluids;

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
public class RestrictedTank extends Tank {

	protected final IFluidAcceptor acceptor;

	public RestrictedTank(String name, int capacity, TileEntity tile, IFluidAcceptor acceptor) {
		super(capacity, tile, name);
		this.acceptor = acceptor;
	}

	@Override
	public void setFluid(FluidStack fluid) {
		if (acceptor.accepts(fluid))
			super.setFluid(fluid);
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (acceptor.accepts(resource))
			return super.fill(resource, doFill);
		return 0;
	}

	public boolean canFill(Fluid fluid) {
		return acceptor.accepts(new FluidStack(fluid, 1));
	}

	@Override
	public String toString() {
		return super.toString() + ", Restrictions: [" + acceptor.getDescription() + "]";
	}

}
