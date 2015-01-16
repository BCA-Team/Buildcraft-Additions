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
	public static ResourceLocation LEFT = new ResourceLocation("bcadditions:textures/gui/button1Left.png");
	public static ResourceLocation RIGHT = new ResourceLocation("bcadditions:textures/gui/button1Right.png");
	public static ResourceLocation MIDDLE = new ResourceLocation("bcadditions:textures/gui/button1CenterPart.png");
	public static ResourceLocation MIDDLESIDEHOVER = new ResourceLocation("bcadditions:textures/gui/button1CenterSidesOnHover.png");
	public static ResourceLocation MIDDLEHOVER = new ResourceLocation("bcadditions:textures/gui/button1CenterOnHover.png");

	public WidgetButton(int id, int x, int y, int width, int height, GuiBase gui) {
		super(id, x, y, width, height, gui);
	}

	@Override
	public void render(int mouseX, int mouseY) {

		int t = 6;
		ResourceLocation centerPiece = MIDDLE;
		if (getBounds().contains(mouseX, mouseY)) {
			centerPiece = MIDDLEHOVER;
		}
		while (t < width - 6) {
			RenderUtils.drawImage(centerPiece, x + t, y, 1, height);
			t++;
		}
		RenderUtils.drawImage(RIGHT, x + width - 6, y, 6, height);
		RenderUtils.drawImage(LEFT, x, y, 6, height);
		if (getBounds().contains(mouseX, mouseY)) {
			RenderUtils.drawImage(MIDDLESIDEHOVER, x + 6, y, 1, height);
			RenderUtils.drawImage(MIDDLESIDEHOVER, x + width - 6, y, 1, height);
		}
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
