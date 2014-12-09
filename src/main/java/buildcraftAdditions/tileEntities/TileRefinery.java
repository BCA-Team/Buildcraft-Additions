package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.multiBlocks.MultiBlockPatern;
import buildcraftAdditions.multiBlocks.MultiBlockPaternRefinery;
import buildcraftAdditions.tileEntities.Bases.TileBase;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileRefinery extends TileBase implements IMultiBlockTile, IFluidHandler {
	public int masterX, masterY, masterZ;
	public boolean isMaster, partOfMultiBlock;
	public MultiBlockPatern patern = new MultiBlockPaternRefinery();

	@Override
	public void updateEntity() {

	}

	@Override
	public void makeMaster() {
		System.out.println("VALID REFINERY");
		isMaster = true;
		partOfMultiBlock = true;
	}

	@Override
	public void sync() {

	}

	@Override
	public void invalidateMultiblock() {
		if (isMaster)
			patern.destroyMultiblock(worldObj, xCoord, yCoord, zCoord);
		else
			patern.destroyMultiblock(worldObj, masterX, masterY, masterZ);
	}

	@Override
	public boolean onBlockActivated(EntityPlayer player) {
		return false;
	}

	@Override
	public void formMultiblock(int masterX, int masterY, int masterZ) {
		partOfMultiBlock = true;
		this.masterX = masterX;
		this.masterY = masterY;
		this.masterZ = masterZ;
	}

	@Override
	public void invalidateBlock() {
		partOfMultiBlock = false;
		isMaster = false;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
		sync();
	}

	@Override
	public void moved(ForgeDirection direction) {

	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[0];
	}
}
