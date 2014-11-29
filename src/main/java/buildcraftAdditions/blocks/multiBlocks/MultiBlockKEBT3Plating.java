package buildcraftAdditions.blocks.multiBlocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import buildcraftAdditions.multiBlocks.MultiBlockPaternKEBT3;
import buildcraftAdditions.tileEntities.TileKEBT3;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockKEBT3Plating extends MulitBlockBase {
	public IIcon icon[];

	public MultiBlockKEBT3Plating() {
		super('W', new MultiBlockPaternKEBT3());
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		icon = new IIcon[2];
		icon [1] = register.registerIcon("bcadditions:multiBlockSeeInvisible");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return icon[meta];
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileKEBT3();
	}

}
