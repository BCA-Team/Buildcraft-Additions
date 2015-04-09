package buildcraftAdditions.inventories.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.tileEntities.TileHeatedFurnace;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerHeatedFurnace extends ContainerBase<TileHeatedFurnace> {

	private int progress;

	public ContainerHeatedFurnace(InventoryPlayer inventoryPlayer, TileHeatedFurnace tile) {
		super(inventoryPlayer, tile);
		addSlotToContainer(new Slot(tile, 0, 56, 34));
		addSlotToContainer(new SlotFurnace(inventoryPlayer.player, tile, 1, 116, 34));
		addPlayerInventory(8, 84);
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, inventory.progress);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (progress != inventory.progress && crafters != null)
			for (Object o : crafters)
				if (o != null && o instanceof ICrafting)
					((ICrafting) o).sendProgressBarUpdate(this, 0, inventory.progress);
		progress = inventory.progress;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		super.updateProgressBar(id, value);
		switch (id) {
			case 0:
				inventory.progress = value;
				break;
			default:
				break;
		}
	}


}
