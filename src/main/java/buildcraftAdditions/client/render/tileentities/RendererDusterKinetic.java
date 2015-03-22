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

import buildcraftAdditions.client.models.ModelKineticDuster;
import buildcraftAdditions.tileEntities.TileKineticDuster;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class RendererDusterKinetic extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float fl) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y + 0.08, z);
		TileKineticDuster duster = (TileKineticDuster) entity;
		ItemStack stack = duster.getStackInSlot(0);
		EntityItem item;
		if (stack != null) {
			item = new EntityItem(Minecraft.getMinecraft().theWorld, 0, 0, 0, stack);
			item.hoverStart = 0;
			RenderManager.instance.renderEntityWithPosYaw(item, 0.5, 0.5, 0.5, 0, 0);
		}
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(x + .5, y + 1.5, z + .5);
		GL11.glRotated(180, 1, 0, 0);
		GL11.glScaled(0.063, 0.063, 0.063);
		ModelKineticDuster.INSTANCE.render(null, 0, 0, 0, 0, 0, 1f, duster.progressStage);
		GL11.glPopMatrix();
	}
}
