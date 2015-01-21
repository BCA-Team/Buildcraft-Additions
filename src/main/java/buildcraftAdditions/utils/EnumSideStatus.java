package buildcraftAdditions.utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public enum EnumSideStatus {
	INPUT("Input", 0x002B87, true),
	OUTPUT("Output", 0x850000, true),
	DISSABLED("Disabled", 0xa6a6a6, false),
	BOTH("Both", 0xFFA500, true);

	private String text;
	private int color;
	private boolean hasPriority;

	EnumSideStatus(String text, int color, boolean hasPriority) {
		this.text = text;
		this.color = color;
		this.hasPriority = hasPriority;
	}

	public String getText() {
		return Utils.localize(text);
	}

	public int getColor() {
		return color;
	}

	public boolean hasPriority() {
		return hasPriority;
	}
}
