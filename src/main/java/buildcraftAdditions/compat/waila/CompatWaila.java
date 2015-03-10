package buildcraftAdditions.compat.waila;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;

import buildcraftAdditions.compat.CompatModule;

/**
 * Copyright (c) 2014-2015, AEnterprise
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
	public void init(FMLInitializationEvent event) {
		FMLInterModComms.sendMessage("Waila", "register", BCACallbackRegister.class.getName() + ".register");
	}
}
