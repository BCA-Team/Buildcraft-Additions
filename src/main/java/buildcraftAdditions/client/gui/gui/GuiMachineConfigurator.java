package buildcraftAdditions.client.gui.gui;

import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.containers.ContainerMachineConfigurator;
import buildcraftAdditions.client.gui.widgets.WidgetButton;
import buildcraftAdditions.utils.IConfigurableOutput;
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
		return 175;
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
		addWidget(new WidgetButton(0, 20, 20, 120, this));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		super.drawGuiContainerBackgroundLayer(f, x, y);
	}
}
