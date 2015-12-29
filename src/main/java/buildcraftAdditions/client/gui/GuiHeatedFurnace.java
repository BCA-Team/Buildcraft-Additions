package buildcraftAdditions.client.gui;

import buildcraftAdditions.inventories.containers.ContainerHeatedFurnace;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;
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
public class GuiHeatedFurnace extends GuiInventory<TileHeatedFurnace> {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/guiHeatedFurnace.png");

	public GuiHeatedFurnace(EntityPlayer player, TileHeatedFurnace furnace) {
		super(new ContainerHeatedFurnace(player, furnace), furnace);
		setDrawPlayerInv(true);
	}

	@Override
	public void drawBackgroundPreWidgets(float f, int x, int y) {
		drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, inventory.getScaledProgress(), 16);
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

}
