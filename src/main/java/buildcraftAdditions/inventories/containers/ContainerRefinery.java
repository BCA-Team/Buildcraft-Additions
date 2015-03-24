package buildcraftAdditions.inventories.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.tileEntities.TileRefinery;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerRefinery extends ContainerBase<TileRefinery> {

	private int currentHeat, requiredHeat, energyCost;

	public ContainerRefinery(InventoryPlayer inventoryPlayer, TileRefinery tile) {
		super(inventoryPlayer, tile);
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, inventory.currentHeat);
		crafting.sendProgressBarUpdate(this, 1, inventory.requiredHeat);
		crafting.sendProgressBarUpdate(this, 2, inventory.energyCost);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (crafters != null) {
			for (Object o : crafters) {
				if (o != null && o instanceof ICrafting) {
					ICrafting crafting = (ICrafting) o;
					if (currentHeat != inventory.currentHeat)
						crafting.sendProgressBarUpdate(this, 0, inventory.currentHeat);
					if (requiredHeat != inventory.requiredHeat)
						crafting.sendProgressBarUpdate(this, 1, inventory.requiredHeat);
					if (energyCost != inventory.energyCost)
						crafting.sendProgressBarUpdate(this, 2, inventory.energyCost);
				}
			}
		}
		currentHeat = inventory.currentHeat;
		requiredHeat = inventory.requiredHeat;
		energyCost = inventory.energyCost;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		super.updateProgressBar(id, value);
		switch (id) {
			case 0:
				inventory.currentHeat = value;
				break;
			case 1:
				inventory.requiredHeat = value;
				break;
			case 2:
				inventory.energyCost = value;
				break;
			default:
				break;
		}
	}


}
