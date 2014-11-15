package buildcraftAdditions.items.itemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemBlockBCA extends ItemBlock {
	public IIcon icon;

	@Override
	public IIcon getIconFromDamage(int p_77617_1_) {
		return icon;
	}

	@Override
	public void registerIcons(IIconRegister register) {
		icon = register.registerIcon("bcadditions:energyBufferMultiblockRaw");
	}

	public ItemBlockBCA(Block block) {
		super(block);
	}
}
