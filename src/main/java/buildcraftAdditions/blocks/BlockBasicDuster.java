package buildcraftAdditions.blocks;

import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.tileEntities.TileBasicDuster;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

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
		super("Basic", "duster");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int variable) {
		return new TileBasicDuster();
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance) {
		if (entity instanceof EntityPlayer || (ConfigurationHandler.slimesUsingDusters && entity instanceof EntitySlime)) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileBasicDuster)
				((TileBasicDuster) tileEntity).makeProgress((EntityPlayer) entity);
		}
	}
}
