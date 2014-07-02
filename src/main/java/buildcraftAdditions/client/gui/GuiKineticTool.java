package buildcraftAdditions.client.gui;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import org.lwjgl.opengl.GL11;

import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.items.Tools.ItemKineticTool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiKineticTool extends GuiContainer{
	
	public static ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/GUITool.png");
	ItemKineticTool tool;
	ItemStack stack;
	EntityPlayer player;

	public GuiKineticTool(InventoryPlayer inventoryplayer, ItemKineticTool Tool, IInventory inventory, ItemStack stack, EntityPlayer player) {
		super(new ContainerKineticTool(inventoryplayer, Tool, inventory, stack, player));
		this.tool = Tool;
		this.stack = stack;
		this.player = player;
        tool.setPlayer(player);
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String title = Utils.localize("item.kineticMultiTool.name");
        fontRendererObj.drawString(Utils.localize(title), (xSize - fontRendererObj.getStringWidth(title)) / 2, 6, 0x404040);
        fontRendererObj.drawString(Utils.localize("gui.inventory"), 8, (ySize-110) + 2, 0x404040);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	}
	
	@Override
	public void onGuiClosed(){
		tool.readBateries(stack, player);
        tool.writeUpgrades(stack);
	}

}
