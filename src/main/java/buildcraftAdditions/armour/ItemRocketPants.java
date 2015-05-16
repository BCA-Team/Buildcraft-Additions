package buildcraftAdditions.armour;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import buildcraftAdditions.client.models.ModelRocketPants;
import buildcraftAdditions.listeners.FlightTracker;
import buildcraftAdditions.reference.ItemsAndBlocks;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemRocketPants extends ItemPoweredArmor {
	private static final int
			MAX_LIFT = 6,
			FLY_POWER = 60,
			SPEED_POWER = 20;

	public ItemRocketPants() {
		super("rocketPants", 2);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
			this.MODEL = new ModelRocketPants();
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		setDamage(itemStack, 0);
		if (FlightTracker.wantsToFly(player.getDisplayName()) || !player.onGround) {
			ItemStack stack = player.getCurrentArmor(2);
			if (stack != null && stack.getItem() == ItemsAndBlocks.kineticBackpack) {
				ItemKineticBackpack backpack = (ItemKineticBackpack) stack.getItem();
				if (backpack.extractEnergy(stack, 40, true) == 40) {
					if (FlightTracker.wantsToMove(player.getDisplayName())) {
						player.moveFlying(0, .2f, .2f);
					}
					player.motionX *= 1.025;
					player.motionZ *= 1.025;
					backpack.extractEnergy(stack, SPEED_POWER, false);
					if (player.motionY < MAX_LIFT && FlightTracker.wantsToFly(player.getDisplayName())) {
						backpack.extractEnergy(stack, FLY_POWER, false);
						player.motionY += 0.1;
						player.fallDistance = 0;
					}
				}
			}
		}
	}


}
