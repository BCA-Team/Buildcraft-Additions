package buildcraftAdditions.items;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.variables.Variables;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemEngineeringDiary extends Item {
	public static IIcon icon;

	public ItemEngineeringDiary(){
		super();
	}

	@Override
	public IIcon getIconFromDamage(int damage) {
		return icon;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.openGui(BuildcraftAdditions.instance, Variables.GuiEngineeringDiary, world, (int)player.posX, (int) player.posY, (int) player.posZ);
		return stack;
	}

	@Override
	public void registerIcons(IIconRegister register) {
		icon = register.registerIcon("bcadditions:engineeringDiary");
	}
}
