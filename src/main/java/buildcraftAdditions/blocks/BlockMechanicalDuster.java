package buildcraftAdditions.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.tileEntities.TileMechanicalDuster;
import buildcraftAdditions.utils.RenderUtils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockMechanicalDuster extends BlockDusterBase {

	@SideOnly(Side.CLIENT)
	private IIcon front, sides, bottom, top[];

	public BlockMechanicalDuster() {
		super("Mechanical", "");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileMechanicalDuster();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
		int meta = access.getBlockMetadata(x, y, z);
		if (meta == 0 && side == 3)
			return front;

		if (side == meta && meta > 1) {
			return front;
		}

		if (side == 1) {
			TileMechanicalDuster duster = (TileMechanicalDuster) access.getTileEntity(x, y, z);
			return top[duster.progressStage];
		}

		switch (side) {
			case 0:
				return bottom;
			default:
				return sides;
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ)) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null && tile instanceof TileMechanicalDuster) {
				TileMechanicalDuster duster = (TileMechanicalDuster) tile;
				duster.player = player;
				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta == 0 && side == 3)
			return front;

		if (side == meta && meta > 1)
			return front;


		switch (side) {
			case 0:
				return bottom;
			case 1:
				return top[0];
		}
		return sides;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		front = RenderUtils.registerIcon(register, "duster" + type + "Front");
		sides = RenderUtils.registerIcon(register, "duster" + type + "Sides");
		bottom = RenderUtils.registerIcon(register, "duster" + type + "Bottom");
		top = new IIcon[4];
		top[0] = RenderUtils.registerIcon(register, "duster" + type + "Top0");
		top[1] = RenderUtils.registerIcon(register, "duster" + type + "Top1");
		top[2] = RenderUtils.registerIcon(register, "duster" + type + "Top2");
		top[3] = RenderUtils.registerIcon(register, "duster" + type + "Top3");
	}
}
