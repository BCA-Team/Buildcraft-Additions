package buildcraftAdditions.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.tileEntities.TileKEBT2;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RendererKEBT2 extends TileEntitySpecialRenderer {
	public ResourceLocation texture = new ResourceLocation("bcadditions", "textures/blocks/energyBufferMultiblockTopAndBottom.png");

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float fl) {
		TileKEBT2 keb = (TileKEBT2) entity;
		bindTexture(texture);
		RenderHelper.disableStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0, 0, 0, 0, 0);
		tessellator.addVertexWithUV(0, 1, 0, 0, 1);
		tessellator.addVertexWithUV(1, 1, 0, 1, 1);
		tessellator.addVertexWithUV(1, 0, 0, 1, 0);

		tessellator.addVertexWithUV(0, 0, 0, 0, 0);
		tessellator.addVertexWithUV(1, 0, 0, 1, 0);
		tessellator.addVertexWithUV(1, 1, 0, 1, 1);
		tessellator.addVertexWithUV(0, 1, 0, 0, 1);

		tessellator.draw();
		GL11.glPopMatrix();
		RenderHelper.enableStandardItemLighting();
	}
}
