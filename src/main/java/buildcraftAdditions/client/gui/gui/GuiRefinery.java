package buildcraftAdditions.client.gui.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.containers.ContainerRefinery;
import buildcraftAdditions.client.gui.widgets.WidgetBase;
import buildcraftAdditions.client.gui.widgets.WidgetFluidTank;
import buildcraftAdditions.tileEntities.TileRefinery;
import buildcraftAdditions.utils.Utils;
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
		titleXoffset = 70;
		titleYoffset = 3;
		TEXT_COLOR = 0xFFCC00;
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
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		bindTexture(texture());
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		drawString(Utils.localize("gui.heat") + ": " + refinery.currentHeat, guiLeft + 45, guiTop + 80);
		drawString(Utils.localize("gui.requiredHeat") + ": " + refinery.requiredHeat, guiLeft + 45, guiTop + 90);
		drawString(Utils.localize("gui.powerUsage") + ": " + refinery.energyCost, guiLeft + 45, guiTop + 100);


		GL11.glEnable(GL11.GL_LIGHTING);
		bindTexture(texture);
		drawTexturedModalRect(guiLeft + 50, guiTop + 150, 0, 186, 80, 6);
		GL11.glEnable(GL11.GL_LIGHTING);

		//rendering widgets last so tooltips are above the other stuff being rendered
		for (WidgetBase widget : widgets)
			widget.render(x, y);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public void initialize() {
		addWidget(new WidgetFluidTank(0, guiLeft + 22, guiTop + 65, 0, 0, 16, 52, this, refinery.getTanks()[0]));
		addWidget(new WidgetFluidTank(1, guiLeft + 148, guiTop + 65, 0, 0, 16, 52, this, refinery.getTanks()[1]));
	}
}
