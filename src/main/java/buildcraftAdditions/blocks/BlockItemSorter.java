package buildcraftAdditions.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.tools.IToolWrench;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileItemSorter;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockItemSorter extends BlockContainer {

	@SideOnly(Side.CLIENT)
	IIcon textureSide;
	@SideOnly(Side.CLIENT)
	IIcon textureUp;
	@SideOnly(Side.CLIENT)
	IIcon textureDown;
	@SideOnly(Side.CLIENT)
	IIcon textureLeft;
	@SideOnly(Side.CLIENT)
	IIcon textureRight;

	public BlockItemSorter() {
		super(Material.iron);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		textureSide = RenderUtils.registerIcon(register, "itemSorterSide");
		textureUp = RenderUtils.registerIcon(register, "itemSorterUp");
		textureDown = RenderUtils.registerIcon(register, "itemSorterDown");
		textureLeft = RenderUtils.registerIcon(register, "itemSorterLeft");
		textureRight = RenderUtils.registerIcon(register, "itemSorterRight");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		switch (side) {
			case 0:
				return textureLeft;
			case 1:
				return textureLeft;
			case 2:
				return textureRight;
			case 3:
				return textureLeft;
			case 4:
				return textureSide;
			case 5:
				return blockIcon;
			default:
				return blockIcon;
		}
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) { //Terrible code but works
		int meta = world.getBlockMetadata(x, y, z);

		switch (meta) {
			case 0:
				switch (side) {
					case 0:
					case 1:
						return textureSide;
					case 2:
					case 3:
					case 4:
					case 5:
						return textureDown;
				}
			case 1:
				switch (side) {
					case 0:
					case 1:
						return textureSide;
					case 2:
					case 3:
					case 4:
					case 5:
						return textureUp;
				}
			case 2:
				switch (side) {
					case 0:
					case 1:
						return textureUp;
					case 2:
					case 3:
						return textureSide;
					case 4:
						return textureLeft;
					case 5:
						return textureRight;
				}
			case 3:
				switch (side) {
					case 0:
					case 1:
						return textureDown;
					case 2:
					case 3:
						return textureSide;
					case 4:
						return textureRight;
					case 5:
						return textureLeft;
				}
			case 4:
				switch (side) {
					case 0:
					case 1:
					case 3:
						return textureLeft;
					case 2:
						return textureRight;
					case 4:
					case 5:
						return textureSide;
				}
			case 5:
				switch (side) {
					case 0:
					case 1:
					case 3:
						return textureRight;
					case 2:
						return textureLeft;
					case 4:
					case 5:
						return textureSide;
				}
			default:
				return blockIcon;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		ForgeDirection orientation = Utils.get3dOrientation(entity).getOpposite();
		world.setBlockMetadataWithNotify(x, y, z, orientation.ordinal(), 1);
		getSorterTile(world, x, y, z).setRotation(orientation);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.isSneaking())
			return false;

		Item equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem().getItem() : null;
		if (equipped != null && equipped instanceof IToolWrench && ((IToolWrench) equipped).canWrench(player, x, y, z)) {
			getSorterTile(world, x, y, z).setRotation(ForgeDirection.getOrientation(rotate(world.getBlockMetadata(x, y, z))));
			((IToolWrench) equipped).wrenchUsed(player, x, y, z);
			return true;
		}

		if (!world.isRemote) {
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.ITEM_SORTER, world, x, y, z);
			return true;
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileItemSorter();
	}

	protected TileItemSorter getSorterTile(World world, int x, int y, int z) {
		return (TileItemSorter) world.getTileEntity(x, y, z);
	}

	protected int rotate(int meta) {
		int rotatedMeta = meta + 1;
		if (rotatedMeta > 5)
			rotatedMeta = 0;
		return rotatedMeta;
	}
}
