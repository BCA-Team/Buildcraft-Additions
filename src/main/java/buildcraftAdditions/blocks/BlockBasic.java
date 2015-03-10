package buildcraftAdditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockBasic extends Block {

	public BlockBasic(String name) {
		super(Material.iron);
		setBlockName(name);
		setBlockTextureName("bcadditions:" + name);
	}

}
