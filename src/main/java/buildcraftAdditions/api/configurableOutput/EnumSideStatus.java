package buildcraftAdditions.api.configurableOutput;

import net.minecraft.util.StatCollector;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public enum EnumSideStatus {

	DISABLED("disabled", 0xA6A6A6, false, false, false),
	BOTH("both", 0xFFA500, true, true, true),
	OUTPUT("output", 0x850000, true, false, true),
	INPUT("input", 0x002B87, false, true, false);

	private final String name;
	private final int color;
	private final boolean hasPriority, canReceive, canSend;

	EnumSideStatus(String name, int color, boolean hasPriority, boolean canReceive, boolean canSend) {
		this.name = name;
		this.color = color;
		this.hasPriority = hasPriority;
		this.canReceive = canReceive;
		this.canSend = canSend;
	}

	public String getName() {
		return ("" + StatCollector.translateToLocal("status." + name)).trim();
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
