package buildcraftAdditions.compat.waila;

import buildcraftAdditions.compat.waila.module.DusterDataProvider;
import buildcraftAdditions.compat.waila.module.KEBT1DataProvider;
import buildcraftAdditions.compat.waila.module.RefineryDataProvider;
import buildcraftAdditions.compat.waila.module.TooltipUpgradeRenderer;
import buildcraftAdditions.compat.waila.module.UpgradableMachineDataProvider;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileBaseDuster;
import buildcraftAdditions.tileEntities.TileKineticEnergyBufferTier1;
import buildcraftAdditions.tileEntities.TileRefinery;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;

import mcp.mobius.waila.api.IWailaRegistrar;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BCACallbackRegister {

	public static void register(IWailaRegistrar registrar) {
		registrar.registerStackProvider(new UpgradableMachineDataProvider(), IUpgradableMachine.class);
		registrar.registerBodyProvider(new UpgradableMachineDataProvider(), IUpgradableMachine.class);
		registrar.registerNBTProvider(new UpgradableMachineDataProvider(), IUpgradableMachine.class);


		registrar.registerStackProvider(new KEBT1DataProvider(), TileKineticEnergyBufferTier1.class);

		registrar.registerBodyProvider(new RefineryDataProvider(), TileRefinery.class);
		registrar.registerNBTProvider(new RefineryDataProvider(), TileRefinery.class);

		registrar.registerBodyProvider(new DusterDataProvider(), TileBaseDuster.class);
		registrar.registerNBTProvider(new DusterDataProvider(), TileBaseDuster.class);
		registrar.addConfigRemote(Variables.MOD.NAME, CompatWaila.GRAPHICAL_DUSTER_PROGRESS_CONFIG_KEY, true);
		registrar.addConfigRemote(Variables.MOD.NAME, CompatWaila.TEXTUAL_DUSTER_PROGRESS_CONFIG_KEY, false);

		registrar.registerTooltipRenderer("BCA.upgradeRenderer", new TooltipUpgradeRenderer());
	}

}
