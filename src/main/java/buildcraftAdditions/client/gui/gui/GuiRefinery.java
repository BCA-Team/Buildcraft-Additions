package buildcraftAdditions.client.gui.gui;

import buildcraftAdditions.client.gui.containers.ContainerRefinery;
import buildcraftAdditions.client.gui.widgets.WidgetFluidTank;
import buildcraftAdditions.tileEntities.TileRefinery;
import buildcraftAdditions.utils.Utils;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiRefinery extends GuiBase {

	public ResourceLocation texture = new ResourceLocation("bcadditions:textures/gui/refineryHeater.png");
	TileRefinery refinery;

	public GuiRefinery(TileRefinery refinery) {
		super(new ContainerRefinery());
		setDrawPlayerInv(false);
		setTitleXOffset(70);
		setTitleYOffset(3);
		setTextColor(0xFFCC00);
		setCenterTitle(true);
		this.refinery = refinery;
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
		return "refinery";
	}

	@Override
	public void drawBackgroundPostWidgets(float f, int x, int y) {
		GL11.glDisable(GL11.GL_LIGHTING);
		bindTexture(texture());
		drawTexturedModalRect(guiLeft + 53, guiTop + 150, 0, 186, 80, 7);
		bindTexture(texture());
		drawTexturedModalRect(guiLeft + 22, guiTop + 70, 190, 70, 20, 50);
		drawTexturedModalRect(guiLeft + 148, guiTop + 70, 190, 70, 20, 50);
		int width = (refinery.currentHeat * 68) / refinery.lastRequiredHeat;
		drawTexturedModalRect(guiLeft + 59, guiTop + 151, 6, 198, width, 5);
		drawTexturedModalRect(guiLeft + 59, guiTop + 151, 6, 193, 80, 5);
	}

	@Override
	public void drawForegroundExtra(int x, int y) {
		drawString(Utils.localize("gui.heat") + ": " + refinery.currentHeat, guiLeft + 45, guiTop + 80);
		drawString(Utils.localize("gui.requiredHeat") + ": " + refinery.requiredHeat, guiLeft + 45, guiTop + 90);
		drawString(Utils.localize("gui.powerUsage") + ": " + refinery.energyCost, guiLeft + 45, guiTop + 100);
	}

	@Override
	public void initialize() {
		addWidget(new WidgetFluidTank(0, guiLeft + 22, guiTop + 65, 16, 52, this, refinery.getTanks()[0]));
		addWidget(new WidgetFluidTank(1, guiLeft + 148, guiTop + 65, 16, 52, this, refinery.getTanks()[1]));
	}
}
