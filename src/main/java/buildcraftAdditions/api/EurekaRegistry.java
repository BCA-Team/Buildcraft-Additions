package buildcraftAdditions.api;

import java.util.ArrayList;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EurekaRegistry {
    private static ArrayList<String> eurekaRegistry = new ArrayList<String>(50);

    /**
     * Register your keys here for the EUREKA system
     * @param key the key you want to register
     * @return true if successful, false if key is already occupied
     */
    public static boolean registerKey(String key){
        if (!eurekaRegistry.contains(key)){
            eurekaRegistry.add(key);
            return true;
        }
        return false;
    }

    /**
     * @return a clone of the list containing all EUREKA keys
     */
    public static ArrayList<String> getKeys(){
        return (ArrayList) eurekaRegistry.clone();
    }
}
