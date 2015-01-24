package buildcraftAdditions.client.gui.gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.widgets.WidgetBase;
import buildcraftAdditions.utils.RenderUtils;

import eureka.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public abstract class GuiBase extends GuiContainer {

	public static final ResourceLocation PLAYER_INV_TEXTURE = new ResourceLocation("bcadditions:textures/gui/guiPlayerInv.png");

	private final ResourceLocation texture;
	private boolean drawPlayerInv = false;
	public List<WidgetBase> widgets = new ArrayList<WidgetBase>();
	public int xSizePlayerInv = 175;
	public int ySizePlayerInv = 99;
	public int titleXoffset = 5;
	public int titleYoffset = 6;
	public boolean shouldDrawWidgets = true;
	public int textColor = 0x404040;
	public boolean centerTitle = false;
	public int tileGuiYSize = 0;

	public GuiBase(Container container) {
		super(container);
		texture = texture();
		xSize = getXSize();
		ySize = getYSize();
		tileGuiYSize = getYSize();
	}

	public GuiBase setDrawPlayerInv(boolean draw) {
		drawPlayerInv = draw;
		if (draw)
			ySize = getYSize() + ySizePlayerInv;
		return this;
	}

	public GuiBase setTitleXOffset(int offset) {
		titleXoffset = offset;
		return this;
	}

	public GuiBase setTitleYOffset(int offset) {
		titleYoffset = offset;
		return this;
	}

	public GuiBase setTextColor(int color) {
		textColor = color;
		return this;
	}

	public GuiBase setCenterTitle(boolean value) {
		centerTitle = value;
		return this;
	}

	public GuiBase setDrawWidgets(boolean value) {
		shouldDrawWidgets = value;
		return this;
	}

	public abstract ResourceLocation texture();

	public abstract int getXSize();

	public abstract int getYSize();

	public abstract String getInventoryName();

	public abstract void initialize();

	public SoundHandler soundHandler() {
		return Minecraft.getMinecraft().getSoundHandler();
	}

	public void bindTexture(ResourceLocation texture) {
		RenderUtils.bindTexture(texture);
	}

	public void drawString(String text, int x, int y) {
		drawString(text, x, y, textColor);
	}

	public void drawString(String text, int x, int y, int color) {
		fontRendererObj.drawString(text, x, y, color);
	}

	public void widgetActionPerformed(WidgetBase widget) {

	}

	public void addWidget(WidgetBase widget) {
		widgets.add(widget);
	}

	@Override
	public void initGui() {
		super.initGui();
		if (drawPlayerInv)
			this.guiTop = (this.height - this.ySize) / 2;
		initialize();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(texture());
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, tileGuiYSize);

		if (drawPlayerInv) {
			bindTexture(PLAYER_INV_TEXTURE);
			drawTexturedModalRect(guiLeft, guiTop + tileGuiYSize, 0, 0, xSizePlayerInv, ySizePlayerInv);
		}
		bindTexture(texture());
		drawBackgroundPreWidgets(f, x, y);

		if (shouldDrawWidgets)
			drawWidgets(x, y);
		bindTexture(texture());

		drawBackgroundPostWidgets(f, x, y);
	}

	public void drawBackgroundPreWidgets(float f, int x, int y) {

	}

	public void drawBackgroundPostWidgets(float f, int x, int y) {

	}

	protected void drawWidgets(int x, int y) {
		for (WidgetBase widget : widgets)
			widget.render(x, y);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		if (drawPlayerInv)
			drawString(Utils.localize("container.inventory"), 5, tileGuiYSize + 6, textColor);
		String name = Utils.localize(String.format("gui.%s.name", getInventoryName()));
		drawString(name, centerTitle ? getXSize() / 2 - (name.length() * 2) : titleXoffset, titleYoffset, textColor);
		drawForegroundExtra(x, y);
	}

	public void drawForegroundExtra(int x, int y) {

	}

	@Override
	public void setWorldAndResolution(Minecraft minecraft, int width, int height) {
		widgets.clear();
		super.setWorldAndResolution(minecraft, width, height);
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		for (WidgetBase widget : widgets) {
			if (widget.getBounds().contains(x, y) && widget.enabled)
				widget.onWidgetClicked(x, y, button);
		}
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);
		List<String> tooltips = new ArrayList<String>();

		for (WidgetBase widget : widgets)
			if (widget.getBounds().contains(x, y))
				widget.addTooltip(x, y, tooltips, isShiftKeyDown());

		if (!tooltips.isEmpty()) {
			List<String> finalLines = new ArrayList<String>();
			for (String line : tooltips) {
				String[] lines = WordUtils.wrap(line, 30).split(System.getProperty("line.separator"));
				for (String wrappedLine : lines) {
					finalLines.add(wrappedLine);
				}
			}
			drawHoveringText(finalLines, x, y, fontRendererObj);
		}
	}

	public void redraw() {
		widgets.clear();
		buttonList.clear();
		initialize();
	}

	public int guiLeft() {
		return guiLeft;
	}

	public int guiTop() {
		return guiTop;
	}
}
