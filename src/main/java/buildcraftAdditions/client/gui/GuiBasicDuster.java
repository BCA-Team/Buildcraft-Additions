package buildcraftAdditions.client.gui;

import buildcraftAdditions.entities.TileBasicDuster;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiBasicDuster extends GuiContainer {
    public static ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/BasicCoilGui.png");
    public TileBasicDuster duster;


    public GuiBasicDuster(IInventory inventory, TileBasicDuster duster) {
        super(new ContainerBasicDuster(inventory, duster));
        this.duster = duster;

    }

    @Override
    public void drawGuiContainerBackgroundLayer(float opacity, int xMouse, int yMouse) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
