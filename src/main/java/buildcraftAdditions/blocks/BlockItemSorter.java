package buildcraftAdditions.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.tools.IToolWrench;
import buildcraft.core.ItemList;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.client.render.RendererItemSorter;
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
	private IIcon textureIn, textureOut, textureSide, textureSide2;

	public BlockItemSorter() {
		super(Material.iron);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		textureSide = RenderUtils.registerIcon(register, "itemSorterSide");
		textureSide2 = RenderUtils.registerIcon(register, "itemSorterSide2");
		textureIn = RenderUtils.registerIcon(register, "itemSorterIn");
		textureOut = RenderUtils.registerIcon(register, "itemSorterOut");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return RendererItemSorter.RENDER_ID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		ForgeDirection direction = ForgeDirection.getOrientation(meta);
		if (side == direction.ordinal())
			return textureOut;
		if (side == direction.getOpposite().ordinal())
			return textureIn;
		if (direction.ordinal() == 0 || direction.getOpposite().ordinal() == 0)
			if (side == 2 || side == 3)
				return textureSide2;
		if (direction.ordinal() == 2 || direction.getOpposite().ordinal() == 2)
			if (side == 4 || side == 5)
				return textureSide2;
		if (direction.ordinal() == 4 || direction.getOpposite().ordinal() == 4)
			if (side == 2 || side == 3)
				return textureSide2;
		return textureSide;
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

		if (!world.isRemote)
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.ITEM_SORTER.ordinal(), world, x, y, z);

		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileItemSorter tile = (TileItemSorter) world.getTileEntity(x, y, z);
		ItemStack stack = tile.getStackInSlot(0);
		if (stack != null)
			Utils.dropItemstack(world, x, y, z, stack);
		for (int i = 1; i < tile.getSizeInventory(); ++i) {
			ItemStack list = tile.getStackInSlot(i);
			if (list != null && list.getItem() instanceof ItemList)
				Utils.dropItemstack(world, x, y, z, list);
		}
		super.breakBlock(world, x, y, z, block, meta);
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
