package buildcraftAdditions.client.render.entities;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.entities.EntityLaserShot;
import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class EntityLaserShotRenderer extends Render {

	private static final ResourceLocation texture = new ResourceLocation(Variables.MOD.ID, "textures/entities/laserShot.png");

	private void doRender(EntityLaserShot entity, double x, double y, double z, float rotation, float partialTicks) {
		bindEntityTexture(entity);
		GL11.glPushMatrix();
		float strength = entity.getStrength();
		if (strength >= 1)
			GL11.glColor3f(1, 0, 0);
		else if (strength > 0.75)
			GL11.glColor3f(0.75F, 0, 0.25F);
		else if (strength > 0.6)
			GL11.glColor3f(0.5F, 0, 0.5F);
		else if (strength > 0.35)
			GL11.glColor3f(0.25F, 0, 0.75F);
		else
			GL11.glColor3f(0, 0, 1);
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90, 0, 1, 0);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0, 0, 1);
		Tessellator t = Tessellator.instance;
		double d1 = 0;
		double d2 = 0.5;
		double d3 = 0 / 32D;
		double d4 = 5 / 32D;
		double d5 = 0.05625;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		float f1 = entity.throwableShake - partialTicks;
		if (f1 > 0) {
			float f2 = -MathHelper.sin(f1 * 3) * f1;
			GL11.glRotatef(f2, 0, 0, 1);
		}

		GL11.glRotatef(45, 1, 0, 0);
		GL11.glScaled(d5, d5, d5);
		GL11.glTranslatef(-4, 0, 0);
		for (int i = 0; i < 4; i++) {
			GL11.glRotatef(90, 1, 0, 0);
			GL11.glNormal3d(0, 0, d5);
			t.startDrawingQuads();
			t.addVertexWithUV(-8, -2, 0, d1, d3);
			t.addVertexWithUV(8, -2, 0, d2, d3);
			t.addVertexWithUV(8, 2, 0, d2, d4);
			t.addVertexWithUV(-8, 2, 0, d1, d4);
			t.draw();
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float rotation, float partialTicks) {
		if (entity instanceof EntityLaserShot)
			doRender((EntityLaserShot) entity, x, y, z, rotation, partialTicks);
	}

}
