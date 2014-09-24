package buildcraftAdditions.tileEntities;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import buildcraftAdditions.tileEntities.Bases.TileCoilBase;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileLavaCoil extends TileCoilBase implements IFluidHandler {
	public static int lavaVar;
	public int lava;

	public TileLavaCoil() {
		super();
		burnTime = 0;
		fullBurnTime = 0;
		lava = 0;
		shouldHeat = false;
		burning = false;
	}

	public static int getLavaAmount() {
		return lavaVar;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		lava = tag.getInteger("lava");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("lava", lava);
	}

	@Override
	public void updateEntity() {
		lavaVar = lava;
		if (isBurning() && burnTime > 0)
			burnTime--;
		if (burnTime == 0)
			burning = false;
		if (!isBurning() && shouldHeat && lava >= 100) {
			lava = lava - 100;
			burnTime = 10000;
			increment = 32;
			burning = true;
		}
		if (!isBurning())
			increment = 0;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource.getFluid().getID() == FluidRegistry.LAVA.getID()) {
			int amount = 3000;
			if (doFill) {
				if (resource.amount > 3000 - lava)
					amount = 3000 - lava;
				else if (resource.amount < 3000 - lava)
					amount = resource.amount;
				lava = lava + amount;
				return amount;
			} else {
				if (resource.amount > 3000 - lava)
					amount = 3000 - lava;
				else if (resource.amount < 3000 - lava)
					amount = resource.amount;
				return amount;
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (doDrain) {
			int amount = maxDrain;
			if (maxDrain > lava)
				amount = lava;
			lava = lava - amount;
			return new FluidStack(FluidRegistry.LAVA, amount);
		} else {
			int amount = maxDrain;
			if (maxDrain > lava)
				amount = lava;
			return new FluidStack(FluidRegistry.LAVA, amount);
		}
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid == FluidRegistry.LAVA;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{new FluidTankInfo(new FluidStack(FluidRegistry.LAVA, lava), 3000)};
	}
}
