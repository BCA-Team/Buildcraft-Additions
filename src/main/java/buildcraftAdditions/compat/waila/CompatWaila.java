package buildcraftAdditions.compat.waila;

import cpw.mods.fml.common.event.FMLLoadCompleteEvent;

import buildcraftAdditions.compat.waila.module.DusterDataProvider;
import buildcraftAdditions.compat.waila.module.KEBT1DataProvider;
import buildcraftAdditions.compat.waila.module.RefineryDataProvider;
import buildcraftAdditions.compat.waila.module.TooltipUpgradeRenderer;
import buildcraftAdditions.compat.waila.module.UpgradableMachineDataProvider;
import buildcraftAdditions.compat.CompatModule;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileBaseDuster;
import buildcraftAdditions.tileEntities.TileKineticEnergyBufferTier1;
import buildcraftAdditions.tileEntities.TileRefinery;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;

import mcp.mobius.waila.api.impl.ModuleRegistrar;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@CompatModule(id = "Waila", requiredMods = "Waila")
public class CompatWaila {

	public static final String GRAPHICAL_DUSTER_PROGRESS_CONFIG_KEY = "bca.duster.progress.graphical";
	public static final String TEXTUAL_DUSTER_PROGRESS_CONFIG_KEY = "bca.duster.progress.textual";

	@CompatModule.Handler
	public void doneLoading(FMLLoadCompleteEvent event) {
		ModuleRegistrar.instance().registerStackProvider(new UpgradableMachineDataProvider(), IUpgradableMachine.class);
		ModuleRegistrar.instance().registerBodyProvider(new UpgradableMachineDataProvider(), IUpgradableMachine.class);
		ModuleRegistrar.instance().registerNBTProvider(new UpgradableMachineDataProvider(), IUpgradableMachine.class);

		ModuleRegistrar.instance().registerStackProvider(new KEBT1DataProvider(), TileKineticEnergyBufferTier1.class);

		ModuleRegistrar.instance().registerBodyProvider(new RefineryDataProvider(), TileRefinery.class);
		ModuleRegistrar.instance().registerNBTProvider(new RefineryDataProvider(), TileRefinery.class);

		ModuleRegistrar.instance().registerBodyProvider(new DusterDataProvider(), TileBaseDuster.class);
		ModuleRegistrar.instance().registerNBTProvider(new DusterDataProvider(), TileBaseDuster.class);
		ModuleRegistrar.instance().addConfigRemote(Variables.MOD.NAME, GRAPHICAL_DUSTER_PROGRESS_CONFIG_KEY, true);
		ModuleRegistrar.instance().addConfigRemote(Variables.MOD.NAME, TEXTUAL_DUSTER_PROGRESS_CONFIG_KEY, false);

		ModuleRegistrar.instance().registerTooltipRenderer("BCA.upgradeRenderer", new TooltipUpgradeRenderer());
	}
}
