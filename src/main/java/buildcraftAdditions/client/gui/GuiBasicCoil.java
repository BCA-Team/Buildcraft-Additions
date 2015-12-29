package buildcraftAdditions.client.gui;

import buildcraftAdditions.inventories.containers.ContainerBasicCoil;
import buildcraftAdditions.tileEntities.TileBasicCoil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiBasicCoil extends GuiInventory<TileBasicCoil> {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/guiBasicCoil.png");
	private final TileBasicCoil coil;

	public GuiBasicCoil(EntityPlayer player, TileBasicCoil coil) {
		super(new ContainerBasicCoil(player, coil), coil);
		setDrawPlayerInv(true);
		this.coil = coil;
	}

	@Override
	public void drawBackgroundPreWidgets(float f, int x, int y) {
		drawTexturedModalRect(guiLeft + 79, guiTop + 27 + (16 - coil.getBurnIconHeight()), 176, 16 - coil.getBurnIconHeight(), 16, coil.getBurnIconHeight());
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

}
