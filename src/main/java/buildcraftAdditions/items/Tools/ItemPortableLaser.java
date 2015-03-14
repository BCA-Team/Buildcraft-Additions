package buildcraftAdditions.items.Tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.entities.EntityLaserShot;
import buildcraftAdditions.inventories.InventoryItem;
import buildcraftAdditions.inventories.InventoryPortableLaser;
import buildcraftAdditions.items.ItemInventoryPoweredBase;
import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemPortableLaser extends ItemInventoryPoweredBase {

	public ItemPortableLaser() {
		super("portableLaser");
		setNoRepair();
		setFull3D();
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return Integer.MAX_VALUE;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && player.isSneaking()) {
			MovingObjectPosition mop = getMovingObjectPositionFromPlayer(world, player, true);
			if (mop == null || mop.typeOfHit == MovingObjectPosition.MovingObjectType.MISS || mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
				player.openGui(BuildcraftAdditions.instance, Variables.Gui.PORTABLE_LASER.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
				return stack;
			}
		}
		if (getEnergyStored(stack) > 0)
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int count) {
		float j = getMaxItemUseDuration(stack) - count;
		float f = j / 20;
		f = (f * f + f * 2) / 3;
		if (f < 0.1)
			return;
		if (f > 1)
			f = 1;
		f *= 10;
		if (getEnergyStored(stack) < (int) (f * ConfigurationHandler.portableLaserPowerUse))
			return;
		extractEnergy(stack, (int) (f * ConfigurationHandler.portableLaserPowerUse), false);
		world.playSoundAtEntity(player, "random.bow", 1, 1 / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.25F);
		if (!world.isRemote)
			world.spawnEntityInWorld(new EntityLaserShot(world, player, f));
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		float j = getMaxItemUseDuration(stack) - (count + 1);
		float f = j / 20;
		f = (f * f + f * 2) / 3;
		if (f < 0.1)
			return;
		if (f > 1)
			f = 1;
		f *= 10;
		if (getEnergyStored(stack) < f * ConfigurationHandler.portableLaserPowerUse)
			player.stopUsingItem();
	}

	@Override
	public InventoryItem getInventory(ItemStack stack) {
		return new InventoryPortableLaser(stack);
	}
}
