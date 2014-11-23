package buildcraftAdditions.blocks.multiBlocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import buildcraftAdditions.multiBlocks.MultiBlockPaternKEBT2;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.tileEntities.TileKEBT2;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockKEBT2 extends MulitBlockBase {
	public IIcon icon;

	@Override
	public void registerBlockIcons(IIconRegister register) {
		icon = register.registerIcon("bcadditions:energyBufferMultiblockRaw");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return icon;
	}

	public MultiBlockKEBT2() {
		super('K', new MultiBlockPaternKEBT2());
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileKEBT2();
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity instanceof TileKEBT2)
			((TileKEBT2) entity).destruction();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if (entity instanceof EntityPlayer) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileKineticEnergyBufferBase)
				((TileKineticEnergyBufferBase) tileEntity).setOwner(((EntityPlayer) entity).getDisplayName());
		}
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
}
