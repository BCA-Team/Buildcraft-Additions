package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;

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
import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.RotationUtils;
import buildcraftAdditions.utils.Tank;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileRefinery extends TileBase implements IMultiBlockTile, IFluidHandler, IFlexibleCrafter, IEnergyHandler {
	public int masterX, masterY, masterZ, rotationIndex, timer, energy, maxEnergy, currentHeat, requiredHeat, energyCost, heatTimer, oldmasterX, oldmasterY, oldmasterZ;
	public boolean isMaster, partOfMultiBlock, init, valve, isCooling, moved;
	public MultiBlockPatern patern = new MultiBlockPaternRefinery();
	public TileRefinery master;
	public Tank input = new Tank(3000, this);
	public Tank output = new Tank(3000, this);
	private CraftingResult<FluidStack> currentResult;
	private IFlexibleRecipe<FluidStack> currentRecepie;

	public TileRefinery() {
		maxEnergy = 50000;
		timer = 0;
		init = false;
	}

	public void updateEntity() {
		if (moved) {
			if (!patern.isPaternValid(worldObj, masterX, masterY, masterZ, rotationIndex)) {
				patern.destroyMultiblock(worldObj, masterX, masterY, masterZ, rotationIndex);
				patern.destroyMultiblock(worldObj, oldmasterX, oldmasterY, oldmasterZ, rotationIndex);
			}
			moved = false;
		}
		if (input.getFluid() != null && input.getFluid().amount == 0)
			input.setFluid(null);
		if (input.getFluid() == null)
			updateRecepie();
		if (timer == 0) {
			sync();
			timer = 40;
		}
		timer--;
		updateHeat();
		if (!isMaster)
			return;
		energyCost = (currentResult == null || currentRecepie == null || isCooling || energy < (int) (50 + (50 * ((double) currentHeat / 100)))) ? 0 : (int) (50 + (50 * ((double) currentHeat / 100)));
		energy -= energyCost;
		if (currentResult == null || currentRecepie == null)
			return;
		if (currentHeat < requiredHeat) {
			return;
		}
		CraftingResult<FluidStack> r = currentRecepie.craft(this, false);

		if (r == null || r.crafted == null || energyCost == 0)
			return;
		output.fill(r.crafted.copy(), true);
	}

	private void updateHeat() {
		if (heatTimer == 0) {
			if ((currentHeat > requiredHeat || (energy < energyCost || energyCost == 0)) && currentHeat > 0) {
				currentHeat--;
				isCooling = true;
			}
			if (currentHeat < requiredHeat && (energy >= energyCost && energyCost != 0)) {
				currentHeat++;
				isCooling = false;
			}
			heatTimer = 10;
		}
		if (currentHeat == 0)
			isCooling = false;
		heatTimer -= 1;
	}

	private void updateRecepie() {
		for (IFlexibleRecipe<FluidStack> recepie : BuildcraftRecipeRegistry.refinery.getRecipes()) {
			currentResult = recepie.craft(this, true);
			if (currentResult != null) {
				currentRecepie = recepie;
				requiredHeat = currentResult.energyCost;
				return;
			}
		}
		requiredHeat = 0;
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
		if (isMaster) {
			patern.destroyMultiblock(worldObj, xCoord, yCoord, zCoord, rotationIndex);
		}
		else
			patern.destroyMultiblock(worldObj, masterX, masterY, masterZ, rotationIndex);
	}

	private void emptyTanks() {
		if (input.getFluid() == null || input.getFluid().amount < 1000)
			return;
		ForgeDirection[] directions = RotationUtils.rotateDirections(new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.EAST, ForgeDirection.UP}, rotationIndex);
		Location location = new Location(this);
		location.move(directions);
		while (input.getFluid().amount > 1000) {
			if (input.getFluidType().getBlock() != null)
				location.setBlock(input.getFluidType().getBlock());
			input.drain(1000, true);
			location.move(RotationUtils.rotatateDirection(ForgeDirection.NORTH, rotationIndex));
		}
		location.move(RotationUtils.rotatateDirection(ForgeDirection.NORTH, rotationIndex));
		if (output.getFluid() == null || output.getFluid().amount < 1000)
			return;
		while (output.getFluid().amount > 1000) {
			location.setBlock(output.getFluidType().getBlock());
			output.drain(1000, true);
			location.move(RotationUtils.rotatateDirection(ForgeDirection.NORTH, rotationIndex));
		}
	}

	@Override
	public boolean onBlockActivated(EntityPlayer player) {
		if (isMaster) {
			if (!worldObj.isRemote) {
				String fluid = "Nothing";
				if (input.getFluid() != null)
					fluid = input.getFluid().amount + " mb of " + input.getFluid().getLocalizedName();
				player.addChatComponentMessage(new ChatComponentText("Input:  " + fluid));
				fluid = "Nothing";
				if (output.getFluid() != null)
					fluid = output.getFluid().amount + " mb of " + output.getFluid().getLocalizedName();
				player.addChatComponentMessage(new ChatComponentText("Output:  " + fluid));
				player.addChatComponentMessage(new ChatComponentText("Energy stored: " + energy));
				player.addChatComponentMessage(new ChatComponentText("Heat: " + currentHeat));
				player.addChatComponentMessage(new ChatComponentText("Required Heat: " + requiredHeat));
				player.addChatComponentMessage(new ChatComponentText("Energy used: " + energyCost));
			}
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
		isMaster = tag.getBoolean("isMaster");
		partOfMultiBlock = tag.getBoolean("partOfMultiblock");
		valve = tag.getBoolean("valve");
		rotationIndex = tag.getInteger("rotationIndex");
		energy = tag.getInteger("energy");
		masterX = tag.getInteger("masterX");
		masterY = tag.getInteger("masterY");
		masterZ = tag.getInteger("masterZ");
		currentHeat = tag.getInteger("currentHeat");
		requiredHeat = tag.getInteger("requiredHeat");
		if (tag.hasKey("fluidIDinput") && tag.hasKey("fluidIDoutput")) {
			FluidStack stack;
			if (tag.getInteger("fluidIDinput") == -1)
				stack = null;
			else
				stack = new FluidStack(tag.getInteger("fluidIDinput"), tag.getInteger("fluidAmountInput"));
			input.setFluid(stack);
			if (tag.getInteger("fluidIDoutput") == -1)
				stack = null;
			else
				stack = new FluidStack(tag.getInteger("fluidIDoutput"), tag.getInteger("fluidAmountOutput"));
		}
		updateRecepie();
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setBoolean("isMaster", isMaster);
		tag.setBoolean("partOfMultiblock", partOfMultiBlock);
		tag.setBoolean("valve", valve);
		tag.setInteger("rotationIndex", rotationIndex);
		tag.setInteger("energy", energy);
		tag.setInteger("masterX", masterX);
		tag.setInteger("masterY", masterY);
		tag.setInteger("masterZ", masterZ);
		tag.setInteger("currentHeat", currentHeat);
		tag.setInteger("requiredHeat", requiredHeat);
		if (input.getFluid() == null) {
			tag.setInteger("fluidIDinput", -1);
		} else {
			tag.setInteger("fluidIDinput", input.getFluid().fluidID);
			tag.setInteger("fluidAmountInput", input.getFluid().amount);
		}
		if (output.getFluid() == null) {
			tag.setInteger("fluidIDoutput", -1);
		} else {
			tag.setInteger("fluidIDoutput", output.getFluid().fluidID);
			tag.setInteger("fluidAmountOutput", output.getFluid().amount);
		}
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
		if (isMaster)
			emptyTanks();
		partOfMultiBlock = false;
		isMaster = false;
		input.setFluid(null);
		output.setFluid(null);
		masterX = 0;
		masterY = 0;
		masterZ = 0;
		requiredHeat = 0;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
		worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, ItemsAndBlocks.refineryWalls, 80);
		sync();
	}

	@Override
	public void moved(ForgeDirection direction) {
		if (isMaster) {
			oldmasterX = xCoord;
			oldmasterY = yCoord;
			oldmasterZ = zCoord;
			masterX = xCoord + direction.offsetX;
			masterY = yCoord + direction.offsetY;
			masterZ = zCoord + direction.offsetZ;
			moved = true;
		} else {
			oldmasterX = masterX;
			oldmasterY = masterY;
			oldmasterZ = masterZ;
			moved = true;
			master = null;
			masterX += direction.offsetX;
			masterY += direction.offsetY;
			masterZ += direction.offsetZ;
		}

	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (!valve)
			return 0;
		if (master == null)
			findMaster();
		if (master != null)
			return master.realFill(resource, doFill);
		return 0;
	}

	public int realFill(FluidStack resource, boolean doFill) {
		int result = input.fill(resource, doFill);
		updateRecepie();
		return result;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (!valve)
			return null;
		if (master == null)
			findMaster();
		if (master != null)
			return master.realDrain(maxDrain, doDrain);
		return null;
	}

	public FluidStack realDrain(int maxDrain, boolean doDrain) {
		FluidStack result = output.drain(maxDrain, doDrain);
		updateRecepie();
		return result;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return partOfMultiBlock && valve;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return partOfMultiBlock && valve;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if (!valve)
			return null;
		if (master == null)
			findMaster();
		if (master != null)
			return master.realGetTankInfo();
		return new FluidTankInfo[]{new FluidTankInfo(input), new FluidTankInfo(output)};
	}

	public FluidTankInfo[] realGetTankInfo() {
		return new FluidTankInfo[]{new FluidTankInfo(input), new FluidTankInfo(output)};
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
		return true;
	}
}
