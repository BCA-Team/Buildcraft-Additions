package buildcraftAdditions.compat.buildcraft.schematics;

import java.util.LinkedList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.core.builders.schematics.SchematicRotateMeta;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class SchematicRotatableBCABlock extends SchematicRotateMeta {

	public SchematicRotatableBCABlock(int[] rotations, boolean rotateForward) {
		super(rotations, rotateForward);
	}

	@Override
	public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
		super.initializeFromObjectAt(context, x, y, z);
		tileNBT = new NBTTagCompound();
	}

	@Override
	public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
		requirements.add(new ItemStack(block, 1, 0));
	}

	@Override
	public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
		context.world().setBlock(x, y, z, block, meta, 3);
	}
}
