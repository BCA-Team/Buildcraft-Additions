package buildcraftAdditions.inventories.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.tileEntities.TileBasicCoil;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerBasicCoil extends ContainerBase<TileBasicCoil> {

	private int burnTime, fullBurnTime;

	public ContainerBasicCoil(InventoryPlayer inventoryPlayer, TileBasicCoil tile) {
		super(inventoryPlayer, tile);
		addSlotToContainer(new Slot(tile, 0, 78, 43));
		addPlayerInventory(8, 84);
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, inventory.burnTime);
		crafting.sendProgressBarUpdate(this, 1, inventory.fullBurnTime);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (crafters != null) {
			for (Object o : crafters) {
				if (o != null && o instanceof ICrafting) {
					ICrafting crafting = (ICrafting) o;
					if (burnTime != inventory.burnTime)
						crafting.sendProgressBarUpdate(this, 0, inventory.burnTime);
					if (fullBurnTime != inventory.fullBurnTime)
						crafting.sendProgressBarUpdate(this, 1, inventory.fullBurnTime);
				}
			}
		}
		burnTime = inventory.burnTime;
		fullBurnTime = inventory.fullBurnTime;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		super.updateProgressBar(id, value);
		switch (id) {
			case 0:
				inventory.burnTime = value;
				break;
			case 1:
				inventory.fullBurnTime = value;
				break;
			default:
				break;
		}
	}

}
