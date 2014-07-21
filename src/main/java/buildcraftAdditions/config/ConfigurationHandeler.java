package buildcraftAdditions.config;

import buildcraftAdditions.core.VersionCheck;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ConfigurationHandeler {
    public static Configuration configFile;
    public static boolean shouldPrintChangelog;
    public static int[] powerDifficultyModifiers = new int[4];
    public static int basePowerModifier;

    public static void init(File file){
        configFile = new Configuration(file);
        readConfig();
    }

    public static void readConfig(){
        configFile.addCustomCategoryComment("Updates", "Section about updates");
        if (configFile.get("Updates", "shouldCheckForUpdates", true).getBoolean())
            VersionCheck.start();
        shouldPrintChangelog = configFile.get("Updates", "shouldPrintOutChangelog", false).getBoolean();

        configFile.addCustomCategoryComment("Power Usage", "Modify how much energy the tools use");
        powerDifficultyModifiers[0] = configFile.get("Power Usage", "PeacefullDifficultyModifier", 1).getInt();
        powerDifficultyModifiers[1] = configFile.get("Power Usage", "EasyDifficultyModifier", 2).getInt();
        powerDifficultyModifiers[2] = configFile.get("Power Usage", "NormalDifficultyModifier", 3).getInt();
        powerDifficultyModifiers[3] = configFile.get("Power Usage", "HardDifficultyModifier", 4).getInt();
        basePowerModifier = configFile.get("Power Usage", "BaseModifier", 10).getInt();

        if (configFile.hasChanged())
            configFile.save();
    }
}
