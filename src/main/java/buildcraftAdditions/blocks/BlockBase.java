package buildcraftAdditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class BlockBase extends BlockContainer {

	protected final String name;

	public BlockBase(String name) {
		super(Material.iron);
		this.name = name;
		setBlockName(name);
		setBlockTextureName("bcadditions:" + name);
		setHardness(5F);
		setResistance(10F);
		setCreativeTab(BuildcraftAdditions.bcadditions);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof IInventory) {
			IInventory inventory = (IInventory) tile;
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack stack = inventory.getStackInSlot(i);
				if (stack != null) {
					inventory.setInventorySlotContents(i, null);
					Utils.dropItemstack(world, x, y, z, stack);
				}
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
}
