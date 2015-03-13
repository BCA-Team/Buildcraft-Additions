package buildcraftAdditions.compat.eureka;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;

import net.minecraftforge.common.MinecraftForge;

import buildcraftAdditions.compat.CompatModule;
import buildcraftAdditions.config.ConfigurationHandler;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@CompatModule(id = "Eureka", requiredMods = "eureka")
public class CompatEureka {

	@CompatModule.Handler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new BCAEurekaEvents());
	}

	@CompatModule.Handler
	public void doneLoading(FMLLoadCompleteEvent event) {
		if (ConfigurationHandler.eurekaIntegration)
			BCAEurekaResearch.addEurekeResearch();
	}
}
