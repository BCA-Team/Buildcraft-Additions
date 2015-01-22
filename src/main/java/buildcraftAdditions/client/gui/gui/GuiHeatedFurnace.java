package buildcraftAdditions.client.gui.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.containers.ContainerHeatedFurnace;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiHeatedFurnace extends GuiBase {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/guiHeatedFurnace.png");
	private final TileHeatedFurnace furnace;

	public GuiHeatedFurnace(InventoryPlayer inventoryplayer, TileHeatedFurnace furnace) {
		super(new ContainerHeatedFurnace(inventoryplayer, furnace));
		setDrawPlayerInv(true);
		this.furnace = furnace;
	}

	@Override
	public void drawBackgroundPreWidgets(float f, int x, int y) {
		drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, furnace.getScaledProgress(), 16);
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
		return "heatedFurnace";
	}

	@Override
	public void initialize() {
	}
}
