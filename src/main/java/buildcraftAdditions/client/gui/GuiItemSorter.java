package buildcraftAdditions.client.gui;

import buildcraftAdditions.client.gui.widgets.WidgetBase;
import buildcraftAdditions.client.gui.widgets.WidgetColor;
import buildcraftAdditions.inventories.containers.ContainerItemSorter;
import buildcraftAdditions.networking.MessageWidgetUpdate;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.tileEntities.TileItemSorter;
import buildcraftAdditions.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiItemSorter extends GuiInventory<TileItemSorter> {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions:textures/gui/guiItemSorter.png");

	public GuiItemSorter(EntityPlayer player, TileItemSorter tile) {
		super(new ContainerItemSorter(player, tile), tile);
		setDrawPlayerInv(true);
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
			addWidget(new WidgetSortingColor(i, guiLeft + 7 + i * 18, guiTop + 125, 176, 0, 18, 18, this, inventory.colors[i], texture));
		}
	}

	@Override
	public void widgetActionPerformed(WidgetBase widget) {
		if (widget.id >= 0 && widget.id <= 8)
			PacketHandler.instance.sendToServer(new MessageWidgetUpdate(inventory, widget.id, widget.value));
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
