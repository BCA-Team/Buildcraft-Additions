package buildcraftAdditions.triggers;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraft.api.gates.IGate;
import buildcraft.api.gates.IStatementParameter;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerParameter;

import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TriggerDoneCharging implements ITrigger {
	public IIcon icon;

	public TriggerDoneCharging() {}

	@Override
	public String getDescription() {
		return Utils.localize("trigger.doneCharging");
	}

	@Override
	public IStatementParameter createParameter(int index) {
		return null;
	}

	@Override
	public String getUniqueTag() {
		return "bcadditions:TriggerDoneCharging";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon() {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		icon = iconregister.registerIcon("bcadditions:TriggerDoneCharging");
	}

	@Override
	public int maxParameters() {
		return 0;
	}

	@Override
	public int minParameters() {
		return 0;
	}

	@Override
	public ITrigger rotateLeft() {
		return this;
	}

	@Override
	public boolean isTriggerActive(IGate gate, ITriggerParameter[] parameters) {
		TileEntity tile = gate.getPipe().getAdjacentTile(gate.getSide());
		if (tile instanceof TileChargingStation) {
			TileChargingStation chargingStation = (TileChargingStation) tile;
			return chargingStation.getProgress() == 1;
		}
		return false;
	}
}
