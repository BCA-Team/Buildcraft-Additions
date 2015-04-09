package buildcraftAdditions.armour;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.listeners.FlightTracker;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemRocketPants extends ItemArmor {

	public ItemRocketPants() {
		super(ArmorMaterial.IRON, BuildcraftAdditions.proxy.addArmor("kineticBackpack"), 2);
		setUnlocalizedName("rocketPants");
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (FlightTracker.wantsToFly(player.getDisplayName())) {
			player.motionY = 0.25;
			player.fallDistance = 0;
		}
	}

	@Override
	public void registerIcons(IIconRegister register) {
	}
}
