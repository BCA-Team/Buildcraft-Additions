package buildcraftAdditions.ModIntegration.waila;

import buildcraftAdditions.tileEntities.TileRefinery;

import mcp.mobius.waila.api.impl.ModuleRegistrar;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class WailaIntegration {

	public static void integrate() {
		ModuleRegistrar.instance().registerBodyProvider(new RefineryDataProvider(), TileRefinery.class);
		ModuleRegistrar.instance().registerNBTProvider(new RefineryDataProvider(), TileRefinery.class);
		ModuleRegistrar.instance().registerTooltipRenderer("BCA.upgradeRenderer", new TooltipUpgradeRenderer());
	}
}
