package buildcraftAdditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
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

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockHeatedFurnace extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon front, back, sides, top, bottom, frontActivated;

	public BlockHeatedFurnace() {
		super(Material.iron);
		setHardness(5F);
		setResistance(10F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileHeatedFurnace();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		if (!world.isRemote)
			entityplayer.openGui(BuildcraftAdditions.instance, Variables.Gui.HEATED_FURNACE.ordinal(), world, x, y, z);
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileHeatedFurnace) {
			TileHeatedFurnace furnace = (TileHeatedFurnace) tile;
			furnace.updateCoils();
		}
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileHeatedFurnace) {
			TileHeatedFurnace furnace = (TileHeatedFurnace) tile;
			furnace.updateCoils();
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, stack);

		ForgeDirection orientation = Utils.get2dOrientation(entityliving);
		world.setBlockMetadataWithNotify(i, j, k, orientation.getOpposite().ordinal(), 1);

	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileHeatedFurnace heatedFurnace = (TileHeatedFurnace) world.getTileEntity(x, y, z);
		for (int t = 0; t < 2; t++) {
			ItemStack stack = heatedFurnace.getStackInSlot(t);
			if (stack != null) {
				heatedFurnace.setInventorySlotContents(t, null);
				Utils.dropItemstack(world, x, y, z, stack);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
		int meta = access.getBlockMetadata(x, y, z);
		if (meta == 0 && side == 3)
			return front;

		if (side == meta && meta > 1) {
			TileHeatedFurnace furnace = (TileHeatedFurnace) access.getTileEntity(x, y, z);
			if (furnace.isCooking) {
				return frontActivated;
			}
			return front;
		}

		switch (side) {
			case 0:
				return bottom;
			case 1:
				return top;
			case 5:
				return back;
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

		if (side == ForgeDirection.getOrientation(meta).getOpposite().ordinal())

			switch (side) {
				case 0:
					return bottom;
				case 1:
					return top;
			}
		return sides;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		front = RenderUtils.registerIcon(register, "furnaceFront");
		frontActivated = RenderUtils.registerIcon(register, "furnaceFront_on");
		top = RenderUtils.registerIcon(register, "furnaceTop");
		back = RenderUtils.registerIcon(register, "furnaceBack");
		bottom = RenderUtils.registerIcon(register, "furnaceBottom");
		sides = RenderUtils.registerIcon(register, "furnaceSide");
	}
}
