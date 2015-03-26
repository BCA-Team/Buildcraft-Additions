package buildcraftAdditions.client.render.tileentities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.tileEntities.Bases.TileBaseDuster;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class RendererDuster extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float fl) {
		if (tile != null && tile instanceof TileBaseDuster) {
			TileBaseDuster duster = (TileBaseDuster) tile;
			GL11.glPushMatrix();
			GL11.glTranslated(x + .5, y + .5 + getYOffset(), z + .5);
			float angle = 0;
			switch (ForgeDirection.getOrientation(duster.getBlockMetadata())) {
				case NORTH:
					angle = 0;
					break;
				case SOUTH:
					angle = 180;
					break;
				case WEST:
					angle = 90;
					break;
				case EAST:
					angle = -90;
					break;
				default:
					break;
			}
			GL11.glRotatef(angle, 0, 1, 0);
			ItemStack stack = duster.getStackInSlot(0);
			if (stack != null && stack.getItem() != null && stack.stackSize > 0) {
				EntityItem item = new EntityItem(Minecraft.getMinecraft().theWorld, 0, 0, 0, stack);
				item.hoverStart = 0;
				RenderManager.instance.renderEntityWithPosYaw(item, 0, 0, 0, 0, 0);
			}
			GL11.glPopMatrix();
		}
	}

	protected double getYOffset() {
		return .5 + 1D / 16;
	}
}
