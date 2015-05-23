package buildcraftAdditions.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.tileEntities.TileBCKinesisPipeWoodPlacer;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockBCKinesisPipeWood extends BlockBase {

	public BlockBCKinesisPipeWood() {
		super(Material.air, "kinesisPipeWood");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileBCKinesisPipeWoodPlacer();
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
	}
}
