package buildcraftAdditions.client.gui.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.containers.ContainerItemSorter;
import buildcraftAdditions.tileEntities.TileItemSorter;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiItemSorter extends GuiBase {

	public ResourceLocation texture = new ResourceLocation("bcadditions:textures/gui/guiItemSorter.png");
	private TileItemSorter tile;

	public GuiItemSorter(InventoryPlayer playerInv, TileItemSorter tile) {
		super(new ContainerItemSorter(playerInv, tile));
	}

	@Override
	public ResourceLocation texture() {
		return texture;
	}

	@Override
	public int getXSize() {
		return 175;
	}

	@Override
	public int getYSize() {
		return 142;
	}

	@Override
	public String getInventoryName() {
		return "itemSorter";
	}

	@Override
	public void initialize() {

	}
}
