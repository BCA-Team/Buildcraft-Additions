package buildcraftAdditions.compat.buildcraft.actions;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.IActionInternal;
import buildcraft.api.statements.IActionProvider;
import buildcraft.api.statements.IStatementContainer;

import buildcraftAdditions.tileEntities.TileFluidicCompressor;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ActionProvider implements IActionProvider {

	@Override
	public Collection<IActionInternal> getInternalActions(IStatementContainer container) {
		return null;
	}

	@Override
	public Collection<IActionExternal> getExternalActions(ForgeDirection side, TileEntity tile) {
		Collection<IActionExternal> actions = new ArrayList<IActionExternal>();
		if (tile instanceof TileFluidicCompressor) {
			actions.add(Actions.actionSwitchFluidicCompressorModeToFill);
			actions.add(Actions.actionSwitchFluidicCompressorModeToEmpty);
		}
		return actions;
	}

}
