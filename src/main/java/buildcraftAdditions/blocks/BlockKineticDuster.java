package buildcraftAdditions.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraft.api.power.ILaserTargetBlock;

import buildcraftAdditions.tileEntities.TileKineticDuster;
import buildcraftAdditions.utils.RenderUtils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockKineticDuster extends BlockDusterBase implements ILaserTargetBlock {

	@SideOnly(Side.CLIENT)
	private IIcon bottom, sides[], top;

	public BlockKineticDuster() {
		super("Kinetic", "");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileKineticDuster();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		switch (side) {
			case 0:
				return bottom;
			case 1:
				return top;
		}
		TileKineticDuster duster = (TileKineticDuster) world.getTileEntity(x, y, z);
		return sides[duster.progressStage];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		switch (side) {
			case 0:
				return bottom;
			case 1:
				return top;
		}
		return sides[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		sides = new IIcon[4];
		sides[0] = RenderUtils.registerIcon(register, "duster" + type + "Side0");
		sides[1] = RenderUtils.registerIcon(register, "duster" + type + "Side1");
		sides[2] = RenderUtils.registerIcon(register, "duster" + type + "Side2");
		sides[3] = RenderUtils.registerIcon(register, "duster" + type + "Side3");
		bottom = RenderUtils.registerIcon(register, "duster" + type + "Bottom");
		top = RenderUtils.registerIcon(register, "duster" + type + "Top");
	}
}
