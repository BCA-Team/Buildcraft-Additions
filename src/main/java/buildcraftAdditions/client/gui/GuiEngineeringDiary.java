package buildcraftAdditions.client.gui;

import buildcraftAdditions.api.EurekaRegistry;
import buildcraftAdditions.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiEngineeringDiary extends GuiContainer {
	public static ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/EngineeringDiary.png");
	public EntityPlayer player;
	public int screen, startX[], lineLimit[];

	public GuiEngineeringDiary(EntityPlayer player) {
		super(new ContainerEngineeringDiary(player));
		this.player = player;
		screen = -1;
		startX = new int[20];
		lineLimit = new int[20];

		startX[0] = 90;
		startX[1] = 90;
		startX[2] = 90;
		startX[3] = 90;
		startX[4] = 50;
		startX[5] = 45;
		startX[6] = 40;
		startX[7] = 35;
		startX[8] = 30;
		startX[9] = 25;
		startX[10] = 25;
		startX[11] = 25;
		startX[12] = 25;
		startX[13] = 25;
		startX[14] = 25;
		startX[15] = 25;
		startX[16] = 25;
		startX[17] = 25;
		startX[18] = 25;
		startX[19] = 25;

		lineLimit[0] = 11;
		lineLimit[1] = 11;
		lineLimit[2] = 11;
		lineLimit[3] = 11;
		lineLimit[4] = 20;
		lineLimit[5] = 20;
		lineLimit[6] = 22;
		lineLimit[7] = 22;
		lineLimit[8] = 24;
		lineLimit[9] = 24;
		lineLimit[10] = 24;
		lineLimit[11] = 21;
		lineLimit[12] = 21;
		lineLimit[13] = 20;
		lineLimit[14] = 18;
		lineLimit[15] = 16;
		lineLimit[16] = 14;
		lineLimit[17] = 14;
		lineLimit[18] = 12;
		lineLimit[19] = 12;

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String key;
		String output = "";
		int line = 0;
		if (screen == -1){
			key = "mainScreen";
		} else {
			key = EurekaRegistry.getKeys().get(screen);
		}
		String title = Utils.localize("engineeringDiary." + key + ".title");
		String[] titleWords = title.split(" ", 0);
		for (String word: titleWords){
			if (output.length() + word.length() > lineLimit[line]){
				fontRendererObj.drawString(output, startX[line], line*7 + line + 6, Integer.parseInt("F8DF17", 16));
				output = "";
				line++;
			}
			output = output + word + " ";
		}
		fontRendererObj.drawString(output, startX[line], line*7 + line + 6, 0xF8DF17);
		output = "";
		line = 5;
		String description = Utils.localize("engineeringDiary." + key + ".description");
		String[] descriptionWords = description.split(" ", 0);
		for (String word: descriptionWords){
			if (output.length() + word.length() > lineLimit[line]){
				fontRendererObj.drawString(output, startX[line], line*7 + line + 6, 0xFFFFFF);
				output = "";
				line++;
			}
			output = output + word + " ";
		}
		fontRendererObj.drawString(output, startX[line], line*7 + line + 6, 0xFFFFFF);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		xSize = 210;
		ySize = 180;
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 30, 0, xSize, ySize);
		int teller = 1;
		for (String key : EurekaRegistry.getKeys()){
			drawTexturedModalRect(x + 7, y - 6 + teller + (15 * teller), 98, 180, 22, 15);
			RenderItem item = new RenderItem();
			item.renderItemIntoGUI(fontRendererObj, mc.getTextureManager(), EurekaRegistry.getDisplayStack(key), x + 11, y - 7 + teller + (15 * teller));
			teller++;


		}
	}
}
