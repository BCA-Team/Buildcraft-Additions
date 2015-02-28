package buildcraftAdditions.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.inventories.containers.ContainerPortableLaser;
import buildcraftAdditions.inventories.InventoryPortableLaser;
import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiPortableLaser extends GuiInventory<InventoryPortableLaser> {

	private static final ResourceLocation texture = new ResourceLocation(Variables.MOD.ID, "textures/gui/guiPortableLaser.png");

	public GuiPortableLaser(InventoryPlayer inventoryPlayer, InventoryPortableLaser inventory) {
		super(new ContainerPortableLaser(inventoryPlayer, inventory), inventory);
		setDrawPlayerInv(true);
	}

	@Override
	public ResourceLocation texture() {
		return texture;
	}

	@Override
	public int getXSize() {
		return 176;
	}

	@Override
	public int getYSize() {
		return 53;
	}

	@Override
	public String getInventoryName() {
		return "portableLaser";
	}

}
