package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileBaseDuster;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileBasicDuster extends TileBaseDuster {

	public TileBasicDuster() {
		super(Variables.Eureka.DustT1Key, Variables.SyncIDs.BASIC_DUSTER.ordinal());
	}

	@Override
	public void dust() {
		if (worldObj.isRemote) return;
		Utils.dropItemstack(worldObj, xCoord, yCoord, zCoord, BCARecipeManager.duster.getRecipe(getStackInSlot(0)).getOutput(getStackInSlot(0)));
		setInventorySlotContents(0, null);
	}

	@Override
	public double getProgress() {
		return progress / 8D;
	}

	public void makeProgress(EntityPlayer player) {
		if (BCARecipeManager.duster.getRecipe(getStackInSlot(0)) != null) {
			progress++;
			spawnDustingParticles();
			if (progress >= 8) {
				dust();
				progress = 0;
				makeEurekaProgress(player);
			}
		}
	}
}
