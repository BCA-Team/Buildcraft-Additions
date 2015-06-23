package buildcraftAdditions.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.widgets.WidgetFluidTank;
import buildcraftAdditions.inventories.containers.ContainerRefinery;
import buildcraftAdditions.tileEntities.TileRefinery;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiRefinery extends GuiBase {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions:textures/gui/guiRefinery.png");
	private final TileRefinery refinery;

	public GuiRefinery(InventoryPlayer inventoryPlayer, TileRefinery refinery) {
		super(new ContainerRefinery(inventoryPlayer, refinery));
		setTitleXOffset(70);
		setTitleYOffset(3);
		this.refinery = refinery;
	}

	@Override
	public ResourceLocation texture() {
		return texture;
	}

	@Override
	public int getXSize() {
		return 186;
	}

	@Override
	public int getYSize() {
		return 186;
	}

	@Override
	public String getInventoryName() {
		return "refinery";
	}

	@Override
	public void drawBackgroundPostWidgets(float f, int x, int y) {
		drawTexturedModalRect(guiLeft + 53, guiTop + 150, 0, 186, 80, 7);
		drawTexturedModalRect(guiLeft + 59, guiTop + 151, 6, 198, (refinery.currentHeat * 68) / (refinery.lastRequiredHeat + 1), 5);
		drawTexturedModalRect(guiLeft + 59, guiTop + 151, 6, 193, 80, 5);
	}

	@Override
	public void drawForegroundExtra(int x, int y) {
		drawString(Utils.localize("gui.heat") + ": " + refinery.currentHeat, 45, 80);
		drawString(Utils.localize("gui.requiredHeat") + ": " + refinery.requiredHeat, 45, 90);
		drawString(Utils.localize("gui.powerUsage") + ": " + refinery.energyCost, 45, 100);
	}

	@Override
	public void initialize() {
		addWidget(new WidgetFluidTank(0, guiLeft + 22, guiTop + 65, 16, 52, this, refinery.getTanks()[0]));
		addWidget(new WidgetFluidTank(1, guiLeft + 148, guiTop + 65, 16, 52, this, refinery.getTanks()[1]));
	}
}
