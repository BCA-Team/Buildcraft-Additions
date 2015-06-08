package buildcraftAdditions.client.render.tileentities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.tileEntities.TileKEBT2;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class RendererKEBT2 extends TileEntitySpecialRenderer {

	public static final ResourceLocation[] side = new ResourceLocation[]{
			new ResourceLocation("bcadditions", "textures/blocks/KEB/T2/0.png"),
			new ResourceLocation("bcadditions", "textures/blocks/KEB/T2/1.png"),
			new ResourceLocation("bcadditions", "textures/blocks/KEB/T2/2.png"),
			new ResourceLocation("bcadditions", "textures/blocks/KEB/T2/3.png"),
			new ResourceLocation("bcadditions", "textures/blocks/KEB/T2/4.png"),
			new ResourceLocation("bcadditions", "textures/blocks/KEB/T2/5.png")};
	public static final ResourceLocation topAndBottom = new ResourceLocation("bcadditions", "textures/blocks/KEB/T2/topAndBottom.png");

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float fl) {
		TileKEBT2 keb = (TileKEBT2) entity;
		if (!keb.isMaster())
			return;
		bindTexture(side[keb.energyState]);
		RenderHelper.disableStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();

		//NORTH
		tessellator.addVertexWithUV(0, 0, -1, 1, 1);
		tessellator.addVertexWithUV(0, 2, -1, 1, 0);
		tessellator.addVertexWithUV(2, 2, -1, 0, 0);
		tessellator.addVertexWithUV(2, 0, -1, 0, 1);

		//EAST
		tessellator.addVertexWithUV(2, 0, -1, 0, 1);
		tessellator.addVertexWithUV(2, 2, -1, 0, 0);
		tessellator.addVertexWithUV(2, 2, 1, 1, 0);
		tessellator.addVertexWithUV(2, 0, 1, 1, 1);

		//SOUTH
		tessellator.addVertexWithUV(2, 0, 1, 0, 1);
		tessellator.addVertexWithUV(2, 2, 1, 0, 0);
		tessellator.addVertexWithUV(0, 2, 1, 1, 0);
		tessellator.addVertexWithUV(0, 0, 1, 1, 1);

		//WEST
		tessellator.addVertexWithUV(0, 0, 1, 1, 1);
		tessellator.addVertexWithUV(0, 2, 1, 1, 0);
		tessellator.addVertexWithUV(0, 2, -1, 0, 0);
		tessellator.addVertexWithUV(0, 0, -1, 0, 1);
		tessellator.draw();

		bindTexture(topAndBottom);
		tessellator.startDrawingQuads();

		//BOTTOM
		tessellator.addVertexWithUV(2, 0, -1, 1, 1);
		tessellator.addVertexWithUV(2, 0, 1, 1, 0);
		tessellator.addVertexWithUV(0, 0, 1, 0, 0);
		tessellator.addVertexWithUV(0, 0, -1, 0, 1);

		//TOP
		tessellator.addVertexWithUV(0, 2, -1, 0, 1);
		tessellator.addVertexWithUV(0, 2, 1, 0, 0);
		tessellator.addVertexWithUV(2, 2, 1, 1, 0);
		tessellator.addVertexWithUV(2, 2, -1, 1, 1);


		tessellator.draw();
		GL11.glPopMatrix();
		RenderHelper.enableStandardItemLighting();
	}
}
