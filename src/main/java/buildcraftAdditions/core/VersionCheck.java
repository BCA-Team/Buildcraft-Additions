package buildcraftAdditions.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class VersionCheck {

	public static final String currentVersion = "@MODVERSION@";
	public static boolean newerVersionAvailable = false;
	public static String newerVersionNumber = "";
	public static String[] changelog;

	public static void start() {
		new VersionCheckThread().start();
	}

	private static class VersionCheckThread extends Thread {

		@Override
		public void run() {
			Logger.info("BuildCraft Additions version check initiated, current version: @VERSION@");
			try {

				URL version = new URL("https://raw.githubusercontent.com/BCA-Team/Buildcraft-Additions/master/src/main/resources/changelogs/version.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(version.openStream()));
				newerVersionNumber = reader.readLine();
				if (!currentVersion.equals(newerVersionNumber)) {
					newerVersionAvailable = true;
					Logger.info("There is a newer version of Buildcraft Additions available (" + newerVersionNumber + ") please consider updating");
					URL changelogURL = new URL("https://raw.githubusercontent.com/BCA-Team/Buildcraft-Additions/master/src/main/resources/changelogs/" + newerVersionNumber);
					BufferedReader changelogReader = new BufferedReader((new InputStreamReader(changelogURL.openStream())));
					String line;
					ArrayList<String> changelogList = new ArrayList<String>();
					while ((line = changelogReader.readLine()) != null)
						changelogList.add(line);
					changelog = new String[changelogList.size()];
					changelogList.toArray(changelog);
					pingVersionChecker();
				}

			} catch (Throwable e) {
				Logger.error("BuildCraft Additions version check failed!");
				e.printStackTrace();
			}
		}

		public void pingVersionChecker() {
			if (Loader.isModLoaded("VersionChecker")) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("modDisplayName", "BuildCraft Additions");
				tag.setString("oldVersion", currentVersion);
				tag.setString("newVersion", newerVersionNumber);
				tag.setString("updateUrl", "http://buildcraftAdditions.wordpress.com/downloads/");
				tag.setBoolean("isDirectLink", false);
				StringBuilder builder = new StringBuilder();
				for (String s : changelog)
					builder.append(s).append("/n");
				tag.setString("changeLog", builder.toString());
				FMLInterModComms.sendRuntimeMessage("bcadditions", "VersionChecker", "addUpdate", tag);
			}
		}
	}
}
