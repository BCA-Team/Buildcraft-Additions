package buildcraftAdditions.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.tileEntities.TileLavaCoil;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockLavaCoil extends BlockCoilBase {

	@SideOnly(Side.CLIENT)
	private IIcon sides, top, bottom;

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.isSneaking())
			return false;

		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileLavaCoil) {
				TileLavaCoil coil = (TileLavaCoil) tile;
				player.addChatComponentMessage(new ChatComponentTranslation("lavaCoil.info", Utils.localizeFormatted("fluids.info", coil.getLavaAmount(), coil.getLavaCapaity())));
			}
		}

		return true;
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
		sides = RenderUtils.registerIcon(register, "coilLavaSides");
		top = RenderUtils.registerIcon(register, "coilLavaTop");
		bottom = RenderUtils.registerIcon(register, "coilLavaBottom");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileLavaCoil();
	}
}
