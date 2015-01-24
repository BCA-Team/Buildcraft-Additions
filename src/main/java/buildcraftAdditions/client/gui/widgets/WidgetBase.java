package buildcraftAdditions.client.gui.widgets;

import java.awt.Rectangle;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.gui.GuiBase;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class WidgetBase {

	public final int id;
	public final int x;
	public final int y;
	public final int width;
	public final int height;
	public final int u;
	public final int v;
	public GuiBase gui;
	public ResourceLocation[] textures;
	public int textureIndex = 0;
	public int value = 0;
	public boolean enabled = true;
	public boolean playSound = true;

	public WidgetBase(int id, int x, int y, int u, int v, int width, int height, GuiBase gui, String... textures) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.u = u;
		this.v = v;
		this.width = width;
		this.height = height;
		this.gui = gui;
		this.textures = new ResourceLocation[textures.length];

		for (int i = 0; i < textures.length; i++)
			this.textures[i] = new ResourceLocation(textures[i]);
	}

	public WidgetBase(int id, int x, int y, int u, int v, int width, int height, GuiBase gui, int value, String... textures) {
		this(id, x, y, u, v, width, height, gui, textures);
		this.value = value;
	}

	public WidgetBase(int id, int x, int y, int width, int height, GuiBase gui) {
		this(id, x, y, 0, 0, width, height, gui, "");
	}

	public WidgetBase setEnableClockSound(boolean value) {
		playSound = value;
		return this;
	}

	public void render(int mouseX, int mouseY) {
		float shade = enabled ? 1.0F : 0.2F;
		GL11.glColor4f(shade, shade, shade, shade);
		if (textures[textureIndex] != null)
			gui.bindTexture(textures[textureIndex]);
		gui.drawTexturedModalRect(x, y, u, v, width, height);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void onWidgetClicked(int x, int y, int button) {
		if (playSound) {
			gui.soundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		}
		gui.widgetActionPerformed(this);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {

	}
}
