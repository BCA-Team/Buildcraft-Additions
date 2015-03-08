package buildcraftAdditions.tileEntities.Bases;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.Explosion;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

import buildcraftAdditions.api.configurableOutput.EnumPriority;
import buildcraftAdditions.api.configurableOutput.EnumSideStatus;
import buildcraftAdditions.api.configurableOutput.IConfigurableOutput;
import buildcraftAdditions.api.configurableOutput.SideConfiguration;
import buildcraftAdditions.api.networking.ISynchronizedTile;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.networking.MessageConfiguration;
import buildcraftAdditions.networking.MessageSelfDestruct;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.tileEntities.TileKineticEnergyBufferTier1;
import buildcraftAdditions.tileEntities.interfaces.IOwnableMachine;
import buildcraftAdditions.utils.PlayerUtils;
import buildcraftAdditions.utils.Utils;

import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileKineticEnergyBufferBase extends TileBase implements IEnergyReceiver, IEnergyProvider, IConfigurableOutput, ISynchronizedTile, IOwnableMachine {

	public final int tier;
	protected final SideConfiguration configuration = new SideConfiguration();
	protected final boolean[] blocked = new boolean[6];
	public int energy, maxEnergy, maxInput, maxOutput, loss, fuse;
	public boolean selfDestruct, engineControl;
	public EntityPlayer destroyer;
	private UUID owner;


	public TileKineticEnergyBufferBase(int maxEnergy, int maxInput, int maxOutput, int loss, int tier) {
		super();
		this.maxEnergy = maxEnergy;
		this.maxInput = maxInput;
		this.maxOutput = maxOutput;
		this.loss = loss;
		this.tier = tier;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (!configuration.canReceive(from))
			return 0;
		int recieved = maxReceive;
		if (recieved > maxEnergy - energy)
			recieved = maxEnergy - energy;
		if (recieved > maxInput)
			recieved = maxInput;
		if (!simulate) {
			energy += recieved;
			blocked[from.ordinal()] = true;
		}
		return recieved;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if (!configuration.canSend(from))
			return 0;
		int extracted = maxExtract;
		if (extracted > energy)
			extracted = energy;
		if (extracted > maxOutput)
			extracted = maxOutput;
		if (!simulate)
			energy -= extracted;
		return extracted;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energy;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return maxEnergy;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		energy = tag.getInteger("energy");
		maxEnergy = tag.getInteger("maxEnergy");
		maxInput = tag.getInteger("maxInput");
		maxOutput = tag.getInteger("maxOutput");
		loss = tag.getInteger("loss");
		engineControl = tag.getBoolean("engineControl");
		configuration.readFromNBT(tag);
		owner = PlayerUtils.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("energy", energy);
		tag.setInteger("maxEnergy", maxEnergy);
		tag.setInteger("maxInput", maxInput);
		tag.setInteger("maxOutput", maxOutput);
		tag.setInteger("loss", loss);
		tag.setBoolean("engineControl", engineControl);
		configuration.writeToNBT(tag);
		PlayerUtils.writeToNBT(tag, owner);
	}

	@Override
	public void updateEntity() {
		if (getEnergyLevel() > 85)
			engineControl = false;
		if (getEnergyLevel() < 30)
			engineControl = true;
		super.updateEntity();
		if (selfDestruct) {
			fuse--;
			if (fuse % 20 == 0)
				destroyer.addChatComponentMessage(new ChatComponentText(Utils.localize("selfdestructCountdouwn") + ": " + fuse / 20));
		}
		if (fuse <= 0 && selfDestruct)
			byeBye();
		if (ConfigurationHandler.powerloss)
			energy -= loss;
		if (energy < 0)
			energy = 0;
		outputEnergy();
	}

	public abstract void outputEnergy();

	protected boolean canSharePower(TileEntity target, ForgeDirection outputSide) {
		if (configuration.canReceive(outputSide) && configuration.canSend(outputSide) && target instanceof TileKineticEnergyBufferTier1) {
			TileKineticEnergyBufferTier1 keb = (TileKineticEnergyBufferTier1) target;
			if (keb.getStatus(outputSide.getOpposite()) == EnumSideStatus.BOTH)
				return true;
		}
		return false;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return configuration.canReceive(from) || configuration.canSend(from);
	}

	public void sendConfigurationToSever() {
		PacketHandler.instance.sendToServer(new MessageConfiguration(this));
	}

	@Override
	public UUID getOwner() {
		return owner;
	}

	@Override
	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	@Override
	public boolean hasOwner() {
		return owner != null;
	}

	public void activateSelfDestruct() {
		if (worldObj.isRemote) {
			PacketHandler.instance.sendToServer(new MessageSelfDestruct(xCoord, yCoord, zCoord));
			return;
		}
		selfDestruct = true;
		fuse = 100;
		destroyer.addChatComponentMessage(new ChatComponentText(Utils.localize("selfdestructActivated")));
		destroyer.closeScreen();
	}

	public void byeBye() {
		Explosion explosion = worldObj.createExplosion(destroyer, xCoord, yCoord, zCoord, (energy / 900000) + 5, true);
		explosion.doExplosionA();
		explosion.doExplosionB(true);
	}

	@Override
	public EnumSideStatus getStatus(ForgeDirection side) {
		return configuration.getStatus(side);
	}

	@Override
	public void changeStatus(ForgeDirection side) {
		configuration.changeStatus(side);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void writeToByteBuff(ByteBuf buf) {
		buf.writeInt(energy);
		configuration.writeToByteBuff(buf);
		PlayerUtils.writeToByteBuff(buf, owner);
	}

	@Override
	public void readFromByteBuff(ByteBuf buf) {
		energy = buf.readInt();
		configuration.readFromByteBuff(buf);
		owner = PlayerUtils.readFromByteBuff(buf);
	}

	@Override
	public int getX() {
		return xCoord;
	}

	@Override
	public int getY() {
		return yCoord;
	}

	@Override
	public int getZ() {
		return zCoord;
	}

	public int getEnergyLevel() {
		if (getMaxEnergyStored(ForgeDirection.UNKNOWN) == 0)
			return 0;
		return (int) ((float) getEnergyStored(ForgeDirection.UNKNOWN) / getMaxEnergyStored(ForgeDirection.UNKNOWN) * 100);
	}

	@Override
	public EnumPriority getPriority(ForgeDirection side) {
		return configuration.getPriority(side);
	}

	@Override
	public void changePriority(ForgeDirection side) {
		configuration.changePriority(side);
	}

	@Override
	public SideConfiguration getSideConfiguration() {
		return configuration;
	}

	@Override
	public void setSideConfiguration(SideConfiguration configuration) {
		this.configuration.load(configuration);
	}
}
