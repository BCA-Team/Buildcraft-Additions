package buildcraftAdditions.compat.buildcraft.schematics;

import java.util.LinkedList;

import net.minecraft.item.ItemStack;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.builders.schematics.SchematicPiston;

import buildcraftAdditions.reference.BlockLoader;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class SchematicSorter extends SchematicPiston {

	@Override
	public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
		requirements.add(new ItemStack(BlockLoader.itemSorter));
	}

	@Override
	public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
		super.placeInWorld(context, x, y, z, stacks);
		tileNBT.setBoolean("reloadRotation", true);
		context.world().getTileEntity(x, y, z).readFromNBT(tileNBT);
	}
}
