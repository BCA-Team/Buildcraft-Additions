package buildcraftAdditions.blocks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import buildcraftAdditions.tileEntities.TileBackpackStand;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BlockBackpackStand extends BlockRotationBase {

	public BlockBackpackStand() {
		super("backpackStand", "", false);
		setBlockBounds(2F / 10F, 0, 2F / 10F, 8F / 10F, 1, 8F / 10F);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileBackpackStand();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		player.addChatComponentMessage(new ChatComponentText(String.format("%s, %s, %s", hitX, hitY, hitZ)));
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
