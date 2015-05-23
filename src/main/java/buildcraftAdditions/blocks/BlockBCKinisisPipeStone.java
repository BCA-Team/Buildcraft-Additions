package buildcraftAdditions.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.tileEntities.TileBCKinesisPipeStonePlacer;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockBCKinisisPipeStone extends BlockBase {

	public BlockBCKinisisPipeStone() {
		super(Material.air, "kinesisPipeCobble");
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {

	}

	@Override
	public TileEntity createNewTileEntity(World world, int data) {
		return new TileBCKinesisPipeStonePlacer();
	}
}
