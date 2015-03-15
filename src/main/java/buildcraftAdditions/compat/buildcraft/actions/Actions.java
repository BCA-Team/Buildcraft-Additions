package buildcraftAdditions.compat.buildcraft.actions;

import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.StatementManager;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Actions {

	public static final IActionExternal actionSwitchFluidicCompressorModeToFill = new ActionSwitchFluidicCompressorMode(true);
	public static final IActionExternal actionSwitchFluidicCompressorModeToEmpty = new ActionSwitchFluidicCompressorMode(false);

	public static void register() {
		StatementManager.registerActionProvider(new ActionProvider());
		StatementManager.registerStatement(actionSwitchFluidicCompressorModeToFill);
		StatementManager.registerStatement(actionSwitchFluidicCompressorModeToEmpty);
	}

}
