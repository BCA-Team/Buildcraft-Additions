package buildcraftAdditions.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileSemiAutomaticDuster;
import buildcraftAdditions.utils.RenderUtils;

import eureka.api.EurekaKnowledge;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockSemiAutomaticDuster extends BlockDusterBase {

	@SideOnly(Side.CLIENT)
	private IIcon front, sides, top, bottom;

	public BlockSemiAutomaticDuster() {
		super("SemiAutomatic");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileSemiAutomaticDuster();
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance) {
		if (entity instanceof EntityPlayer) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileSemiAutomaticDuster) {
				EntityPlayer player = (EntityPlayer) entity;
				if (EurekaKnowledge.isFinished(player, Variables.Eureka.DustT1Key))
					((TileSemiAutomaticDuster) tileEntity).makeProgress(player);
			}
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
		return sides;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		front = RenderUtils.registerIcon(register, "duster" + type + "Front");
		sides = RenderUtils.registerIcon(register, "duster" + type + "Sides");
		top = RenderUtils.registerIcon(register, "duster" + type + "Top");
		bottom = RenderUtils.registerIcon(register, "duster" + type + "Bottom");
	}
}
