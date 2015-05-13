package buildcraftAdditions.armour;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.BuildcraftAdditions;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemPoweredArmor extends ItemArmor {
	protected ModelBiped MODEL;

	public ItemPoweredArmor(String name, int slot) {
		super(ArmorMaterial.IRON, BuildcraftAdditions.proxy.addArmor(name), slot);
		setUnlocalizedName(name);
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
