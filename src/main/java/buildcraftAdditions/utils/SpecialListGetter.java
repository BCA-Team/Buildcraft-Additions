package buildcraftAdditions.utils;

import java.io.File;
import java.net.URL;

import buildcraftAdditions.core.Logger;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class SpecialListGetter extends Thread {

	@Override
	public void run() {
		Logger.info("Trying to get the special list file");
		try {
			URL listFile = new URL("https://raw.githubusercontent.com/AEnterprise/Buildcraft-Additions/master/src/main/resources/specialList.json");
			File file = new File(listFile.toURI());
			SpecialListMananger.readFile(file);
		} catch (Throwable e) {
			Logger.error("Failed to get the special stuff list!");
			e.printStackTrace();
		}
	}

}