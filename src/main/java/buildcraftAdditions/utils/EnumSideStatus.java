package buildcraftAdditions.utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public enum EnumSideStatus {
	INPUT("Input", 0x0000ff),
	OUTPUT("Output", 0xff0000),
	DISSABLED("Disabled", 0xa6a6a6),
	BOTH("Both", 0xFFA500);

	private String text;
	private int color;

	EnumSideStatus(String text, int color) {
		this.text = text;
		this.color = color;
	}

	public String getText() {
		return Utils.localize(text);
	}

	public int getColor() {
		return color;
	}
}
