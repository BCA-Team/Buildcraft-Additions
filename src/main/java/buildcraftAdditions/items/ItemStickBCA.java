package buildcraftAdditions.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.oredict.OreDictionary;

import buildcraftAdditions.items.bases.ItemBase;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemStickBCA extends ItemBase {

	public ItemStickBCA(String name) {
		super("stick" + name);
		OreDictionary.registerOre(getName(), new ItemStack(this));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		if (!getName().endsWith("Iron")) {
			list.add(Utils.localize("tooltip.forKineticMultiTool"));
			list.add(Utils.localizeAllFormatted("tooltip.stick.0", "tooltip." + getName() + ".chipset"));
			list.add(Utils.localizeAllFormatted("tooltip.stick.1", "tooltip." + getName() + ".stick"));
		}
	}
}
