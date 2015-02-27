package buildcraftAdditions.items.dust;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.util.StringUtils;

import cpw.mods.fml.common.registry.GameRegistry;

import buildcraftAdditions.api.item.dust.IDust;
import buildcraftAdditions.api.item.dust.IDustManager;
import buildcraftAdditions.api.item.dust.IDustType;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.core.Logger;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DustManager implements IDustManager {

	private final IDust[] dusts = new IDust[Short.MAX_VALUE];

	@Override
	public void addDust(int meta, String name, int colorMultiplier, IDustType dustType) {
		if (!ConfigurationHandler.shouldRegisterDusts) {
			Logger.debug("Dust registering is disabled via config.");
			Logger.debug("Was trying to add: Meta: " + meta + ", Name: " + name + ", Color multiplier: " + colorMultiplier + ", Dust type: " + (dustType != null ? dustType.getName() : "null"));
			return;
		}
		if (meta < 0 || meta >= dusts.length) {
			Logger.error("Tried to register an invalid dust! The meta '" + meta + "' is out of bounds! Skipping.");
			Logger.error("Was trying to add: Meta: " + meta + ", Name: " + name + ", Color multiplier: " + colorMultiplier + ", Dust type: " + (dustType != null ? dustType.getName() : "null"));
			return;
		}
		if (StringUtils.isNullOrEmpty(name) || Character.isLowerCase(name.charAt(0))) {
			Logger.error("Tried to register an invalid dust! The name '" + name + "' is not valid! Skipping.");
			Logger.error("Was trying to add: Meta: " + meta + ", Name: " + name + ", Color multiplier: " + colorMultiplier + ", Dust type: " + (dustType != null ? dustType.getName() : "null"));
			return;
		}
		if (dustType == null) {
			Logger.error("Tried to register an invalid dust! The dust type must not be null! Skipping.");
			Logger.error("Was trying to add: Meta: " + meta + ", Name: " + name + ", Color multiplier: " + colorMultiplier + ", Dust type: " + (dustType != null ? dustType.getName() : "null"));
			return;
		}
		IDust dust = getDust(meta);
		if (dust != null) {
			Logger.error("A dust with the meta '" + meta + "' is already registered! Skipping.");
			Logger.error("Was trying to add: Meta: " + meta + ", Name: " + name + ", Color multiplier: " + colorMultiplier + ", Dust type: " + (dustType != null ? dustType.getName() : "null"));
			Logger.error("Found: Meta: " + dust.getMeta() + ", Name: " + dust.getName() + ", Color multiplier: " + dust.getColorMultiplier() + ", Dust type: " + (dust.getDustType() != null ? dust.getDustType().getName() : "null"));
			return;
		}
		dust = getDust(name);
		if (dust != null) {
			Logger.error("A dust with the name '" + name + "' is already registered! Skipping.");
			Logger.error("Was trying to add: Meta: " + meta + ", Name: " + name + ", Color multiplier: " + colorMultiplier + ", Dust type: " + (dustType != null ? dustType.getName() : "null"));
			Logger.error("Found: Meta: " + dust.getMeta() + ", Name: " + dust.getName() + ", Color multiplier: " + dust.getColorMultiplier() + ", Dust type: " + (dust.getDustType() != null ? dust.getDustType().getName() : "null"));
			return;
		}
		dust = new Dust(meta, name, colorMultiplier, dustType);
		if (dustType.isValid(meta, name, dust.getDustStack())) {
			dusts[meta] = dust;
			Item converter = new ItemConverter(dust);
			GameRegistry.registerItem(converter, "converter" + name.toLowerCase());
			Logger.info("Successfully added a dust: Meta: " + meta + ", Name: " + name + ", Color multiplier: " + colorMultiplier + ", Dust type: " + (dustType != null ? dustType.getName() : "null"));
		} else {
			Logger.debug("The dust with the name '" + name + "' will not be registered because it is invalid in this environment! Skipping.");
			Logger.debug("Was trying to add: Meta: " + meta + ", Name: " + name + ", Color multiplier: " + colorMultiplier + ", Dust type: " + (dustType != null ? dustType.getName() : "null"));
		}
	}

	@Override
	public void removeDust(int meta) {
		IDust dust = getDust(meta);
		if (dust == null) {
			Logger.error("Tried to remove an invalid dust! A dust with the meta '" + meta + "' could not be found! Skipping.");
			return;
		}
		if (meta >= 0 && meta < dusts.length) {
			dusts[meta] = null;
			Logger.info("Successfully removed dust: Meta: " + dust.getMeta() + ", Name: " + dust.getName() + ", Color multiplier: " + dust.getColorMultiplier() + ", Dust type: " + (dust.getDustType() != null ? dust.getDustType().getName() : "null"));
			return;
		}
	}

	@Override
	public void removeDust(String name) {
		IDust dust = getDust(name);
		if (dust == null) {
			Logger.error("Tried to remove an invalid dust! A dust with the name '" + name + "' could not be found! Skipping.");
			return;
		}
		if (!StringUtils.isNullOrEmpty(name)) {
			for (int i = 0; i < dusts.length; i++) {
				if (dusts[i] != null && dusts[i].getName().equalsIgnoreCase(name)) {
					dusts[i] = null;
					Logger.info("Successfully removed dust: Meta: " + dust.getMeta() + ", Name: " + dust.getName() + ", Color multiplier: " + dust.getColorMultiplier() + ", Dust type: " + (dust.getDustType() != null ? dust.getDustType().getName() : "null"));
					return;
				}
			}
		}
	}

	@Override
	public IDust getDust(int meta) {
		if (meta >= 0 && meta < dusts.length) {
			return dusts[meta];
		}
		return null;
	}

	@Override
	public IDust getDust(String name) {
		if (!StringUtils.isNullOrEmpty(name)) {
			for (IDust dust : dusts) {
				if (dust != null && dust.getName().equalsIgnoreCase(name)) {
					return dust;
				}
			}
		}
		return null;
	}

	@Override
	public List<? extends IDust> getDusts() {
		return Collections.unmodifiableList(Arrays.asList(dusts));
	}
}
