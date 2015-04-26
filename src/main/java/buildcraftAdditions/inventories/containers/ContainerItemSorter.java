package buildcraftAdditions.inventories.containers;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.GuiBase;
import buildcraftAdditions.inventories.slots.SlotPhantom;
import buildcraftAdditions.proxy.ClientProxy;
import buildcraftAdditions.tileEntities.TileItemSorter;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerItemSorter extends ContainerBase<TileItemSorter> {

	private final byte[] colors = new byte[inventory.colors.length];

	public ContainerItemSorter(InventoryPlayer inventoryPlayer, TileItemSorter tile) {
		super(inventoryPlayer, tile);
		for (int i = 0; i < 8; i++)
			addSorterSlotColumn(26 + i * 18, 18, 1 + i * 6);
		addPlayerInventory(8, 160);
		setCanShift(false);
	}

	public void addSorterSlotColumn(int x, int y, int startID) {
		int id = startID;
		for (int i = 0; i < 6; i++) {
			addSlotToContainer(new SlotPhantom(inventory, id, x, y + i * 18));
			id++;
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		for (int i = 0; i < colors.length; i++)
			crafting.sendProgressBarUpdate(this, i, inventory.colors[i]);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (crafters != null) {
			for (Object o : crafters) {
				if (o != null && o instanceof ICrafting) {
					ICrafting crafting = (ICrafting) o;
					for (int i = 0; i < colors.length; i++)
						if (colors[i] != inventory.colors[i])
							crafting.sendProgressBarUpdate(this, i, inventory.colors[i]);
				}
			}
		}
		for (int i = 0; i < colors.length; i++)
			colors[i] = inventory.colors[i];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		super.updateProgressBar(id, value);
		if (id >= 0 && id < colors.length) {
			inventory.colors[id] = (byte) (0xFF & value);
			redrawOpenGui();
		}
	}
}
