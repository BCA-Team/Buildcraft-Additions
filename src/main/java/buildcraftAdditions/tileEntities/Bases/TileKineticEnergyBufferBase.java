package buildcraftAdditions.tileEntities.Bases;

import java.util.UUID;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.Explosion;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;

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

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileKineticEnergyBufferBase extends TileBase implements IEnergyReceiver, IEnergyProvider, IConfigurableOutput, ISynchronizedTile, IOwnableMachine, IPipeConnection {

	public final int tier;
	protected final SideConfiguration configuration = new SideConfiguration();
	protected final boolean[] blocked = new boolean[6];
	public int energy, capacity, maxTransfer, loss, fuse;
	public boolean selfDestruct, engineControl;
	public EntityPlayer destroyer;
	private UUID owner;

	public TileKineticEnergyBufferBase(int capacity, int maxTransfer, int loss, int tier, int identifier) {
		super(identifier);
		this.capacity = capacity;
		this.maxTransfer = maxTransfer;
		this.loss = loss;
		this.tier = tier;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if (!configuration.canReceive(from))
			return 0;
		int energyReceived = Math.min(capacity - energy, Math.min(maxTransfer, maxReceive));
		if (!simulate) {
			energy += energyReceived;
			blocked[from.ordinal()] = true;
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if (!configuration.canSend(from))
			return 0;
		int energyExtracted = Math.min(energy, Math.min(maxTransfer, maxExtract));
		if (!simulate)
			energy -= energyExtracted;
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energy;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return capacity;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (tag.hasKey("energy")) {
			energy = tag.getInteger("energy");
			capacity = tag.getInteger("maxEnergy");
			maxTransfer = Math.max(tag.getInteger("maxTransfer"), Math.max(tag.getInteger("maxInput"), tag.getInteger("maxOutput")));
			loss = tag.getInteger("loss");
			engineControl = tag.getBoolean("engineControl");
			configuration.readFromNBT(tag);
			owner = PlayerUtils.readFromNBT(tag);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("energy", energy);
		tag.setInteger("maxEnergy", capacity);
		tag.setInteger("maxTransfer", maxTransfer);
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

	public ConnectOverride overridePipeConnection(IPipeTile.PipeType type, ForgeDirection from) {
		if ((configuration.getStatus(from).canReceive() || configuration.getStatus(from).canSend()) && type == IPipeTile.PipeType.POWER)
			return ConnectOverride.CONNECT;
		return ConnectOverride.DISCONNECT;
	}
}
