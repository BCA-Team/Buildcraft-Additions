package buildcraftAdditions.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import buildcraftAdditions.core.VersionCheck;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ConfigurationHandeler {
	public static Configuration configFile;
	public static boolean shouldPrintChangelog, shouldRegisterDusts;
	public static int[] powerDifficultyModifiers = new int[4];
	public static int basePowerModifier;

	public static void init(File file) {
		configFile = new Configuration(file);
		readConfig();
	}

	public static void readConfig() {
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

		configFile.addCustomCategoryComment("Misc", "Stuff that didn't fit in any other category");

		shouldRegisterDusts = configFile.get("Misc", "shouldRegisterDusts", true).setRequiresMcRestart(true).getBoolean();

		if (configFile.hasChanged())
			configFile.save();
	}
}
