package buildcraftAdditions.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class VersionCheckThread extends Thread {

    public static boolean newerVersionAvailable = false;
    public static String newerVersionNumber = "";
    public static String currentVersion = "@MODVERSION@";
    public static String[] changelog;
    public boolean check = true;


    @Override
    public void run() {
        if (check)
            try {
                URL version = new URL("https://raw.githubusercontent.com/AEnterprise/Buildcraft-Additions/master/src/main/resources/changelogs/version.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(version.openStream()));

            } catch (Throwable e) {
                e.printStackTrace();
            }
    }
}
