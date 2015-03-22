package buildcraftAdditions.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraft.api.power.ILaserTargetBlock;

import buildcraftAdditions.tileEntities.TileKineticDuster;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockKineticDuster extends BlockDusterBase implements ILaserTargetBlock {

	public BlockKineticDuster() {
		super("Kinetic", "");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileKineticDuster();
	}



	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {

	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isBlockNormalCube() {
		return false;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}
}
