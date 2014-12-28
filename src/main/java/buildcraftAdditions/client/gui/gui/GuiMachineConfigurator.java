package buildcraftAdditions.client.gui.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

import buildcraftAdditions.client.gui.containers.ContainerMachineConfigurator;
import buildcraftAdditions.utils.IConfigurableOutput;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiMachineConfigurator extends GuiContainer {

	public GuiMachineConfigurator(IConfigurableOutput configurableOutput) {
		super(new ContainerMachineConfigurator());
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {

	}
}
