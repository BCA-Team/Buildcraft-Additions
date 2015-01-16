package buildcraftAdditions.client.gui.gui;

import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.containers.ContainerMachineConfigurator;
import buildcraftAdditions.client.gui.widgets.WidgetButton;
import buildcraftAdditions.utils.IConfigurableOutput;
import buildcraftAdditions.utils.Utils;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiMachineConfigurator extends GuiBase {
	public ResourceLocation texture = new ResourceLocation("bcadditions:textures/gui/machineConfigurator.png");
	private IConfigurableOutput configurableOutput;
	private WidgetButton north, east, south, west, up, down;

	public GuiMachineConfigurator(IConfigurableOutput configurableOutput) {
		super(new ContainerMachineConfigurator());
		this.configurableOutput = configurableOutput;
		setCenterTitle(true);
		setDrawPlayerInv(false);
	}

	@Override
	public ResourceLocation texture() {
		return texture;
	}

	@Override
	public void drawBackgroundPreWidgets(float f, int x, int y) {
		super.drawBackgroundPreWidgets(f, x, y);
	}

	@Override
	public void drawBackgroundPostWidgets(float f, int x, int y) {
		super.drawBackgroundPostWidgets(f, x, y);
	}

	@Override
	public int getXSize() {
		return 224;
	}

	@Override
	public int getYSize() {
		return 165;
	}

	@Override
	public String getInventoryName() {
		return "machineConfigurator";
	}

	@Override
	public void initialize() {
		north = new WidgetButton(0, guiLeft + 10, 67, 60, 15, this);
		east = new WidgetButton(0, 240, 67, 60, 15, this);
		addWidget(north);
		addWidget(east);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		super.drawGuiContainerBackgroundLayer(f, x, y);
		drawString(Utils.localize("gui.north") + ":", guiLeft + 10, guiTop + 30);
		drawString(Utils.localize("gui.east") + ": ", 213, 70);
	}
}
