package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import buildcraftAdditions.api.DusterRecipes;


import eureka.api.EurekaKnowledge;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileBaseDuster extends TileBase implements IInventory {
	public int progress;
	private String key;

	public TileBaseDuster(String key) {
		this.key = key;
	}

	public void makeProgress(EntityPlayer player) {
		if (getStackInSlot(0) != null && DusterRecipes.dusting().hasDustingResult(getStackInSlot(0))) {
			progress++;
			if (progress == 8) {
				dust();
				progress = 0;
				makeProgress(player, key);
			}
		}
	}

	public abstract void dust();

	public void makeProgress(EntityPlayer player, String key) {
		EurekaKnowledge.makeProgress(player, key, 1);
	}
}
