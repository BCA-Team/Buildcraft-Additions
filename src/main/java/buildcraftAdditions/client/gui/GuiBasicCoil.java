package buildcraftAdditions.client.gui;

import buildcraft.core.gui.GuiBuildCraft;
import buildcraftAdditions.utils.Utils;
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
    TileBasicCoil coil;

    public GuiBasicCoil(InventoryPlayer inventoryplayer, TileBasicCoil coil) {
        super(new ContainerBasicCoil(inventoryplayer, coil), coil, texture);
        this.coil = coil;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j + 79, k + 28 + (16-coil.getBurnIconHeight()), 176, 16-coil.getBurnIconHeight(), 16, coil.getBurnIconHeight());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String title = Utils.localize("tile.blockCoilBasic.name");
        fontRendererObj.drawString(Utils.localize(title), getCenteredOffset(title), 6, 0x404040);
        fontRendererObj.drawString(Utils.localize("gui.inventory"), 8, (ySize - 96) + 2, 0x404040);
    }
}
