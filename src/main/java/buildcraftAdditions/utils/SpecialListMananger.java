package buildcraftAdditions.utils;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import buildcraftAdditions.core.Logger;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class SpecialListMananger {
	protected static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	public static ArrayList<String> greenButtonList = new ArrayList<String>(20);
	public static HashMap<String, String> specialTexts = new HashMap<String, String>(20);

	public void init() {
		Thread thread = new SpecialListGetter();
		thread.start();
	}

	public static void readFile(File file) {
		try {
			SpecialList specialList = gson.fromJson(new FileReader(file), SpecialList.class);
			for (String name: specialList.greenButton)
				greenButtonList.add(name);
			int max = specialList.specialTextNames.length;
			for (int teller = 0; teller < max; teller++)
				specialTexts.put(specialList.specialTextNames[teller], specialList.specialTexts[teller]);
		} catch (Throwable e) {
			Logger.error("Error while reading the special list file");
			e.printStackTrace();
		}
	}
}
