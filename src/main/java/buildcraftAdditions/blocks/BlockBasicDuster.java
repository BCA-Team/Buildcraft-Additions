package buildcraftAdditions.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.tileEntities.Bases.TileBaseDuster;
import buildcraftAdditions.tileEntities.TileBasicDuster;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockBasicDuster extends BlockBase {

	@SideOnly(Side.CLIENT)
	private IIcon front, back, sides, top, bottom;

	public BlockBasicDuster() {
		super("blockDusterBasic");
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		world.setBlockMetadataWithNotify(x, y, z, Utils.get2dOrientation(entity).getOpposite().ordinal(), 1);

	}

	@Override
	public TileEntity createNewTileEntity(World world, int variable) {
		return new TileBasicDuster();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.isSneaking())
			return false;

		TileBaseDuster duster = (TileBaseDuster) world.getTileEntity(x, y, z);
		if (duster != null) {
			if (duster.getStackInSlot(0) == null && player.getCurrentEquippedItem() != null) {
				ItemStack stack = player.getCurrentEquippedItem().copy();
				stack.stackSize = 1;
				duster.setInventorySlotContents(0, stack);
				player.getCurrentEquippedItem().stackSize--;
				if (player.getCurrentEquippedItem().stackSize <= 0)
					player.setCurrentItemOrArmor(0, null);
			} else {
				if (duster.getStackInSlot(0) != null) {
					if (!world.isRemote)
						Utils.dropItemstack(world, x, y, z, duster.getStackInSlot(0));
					duster.setInventorySlotContents(0, null);
				}
			}
			world.markBlockForUpdate(x, y, z);
		}
		return true;
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float hit) {
		if (entity instanceof EntityPlayer) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileBasicDuster)
				((TileBasicDuster) tileEntity).makeProgress((EntityPlayer) entity);
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta == 0 && side == 3)
			return front;

		if (side == meta && meta > 1)
			return front;

		switch (side) {
			case 0:
				return bottom;
			case 1:
				return top;
		}

		if (side == ForgeDirection.getOrientation(meta).getOpposite().ordinal())
			return back;
		return sides;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		front = RenderUtils.registerIcon(register, "dusterFront");
		back = RenderUtils.registerIcon(register, "dusterBack");
		sides = RenderUtils.registerIcon(register, "dusterSides");
		top = RenderUtils.registerIcon(register, "dusterTop");
		bottom = RenderUtils.registerIcon(register, "dusterBottom");
	}
}
