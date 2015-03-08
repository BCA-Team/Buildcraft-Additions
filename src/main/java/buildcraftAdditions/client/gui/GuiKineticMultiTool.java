package buildcraftAdditions.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.inventories.InventoryKineticMultiTool;
import buildcraftAdditions.inventories.containers.ContainerKineticMultiTool;
import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiKineticMultiTool extends GuiInventory<InventoryKineticMultiTool> {

	private static final ResourceLocation texture = new ResourceLocation(Variables.MOD.ID, "textures/gui/guiKineticTool.png");

	public GuiKineticMultiTool(InventoryPlayer inventoryPlayer, InventoryKineticMultiTool inventory) {
		super(new ContainerKineticMultiTool(inventoryPlayer, inventory), inventory);
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
		return "kineticMultiTool";
	}

}
