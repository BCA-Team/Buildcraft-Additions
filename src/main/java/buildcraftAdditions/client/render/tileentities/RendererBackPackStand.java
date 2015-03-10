package buildcraftAdditions.client.render.tileentities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.models.BackPackModel;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RendererBackPackStand extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float fl) {
		bindTexture(new ResourceLocation("bcadditions", "textures/blocks/charger_bottom.png"));
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.2, z + 0.5);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		ModelBiped model = new BackPackModel();
		model.render(null, 0, 0, 0, 0, 0, 0.1F);
		GL11.glPopMatrix();
	}
}
