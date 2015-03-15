package buildcraftAdditions.compat.buildcraft.actions;

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
public class ActionSwitchFluidicCompressorMode extends BasicAction {

	private final boolean mode;

	public ActionSwitchFluidicCompressorMode(boolean mode) {
		super("fluidicCompressorModeSwitchTo" + (mode ? "Fill" : "Empty"), "ActionFluidicCompressorModeSwitchTo" + (mode ? "Fill" : "Empty"));
		this.mode = mode;
	}

	@Override
	public void actionActivate(TileEntity target, ForgeDirection side, IStatementContainer source, IStatementParameter[] parameters) {
		if (target instanceof TileFluidicCompressor) {
			TileFluidicCompressor tile = (TileFluidicCompressor) target;
			if (tile.fill != mode) {
				tile.fill = mode;
				tile.sync();
			}
		}
	}
}
