package buildcraftAdditions.blocks;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

public class BlockChargingStation extends BlockBase {

	@SideOnly(Side.CLIENT)
	private IIcon textureFront, textureBack, textureLeft, textureRight, textureTop, textureBottom;

	public BlockChargingStation() {
		super("blockChargingStation");
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileChargingStation();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.isSneaking())
			return false;


		if (!world.isRemote)
			player.openGui(BuildcraftAdditions.instance, Variables.Gui.CHARGING_STATION.ordinal(), world, x, y, z);

		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		world.setBlockMetadataWithNotify(x, y, z, Utils.get2dOrientation(entity).getOpposite().ordinal(), 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta == 0 && side == 3)
			return textureFront;

		if (side == meta && side > 1)
			return textureFront;

		switch (side) {
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

}
