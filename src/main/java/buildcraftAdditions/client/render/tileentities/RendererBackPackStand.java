package buildcraftAdditions.client.render.tileentities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.models.BackPackModel;
import buildcraftAdditions.client.models.ModelBackpackStand;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class RendererBackPackStand extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float fl) {
		bindTexture(new ResourceLocation("bcadditions", "textures/models/armor/kineticBackpack_layer_1.png"));
		GL11.glPushMatrix();
		int orientation = entity.getWorldObj().getBlockMetadata(entity.xCoord, entity.yCoord, entity.zCoord);
		int angle;
		switch (orientation) {
			case 2:
				angle = 0;
				break;
			case 3:
				angle = 180;
				break;
			case 4:
				angle = 90;
				break;
			case 5:
				angle = -90;
				break;
			default:
				angle = 0;
		}
		GL11.glTranslated(x + 0.5, y + 1.4, z + 0.5);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glRotated(angle, 0, 1, 0);
		GL11.glRotated(180, 1, 0, 0);
		BackPackModel.INSTANCE.render(null, 0, 0, 0, 0, 0, 0.1F);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 2.4, z + 0.5);
		GL11.glRotated(angle, 0, 1, 0);
		GL11.glRotated(180, 1, 0, 0);
		GL11.glScaled(10, 10, 10);
		ModelBackpackStand.INSTANCE.render(null, 0, 0, 0, 0, 0, 0.01f);
		GL11.glPopMatrix();



	}
}
