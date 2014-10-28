package buildcraftAdditions.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import buildcraftAdditions.tileEntities.TileKineticEnergyBufferTier1;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockKineticEnergyBufferTier1 extends BlockContainer {
	public IIcon icons[];

	public BlockKineticEnergyBufferTier1() {
		super(Material.iron);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[9];
		for (int teller = 0; teller < 9; teller++){
			icons[teller] = iconRegister.registerIcon("bcadditions:kineticEnergyBuffer" + teller);
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return icons[meta];
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileKineticEnergyBufferTier1();
	}
}
