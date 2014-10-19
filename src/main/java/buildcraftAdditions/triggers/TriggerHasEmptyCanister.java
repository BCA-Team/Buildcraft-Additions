package buildcraftAdditions.triggers;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraft.api.gates.IGate;
import buildcraft.api.gates.IStatement;
import buildcraft.api.gates.IStatementParameter;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerParameter;

import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TriggerHasEmptyCanister implements ITrigger {
	public IIcon icon;

	public TriggerHasEmptyCanister() {}

	@Override
	public String getDescription() {
		return Utils.localize("trigger.hasEmptyCanister");
	}

	@Override
	public IStatementParameter createParameter(int index) {
		return null;
	}

	@Override
	public IStatement rotateLeft() {
		return null;
	}

	@Override
	public String getUniqueTag() {
		return "bcadditions:TriggerHasEmptyCanister";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon() {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		icon = iconregister.registerIcon("bcadditions:TriggerHasEmptyCanister");
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
	public boolean isTriggerActive(IGate gate, ITriggerParameter[] parameters) {
		TileEntity tile = gate.getPipe().getAdjacentTile(gate.getSide());
		if (tile instanceof TileFluidicCompressor) {
			TileFluidicCompressor fluidicCompressor = (TileFluidicCompressor) tile;
			return (fluidicCompressor.getStackInSlot(1) != null && fluidicCompressor.getStackInSlot(1).stackTagCompound.hasKey("Fluid"));
		}
		return false;
	}
}
