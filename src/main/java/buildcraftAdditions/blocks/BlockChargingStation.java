package buildcraftAdditions.blocks;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileChargingStation;

public class BlockChargingStation extends BlockRotationBase {

	public BlockChargingStation() {
		super("blockChargingStation", "charging station/", true);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileChargingStation();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.isSneaking())
			return false;


		if (!world.isRemote)
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.CHARGING_STATION.ordinal(), world, x, y, z);

		return true;
	}

}
