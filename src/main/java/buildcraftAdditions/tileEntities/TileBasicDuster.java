package buildcraftAdditions.tileEntities;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileBaseDuster;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileBasicDuster extends TileBaseDuster {

	public TileBasicDuster() {
		super(Variables.Eureka.DustT1Key);
	}

	@Override
	public void dust() {
		Utils.dropItemstack(worldObj, xCoord, yCoord, zCoord, BCARecipeManager.duster.getRecipe(getStackInSlot(0)).getOutput(getStackInSlot(0)));
		setInventorySlotContents(0, null);
	}
}
