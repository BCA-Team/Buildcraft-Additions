package buildcraftAdditions.client.gui;

import buildcraft.core.gui.BuildCraftContainer;
import buildcraftAdditions.entities.TileBasicCoil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerBasicCoil extends BuildCraftContainer {
    public TileBasicCoil coil;

    public ContainerBasicCoil(IInventory inventory, TileBasicCoil coil){
        super(coil.getSizeInventory());
        this.coil = coil;

        this.addSlot(new Slot(coil, 0, 78, 43));

        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }
        for (int hotbbarIndex = 0; hotbbarIndex < 9; ++hotbbarIndex) {
            this.addSlotToContainer(new Slot(inventory, hotbbarIndex, 8 + hotbbarIndex * 18, 142));
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        coil.sendNetworkUpdate();
    }
}
