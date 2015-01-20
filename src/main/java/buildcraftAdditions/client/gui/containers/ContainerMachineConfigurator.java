package buildcraftAdditions.client.gui.containers;

import net.minecraft.entity.player.InventoryPlayer;

import buildcraftAdditions.utils.IConfigurableOutput;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerMachineConfigurator extends ContainerBase<IConfigurableOutput> {

	public ContainerMachineConfigurator(InventoryPlayer inventoryPlayer, IConfigurableOutput tile) {
		super(inventoryPlayer, tile);
	}

}
