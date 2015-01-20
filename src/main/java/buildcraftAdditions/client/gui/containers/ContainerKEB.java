package buildcraftAdditions.client.gui.containers;

import net.minecraft.entity.player.EntityPlayer;

import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerKEB extends ContainerBase<TileKineticEnergyBufferBase> {

	public ContainerKEB(EntityPlayer player, TileKineticEnergyBufferBase tile) {
		super(player.inventory, tile);
		tile.sync();
		if (player.getDisplayName().equals(tile.owner))
			tile.destroyer = player;
	}

}
