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

import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileBaseDuster;
import buildcraftAdditions.tileEntities.TileSemiAutomaticDuster;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

import eureka.api.EurekaKnowledge;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockSemiAutomaticDuster extends BlockBase {

	@SideOnly(Side.CLIENT)
	private IIcon front, sides, top, bottom;

	public BlockSemiAutomaticDuster() {
		super("blockDusterSemiAutomatic");
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
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileSemiAutomaticDuster();
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance) {
		if (entity instanceof EntityPlayer) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileSemiAutomaticDuster) {
				EntityPlayer player = (EntityPlayer) entity;
				if (EurekaKnowledge.isFinished(player, Variables.Eureka.DustT1Key))
					((TileSemiAutomaticDuster) tileEntity).makeProgress((EntityPlayer) entity);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		world.setBlockMetadataWithNotify(x, y, z, Utils.get2dOrientation(entity).getOpposite().ordinal(), 1);

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
		return sides;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		front = RenderUtils.registerIcon(register, "dusterSemiAutomaticFront");
		sides = RenderUtils.registerIcon(register, "dusterSemiAutomaticSides");
		top = RenderUtils.registerIcon(register, "dusterSemiAutomaticTop");
		bottom = RenderUtils.registerIcon(register, "dusterSemiAutomaticBottom");
	}
}
