package buildcraftAdditions.items.dust;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import buildcraftAdditions.api.item.dust.IDust;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemConverter extends Item {
	private IDust dust;

	public ItemConverter() {

	}

	public ItemConverter(IDust dust) {
		this.dust = dust;
	}

	public IDust getDust() {
		return dust;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("Converter item, please drop this on the ground to retreive your dust");
		list.add("My appologies for this and any issues this might have caused");
	}
}
