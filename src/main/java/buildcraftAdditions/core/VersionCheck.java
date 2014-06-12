package buildcraftAdditions.core;

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
            try {
                URL version = new URL("https://raw.githubusercontent.com/AEnterprise/Buildcraft-Additions/master/src/main/resources/changelogs/version.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(version.openStream()));
                newerVersionNumber = reader.readLine();
                if (!currentVersion.equals(newerVersionNumber)) {
                    newerVersionAvailable = true;
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
                }

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
