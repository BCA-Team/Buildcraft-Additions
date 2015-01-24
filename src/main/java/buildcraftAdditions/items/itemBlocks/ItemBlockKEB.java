package buildcraftAdditions.items.itemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.utils.Utils;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemBlockKEB extends ItemBlock {

	public ItemBlockKEB(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (BuildcraftAdditions.proxy.getPlayer().getDisplayName().equals("corjaantje")) {
			if (stack.stackTagCompound != null && stack.stackTagCompound.getBoolean("creative"))
				return "Creative Kebab Extreme Bakery";
			return "Kebab Extreme Bakery";
		}
		if (stack.stackTagCompound != null && stack.stackTagCompound.getBoolean("creative"))
			return Utils.localize("tile.blockKEBT1Creative.name");
		return super.getItemStackDisplayName(stack);
	}
}
