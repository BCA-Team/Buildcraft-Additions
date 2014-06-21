package buildcraftAdditions.client.gui;

import buildcraft.core.gui.GuiBuildCraft;
import buildcraftAdditions.entities.TileHeatedFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiHeatedFurnace extends GuiBuildCraft {
    public static ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/HeatedFurnaceGUI.png");

    public GuiHeatedFurnace(InventoryPlayer inventoryplayer, TileHeatedFurnace furnace) {
        super(new ContainerHeatedFurnace(inventoryplayer, furnace), furnace, texture);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
    }
}
