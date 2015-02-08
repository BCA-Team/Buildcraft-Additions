package buildcraftAdditions.client.gui.widgets;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.gui.GuiBase;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class WidgetImage extends WidgetBase {
	private final ResourceLocation texture;
	private final String tooltip;

	public WidgetImage(int id, int x, int y, int width, int height, GuiBase gui, ResourceLocation texture, String tooltip) {
		super(id, x, y, width, height, gui);
		this.texture = texture;
		this.tooltip = tooltip;
	}

	@Override
	public void render(int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtils.drawImage(texture, x, y, width, height);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {
		tooltips.add(Utils.localize(tooltip));
	}
}
