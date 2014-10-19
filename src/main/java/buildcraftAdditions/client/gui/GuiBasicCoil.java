package buildcraftAdditions.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.tileEntities.TileBasicCoil;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiBasicCoil extends GuiContainer {
	public static ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/BasicCoilGui.png");
	TileBasicCoil coil;

	public GuiBasicCoil(InventoryPlayer inventoryplayer, TileBasicCoil coil) {
		super(new ContainerBasicCoil(inventoryplayer, coil));
		this.coil = coil;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j + 79, k + 28 + (16 - coil.getBurnIconHeight()), 176, 16 - coil.getBurnIconHeight(), 16, coil.getBurnIconHeight());
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		String title = Utils.localize("tile.blockCoilBasic.name");
		fontRendererObj.drawString(Utils.localize(title), 0, 6, 0x404040);
		fontRendererObj.drawString(Utils.localize("gui.inventory"), 8, (ySize - 96) + 2, 0x404040);
	}
}
