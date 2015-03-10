package buildcraftAdditions.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.tileEntities.TileBasicDuster;
import buildcraftAdditions.utils.RenderUtils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockBasicDuster extends BlockDusterBase {

	@SideOnly(Side.CLIENT)
	private IIcon front, back, sides, top, bottom;

	public BlockBasicDuster() {
		super("Basic");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int variable) {
		return new TileBasicDuster();
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance) {
		if (entity instanceof EntityPlayer) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileBasicDuster)
				((TileBasicDuster) tileEntity).makeProgress((EntityPlayer) entity);
		}
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
				return top;
		}

		if (side == ForgeDirection.getOrientation(meta).getOpposite().ordinal())
			return back;
		return sides;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		front = RenderUtils.registerIcon(register, "dusterFront");
		back = RenderUtils.registerIcon(register, "dusterBack");
		sides = RenderUtils.registerIcon(register, "dusterSides");
		top = RenderUtils.registerIcon(register, "dusterTop");
		bottom = RenderUtils.registerIcon(register, "dusterBottom");
	}
}
