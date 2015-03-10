package buildcraftAdditions.blocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileSemiAutomaticDuster;

import eureka.api.EurekaAPI;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockSemiAutomaticDuster extends BlockDusterBase {

	@SideOnly(Side.CLIENT)
	private IIcon front, sides, top, bottom;

	public BlockSemiAutomaticDuster() {
		super("SemiAutomatic", "dusterSemiAutomatic");
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
				if (EurekaAPI.API.hasPlayerFinishedResearch(Variables.Eureka.DustT1Key, player))
					((TileSemiAutomaticDuster) tileEntity).makeProgress(player);
			}
		}
	}
}
