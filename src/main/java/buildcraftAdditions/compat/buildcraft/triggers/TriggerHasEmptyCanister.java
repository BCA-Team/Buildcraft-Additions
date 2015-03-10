package buildcraftAdditions.compat.buildcraft.triggers;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IStatementParameter;

import buildcraftAdditions.tileEntities.TileFluidicCompressor;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TriggerHasEmptyCanister extends BasicTrigger {

	public TriggerHasEmptyCanister() {
		super("hasEmptyCanister", "TriggerHasEmptyCanister");
	}

	@Override
	public boolean isTriggerActive(TileEntity target, ForgeDirection side, IStatementContainer source, IStatementParameter[] parameters) {
		if (target instanceof TileFluidicCompressor) {
			TileFluidicCompressor fluidicCompressor = (TileFluidicCompressor) target;
			return (fluidicCompressor.getStackInSlot(1) != null && fluidicCompressor.getStackInSlot(1).stackTagCompound != null && fluidicCompressor.getStackInSlot(1).stackTagCompound.hasKey("Fluid"));
		}
		return false;
	}
}
