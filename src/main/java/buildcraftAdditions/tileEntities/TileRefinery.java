package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.multiBlocks.MultiBlockPatern;
import buildcraftAdditions.multiBlocks.MultiBlockPaternRefinery;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.tileEntities.Bases.TileBase;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileRefinery extends TileBase implements IMultiBlockTile, IFluidHandler {
	public int masterX, masterY, masterZ, rotationIndex;
	public boolean isMaster, partOfMultiBlock;
	public MultiBlockPatern patern = new MultiBlockPaternRefinery();

	@Override
	public void updateEntity() {
	}

	@Override
	public void makeMaster(int rotationIndex) {
		System.out.println("VALID REFINERY");
		isMaster = true;
		partOfMultiBlock = true;
		this.rotationIndex = rotationIndex;
	}

	@Override
	public void sync() {

	}

	@Override
	public void invalidateMultiblock() {
		if (isMaster)
			patern.destroyMultiblock(worldObj, xCoord, yCoord, zCoord, rotationIndex);
		else
			patern.destroyMultiblock(worldObj, masterX, masterY, masterZ, rotationIndex);
	}

	@Override
	public boolean onBlockActivated(EntityPlayer player) {
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		rotationIndex = tag.getInteger("rotationIndex");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("rotationIndex", rotationIndex);
	}

	@Override
	public void formMultiblock(int masterX, int masterY, int masterZ, int rotationIndex) {
		partOfMultiBlock = true;
		this.masterX = masterX;
		this.masterY = masterY;
		this.masterZ = masterZ;
		this.rotationIndex = rotationIndex;
	}

	@Override
	public void invalidateBlock() {
		partOfMultiBlock = false;
		isMaster = false;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
		worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, ItemsAndBlocks.refineryWalls, 80);
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