package buildcraftAdditions.client.gui;

import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.widgets.WidgetFluidTank;
import buildcraftAdditions.inventories.containers.ContainerCoolingTower;
import buildcraftAdditions.tileEntities.TileCoolingTower;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiCoolingTower extends GuiBase {

	public static final ResourceLocation texture = new ResourceLocation("bcadditions:textures/gui/guiCoolingTower.png");
	private final TileCoolingTower tower;

	public GuiCoolingTower(TileCoolingTower tower) {
		super(new ContainerCoolingTower(tower));
		this.tower = tower;
		setTitleXOffset(70);
		setTitleYOffset(3);
		setCenterTitle(true);
	}

	@Override
	public void drawBackgroundPostWidgets(float f, int x, int y) {
		int width = (int) (tower.heat * 68) / 80;
		drawTexturedModalRect(guiLeft + 53, guiTop + 84, 0, 186, 80, 7);
		drawTexturedModalRect(guiLeft + 59, guiTop + 85, 6, 198, width, 5);
		drawTexturedModalRect(guiLeft + 59, guiTop + 85, 6, 193, 80, 5);
	}

	@Override
	public void drawForegroundExtra(int x, int y) {
		drawString("Heat: " + Math.round(tower.heat), 50, 60);
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
		return "coolingTower";
	}

	@Override
	public void initialize() {
		addWidget(new WidgetFluidTank(0, guiLeft + 22, guiTop + 65, 16, 52, this, tower.getTanks()[0]));
		addWidget(new WidgetFluidTank(1, guiLeft + 148, guiTop + 65, 16, 52, this, tower.getTanks()[1]));
		addWidget(new WidgetFluidTank(2, guiLeft + 85, guiTop + 108, 16, 52, this, tower.getTanks()[2]));
	}
}
