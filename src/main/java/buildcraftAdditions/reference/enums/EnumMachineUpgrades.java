package buildcraftAdditions.reference.enums;


import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.reference.ItemLoader;
import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public enum EnumMachineUpgrades {

	AUTO_OUTPUT("upgradeAutoEject", false),
	EFFICIENCY_1("upgradeEfficiency1", false),
	EFFICIENCY_2("upgradeEfficiency2", false),
	EFFICIENCY_3("upgradeEfficiency3", false),
	SPEED_1("upgradeSpeed1", false),
	SPEED_2("upgradeSpeed2", false),
	SPEED_3("upgradeSpeed3", false),
	AUTO_IMPORT("upgradeAutoImport", false);

	private final String tag;
	private final boolean multipleInstalls;
	private final ResourceLocation texture;

	private EnumMachineUpgrades(String tag, boolean multipleInstalls) {
		this.tag = tag;
		this.multipleInstalls = multipleInstalls;
		texture = new ResourceLocation(Variables.MOD.ID, "textures/items/upgrades/" + tag.substring(7).toLowerCase() + ".png");
	}

	public String getTag() {
		return tag;
	}

	public boolean canBeInstalledMultipleTimes() {
		return multipleInstalls;
	}

	public ItemStack getItemStack() {
		return new ItemStack(ItemLoader.upgrade, 1, ordinal());
	}

	public ResourceLocation getTexture() {
		return texture;
	}

	public String getTextureName() {
		return "upgrades/" + tag.substring(7).toLowerCase();
	}


}
