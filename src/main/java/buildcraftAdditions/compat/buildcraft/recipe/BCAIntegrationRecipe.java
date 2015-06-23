package buildcraftAdditions.compat.buildcraft.recipe;

import net.minecraft.item.Item;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BCAIntegrationRecipe {
	private final Item expansion;
	private final String installation;
	private final boolean stick;
	private String prevStick;

	public BCAIntegrationRecipe(String installation, boolean stick, Item expansion) {
		this.installation = installation;
		this.stick = stick;
		this.expansion = expansion;
	}

	public Item getExpansion() {
		return expansion;
	}

	public String getInstallation() {
		return installation;
	}

	public boolean installStick() {
		return stick;
	}

	public String getPrevStick() {
		return prevStick;
	}

	public BCAIntegrationRecipe setPrevStick(String prevStick) {
		this.prevStick = prevStick;
		return this;
	}
}
