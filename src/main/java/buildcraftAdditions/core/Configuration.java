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
    public static int[] powerDifficultyModifiers;
    public static int basePowerModifier;

    public static void readConfig(FMLPreInitializationEvent event){
        powerDifficultyModifiers = new int[4];
        net.minecraftforge.common.config.Configuration config = new net.minecraftforge.common.config.Configuration(event.getSuggestedConfigurationFile());
        config.load();

        config.addCustomCategoryComment("Updates", "Section about updates");

        Property shouldDoUpdateCheck = config.get("Updates", "shouldCheckForUpdates", true);
        if (shouldDoUpdateCheck.getBoolean(true))
            VersionCheck.start();

        Property shouldPrintOutChangelog = config.get("Updates", "shouldPrintOutChangelog", false);
        shouldPrintChangelog = shouldPrintOutChangelog.getBoolean(false);



        config.addCustomCategoryComment("Power Usage", "Modify how much energy the tools use");

        Property peacefullModifier = config.get("Power Usage", "PeacefullDifficultyModifier", 1);
        powerDifficultyModifiers[0] = peacefullModifier.getInt();

        Property easyModifier = config.get("Power Usage", "EasyDifficultyModifier", 2);
        powerDifficultyModifiers[1] = easyModifier.getInt();

        Property normalModifier = config.get("Power Usage", "NormalDifficultyModifier", 3);
        powerDifficultyModifiers[2] = normalModifier.getInt();

        Property hardModifier = config.get("Power Usage", "HardDifficultyModifier", 4);
        powerDifficultyModifiers[3] = hardModifier.getInt();

        Property baseModifier = config.get("Power Usage", "BaseModifier", 10);
        basePowerModifier = baseModifier.getInt();


        if (config.hasChanged())
            config.save();
    }

}
