package buildcraftAdditions.client.gui.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.containers.ContainerPipeColoringTool;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiPipeColoringTool extends GuiBase {

	public static final ResourceLocation TEXTURE = new ResourceLocation("bcadditions", "textures/gui/guiPipeColoringTool.png");

	public GuiPipeColoringTool(ItemStack stack) {
		super (new ContainerPipeColoringTool());
	}

	@Override
	public ResourceLocation texture() {
		return TEXTURE;
	}

	@Override
	public int getXSize() {
		return 176;
	}

	@Override
	public int getYSize() {
		return 84;
	}

	@Override
	public String getInventoryName() {
		return "pipeColorTool";
	}

	@Override
	public void initialize() {

	}
}
