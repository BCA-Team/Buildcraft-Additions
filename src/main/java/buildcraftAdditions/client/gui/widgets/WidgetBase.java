package buildcraftAdditions.client.gui.widgets;

import java.awt.Rectangle;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.GuiBase;

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
	public int value;
	public GuiBase gui;
	public final ResourceLocation[] textures;
	public int textureIndex = 0;
	public boolean enabled = true;

	public WidgetBase(int id, int x, int y, int u, int v, int width, int height, int value, GuiBase gui, String... textures) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.u = u;
		this.v = v;
		this.width = width;
		this.height = height;
		this.value = value;
		this.gui = gui;
		this.textures = new ResourceLocation[textures.length];

		for (int i = 0; i < textures.length; i++)
			this.textures[i] = new ResourceLocation(textures[i]);
	}

	public void render() {
		float shade = enabled ? 1.0F : 0.2F;
		GL11.glColor4f(shade, shade, shade, shade);
		gui.bindTexture(textures[textureIndex]);
		gui.drawTexturedModalRect(x, y, u, v, width, height);
	}

	public void onWidgetClicked(int x, int y, int button) {
		gui.soundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		gui.widgetActionPerformed(this);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void addTooltip(int mouseX, int mouseY, List<String> curTip, boolean shift) {

	}
}
