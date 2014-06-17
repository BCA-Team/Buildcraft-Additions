package buildcraftAdditions.core;

import buildcraftAdditions.BuildcraftAdditions;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.nbt.NBTTagCompound;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class VersionCheck {

    public static boolean newerVersionAvailable = false;
    public static String newerVersionNumber = "";
    public static String currentVersion = "@MODVERSION@";
    public static String[] changelog;
    public static int numLines = 0;

    public static void start(){
        VersionCheckThread thread = new VersionCheckThread();
        thread.start();
    }

    private static class VersionCheckThread extends Thread {

        @Override
        public void run() {
            Logger.info("Buildcraft Additions version check initiated, current version: @VERSION@");
            try {

                URL version = new URL("https://raw.githubusercontent.com/AEnterprise/Buildcraft-Additions/master/src/main/resources/changelogs/version.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(version.openStream()));
                newerVersionNumber = reader.readLine();
                if (!currentVersion.equals(newerVersionNumber)) {
                    newerVersionAvailable = true;
                    Logger.info("There is a newer version of Buildcraft Additions available (" + newerVersionNumber + ") please considder updating");
                    URL changelogURL = new URL("https://raw.githubusercontent.com/AEnterprise/Buildcraft-Additions/master/src/main/resources/changelogs/" + newerVersionNumber);
                    BufferedReader changelogReader = new BufferedReader((new InputStreamReader(changelogURL.openStream())));
                    String line;
                    ArrayList<String> changelogList = new ArrayList<String>();
                    while ((line = changelogReader.readLine()) != null) {
                        changelogList.add(line);
                        numLines++;
                    }
                    changelog = new String[10];
                    changelogList.toArray(changelog);
                    pingVersionChecker();
                }

            } catch (Throwable e) {
                Logger.error("Builcraft Additions version check failed!");
                e.printStackTrace();
            }
        }
        public void pingVersionChecker(){
            if (Loader.isModLoaded("VersionChecker")){
                NBTTagCompound tag = new NBTTagCompound();
                tag.setString("modDisplayName", "Buildcraft Additions");
                tag.setString("oldVersion", currentVersion);
                tag.setString("newVersion", newerVersionNumber);
                tag.setString("updateUrl", "http://buildcraftadditions.wordpress.com/downloads/");
                tag.setBoolean("isDirectLink", false);
                StringBuilder builder = new StringBuilder();
                for (int t = 0; t < numLines; t++){
                    builder.append(changelog[t]).append("/n");
                }
                tag.setString("changeLog", builder.toString());
                FMLInterModComms.sendRuntimeMessage("bcadditions", "VersionChecker", "addUpdate", tag);
            }
        }
    }
}
