package buildcraftAdditions.client.gui.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.containers.ContainerCoolingTower;
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
	public ResourceLocation texture = new ResourceLocation("bcadditions:textures/gui/coolingTower.png");

	public GuiCoolingTower(TileCoolingTower tower) {
		super(new ContainerCoolingTower());
		this.tower = tower;
		setDrawPlayerInv(false);
		titleXoffset = 70;
		titleYoffset = 3;
		TEXT_COLOR = 0xFFCC00;
		shouldDrawWidgets = false;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		super.drawGuiContainerBackgroundLayer(f, x, y);
		GL11.glDisable(GL11.GL_LIGHTING);
		bindTexture(texture());
		drawString("Heat: " + Math.round(tower.heat), guiLeft + 50, guiTop + 60);
		drawWidgets(x, y);
		bindTexture(texture());
		drawTexturedModalRect(guiLeft + 22, guiTop + 70, 190, 70, 20, 50);
		drawTexturedModalRect(guiLeft + 148, guiTop + 70, 190, 70, 20, 50);
		drawTexturedModalRect(guiLeft + 85, guiTop + 113, 190, 70, 20, 50);
		int width = (int) (tower.heat * 68) / 80;
		drawTexturedModalRect(guiLeft + 53, guiTop + 84, 0, 186, 80, 7);
		drawTexturedModalRect(guiLeft + 59, guiTop + 85, 6, 198, width, 5);
		drawTexturedModalRect(guiLeft + 59, guiTop + 85, 6, 193, 80, 5);
		drawTooltips(x, y);
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
		addWidget(new WidgetFluidTank(0, guiLeft + 22, guiTop + 65, 16, 52, this, tower.getTanks()[0]));
		addWidget(new WidgetFluidTank(1, guiLeft + 148, guiTop + 65, 16, 52, this, tower.getTanks()[1]));
		addWidget(new WidgetFluidTank(2, guiLeft + 85, guiTop + 108, 16, 52, this, tower.getTanks()[2]));
	}
}
