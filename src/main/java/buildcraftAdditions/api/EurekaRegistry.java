package buildcraftAdditions.api;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class EurekaRegistry {
    private static ArrayList<String> keys = new ArrayList<String>(10);
    private static ArrayList<String> maxValues = new ArrayList<String>(10);
    private static ArrayList<String> increments = new ArrayList<String>(10);
	private static ArrayList<ItemStack> displayStack = new ArrayList<ItemStack>(10);

    /**
     * Register your keys here for the EUREKA system
     * @param key the key you want to regigistry
     * @param max the value the progress should reach before finishing
     * @param increment the steps in wich the progress should increase
     * @return true if successful, false if key is already occupied
     */
    public static boolean registerKey(String key, int max, int increment, ItemStack stack){
        if (!keys.contains(key)){
            keys.add(key);
            maxValues.add(Integer.toString(max));
            increments.add(Integer.toString(increment));
	        displayStack.add(stack);
            return true;
        }
        return false;
    }

    /**
     * @return a clone of the list containing all EUREKA keys
     */
    public static ArrayList<String> getKeys(){
        return (ArrayList) keys.clone();
    }

    public static int getMaxValue(String key){
        if (!keys.contains(key))
            return 0;
        return Integer.parseInt(maxValues.get(keys.indexOf(key)));
    }

    public static int getIncrement(String key){
        if (!keys.contains(key))
            return 0;
        return Integer.parseInt(increments.get(keys.indexOf(key)));
    }

	public static ItemStack getDisplayStack(String key){
		if (!keys.contains(key))
			return null;
		return displayStack.get(keys.indexOf(key));
	}
}
