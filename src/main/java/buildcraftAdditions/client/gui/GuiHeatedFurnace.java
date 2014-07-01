package buildcraftAdditions.client.gui;

import buildcraft.core.gui.GuiBuildCraft;
import buildcraftAdditions.utils.Utils;
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
    public TileHeatedFurnace furnace;

    public GuiHeatedFurnace(InventoryPlayer inventoryplayer, TileHeatedFurnace furnace) {
        super(new ContainerHeatedFurnace(inventoryplayer, furnace), furnace, texture);
        this.furnace = furnace;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j + 79, k + 34, 176, 14, furnace.getScaledProgress(), 16);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String title = Utils.localize("tile.blockHeatedFurnace.name");
        fontRendererObj.drawString(Utils.localize(title), getCenteredOffset(title), 6, 0x404040);
        fontRendererObj.drawString(Utils.localize("gui.inventory"), 8, (ySize - 96) + 2, 0x404040);
    }
}
