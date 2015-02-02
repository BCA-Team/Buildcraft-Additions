package buildcraftAdditions.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.reference.enums.EnumMachineUpgrades;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemMachineUpgrade extends ItemBase {
	private EnumMachineUpgrades upgrade;

	public ItemMachineUpgrade(EnumMachineUpgrades upgrade) {
		super(upgrade.getTextureName());
		this.upgrade = upgrade;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null && entity instanceof IUpgradableMachine) {
			IUpgradableMachine machine = (IUpgradableMachine) entity;
			if (machine.canAcceptUpgrade(upgrade)) {
				machine.installUpgrade(upgrade);
				player.getCurrentEquippedItem().stackSize--;
				return false;
			}
		}
		return false;
	}
}
