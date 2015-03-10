package buildcraftAdditions.client.gui;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.widgets.WidgetSelfDestruct;
import buildcraftAdditions.inventories.containers.ContainerKEB;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiKEB extends GuiBase {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/guiKineticEnergyBuffer.png");
	private final TileKineticEnergyBufferBase keb;

	public GuiKEB(TileKineticEnergyBufferBase keb, EntityPlayer player) {
		super(new ContainerKEB(player, keb));
		this.keb = keb;
	}

	@Override
	public ResourceLocation texture() {
		return texture;
	}

	@Override
	public int getXSize() {
		return 175;
	}

	@Override
	public int getYSize() {
		return 102;
	}

	@Override
	public String getInventoryName() {
		return "kebT" + keb.tier;
	}

	@Override
	public void initialize() {
		addWidget(new WidgetSelfDestruct(0, guiLeft + 67, guiTop + 30, 46, 47, this, keb));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		super.drawGuiContainerBackgroundLayer(f, x, y);
		long percent = ((long) keb.energy * 248) / keb.maxEnergy;
		int temp = (int) percent;
		if (temp > 36)
			temp = 36;
		drawTexturedModalRect(guiLeft + 90, guiTop + 17, 215, 42, temp, 11);
		percent -= 36;
		if (percent <= 0)
			return;
		temp = (int) percent;
		if (temp > 62)
			temp = 62;
		drawTexturedModalRect(guiLeft + 115, guiTop + 28, 241, 53, 11, temp);
		percent -= 62;
		if (percent <= 0)
			return;
		temp = (int) percent;
		if (temp > 61)
			temp = 61;
		drawTexturedModalRect(guiLeft + 115 - temp, guiTop + 79, 241 - temp, 104, temp, 11);
		percent -= 61;
		if (percent <= 0)
			return;
		temp = (int) percent;
		if (percent > 62)
			temp = 62;
		drawTexturedModalRect(guiLeft + 54, guiTop + 79 - temp, 180, 104 - temp, 11, temp);
		percent -= 62;
		if (percent <= 0)
			return;
		drawTexturedModalRect(guiLeft + 65, guiTop + 17, 191, 42, (int) percent, 11);
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);
		if (shouldDrawEnergyNumber(x - guiLeft, y - guiTop)) {
			ArrayList<String> list = new ArrayList<String>();
			list.add(Utils.localizeFormatted("rf.info", keb.energy, keb.maxEnergy));
			drawHoveringText(list, x, y, fontRendererObj);
		}
	}

	private boolean shouldDrawEnergyNumber(int mouseX, int mouseY) {
		return mouseX > 54 && mouseX < 125 && mouseY > 13 && mouseY < 27 || mouseX > 54 && mouseX < 65 && mouseY > 13 && mouseY < 90 || mouseX > 54 && mouseX < 125 && mouseY > 75 && mouseY < 90 || mouseX > 110 && mouseX < 125 && mouseY > 13 && mouseY < 90;
	}
}
