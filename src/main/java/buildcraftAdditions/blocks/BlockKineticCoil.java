package buildcraftAdditions.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraft.api.power.ILaserTargetBlock;

import buildcraftAdditions.tileEntities.TileKineticCoil;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockKineticCoil extends BlockCoilBase implements ILaserTargetBlock {
	public IIcon sides, top, bottom;

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileKineticCoil();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		// If no metadata is set, then this is an icon.
		if (j == 0 && i == 3)
			return sides;

		if (i == j && i > 1)
			return sides;

		switch (i) {
			case 0:
				return bottom;
			case 1:
				return top;
			default:
				return sides;
		}
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		sides = par1IconRegister.registerIcon("bcadditions:coilKineticSides");
		top = par1IconRegister.registerIcon("bcadditions:coilKineticTop");
		bottom = par1IconRegister.registerIcon("bcadditions:coilKineticBottom");
	}
}
