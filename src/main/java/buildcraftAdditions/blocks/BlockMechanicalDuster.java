package buildcraftAdditions.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.tileEntities.TileMechanicalDuster;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockMechanicalDuster extends BlockBase {

	@SideOnly(Side.CLIENT)
	private IIcon front, sides, bottom, top[];

	public BlockMechanicalDuster() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int getal) {
		return new TileMechanicalDuster();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
		if (player.isSneaking())
			return false;
		TileMechanicalDuster duster = (TileMechanicalDuster) world.getTileEntity(x, y, z);
		duster.player = player;
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
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, stack);

		ForgeDirection orientation = Utils.get2dOrientation(entityliving);
		world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal(), 1);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
		int meta = access.getBlockMetadata(x, y, z);
		if (meta == 0 && side == 3)
			return front;

		if (side == meta && meta > 1) {
			return front;
		}

		if (side == 1) {
			TileMechanicalDuster duster = (TileMechanicalDuster) access.getTileEntity(x, y, z);
			return top[duster.progressStage];
		}

		switch (side) {
			case 0:
				return bottom;
			default:
				return sides;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		// If no metadata is set, then this is an icon.
		if (meta == 0 && side == 3)
			return front;

		if (side == meta && meta > 1)
			return front;


		switch (side) {
			case 0:
				return bottom;
			case 1:
				return top[0];
		}
		return sides;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		front = RenderUtils.registerIcon(register, "dusterMechanicalFront");
		sides = RenderUtils.registerIcon(register, "dusterMechanicalSides");
		bottom = RenderUtils.registerIcon(register, "dusterMechanicalBottom");
		top = new IIcon[4];
		top[0] = RenderUtils.registerIcon(register, "dusterMechanicalTop0");
		top[1] = RenderUtils.registerIcon(register, "dusterMechanicalTop1");
		top[2] = RenderUtils.registerIcon(register, "dusterMechanicalTop2");
		top[3] = RenderUtils.registerIcon(register, "dusterMechanicalTop3");
	}
}
