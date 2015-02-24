package buildcraftAdditions.blocks;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

public class BlockChargingStation extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon textureFront, textureBack, textureLeft, textureRight, textureTop, textureBottom;

	public BlockChargingStation() {
		super(Material.iron);
		setHardness(5F);
		setResistance(10F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileChargingStation();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		super.onBlockActivated(world, x, y, z, entityplayer, par6, par7, par8, par9);

		// Drop through if the player is sneaking
		if (entityplayer.isSneaking())
			return false;


		if (!world.isRemote)
			entityplayer.openGui(BuildcraftAdditions.instance, Variables.Gui.CHARGING_STATION.ordinal(), world, x, y, z);

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
	public IIcon getIcon(int i, int j) {
		// If no metadata is set, then this is an icon.
		if (j == 0 && i == 3)
			return textureFront;

		if (i == j && i > 1)
			return textureFront;

		switch (i) {
			case 0:
				return textureBottom;
			case 1:
				return textureBottom;
			default:
				return textureBack;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		textureFront = RenderUtils.registerIcon(register, "charger_front");
		textureTop = RenderUtils.registerIcon(register, "charger_top");
		textureBack = RenderUtils.registerIcon(register, "charger_back");
		textureBottom = RenderUtils.registerIcon(register, "charger_bottom");
		textureLeft = RenderUtils.registerIcon(register, "charger_leftSide");
		textureRight = RenderUtils.registerIcon(register, "charger_rightSide");
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileChargingStation station = (TileChargingStation) world.getTileEntity(x, y, z);
		station.openInventory();
		ItemStack stack = station.getStackInSlot(0);
		if (stack != null) {
			station.setInventorySlotContents(0, null);
			Utils.dropItemstack(world, x, y, z, stack);
		}
	}

}
