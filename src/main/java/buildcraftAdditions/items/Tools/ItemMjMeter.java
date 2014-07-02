package buildcraftAdditions.items.Tools;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraft.api.mj.IBatteryObject;
import buildcraft.api.mj.MjAPI;
import buildcraftAdditions.BuildcraftAdditions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMjMeter extends Item{
	
	public IIcon icon;

	public double energyStored=5;
	public double maxEnergy=5;
	
	public ItemMjMeter(){
		super();
		setMaxDamage(0);
		setUnlocalizedName("mjMeter");
		this.setMaxStackSize(1);
		this.setCreativeTab(BuildcraftAdditions.bcadditions);
		
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity == null)
			return false;
		if (!world.isRemote){
		IBatteryObject battery = MjAPI.getMjBattery(entity);
		if (battery == null)
			return false;
		energyStored = battery.getEnergyStored();
		maxEnergy = battery.maxCapacity();
		stack.setItemDamage((int)energyStored);
		}
			ChatComponentText component = new ChatComponentText("This " + entity.getBlockType().getLocalizedName() + " currently has " + Integer.toString(stack.getItemDamage()) + " MJ Stored");
			player.addChatMessage(component);
			component = new ChatComponentText("And can hold " + maxEnergy + " MJ");
			player.addChatMessage(component);
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		icon = par1IconRegister.registerIcon("bcadditions:mjmeter");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		return icon;
	}

}
