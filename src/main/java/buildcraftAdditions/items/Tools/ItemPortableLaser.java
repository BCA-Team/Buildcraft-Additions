package buildcraftAdditions.items.Tools;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.entities.EntityLaserShot;
import buildcraftAdditions.inventories.InventoryItem;
import buildcraftAdditions.inventories.InventoryPortableLaser;
import buildcraftAdditions.items.ItemInventoryPoweredBase;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.RenderUtils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemPortableLaser extends ItemInventoryPoweredBase {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

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
		int energyUse = (int) (f * ConfigurationHandler.portableLaserPowerUse);
		if (getEnergyStored(stack) < energyUse)
			return;
		extractEnergy(stack, energyUse, false);
		world.playSoundAtEntity(player, "random.bow", 1, 1 / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
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
		if (getEnergyStored(stack) < (int) (f * ConfigurationHandler.portableLaserPowerUse))
			player.stopUsingItem();
	}

	@Override
	public InventoryItem getInventory(ItemStack stack) {
		return new InventoryPortableLaser(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		this.itemIcon = RenderUtils.registerIcon(register, "portableLaser/base");
		icons = new IIcon[5];
		for (int i = 0; i < icons.length; i++)
			icons[i] = RenderUtils.registerIcon(register, "portableLaser/" + i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if (usingItem == null || useRemaining <= 0 || useRemaining >= getMaxItemUseDuration(stack))
			return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
		float j = getMaxItemUseDuration(stack) - useRemaining;
		float f = j / 20;
		f = (f * f + f * 2) / 3;
		if (f < 0.1)
			return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
		if (f >= 1)
			return icons[4];
		else if (f > 0.75)
			return icons[3];
		else if (f > 0.6)
			return icons[2];
		else if (f > 0.35)
			return icons[1];
		//else if (f >= 0.1)
		return icons[0];
	}
}
