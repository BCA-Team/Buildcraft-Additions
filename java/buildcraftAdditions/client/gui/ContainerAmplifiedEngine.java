package buildcraftAdditions.client.gui;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraft.core.gui.BuildCraftContainer;
import buildcraftAdditions.entities.TileAmplifiedEngine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class ContainerAmplifiedEngine extends BuildCraftContainer {
    protected TileAmplifiedEngine engine;

    public ContainerAmplifiedEngine(InventoryPlayer inventoryplayer, TileAmplifiedEngine tileEngine) {
        super(tileEngine.getSizeInventory());
        engine = tileEngine;
        addSlotToContainer(new Slot(tileEngine, 0, 80, 41));
        addSlotToContainer(new Slot(tileEngine, 1, 100, 41));
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 9; k++) {
                addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }

        }

        for (int j = 0; j < 9; j++) {
            addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < crafters.size(); i++) {
            engine.sendGUINetworkData(this, (ICrafting) crafters.get(i));
        }
    }

    @Override
    public void updateProgressBar(int i, int j) {
        engine.getGUINetworkData(i, j);
    }

    public boolean isUsableByPlayer(EntityPlayer entityplayer) {
        return engine.isUseableByPlayer(entityplayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return engine.isUseableByPlayer(entityplayer);
    }
}
