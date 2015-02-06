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

import buildcraft.api.power.ILaserTargetBlock;

import buildcraftAdditions.tileEntities.TileKineticDuster;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockKineticDuster extends BlockBase implements ILaserTargetBlock {

	@SideOnly(Side.CLIENT)
	private IIcon bottom, sides[], top;

	public BlockKineticDuster() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int getal) {
		return new TileKineticDuster();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
		if (player.isSneaking())
			return false;
		TileKineticDuster duster = (TileKineticDuster) world.getTileEntity(x, y, z);
		if (duster != null && duster.getStackInSlot(0) == null && player.getCurrentEquippedItem() != null) {
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
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
		switch (side) {
			case 0:
				return bottom;
			case 1:
				return top;
		}
		TileKineticDuster duster = (TileKineticDuster) access.getTileEntity(x, y, z);
		return sides[duster.progressStage];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		// If no metadata is set, then this is an icon.
		switch (side) {
			case 0:
				return bottom;
			case 1:
				return top;
		}
		return sides[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		sides = new IIcon[4];
		sides[0] = RenderUtils.registerIcon(register, "dusterKineticSide0");
		sides[1] = RenderUtils.registerIcon(register, "dusterKineticSide1");
		sides[2] = RenderUtils.registerIcon(register, "dusterKineticSide2");
		sides[3] = RenderUtils.registerIcon(register, "dusterKineticSide3");
		bottom = RenderUtils.registerIcon(register, "dusterKineticBottom");
		top = RenderUtils.registerIcon(register, "dusterKineticTop");
	}
}
