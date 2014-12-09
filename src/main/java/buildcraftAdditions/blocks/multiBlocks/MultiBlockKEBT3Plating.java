package buildcraftAdditions.blocks.multiBlocks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import buildcraftAdditions.multiBlocks.MultiBlockPaternKEBT3;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.tileEntities.TileKEBT3;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockKEBT3Plating extends MulitBlockBase {
	public IIcon icon[];

	public MultiBlockKEBT3Plating() {
		super('W', new MultiBlockPaternKEBT3(), "textureKEBT3Plating");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		return super.onBlockActivated(world, x, y, z, player, meta, hitX, hitY, hitZ);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileKEBT3();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if (entity instanceof EntityPlayer) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileKineticEnergyBufferBase)
				((TileKineticEnergyBufferBase) tileEntity).setOwner(((EntityPlayer) entity).getDisplayName());
		}
	}
}
