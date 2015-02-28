package buildcraftAdditions.client.gui;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public abstract class GuiInventory<T extends IInventory> extends GuiBase {

	protected final T inventory;

	public GuiInventory(Container container, T inventory) {
		super(container);
		this.inventory = inventory;
	}

	@Override
	public void initialize() {
	}

	@Override
	public boolean hasCustomName() {
		return inventory.hasCustomInventoryName();
	}

	@Override
	public String getCustomName() {
		return inventory.getInventoryName();
	}

}
