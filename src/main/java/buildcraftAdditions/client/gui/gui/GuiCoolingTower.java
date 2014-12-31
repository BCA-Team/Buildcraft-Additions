package buildcraftAdditions.client.gui.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.containers.ContainerCoolingTower;
import buildcraftAdditions.client.gui.widgets.WidgetBase;
import buildcraftAdditions.client.gui.widgets.WidgetFluidTank;
import buildcraftAdditions.tileEntities.TileCoolingTower;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiCoolingTower extends GuiBase {
	private TileCoolingTower tower;
	public ResourceLocation texture = new ResourceLocation("bcadditions:textures/gui/refineryHeater.png");

	public GuiCoolingTower(TileCoolingTower tower) {
		super(new ContainerCoolingTower());
		this.tower = tower;
		setDrawPlayerInv(false);
		titleXoffset = 70;
		titleYoffset = 3;
		TEXT_COLOR = 0xFFCC00;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(texture());
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		drawString("Heat: " + tower.heat, guiLeft + 50, guiTop + 60);

		for (WidgetBase widget : widgets)
			widget.render(x, y);
	}

	@Override
	public ResourceLocation texture() {
		return texture;
	}

	@Override
	public int getXSize() {
		return 185;
	}

	@Override
	public int getYSize() {
		return 185;
	}

	@Override
	public String getInventoryName() {
		return "coolingTower";
	}

	@Override
	public void initialize() {
		addWidget(new WidgetFluidTank(0, guiLeft + 80, guiTop + 20, 10, 30, this, tower.getTanks()[0]));
		addWidget(new WidgetFluidTank(1, guiLeft + 95, guiTop + 20, 10, 30, this, tower.getTanks()[1]));
		addWidget(new WidgetFluidTank(2, guiLeft + 110, guiTop + 20, 10, 30, this, tower.getTanks()[2]));
	}
}
