package buildcraftAdditions.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class BlockRotationBase extends BlockBase {
	protected final String textureBaseName;
	private IIcon front, bottom, top, back, sides;

	public BlockRotationBase(String name, String textureBaseName) {
		super(name);
		this.textureBaseName = textureBaseName;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		world.setBlockMetadataWithNotify(x, y, z, Utils.get2dOrientation(entity).getOpposite().ordinal(), 3);
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

		if (side == ForgeDirection.getOrientation(meta).getOpposite().ordinal())
			return back;
		return sides;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		front = RenderUtils.registerIcon(register, textureBaseName + "Front");
		back = RenderUtils.registerIcon(register, textureBaseName + "Back");
		sides = RenderUtils.registerIcon(register, textureBaseName + "Sides");
		top = RenderUtils.registerIcon(register, textureBaseName + "Top");
		bottom = RenderUtils.registerIcon(register, textureBaseName + "Bottom");
	}
}
