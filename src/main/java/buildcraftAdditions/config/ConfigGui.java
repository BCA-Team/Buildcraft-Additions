package buildcraftAdditions.config;

import buildcraftAdditions.utils.Utils;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftAdditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftAdditions.wordpress.com/wiki/licensing-stuff/
 */
public class ConfigGui extends GuiConfig {

    public ConfigGui(GuiScreen parentScreen) {
        super(parentScreen, getList(), "bcadditions", false, false, Utils.localize("config.title"));
    }

    public static List<IConfigElement> getList() {
        List list = new ArrayList<IConfigElement>();
        list.add(new ConfigElement (ConfigurationHandeler.configFile.getCategory("updates")));
        list.add(new ConfigElement (ConfigurationHandeler.configFile.getCategory("power usage")));
	    list.add(new ConfigElement (ConfigurationHandeler.configFile.getCategory("misc")));
        return list;
    }
}

