package buildcraftAdditions.client.gui.widgets;

import java.util.List;

import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.GuiBase;
import buildcraftAdditions.reference.ItemLoader;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class WidgetColor extends WidgetBase {

	public WidgetColor(int id, int x, int y, int u, int v, int width, int height, GuiBase gui, int value, String... textures) {
		super(id, x, y, u, v, width, height, gui, value, textures);
	}

	public WidgetColor(int id, int x, int y, int u, int v, int width, int height, GuiBase gui, int value, ResourceLocation... textures) {
		super(id, x, y, u, v, width, height, gui, value, textures);
	}

	@Override
	public void render(int mouseX, int mouseY) {
		super.render(mouseX, mouseY);
		gui.bindTexture(RenderUtils.MC_ITEM_SHEET);
		gui.drawTexturedModelRectFromIcon(x + ((width - 16) / 2), y + ((height - 16) / 2), ItemLoader.pipeColoringTool.getIconFromDamage(value), 16, 16);
	}

	@Override
	public void onWidgetClicked(int x, int y, int button) {
		if (button == 0) {
			if (value >= 15)
				value = 0;
			else
				value++;
		} else if (button == 1) {
			if (value <= 0)
				value = 15;
			else
				value--;
		}

		super.onWidgetClicked(x, y, button);
	}

	@Override
	public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {
		tooltips.add(Utils.localize("gui.color." + Utils.COLOR_NAMES[value]));
	}
}
