package buildcraftAdditions.client.gui;

import java.util.List;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.widgets.WidgetBase;
import buildcraftAdditions.client.gui.widgets.WidgetButtonUpdate;
import buildcraftAdditions.client.gui.widgets.WidgetFluidTank;
import buildcraftAdditions.inventories.containers.ContainerFluidicCompressor;
import buildcraftAdditions.networking.MessageWidgetUpdate;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiFluidicCompressor extends GuiInventory<TileFluidicCompressor> {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/guiFluidicCompressor.png");

	public GuiFluidicCompressor(InventoryPlayer inventoryPlayer, TileFluidicCompressor fluidicCompressor) {
		super(new ContainerFluidicCompressor(inventoryPlayer, fluidicCompressor), fluidicCompressor);
		setDrawPlayerInv(true);
	}

	@Override
	public void drawBackgroundPreWidgets(float f, int x, int y) {
		drawTexturedModalRect(guiLeft + 86, guiTop + 61, 172, 3, inventory.getProgress() + 3, 4);
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
		return 85;
	}

	@Override
	public String getInventoryName() {
		return "fluidicCompressor";
	}

	@Override
	public void initialize() {
		addWidget(new WidgetFluidTank(0, guiLeft + 53, guiTop + 24, 16, 51, this, inventory.tank));

		WidgetButtonUpdate buttonFill = new WidgetButtonUpdate(1, guiLeft + 20, guiTop + 33, 191, 0, 19, 16, this, texture) {
			@Override
			public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {
				tooltips.add(Utils.localize("tooltip.compressor.fill"));
			}
		};
		buttonFill.setActive(inventory.fill);

		WidgetButtonUpdate buttonEmpty = new WidgetButtonUpdate(2, guiLeft + 20, guiTop + 53, 210, 0, 19, 16, this, texture) {
			@Override
			public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {
				tooltips.add(Utils.localize("tooltip.compressor.empty"));
			}
		};
		buttonEmpty.setActive(!inventory.fill);

		addWidget(buttonFill);
		addWidget(buttonEmpty);
	}

	@Override
	public void widgetActionPerformed(WidgetBase widget) {
		if (widget.id == 1) {
			((WidgetButtonUpdate) widgets.get(2)).setActive(false);
			inventory.fill = true;
			PacketHandler.instance.sendToServer(new MessageWidgetUpdate(inventory, widget.id, 1));
		} else if (widget.id == 2) {
			((WidgetButtonUpdate) widgets.get(1)).setActive(false);
			inventory.fill = false;
			PacketHandler.instance.sendToServer(new MessageWidgetUpdate(inventory, widget.id, 0));
		}
	}
}
