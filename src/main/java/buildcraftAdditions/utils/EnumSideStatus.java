package buildcraftAdditions.utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public enum EnumSideStatus {

	DISABLED("Disabled", 0xA6A6A6, false, false, false),
	BOTH("Both", 0xFFA500, true, true, true),
	OUTPUT("Output", 0x850000, true, false, true),
	INPUT("Input", 0x002B87, true, true, false);

	private final String text;
	private final int color;
	private final boolean hasPriority, canReceive, canSend;

	EnumSideStatus(String text, int color, boolean hasPriority, boolean canReceive, boolean canSend) {
		this.text = text;
		this.color = color;
		this.hasPriority = hasPriority;
		this.canReceive = canReceive;
		this.canSend = canSend;
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

	public boolean canReceive() {
		return canReceive;
	}

	public boolean canSend() {
		return canSend;
	}

	public EnumSideStatus getNextStatus() {
		if (ordinal() > 0)
			return values()[ordinal() - 1];
		return INPUT;
	}
}
