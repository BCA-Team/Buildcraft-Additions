package buildcraftAdditions.client.gui;

import buildcraftAdditions.api.EurekaRegistry;
import buildcraftAdditions.utils.Eureka;
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
	public int screen, startX[], lineLimit[], page, line;
	public boolean hasNextPage, hasPrevPage;

	public GuiEngineeringDiary(EntityPlayer player) {
		super(new ContainerEngineeringDiary(player));
		this.player = player;
		screen = -1;
		startX = new int[20];
		lineLimit = new int[20];
		page = 0;

		startX[0] = 85;
		startX[1] = 85;
		startX[2] = 85;
		startX[3] = 85;
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

		lineLimit[0] = 13;
		lineLimit[1] = 13;
		lineLimit[2] = 16;
		lineLimit[3] = 16;
		lineLimit[4] = 17;
		lineLimit[5] = 18;
		lineLimit[6] = 21;
		lineLimit[7] = 21;
		lineLimit[8] = 23;
		lineLimit[9] = 23;
		lineLimit[10] = 23;
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
		line = 0;
		if (screen == -1){
			key = "mainScreen";
		} else {
			key = EurekaRegistry.getKeys().get(screen);
		}
		String title = Utils.localize("engineeringDiary." + key + ".title");
		writeText(title, 0xF8DF17);
		line = 5;
		String description;
		if (page == 0){
			description = Utils.localize("engineeringDiary." + key + ".description");
			if (screen != -1) {
				writeText(Utils.localize("engineeringDiary.requiredResearch"), 0xFF0000);

				writeText(Utils.localize("engineeringDiary." + key + ".requiredResearch"), 0x0000FF);
				line++;
				writeText(Utils.localize("engineeringDiary.progress") + " " + Eureka.getProgress(player, key) + " / " + EurekaRegistry.getMaxValue(key), 0xFFFF00);
				line++;
				if (!Eureka.isUnlocked(player, key)) {
					writeText(Utils.localize("engineeringDiary." + key + ".howToMakeProgress"), 0xFF6600);
				} else {
					writeText(Utils.localize("engineeringDiary.unlocked"), 0xFF6600);
				}

				line++;
			}
		} else {
			description = Utils.localize("engineeringDiary." + key + ".page" + page);
		}
		hasNextPage = !(Utils.localize("engineeringDiary." + key + ".page" + (page + 1)).equals("engineeringDiary." + key.toString() + ".page" + Integer.toString(page + 1)));
		hasPrevPage = page > 0;
		writeText(description, 0xFFFFFF);
	}

	public void drawTextAtLine(String text, int line, int color){
		fontRendererObj.drawString(text, startX[line], line*8 + 6, color);
	}

	public void writeText(String text, int color){
		String[] words = text.split(" ", 0);
		String output = "";
		for (String word: words){
			if (line == 20)
				return;
			if (output.length() + word.length() > lineLimit[line]){
				drawTextAtLine(output, line, color);
				output = "";
				line++;
			}
			output = output + word + " ";
		}
		drawTextAtLine(output, line, color);
		line++;
	}


	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int status) {
		super.mouseMovedOrUp(mouseX, mouseY, status);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		if (hasNextPage && mouseX > 143 + x && mouseX < 159 + x && mouseY > 149 + y && mouseY < 164 + y && (screen == -1 || Eureka.isUnlocked(player, EurekaRegistry.getKeys().get(screen))))
			page++;
		if (hasPrevPage && mouseX > 34 + x && mouseX < 59 + x && mouseY > 13 + y && mouseY < 28 + y)
			page--;
		if (mouseX > x + 7 && mouseX < x +  31 &&  (mouseY - y) / 25 < EurekaRegistry.getKeys().size()) {
			screen = (mouseY - y) / 25;
			page = 0;
		}
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
		if (hasNextPage && (screen == -1 || Eureka.isUnlocked(player, EurekaRegistry.getKeys().get(screen))))
			drawTexturedModalRect(x + 143, y + 149, 82, 196, 16, 16);
		if (hasPrevPage)
			drawTexturedModalRect(x + 44, y + 13, 66, 196, 16, 16);
		if (hasNextPage && mouseX > 143 + x && mouseX < 159 + x && mouseY > 149 + y && mouseY < 164 + y)
			drawTexturedModalRect(x + 143, y + 149, 82, 180, 16, 16);
		if (hasPrevPage && mouseX > 44 + x && mouseX < 60 + x && mouseY > 13 + y && mouseY < 28 + y)
			drawTexturedModalRect(x + 44, y + 13, 66, 180, 16, 16);
		RenderItem item = new RenderItem();
		int teller = 0;
		for (String key: EurekaRegistry.getKeys()) {
			if (teller == screen){
				drawTexturedModalRect(x + 7, y + (24 * teller + 5), 124, 180, 24, 24);
			} else {
				drawTexturedModalRect(x + 7, y + (24 * teller + 5), 98, 180, 24, 24);
			}
			teller++;
		}
		if (screen != -1) {
			drawTexturedModalRect(x + 95, y + 38, 148, 180, 60, 7);
			String key = EurekaRegistry.getKeys().get(screen);
			drawTexturedModalRect(x + 96, y + 39, 148, 187, Eureka.getProgress(player, key) * 58 / EurekaRegistry.getMaxValue(key), 7);
		}
		teller=0;
		for (String key: EurekaRegistry.getKeys()){
			item.renderItemIntoGUI(fontRendererObj, mc.getTextureManager(), EurekaRegistry.getDisplayStack(key), x + 12, y + 24 * teller + 9);
			teller++;
		}

	}
}
