package buildcraftAdditions.client.gui.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.containers.ContainerBasicCoil;
import buildcraftAdditions.tileEntities.TileBasicCoil;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiBasicCoil extends GuiBase {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/guiBasicCoil.png");
	private final TileBasicCoil coil;

	public GuiBasicCoil(InventoryPlayer inventoryplayer, TileBasicCoil coil) {
		super(new ContainerBasicCoil(inventoryplayer, coil));
		this.coil = coil;
	}

	@Override
	public void drawBackgroundPreWidgets(float f, int x, int y) {
		drawTexturedModalRect((width - xSize) / 2 + 79, (height - ySize) / 2 + 27 + (16 - coil.getBurnIconHeight()), 176, 16 - coil.getBurnIconHeight(), 16, coil.getBurnIconHeight());
	}

	@Override
	public ResourceLocation texture() {
		return texture;
	}

	@Override
	public int getXSize() {
		return 176;
	}

	@Override
	public int getYSize() {
		return 66;
	}

	@Override
	public String getInventoryName() {
		return "basicCoil";
	}

	@Override
	public void initialize() {
	}
}
