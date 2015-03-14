package buildcraftAdditions.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class SpecialListMananger {
	public static final ArrayList<String> greenButtonList = new ArrayList<String>(20);
	public static final HashMap<String, String> specialTexts = new HashMap<String, String>(20);
	protected static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void init() {
		new SpecialListGetterThread().start();
	}

	public static void readFile(String jsonString) {
		try {
			SpecialList specialList = gson.fromJson(jsonString, SpecialList.class);
			Collections.addAll(greenButtonList, specialList.greenButton);
			for (int i = 0; i < specialList.specialTextNames.length; i++)
				specialTexts.put(specialList.specialTextNames[i], specialList.specialTexts[i]);
			Logger.info("Successfully downloaded and read the special list file!");
		} catch (Throwable t) {
			Logger.error("Error while reading the special list file");
			t.printStackTrace();
		}
	}

	private static class SpecialListGetterThread extends Thread {

		@Override
		public void run() {
			Logger.info("Trying to get the special list file...");
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/BCA-Team/Buildcraft-Additions/master/src/main/resources/specialList.json").openStream()));
				String jsonString = "";

				String line;
				while ((line = reader.readLine()) != null)
					jsonString += line;

				SpecialListMananger.readFile(jsonString);
			} catch (Throwable t) {
				Logger.error("Failed to get the special list file!");
				t.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (Throwable t) {
				}
			}
		}

	}

	private static class SpecialList {
		public String[] greenButton;
		public String[] specialTextNames;
		public String[] specialTexts;

		public SpecialList() {
		}

		public SpecialList(String[] greenButton, String[] specialTextNames, String[] specialTexts) {
			this.greenButton = greenButton;
			this.specialTextNames = specialTextNames;
			this.specialTexts = specialTexts;
		}
	}
}
