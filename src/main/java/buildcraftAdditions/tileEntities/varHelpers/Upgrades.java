package buildcraftAdditions.tileEntities.varHelpers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import buildcraftAdditions.networking.ISyncObject;
import buildcraftAdditions.reference.enums.EnumMachineUpgrades;

import com.google.common.collect.ImmutableList;
import io.netty.buffer.ByteBuf;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Upgrades implements ISyncObject {
	private ArrayList<EnumMachineUpgrades> upgrades;
	private int maxUpgrades, installedUpgrades;

	public Upgrades(int maxUpgrades) {
		this.maxUpgrades = maxUpgrades;
		upgrades = new ArrayList<EnumMachineUpgrades>(maxUpgrades);
	}

	public Upgrades installUpgrade(EnumMachineUpgrades upgrade) {
		upgrades.add(installedUpgrades, upgrade);
		installedUpgrades++;
		return this;
	}

	public boolean canInstallUpgrade(EnumMachineUpgrades upgrade) {
		return installedUpgrades < maxUpgrades && (upgrade.canBeInstalledMultipleTimes() || !upgrades.contains(upgrade));
	}

	public List<EnumMachineUpgrades> getUpgrades() {
		return ImmutableList.copyOf(upgrades);
	}

	public EnumMachineUpgrades removeUpgrade() {
		if (installedUpgrades == 0)
			return null;
		EnumMachineUpgrades upgrade = upgrades.get(installedUpgrades - 1);
		upgrades.remove(installedUpgrades - 1);
		installedUpgrades--;
		return upgrade;
	}

	@Override
	public ByteBuf writeToByteBuff(ByteBuf buf) {
		buf.writeInt(maxUpgrades);
		buf.writeInt(installedUpgrades);
		for (int t = 0; t < installedUpgrades; t++) {
			buf.writeInt(upgrades.get(t).ordinal());
		}
		return buf;
	}

	@Override
	public ByteBuf readFromByteBuff(ByteBuf buf) {
		maxUpgrades = buf.readInt();
		installedUpgrades = buf.readInt();
		upgrades.clear();
		for (int t = 0; t < installedUpgrades; t++) {
			upgrades.add(t, EnumMachineUpgrades.values()[buf.readInt()]);
		}
		return buf;
	}

	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("maxUpgrades", maxUpgrades);
		tag.setInteger("installedUpgrades", installedUpgrades);
		for (int t = 0; t < installedUpgrades; t++) {
			if (upgrades.get(t) == null)
				tag.setInteger("upgrade" + t, -1);
			else
				tag.setInteger("upgrade" + t, upgrades.get(t).ordinal());
		}
	}

	public void readFromNBT(NBTTagCompound tag) {
		if (!tag.hasKey("maxUpgrades"))
			return;
		maxUpgrades = tag.getInteger("maxUpgrades");
		installedUpgrades = tag.getInteger("installedUpgrades");
		for (int t = 0; t < installedUpgrades; t++) {
			int number = tag.getInteger("upgrade" + t);
			if (number == -1)
				upgrades.add(t, null);
			else
				upgrades.add(t, EnumMachineUpgrades.values()[number]);
		}
	}
}
