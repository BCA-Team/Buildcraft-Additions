package buildcraftAdditions.utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public enum EnumSideStatus {
	INPUT("Input"),
	OUTPUT("Output"),
	DISSABLED("Dissabled"),
	BOTH("Both");

	private String text;

	EnumSideStatus(String text) {
		this.text = text;
	}

	public String getText() {
		return Utils.localize(text);
	}
}
