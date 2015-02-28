package buildcraftAdditions.tileEntities.varHelpers;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import io.netty.buffer.ByteBuf;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.Constants;

import buildcraftAdditions.api.networking.ISyncObject;
import buildcraftAdditions.reference.enums.EnumMachineUpgrades;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Upgrades implements ISyncObject {
	private final EnumSet<EnumMachineUpgrades> upgrades, whitelist, blacklist;
	private int maxUpgrades;

	public Upgrades(int maxUpgrades) {
		this.maxUpgrades = maxUpgrades;
		upgrades = EnumSet.noneOf(EnumMachineUpgrades.class);
		whitelist = EnumSet.noneOf(EnumMachineUpgrades.class);
		blacklist = EnumSet.noneOf(EnumMachineUpgrades.class);
	}

	public Upgrades installUpgrade(EnumMachineUpgrades upgrade) {
		upgrades.add(upgrade);
		return this;
	}

	public boolean canInstallUpgrade(EnumMachineUpgrades upgrade) {
		return upgrades.size() < maxUpgrades && (!upgrades.contains(upgrade) || upgrade.canBeInstalledMultipleTimes()) && !blacklist.contains(upgrade) && (whitelist.isEmpty() || whitelist.contains(upgrade));
	}

	public Upgrades blacklistUpgrade(EnumMachineUpgrades upgrade) {
		blacklist.add(upgrade);
		return this;
	}

	public Upgrades whitelistUpgrade(EnumMachineUpgrades upgrade) {
		whitelist.add(upgrade);
		return this;
	}

	public Set<EnumMachineUpgrades> getUpgrades() {
		return ImmutableSet.copyOf(upgrades);
	}

	public boolean hasUpgrade(EnumMachineUpgrades upgrade) {
		return upgrades.contains(upgrade);
	}

	public EnumMachineUpgrades removeUpgrade() {
		if (upgrades.size() == 0)
			return null;
		EnumMachineUpgrades upgrade = null;
		Iterator<EnumMachineUpgrades> it = upgrades.iterator();
		while (it.hasNext())
			upgrade = it.next();
		it.remove();
		return upgrade;
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf.writeInt(maxUpgrades);
		buf.writeInt(upgrades.size());
		for (EnumMachineUpgrades upgrade : upgrades) buf.writeInt(upgrade.ordinal());
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		maxUpgrades = buf.readInt();
		int installedUpgrades = buf.readInt();
		upgrades.clear();
		for (int i = 0; i < installedUpgrades; i++)
			upgrades.add(EnumMachineUpgrades.values()[buf.readInt()]);
		return buf;
	}

	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("maxUpgrades", maxUpgrades);
		int[] upgradeIDs = new int[upgrades.size()];
		EnumMachineUpgrades[] upgradeArray = upgrades.toArray(new EnumMachineUpgrades[upgrades.size()]);
		for (int i = 0; i < upgradeIDs.length; i++) {
			upgradeIDs[i] = upgradeArray[i].ordinal();
		}
		tag.setIntArray("upgrades", upgradeIDs);
	}

	public void readFromNBT(NBTTagCompound tag) {
		maxUpgrades = tag.getInteger("maxUpgrades");
		if (tag.hasKey("upgrades", Constants.NBT.TAG_INT_ARRAY)) {
			int[] upgradeIDs = tag.getIntArray("upgrades");
			for (int i : upgradeIDs)
				upgrades.add(EnumMachineUpgrades.values()[i]);
		}
	}

	public void setMaxUpgrades(int max) {
		maxUpgrades = max;
	}

	public void invalidate() {
		upgrades.clear();
	}

	@Override
	public String toString() {
		return "Upgrades: maxUpgrades = [" + maxUpgrades + "], upgrades = " + upgrades + ", whitelist = " + whitelist + ", blacklist = " + blacklist;
	}
}
