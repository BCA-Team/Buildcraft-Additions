package buildcraftAdditions.reference.enums;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.common.registry.GameRegistry;

import buildcraftAdditions.reference.Variables;
/**
 * This is no part of the api and will probably never be part of it.
 * I might make some other upgrades later but as each upgrade does different
 * things and is handled differently it would be more trouble then it's worth
 * to do things like that
 */
public enum EnumMachineUpgrades {
	AUTO_OUTPUT("AUTO_OUTPUT", false, "upgradeAutoEject"),
	EFFICIENTY1("EFFICIENTY1", false, "upgradeEfficienty1");

	private String tag, textureName;
	private boolean multipleInstalls;
	private ResourceLocation texture;

	private EnumMachineUpgrades(String tag, boolean canBeDouble, String texture) {
		this.tag = tag;
		this.multipleInstalls = canBeDouble;
		textureName = texture;
		this.texture = new ResourceLocation(Variables.MOD.ID, "textures/items/" + texture + ".png");
	}

	public String getTag() {
		return tag;
	}

	public boolean canBeInstalledMultipleTimes() {
		return multipleInstalls;
	}

	public ItemStack getItemStack() {
		return GameRegistry.findItemStack(Variables.MOD.ID, tag, 1);
	}

	public ResourceLocation getTexture() {
		return texture;
	}

	public String getTextureName() {
		return textureName;
	}



}
