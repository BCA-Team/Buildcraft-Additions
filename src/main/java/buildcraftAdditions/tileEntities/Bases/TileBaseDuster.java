package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import buildcraftAdditions.api.DusterRecepies;


import eureka.api.EurekaKnowledge;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileBaseDuster extends TileBase {
	public int progress;
	private String key;

	public TileBaseDuster(String key) {
		this.key = key;
	}

	public void makeProgress(EntityPlayer player) {
		if (getStackInSlot(0) != null && getDust(getStackInSlot(0)) != null) {
			progress++;
			if (progress == 8) {
				dust();
				progress = 0;
				makeProgress(player, key);
			}
		}
	}

	public abstract void dust();

	public ItemStack getDust(ItemStack stack) {
		return DusterRecepies.getOutput(stack);
	}

	public void makeProgress(EntityPlayer player, String key) {
		EurekaKnowledge.makeProgress(player, key, 1);
	}
}
