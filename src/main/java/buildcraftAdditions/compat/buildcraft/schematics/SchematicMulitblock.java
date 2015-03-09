package buildcraftAdditions.compat.buildcraft.schematics;

import java.util.LinkedList;

import net.minecraft.item.ItemStack;

import buildcraft.api.blueprints.IBuilderContext;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class SchematicMulitblock extends SchematicBCABase {

	@Override
	public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
		context.world().setBlock(x, y, z, block, 0, 2);
		context.world().scheduleBlockUpdate(x, y, z, block, 80);
	}
}
