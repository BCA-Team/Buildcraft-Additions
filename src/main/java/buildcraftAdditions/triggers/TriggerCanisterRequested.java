package buildcraftAdditions.triggers;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.statements.IStatement;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IStatementParameter;
import buildcraft.api.statements.ITriggerExternal;

import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TriggerCanisterRequested implements ITriggerExternal {
	public IIcon icon;

	@Override
	public String getUniqueTag() {
		return "canisterRequestedTriger";
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		icon = iconregister.registerIcon("bcadditions:TriggerCanisterRequested");
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
	public String getDescription() {
		return Utils.localize("trigger.canisterRequested");
	}

	@Override
	public IStatementParameter createParameter(int index) {
		return null;
	}

	@Override
	public IStatement rotateLeft() {
		return this;
	}


	@Override
	public boolean isTriggerActive(TileEntity target, ForgeDirection side, IStatementContainer source, IStatementParameter[] parameters) {
		if ((target instanceof TileFluidicCompressor))
			return ((TileFluidicCompressor) target).getStackInSlot(0) == null;
		return false;
	}
}
