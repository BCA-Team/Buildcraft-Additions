package buildcraftAdditions.compat.minetweaker;

import cpw.mods.fml.common.event.FMLLoadCompleteEvent;

import buildcraftAdditions.compat.CompatModule;
import buildcraftAdditions.compat.minetweaker.script.Cooling;
import buildcraftAdditions.compat.minetweaker.script.Dusters;
import buildcraftAdditions.compat.minetweaker.script.Refinery;

import minetweaker.MineTweakerAPI;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@CompatModule(id = "MineTweaker")
public class CompatMineTweaker {

	@CompatModule.Handler
	public void doneLoading(FMLLoadCompleteEvent event) {
		MineTweakerAPI.registerClass(Dusters.class);
		MineTweakerAPI.registerClass(Refinery.class);
		MineTweakerAPI.registerClass(Cooling.class);
	}
}
