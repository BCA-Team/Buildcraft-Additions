package buildcraftAdditions.armour;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.models.ModelRocketPants;
import buildcraftAdditions.listeners.FlightTracker;
import buildcraftAdditions.reference.ArmorLoader;
import buildcraftAdditions.utils.RenderUtils;

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

	public static IIcon icon;

	public ItemRocketPants() {
		super("rocketPants", 2);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		setDamage(itemStack, 0);
		if (FlightTracker.wantsToFly(player) || !player.onGround) {
			ItemStack stack = player.getCurrentArmor(2);
			if (stack != null && stack.getItem() == ArmorLoader.kineticBackpack) {
				ItemKineticBackpack backpack = (ItemKineticBackpack) stack.getItem();
				if (backpack.extractEnergy(stack, 40, true) == 40) {
					if (FlightTracker.wantsToMove(player)) {
						player.moveFlying(0, .2f, .2f);
					}
					player.motionX *= 1.025;
					player.motionZ *= 1.025;
					backpack.extractEnergy(stack, SPEED_POWER, false);
					if (player.motionY < MAX_LIFT && FlightTracker.wantsToFly(player)) {
						backpack.extractEnergy(stack, FLY_POWER, false);
						player.motionY += 0.1;
						player.fallDistance = 0;
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return ModelRocketPants.INSTANCE;
	}

	@Override
	public void registerIcons(IIconRegister register) {
		icon = RenderUtils.registerIcon(register, "rocketPants");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return icon;
	}

	@Override
	public IIcon getIconFromDamage(int damage) {
		return icon;
	}
}
