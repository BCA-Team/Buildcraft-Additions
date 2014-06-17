package buildcraftAdditions.core;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Property;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class Configuration {
    public static boolean shouldPrintChangelog;

    public static void readConfig(FMLPreInitializationEvent event){
        net.minecraftforge.common.config.Configuration config = new net.minecraftforge.common.config.Configuration(event.getSuggestedConfigurationFile());
        config.load();

        config.addCustomCategoryComment("Updates", "Section about updates");

        Property shouldDoUpdateCheck = config.get("Updates", "shouldCheckForUpdates", true);
        if (shouldDoUpdateCheck.getBoolean(true))
            VersionCheck.start();

        Property shouldPrintOutChangelog = config.get("Updates", "shouldPrintOutChangelog", false);
        shouldPrintChangelog = shouldPrintOutChangelog.getBoolean(false);

        if (config.hasChanged())
            config.save();
    }

}
