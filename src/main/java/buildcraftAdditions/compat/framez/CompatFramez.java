package buildcraftAdditions.compat.framez;

import cpw.mods.fml.common.event.FMLLoadCompleteEvent;

import buildcraftAdditions.compat.CompatModule;

import com.amadornes.framez.api.FramezApi;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@CompatModule(id = "Framez", requiredMods = "framez")
public class CompatFramez {

	@CompatModule.Handler
	public void doneLoading(FMLLoadCompleteEvent event) {
		FramezApi.inst().getMovementApi().registerMovementHandler(new MovementHandler());
	}
}
