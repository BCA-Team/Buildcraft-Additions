package buildcraftAdditions.client.gui.widgets;

import java.util.List;

import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.gui.GuiBase;
import buildcraftAdditions.utils.RenderUtils;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class WidgetButton extends WidgetBase {
	public static final ResourceLocation LEFT = new ResourceLocation("bcadditions:textures/gui/Pieces/button1Left.png");
	public static final ResourceLocation RIGHT = new ResourceLocation("bcadditions:textures/gui/Pieces/button1Right.png");
	public static final ResourceLocation MIDDLE = new ResourceLocation("bcadditions:textures/gui/Pieces/button1CenterPart.png");

	public WidgetButton(int id, int x, int y, int width, GuiBase gui) {
		super(id, x, y, width, 21, gui);
	}

	@Override
	public void render(int mouseX, int mouseY) {
		RenderUtils.drawImage(LEFT, x, y, 6, 20);
	}

	@Override
	public void onWidgetClicked(int x, int y, int button) {
		super.onWidgetClicked(x, y, button);
	}

	@Override
	public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {
		super.addTooltip(mouseX, mouseY, tooltips, shift);
	}
}
