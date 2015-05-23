package buildcraftAdditions.blocks;

import com.google.common.base.Strings;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.reference.Variables;
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
	private final boolean leftRight;
	private IIcon front, bottom, top, back, sides, left, right;

	public BlockRotationBase(String name, String textureBaseName, boolean leftRight) {
		this(name, textureBaseName, leftRight, name);
	}

	public BlockRotationBase(String name, String textureBaseName, boolean leftRight, String gameRegistryName) {
		super(name, "", gameRegistryName);
		this.textureBaseName = textureBaseName;
		this.leftRight = leftRight;
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

		if (side == ForgeDirection.getOrientation(meta).getOpposite().ordinal())
			return back;

		switch (side) {
			case 0:
				return bottom;
			case 1:
				return top;
			case 3:
				if (ForgeDirection.getOrientation(meta) == ForgeDirection.WEST)
					return leftRight ? left : sides;
				return leftRight ? right : sides;
			case 5:
				if (ForgeDirection.getOrientation(meta) == ForgeDirection.SOUTH)
					return leftRight ? left : sides;
				return leftRight ? right : sides;
			case 2:
				if (ForgeDirection.getOrientation(meta) == ForgeDirection.WEST)
					return leftRight ? right : sides;
				return leftRight ? left : sides;
			case 4:
				if (ForgeDirection.getOrientation(meta) == ForgeDirection.SOUTH)
					return leftRight ? right : sides;
				return leftRight ? left : sides;
		}
		return sides;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		if (Strings.isNullOrEmpty(textureBaseName))
			return;
		front = RenderUtils.registerIcon(register, textureBaseName + "Front");
		back = RenderUtils.registerIcon(register, textureBaseName + "Back");
		if (leftRight) {
			left = RenderUtils.registerIcon(register, textureBaseName + "Left");
			right = RenderUtils.registerIcon(register, textureBaseName + "Right");
		} else {
			sides = RenderUtils.registerIcon(register, textureBaseName + "Sides");
		}
		top = RenderUtils.registerIcon(register, textureBaseName + "Top");
		bottom = RenderUtils.registerIcon(register, textureBaseName + "Bottom");
	}

	@Override
	public int getRenderType() {
		return Variables.RenderIDs.SIDED_TEXTURES_RENDER_ID;
	}
}
