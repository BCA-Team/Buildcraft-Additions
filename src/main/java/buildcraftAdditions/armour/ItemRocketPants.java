package buildcraftAdditions.armour;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.BuildcraftAdditions;
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
public class ItemRocketPants extends ItemArmor {
	private static final int POWER_USE = 15;
	private static final ModelBiped MODEL = new ModelRocketPants();

	public ItemRocketPants() {
		super(ArmorMaterial.IRON, BuildcraftAdditions.proxy.addArmor("kineticBackpack"), 2);
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
					player.motionY = 0.25;
					player.fallDistance = 0;
				}
			}
		}
	}

	@Override
	public void registerIcons(IIconRegister register) {
	}

	@Override
	public boolean isDamageable() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return MODEL;
	}
}
