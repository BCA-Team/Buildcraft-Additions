package buildcraftAdditions.compat;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import buildcraftAdditions.core.Logger;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@CompatModule(id = "test", requiredMods = "buildcraft")
public class CompatTest {

	@CompatModule.Handler
	public void preInit(FMLPreInitializationEvent event) {
		Logger.debug("PREINIT");
	}

	@CompatModule.Handler
	public void init(FMLInitializationEvent event) {
		Logger.debug("INIT");
	}

	@CompatModule.Handler
	public void postInit(FMLPostInitializationEvent event) {
		Logger.debug("POSTINIT");
	}

	@CompatModule.Handler
	public void doneLoading(FMLLoadCompleteEvent event) {
		Logger.debug("DONELOADING");
	}
}
