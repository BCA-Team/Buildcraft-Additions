package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.NetworkRegistry;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import cofh.api.energy.IEnergyHandler;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.CraftingResult;
import buildcraft.api.recipes.IFlexibleCrafter;
import buildcraft.api.recipes.IFlexibleRecipe;

import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.multiBlocks.MultiBlockPatern;
import buildcraftAdditions.multiBlocks.MultiBlockPaternRefinery;
import buildcraftAdditions.networking.MessageRefinery;
import buildcraftAdditions.networking.PacketHandeler;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.tileEntities.Bases.TileBase;
import buildcraftAdditions.utils.Tank;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileRefinery extends TileBase implements IMultiBlockTile, IFluidHandler, IFlexibleCrafter, IEnergyHandler {
	public int masterX, masterY, masterZ, rotationIndex, timer, energy, maxEnergy;
	public boolean isMaster, partOfMultiBlock;
	public MultiBlockPatern patern = new MultiBlockPaternRefinery();
	public TileRefinery master;
	public Tank input = new Tank(10000, this);
	public Tank output = new Tank(10000, this);
	private CraftingResult<FluidStack> currentResult;
	private IFlexibleRecipe<FluidStack> currentRecepie;

	public TileRefinery() {
		maxEnergy = 50000;
		timer = 0;
	}

	public void updateEntity() {
		if (timer == 0) {
			sync();
			timer = 40;
		}
		timer--;
		if (!isMaster)
			return;

		if (currentRecepie == null)
			return;

		if (energy < currentResult.energyCost) {
			return;
		}
		CraftingResult<FluidStack> r = currentRecepie.craft(this, false);
		output.fill(r.crafted.copy(), true);
	}

	private void updateRecepie() {
		for (IFlexibleRecipe<FluidStack> recepie : BuildcraftRecipeRegistry.refinery.getRecipes()) {
			currentResult = recepie.craft(this, true);
			if (currentResult != null) {
				currentRecepie = recepie;
				break;
			}
		}
	}

	@Override
	public void makeMaster(int rotationIndex) {
		isMaster = true;
		partOfMultiBlock = true;
		this.rotationIndex = rotationIndex;
	}

	public void findMaster() {
		TileEntity entity = worldObj.getTileEntity(masterX, masterY, masterZ);
		if (entity instanceof TileRefinery) {
			master = (TileRefinery) entity;
		}
		if (master == null || !master.isMaster) {
			master = null;
			invalidateMultiblock();
		}
	}

	@Override
	public void sync() {
		if (!worldObj.isRemote)
			PacketHandeler.instance.sendToAllAround(new MessageRefinery(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 20));
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
		if (isMaster) {
			return true;
		}
		if (partOfMultiBlock) {
			if (master == null)
				findMaster();
			if (master != null)
				return master.onBlockActivated(player);
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		rotationIndex = tag.getInteger("rotationIndex");
		energy = tag.getInteger("energy");
		masterX = tag.getInteger("masterX");
		masterY = tag.getInteger("masterY");
		masterZ = tag.getInteger("masterZ");
		updateRecepie();
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("rotationIndex", rotationIndex);
		tag.setInteger("energy", energy);
		tag.setInteger("masterX", masterX);
		tag.setInteger("masterY", masterY);
		tag.setInteger("masterZ", masterZ);
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
		if (isMaster) {
			int result = input.fill(resource, doFill);
			updateRecepie();
			return result;
		} else {
			if (master == null)
				findMaster();
			if (master != null)
				return master.fill(from, resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (isMaster) {
			FluidStack result = output.drain(maxDrain, doDrain);
			updateRecepie();
			return result;
		} else {
			if (master == null)
				findMaster();
			if (master != null)
				return master.drain(from, maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return partOfMultiBlock;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return partOfMultiBlock;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if (isMaster)
			return new FluidTankInfo[]{new FluidTankInfo(input), new FluidTankInfo(output)};
		if (partOfMultiBlock) {
			if (master == null)
				findMaster();
			if (master != null)
				return master.getTankInfo(from);
		}
		return null;
	}

	@Override
	public int getCraftingItemStackSize() {
		return 0;
	}

	@Override
	public ItemStack getCraftingItemStack(int slotid) {
		return null;
	}

	@Override
	public ItemStack decrCraftingItemStack(int slotid, int val) {
		return null;
	}

	@Override
	public FluidStack getCraftingFluidStack(int tankid) {
		return input.getFluid();
	}

	@Override
	public FluidStack decrCraftingFluidStack(int tankid, int amount) {
		FluidStack fluid = null;
		if (amount > input.getFluidAmount()) {
			fluid = input.getFluid();
			input.setFluid(null);
		} else {
			fluid = input.getFluid().copy();
			fluid.amount = amount;
			input.getFluid().amount -= amount;
		}
		return fluid;
	}

	@Override
	public int getCraftingFluidStackSize() {
		return 1;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (isMaster) {
			int recieved = maxReceive;
			if (recieved > maxEnergy - energy)
				recieved = maxEnergy - energy;
			if (!simulate)
				energy += recieved;
			return recieved;
		} else {
			if (master == null)
				findMaster();
			if (master != null)
				return master.receiveEnergy(from, maxReceive, simulate);
		}
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if (isMaster)
			return energy;
		else {
			if (master == null)
				findMaster();
			if (master != null)
				return master.getEnergyStored(from);
		}
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		if (isMaster)
			return maxEnergy;
		else {
			if (master == null)
				findMaster();
			if (master != null)
				return master.getMaxEnergyStored(from);
		}
		return 0;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return partOfMultiBlock;
	}
}
