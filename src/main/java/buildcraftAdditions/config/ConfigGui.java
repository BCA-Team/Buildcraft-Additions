package buildcraftAdditions.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen parentScreen) {
		super(parentScreen, getList(), "bcadditions", false, false, Utils.localize("config.title"));
	}

	public static List<IConfigElement> getList() {
		List list = new ArrayList<IConfigElement>();
		for (String categoryName : ConfigurationHandler.configFile.getCategoryNames()) {
			ConfigCategory category = ConfigurationHandler.configFile.getCategory(categoryName);
			if (category != null && !category.isChild())
				list.add(new ConfigElement(category));
		}
		return list;
	}
}

