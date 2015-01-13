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
public class ContainerKEB extends ContainerBase {
	private TileKineticEnergyBufferBase keb;

	public ContainerKEB(TileKineticEnergyBufferBase keb, EntityPlayer player) {
		super();
		keb.sync();
		this.keb = keb;
		if (player.getDisplayName().equals(keb.owner))
			keb.destroyer = player;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

	}
}
