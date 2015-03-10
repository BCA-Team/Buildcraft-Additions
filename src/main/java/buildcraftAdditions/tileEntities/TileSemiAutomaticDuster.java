package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileDusterWithConfigurableOutput;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileSemiAutomaticDuster extends TileDusterWithConfigurableOutput {

	public TileSemiAutomaticDuster() {
		super(Variables.Eureka.DustT2Key1);
	}

	@Override
	public double getProgress() {
		return progress / 8D;
	}

	public void makeProgress(EntityPlayer player) {
		if (BCARecipeManager.duster.getRecipe(getStackInSlot(0)) != null) {
			progress++;
			if (progress >= 8) {
				dust();
				progress = 0;
				makeEurekaProgress(player);
			}
		}
	}
}
