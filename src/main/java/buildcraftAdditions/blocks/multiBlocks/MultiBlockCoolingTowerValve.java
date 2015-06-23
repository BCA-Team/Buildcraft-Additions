package buildcraftAdditions.blocks.multiBlocks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileCoolingTower;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockCoolingTowerValve extends MultiBlockBase {

	public MultiBlockCoolingTowerValve() {
		super("blockCoolingTowerValve", Variables.Identifiers.COOLING_TOWER_VALVE, Variables.Paterns.COOLING_TOWER, "refineryValve_2", "coolingtowerValve", "blockCoolingTowerValve");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileCoolingTower();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity instanceof TileCoolingTower)
			((TileCoolingTower) entity).valve = true;
	}
}
