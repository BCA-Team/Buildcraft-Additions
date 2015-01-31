package buildcraftAdditions.reference.enums;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

/**
 * This is no part of the api and will probably never be part of it.
 * I might make some other upgrades later but as each upgrade does different
 * things and is handled differently it would be more trouble then it's worth
 * to do things like that
 */
public enum EnumMachineUpgrades {
	AUTO_OUTPUT("AUTO_OUTPUT");

	private String tag;

	private EnumMachineUpgrades(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}
}
