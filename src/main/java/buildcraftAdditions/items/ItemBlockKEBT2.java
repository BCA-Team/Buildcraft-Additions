package buildcraftAdditions.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemBlockKEBT2 extends ItemBlock {

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean sneaking) {
		list.add("Build in a 2x2x2 patern to form the multiblock");
	}

	public ItemBlockKEBT2(Block block) {
		super(block);
	}
}
