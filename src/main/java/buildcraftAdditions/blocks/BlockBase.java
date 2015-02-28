package buildcraftAdditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
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
		IInventory inventory = (IInventory) world.getTileEntity(x, y, z);
		if (inventory != null) {
			inventory.openInventory();
			for (int t = 0; t < inventory.getSizeInventory(); t++) {
				ItemStack stack = inventory.getStackInSlot(t);
				if (stack != null) {
					inventory.setInventorySlotContents(t, null);
					Utils.dropItemstack(world, x, y, z, stack);
				}
			}
			inventory.closeInventory();
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
}
