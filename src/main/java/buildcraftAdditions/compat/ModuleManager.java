package buildcraftAdditions.compat;

import java.lang.reflect.Method;
import java.util.Map;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import buildcraftAdditions.compat.buildcraft.CompatBuildCraft;
import buildcraftAdditions.compat.eureka.CompatEureka;
import buildcraftAdditions.compat.framez.CompatFramez;
import buildcraftAdditions.compat.minetweaker.CompatMineTweaker;
import buildcraftAdditions.core.Logger;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ModuleManager {

	private Map<String, Object> modules;

	public ModuleManager() {
		modules = Maps.newHashMap();
	}

	public void setupModules() {
		//registerModule(new CompatTest()); //debug compat.
		registerModule(new CompatBuildCraft());
		registerModule(new CompatEureka());
		registerModule(new CompatFramez());
		registerModule(new CompatMineTweaker());
	}

	public void registerModule(Object object) {
		String id;
		String deps;
		boolean enabled = true;

		try {
			CompatModule module = object.getClass().getAnnotation(CompatModule.class);
			id = module.id();
			deps = module.requiredMods();
		} catch (Exception e) {
			throw new RuntimeException("failed to read CompatModule:" + object);
		}

		if (!Strings.isNullOrEmpty(deps)) {
			for (String mod : deps.split(",")) {
				if (!Loader.isModLoaded(mod)) {
					Logger.error(String.format("CompatModule %s is missing a dependency: %s, this module will not be loaded", id, mod));
					enabled = false;
					break;
				}
			}
		}

		if (enabled)
			modules.put(id, object);
	}

	public void preInit(FMLPreInitializationEvent event) {
		for (Object module : modules.values())
			invokeHandlers(module, event);
	}

	public void init(FMLInitializationEvent event) {
		for (Object module : modules.values())
			invokeHandlers(module, event);
	}

	public void postInit(FMLPostInitializationEvent event) {
		for (Object module : modules.values())
			invokeHandlers(module, event);
	}

	public void doneLoadingEvent(FMLLoadCompleteEvent event) {
		for (Object module : modules.values())
			invokeHandlers(module, event);
	}

	private void invokeHandlers(Object module, Object event) {
		for (Method method : module.getClass().getDeclaredMethods()) {
			try {
				if (method.getAnnotation(CompatModule.Handler.class) == null)
					continue;
				if (method.getParameterTypes().length != 1)
					continue;
				if (method.getParameterTypes()[0].isAssignableFrom(event.getClass()))
					method.invoke(module, event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
