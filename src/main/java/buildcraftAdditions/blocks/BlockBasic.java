package buildcraftAdditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import buildcraftAdditions.utils.RenderUtils;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockBasic extends Block {
	public IIcon icon;
	String name;

	public BlockBasic(String name) {
		super(Material.iron);
		this.name = name;
	}

	@Override
	public String getUnlocalizedName() {
		return name;
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		icon = RenderUtils.registerIcon(register, name);
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return icon;
	}
}
