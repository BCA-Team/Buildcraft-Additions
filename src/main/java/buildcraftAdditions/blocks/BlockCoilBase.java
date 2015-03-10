package buildcraftAdditions.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.utils.RenderUtils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class BlockCoilBase extends BlockBase {

	private final String coilType;

	@SideOnly(Side.CLIENT)
	private IIcon sides, top, bottom;

	public BlockCoilBase(String coilType) {
		super("blockCoil" + coilType);
		setBlockBounds(2F / 10F, 0, 2F / 10F, 8F / 10F, 1, 8F / 10F);
		this.coilType = coilType;
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
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta == 0 && side == 3)
			return sides;

		if (side == meta && side > 1)
			return sides;

		switch (side) {
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
	public void registerBlockIcons(IIconRegister register) {
		sides = RenderUtils.registerIcon(register, "coil" + coilType + "Sides");
		top = RenderUtils.registerIcon(register, "coil" + coilType + "Top");
		bottom = RenderUtils.registerIcon(register, "coil" + coilType + "Bottom");
	}
}
