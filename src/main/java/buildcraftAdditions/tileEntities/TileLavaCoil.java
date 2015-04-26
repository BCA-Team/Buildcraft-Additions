package buildcraftAdditions.tileEntities;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileCoilBase;
import buildcraftAdditions.utils.fluids.RestrictedTank;
import buildcraftAdditions.utils.fluids.WhitelistedTank;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileLavaCoil extends TileCoilBase implements IFluidHandler {

	private final RestrictedTank tank = new WhitelistedTank("LavaTank", 3 * FluidContainerRegistry.BUCKET_VOLUME, this, FluidRegistry.LAVA);

	public TileLavaCoil() {
		super(Variables.SyncIDs.LAVA_COIL.ordinal());
		burnTime = 0;
		fullBurnTime = 0;
		shouldHeat = false;
		burning = false;
	}

	public int getLavaAmount() {
		return tank.getFluidAmount();
	}

	public int getLavaCapacity() {
		return tank.getCapacity();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (tag.hasKey("LavaTank", Constants.NBT.TAG_COMPOUND))
			tank.readFromNBT(tag.getCompoundTag("LavaTank"));
		// TODO: Remove once everybody has updated
		if (tag.hasKey("lava", Constants.NBT.TAG_INT))
			tank.setFluid(new FluidStack(FluidRegistry.LAVA, tag.getInteger("lava")));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setTag("LavaTank", tank.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (isBurning() && burnTime > 0)
			burnTime--;
		if (burnTime == 0)
			burning = false;
		if (!isBurning() && shouldHeat && tank.getFluidAmount() >= 100) {
			tank.drain(100, true);
			burnTime = 5000;
			increment = ConfigurationHandler.lavaCoilHeat;
			burning = true;
		}
		if (!isBurning())
			increment = 0;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return tank.canFill(fluid);
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
