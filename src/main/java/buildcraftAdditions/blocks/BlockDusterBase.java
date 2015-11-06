package buildcraftAdditions.blocks;

import buildcraftAdditions.api.recipe.BCARecipeManager;
import buildcraftAdditions.tileEntities.Bases.TileBaseDuster;
import buildcraftAdditions.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class BlockDusterBase extends BlockRotationBase {

	protected final String type;

	public BlockDusterBase(String type, String texturename) {
		super("blockDuster" + type, texturename, false);
		this.type = type;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.isSneaking())
			return false;
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileBaseDuster) {
			TileBaseDuster duster = (TileBaseDuster) tile;
			if (duster.getStackInSlot(0) == null && player.getCurrentEquippedItem() != null) {
				ItemStack stack = player.getCurrentEquippedItem().copy();
				stack.stackSize = 1;
				if (BCARecipeManager.duster.getRecipe(stack) != null) {
					duster.setInventorySlotContents(0, stack);
					player.getCurrentEquippedItem().stackSize--;
					if (player.getCurrentEquippedItem().stackSize <= 0)
						player.setCurrentItemOrArmor(0, null);
				}
			} else if (duster.getStackInSlot(0) != null) {
				if (!world.isRemote)
					Utils.dropItemstackAtEntity(player, duster.getStackInSlot(0));
				duster.setInventorySlotContents(0, null);
			}
			duster.progress = 0;
		}
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
