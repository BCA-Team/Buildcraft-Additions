package buildcraftAdditions.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.items.bases.ItemBase;
import buildcraftAdditions.reference.enums.EnumMachineUpgrades;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemMachineUpgrade extends ItemBase {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public ItemMachineUpgrade() {
		super("upgrade");
		setHasSubtypes(true);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null && entity instanceof IUpgradableMachine) {
			IUpgradableMachine machine = (IUpgradableMachine) entity;
			if (machine.canAcceptUpgrade(EnumMachineUpgrades.values()[stack.getItemDamage()])) {
				machine.installUpgrade(EnumMachineUpgrades.values()[stack.getItemDamage()]);
				if (!player.capabilities.isCreativeMode)
					player.getCurrentEquippedItem().stackSize--;
				return false;
			}
		}
		return false;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + EnumMachineUpgrades.values()[stack.getItemDamage()].getTag();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		list.add(Utils.localize("tooltip.forUpgradableMachines"));
	}


	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		return icons[damage];
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		icons = new IIcon[EnumMachineUpgrades.values().length];
		for (int i = 0; i < icons.length; i++)
			icons[i] = RenderUtils.registerIcon(register, EnumMachineUpgrades.values()[i].getTextureName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (EnumMachineUpgrades upgrade : EnumMachineUpgrades.values())
			list.add(new ItemStack(item, 1, upgrade.ordinal()));
	}
}
