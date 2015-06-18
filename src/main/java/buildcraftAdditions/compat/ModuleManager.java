package buildcraftAdditions.compat;

import buildcraftAdditions.core.Logger;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.reflect.ClassPath;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public final class ModuleManager {

	private final Map<String, Object> modules;

	public ModuleManager() {
		modules = Maps.newHashMap();
	}

	public void setupModules() {
		try {
			for (ClassPath.ClassInfo info : ClassPath.from(ModuleManager.class.getClassLoader()).getTopLevelClassesRecursive(ModuleManager.class.getPackage().getName()))
				if (info.getSimpleName().startsWith("Compat") && !info.getSimpleName().startsWith("CompatModule"))
					registerModule(info);
		} catch (IOException e) {
			throw new RuntimeException("Unknown error while searching for modules!", e);
		}
	}

	private void registerModule(ClassPath.ClassInfo moduleClassInfo) {
		try {
			String id;
			String requiredMods;
			boolean enabled = true;
			Class<?> moduleClass = moduleClassInfo.load();
			CompatModule module = moduleClass.getAnnotation(CompatModule.class);
			id = module.id();
			requiredMods = module.requiredMods();
			if (!Strings.isNullOrEmpty(requiredMods)) {
				for (String mod : requiredMods.split(",")) {
					if (!Loader.isModLoaded(mod)) {
						enabled = false;
						Logger.info(String.format("CompatModule '%s' is missing a dependency: '%s'! This module will not be loaded.", id, mod));
						break;
					}
				}
			}
			if (enabled) {
				Logger.info(String.format("CompatModule '%s' activated.", id));
				modules.put(id, moduleClass.newInstance());
			}
		} catch (Throwable t) {
			Logger.error(String.format("CompatModule '%s' failed to load: '%s'!", moduleClassInfo.getName(), t.getMessage()));
		}
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
