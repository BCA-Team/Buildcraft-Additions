package buildcraftAdditions.utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public enum EnumPriority {

	VERY_HIGH("veryHigh", 0xCC0000),
	HIGH("high", 0xFF3300),
	NORMAL("normal", 0xFF9966),
	LOW("low", 0x99FF33),
	VERY_LOW("veryLow", 0x0099FF);

	private final String name;
	private final int color;

	private EnumPriority(String name, int color) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return Utils.localize("priority." + name);
	}

	public int getColor() {
		return color;
	}

	public EnumPriority getNextPriority() {
		if (ordinal() > 0)
			return values()[ordinal() - 1];
		return VERY_LOW;
	}
}
