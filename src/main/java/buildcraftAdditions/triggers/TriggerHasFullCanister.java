package buildcraftAdditions.triggers;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.gates.ITileTrigger;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerParameter;
import buildcraft.core.triggers.BCTrigger;

import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TriggerHasFullCanister extends BCTrigger implements ITileTrigger {
	public IIcon icon;

	public TriggerHasFullCanister() {
		super("bcadditions:TriggerHasFullCanister");
	}

	@Override
	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter) {
		if (tile instanceof TileFluidicCompressor) {
			TileFluidicCompressor fluidicCompressor = (TileFluidicCompressor) tile;
			return (fluidicCompressor.getStackInSlot(1) != null && fluidicCompressor.getStackInSlot(1).stackTagCompound.hasKey("Fluid"));
		}
		return false;
	}

	@Override
	public String getDescription() {
		return Utils.localize("trigger.hasFullCanister");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon() {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconregister) {
		icon = iconregister.registerIcon("bcadditions:TriggerHasFullCanister");
	}

	@Override
	public ITrigger rotateLeft() {
		return this;
	}
}
