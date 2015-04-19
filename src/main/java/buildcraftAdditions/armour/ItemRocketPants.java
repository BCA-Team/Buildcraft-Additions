package buildcraftAdditions.armour;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
			POWER_USE = 15,
			MAX_LIFT = 1,
			MAX_SPEED = 2;

	public ItemRocketPants() {
		super("rocketPants", 2, new ModelRocketPants());
		setUnlocalizedName("rocketPants");
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		setDamage(itemStack, 0);
		if (FlightTracker.wantsToFly(player.getDisplayName())) {
			ItemStack stack = player.getCurrentArmor(2);
			if (stack != null && stack.getItem() == ItemsAndBlocks.kineticBackpack) {
				ItemKineticBackpack backpack = (ItemKineticBackpack) stack.getItem();
				if (backpack.extractEnergy(stack, POWER_USE, true) == POWER_USE) {
					backpack.extractEnergy(stack, POWER_USE, false);
					player.motionX *= 10 / player.posY;
					if (player.motionY < MAX_LIFT)
						player.motionY += 0.1;
					player.motionZ *= 10 / player.posY;
					player.fallDistance = 0;
				}
			}
		}
	}


}
