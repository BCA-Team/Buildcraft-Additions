package buildcraftAdditions.blocks.multiBlocks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.multiBlocks.MultiBlockPaternRefinery;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileRefinery;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockRefineryValve extends MultiBlockBase {

	public MultiBlockRefineryValve() {
		super("blockRefineryValve", Variables.Identifiers.REFINERY_VALVE, new MultiBlockPaternRefinery(), "refineryValve_2", "refineryValve", "refineryValve");
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase livingBase, ItemStack stack) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity instanceof TileRefinery)
			((TileRefinery) entity).valve = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileRefinery();
	}
}
