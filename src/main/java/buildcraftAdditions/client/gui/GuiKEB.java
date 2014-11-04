package buildcraftAdditions.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.utils.Utils;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiKEB extends GuiContainer {
	public ResourceLocation texture = new ResourceLocation("bcadditions","textures/gui/KineticEnergyBuffer.png");
	private TileKineticEnergyBufferBase keb;


	public GuiKEB(TileKineticEnergyBufferBase keb) {
		super(new ContainerKEB(keb));
		this.keb = keb;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		int percent = (keb.energy * 360) / keb.maxEnergy;
		/*int temp  = percent;
		if (temp > 35)
			temp = 35;
		drawTexturedModalRect(guiLeft + 90, guiTop + 18, 212, 42, 36, temp);
		percent -= 35;
		if (percent < 0)
			return;
		temp = percent;
		if (temp >  35)
			temp = 35;
		drawTexturedModalRect(guiLeft + 90, guiTop + 53, 212, 77, 36, temp);*/

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		fontRendererObj.drawString(Utils.localize("tile.blockKEBT" + keb.tier + ".name"), 5, 6, 0x404040);
		fontRendererObj.drawString(Integer.toString(keb.getEnergyStored(ForgeDirection.UNKNOWN)), 5, 60, 0x404040);
	}

}
