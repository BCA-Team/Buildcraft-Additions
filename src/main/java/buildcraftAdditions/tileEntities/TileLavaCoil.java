package buildcraftAdditions.tileEntities;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import buildcraft.core.fluids.Tank;
import buildcraft.core.fluids.TankManager;

import buildcraftAdditions.tileEntities.Bases.TileCoilBase;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileLavaCoil extends TileCoilBase implements IFluidHandler {
	public Tank tank = new Tank("lavaTank", 3000, this);
	public TankManager manager = new TankManager(tank);

	public TileLavaCoil(){
		super();
		burnTime = 0;
		fullBurnTime = 0;
		shouldHeat = false;
		burning = false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		manager.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		manager.writeToNBT(tag);
	}

	@Override
	public void updateEntity() {
		if (isBurning() && burnTime > 0)
			burnTime--;
		if (burnTime == 0)
			burning = false;
		if (!isBurning() && shouldHeat && !tank.isEmpty() && tank.drain(100, false).amount == 100){
			tank.drain(100, true);
			burnTime = 10000;
			increment = 32;
			burning = true;
		}
		if (!isBurning())
			increment = 0;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource.getFluid().getID() == FluidRegistry.LAVA.getID())
			return tank.fill(resource, doFill);
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
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
		return new FluidTankInfo[]{tank.getInfo()};
	}
}
