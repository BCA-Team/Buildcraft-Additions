package buildcraftAdditions.inventories.containers;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.tileEntities.TileCoolingTower;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ContainerCoolingTower extends ContainerBase<TileCoolingTower> {

	private int heat;

	public ContainerCoolingTower(InventoryPlayer inventoryPlayer, TileCoolingTower tile) {
		super(inventoryPlayer, tile);
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, MathHelper.floor_float(inventory.heat * 32));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (crafters != null)
			for (Object o : crafters)
				if (o != null && o instanceof ICrafting && heat != MathHelper.floor_float(inventory.heat * 32))
					((ICrafting) o).sendProgressBarUpdate(this, 0, MathHelper.floor_float(inventory.heat * 32));
		heat = MathHelper.floor_float(inventory.heat * 32);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		super.updateProgressBar(id, value);
		switch (id) {
			case 0:
				inventory.heat = value / 32F;
				break;
			default:
				break;
		}
	}

}
