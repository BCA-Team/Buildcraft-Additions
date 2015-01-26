package buildcraftAdditions.tileEntities.Bases;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.Explosion;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.networking.ISyncronizedTile;
import buildcraftAdditions.networking.MessageConfiguration;
import buildcraftAdditions.networking.MessageSelfDestruct;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.tileEntities.TileKineticEnergyBufferTier1;
import buildcraftAdditions.utils.EnumPriority;
import buildcraftAdditions.utils.EnumSideStatus;
import buildcraftAdditions.utils.IConfigurableOutput;
import buildcraftAdditions.utils.SideConfiguration;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileKineticEnergyBufferBase extends TileBase implements IEnergyReceiver, IEnergyProvider, IConfigurableOutput, ISyncronizedTile {
	public int energy, maxEnergy, maxInput, maxOutput, loss, fuse;
	protected boolean[] blocked = new boolean[6];
	protected final SideConfiguration configuration = new SideConfiguration();
	public int tier;
	public boolean selfDestruct, engineControl;
	public String owner = "";
	public EntityPlayer destroyer;


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
		if (tag.hasKey("owner"))
			owner = tag.getString("owner");
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
		if (owner != null)
			tag.setString("owner", owner);
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
			energy = energy - loss;
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

	public void setOwner(String owner) {
		this.owner = owner;
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
	}

	@Override
	public void setSideConfiguration(SideConfiguration configuration) {
		this.configuration.load(configuration);
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf.writeInt(energy);
		configuration.writeToByteBuff(buf);
		int length = owner.length();
		buf.writeInt(length);
		char[] chars = owner.toCharArray();
		for (int t = 0; t < length; t++)
			buf.writeChar(chars[t]);
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		energy = buf.readInt();
		configuration.readFromByteBuff(buf);
		int length = buf.readInt();
		owner = "";
		for (int teller = 0; teller < length; teller++)
			owner += buf.readChar();
		return buf;
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
		return (energy * 100) / maxEnergy;
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
}
