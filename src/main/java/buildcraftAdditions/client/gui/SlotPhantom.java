package buildcraftAdditions.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class SlotPhantom extends Slot {

	public SlotPhantom(IInventory inv, int slot, int x, int y) {
		super(inv, slot, x, y);
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}
}
