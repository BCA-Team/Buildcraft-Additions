package buildcraftAdditions.client.gui;

import buildcraft.core.gui.GuiBuildCraft;
import buildcraftAdditions.entities.TileBasicCoil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class GuiBasicCoil extends GuiBuildCraft {
    public static ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/BasicCoilGui.png");

    public GuiBasicCoil(InventoryPlayer inventoryplayer, TileBasicCoil coil) {
        super(new ContainerBasicCoil(inventoryplayer, coil), coil, texture);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
    }
}
