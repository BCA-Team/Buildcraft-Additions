package buildcraftAdditions.client.gui.gui;

import java.util.List;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.containers.ContainerItemSorter;
import buildcraftAdditions.client.gui.widgets.WidgetBase;
import buildcraftAdditions.client.gui.widgets.WidgetColor;
import buildcraftAdditions.networking.MessageWidgetUpdate;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.tileEntities.TileItemSorter;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiItemSorter extends GuiBase {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions:textures/gui/guiItemSorter.png");
	private final TileItemSorter tile;

	public GuiItemSorter(InventoryPlayer playerInv, TileItemSorter tile) {
		super(new ContainerItemSorter(playerInv, tile));
		setDrawPlayerInv(true);
		this.tile = tile;
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
		for (int i = 0; i < 9; i++) {
			addWidget(new WidgetSortingColor(i, guiLeft + 7 + i * 18, guiTop + 125, 176, 0, 18, 18, this, tile.colors[i], texture));
		}
	}

	@Override
	public void widgetActionPerformed(WidgetBase widget) {
		if (widget.id >= 0 && widget.id <= 8)
			PacketHandler.instance.sendToServer(new MessageWidgetUpdate(tile, widget.id, widget.value));
	}

	public static class WidgetSortingColor extends WidgetColor {

		public WidgetSortingColor(int id, int x, int y, int u, int v, int width, int height, GuiBase gui, int value, ResourceLocation texture) {
			super(id, x, y, u, v, width, height, gui, value, texture);
		}

		@Override
		public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {
			if (id == 0)
				tooltips.add(Utils.localize("tooltip.defaultColor") + ":");
			super.addTooltip(mouseX, mouseY, tooltips, shift);
		}
	}
}
