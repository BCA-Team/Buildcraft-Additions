package buildcraftAdditions.tileEntities;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import buildcraft.BuildCraftTransport;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileBCKinesisPipeWoodPlacer extends TileEntity {
	@Override
	public void updateEntity() {
		BuildCraftTransport.pipePowerWood.onItemUse(new ItemStack(BuildCraftTransport.pipePowerWood), null, worldObj, xCoord, yCoord, zCoord, 0, 0, 0, 0);
	}
}
