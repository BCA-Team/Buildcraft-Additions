package buildcraftAdditions.client.gui.widgets;

import org.lwjgl.opengl.GL11;

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
public class WidgetButtonText extends WidgetBase {
	public static ResourceLocation LEFT = new ResourceLocation("bcadditions:textures/gui/button1Left.png");
	public static ResourceLocation RIGHT = new ResourceLocation("bcadditions:textures/gui/button1Right.png");
	public static ResourceLocation MIDDLE = new ResourceLocation("bcadditions:textures/gui/button1CenterPart.png");
	public static ResourceLocation MIDDLESIDEHOVER = new ResourceLocation("bcadditions:textures/gui/button1CenterSidesOnHover.png");
	public static ResourceLocation MIDDLEHOVER = new ResourceLocation("bcadditions:textures/gui/button1CenterOnHover.png");
	private String text;
	private int color;
	private boolean shouldRender = true;

	public WidgetButtonText(int id, int x, int y, int width, int height, GuiBase gui) {
		super(id, x, y, width, height, gui);
	}

	@Override
	public void render(int mouseX, int mouseY) {
		if (!shouldRender)
			return;
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
		gui.drawString(text, x + 8, y + 3, color);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setShouldRender(boolean shouldRender) {
		this.shouldRender = shouldRender;
	}
}
