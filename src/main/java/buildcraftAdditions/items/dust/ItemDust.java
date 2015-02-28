package buildcraftAdditions.items.dust;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.api.item.BCAItemManager;
import buildcraftAdditions.api.item.dust.IDust;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemDust extends Item {

	public ItemDust() {
		setUnlocalizedName("dustUnknown");
		setTextureName("bcadditions:dust");
		setCreativeTab(BuildcraftAdditions.bcaDusts);
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		IDust dust = BCAItemManager.dusts.getDust(stack.getItemDamage());
		if (dust != null) {
			return dust.getColorMultiplier();
		}
		return super.getColorFromItemStack(stack, pass);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		IDust dust = BCAItemManager.dusts.getDust(stack.getItemDamage());
		return dust != null ? "item.dust" + dust.getName() : super.getUnlocalizedName(stack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		IDust dust = BCAItemManager.dusts.getDust(stack.getItemDamage());
		if (dust != null) {
			String s = "item.dust" + dust.getName();
			String tS = Utils.localize(s);
			if (!s.equalsIgnoreCase(tS))
				return tS;
			return Utils.localizeAllFormatted("item.dust.name", "material." + Utils.decapitalizeFirstChar(dust.getName()) + ".name");
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
		for (IDust dust : BCAItemManager.dusts.getDusts())
			if (dust != null)
				list.add(dust.getDustStack());
	}
}
