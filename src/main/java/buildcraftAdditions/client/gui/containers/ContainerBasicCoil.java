package buildcraftAdditions.client.gui.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import buildcraftAdditions.tileEntities.TileBasicCoil;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerBasicCoil extends ContainerBase {

	private final TileBasicCoil coil;

	public ContainerBasicCoil(InventoryPlayer inventory, TileBasicCoil coil) {
		super();
		this.coil = coil;

		this.addSlotToContainer(new Slot(coil, 0, 78, 43));

		addPlayerInventory(inventory, 8, 84);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return coil.isUseableByPlayer(player);
	}

}
