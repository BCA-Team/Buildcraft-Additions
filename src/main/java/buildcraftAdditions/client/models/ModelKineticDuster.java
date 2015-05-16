package buildcraftAdditions.client.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.client.FMLClientHandler;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ModelKineticDuster extends ModelBiped {

	private static final ResourceLocation TEXTURE0 = new ResourceLocation("bcadditions", "textures/models/kineticDuster/0.png");
	private static final ResourceLocation TEXTURE1 = new ResourceLocation("bcadditions", "textures/models/kineticDuster/1.png");
	private static final ResourceLocation TEXTURE2 = new ResourceLocation("bcadditions", "textures/models/kineticDuster/2.png");
	private static final ResourceLocation TEXTURE3 = new ResourceLocation("bcadditions", "textures/models/kineticDuster/3.png");

	public ModelRenderer cube;
	public ModelRenderer shape11;
	public ModelRenderer shape11_1;
	public ModelRenderer shape11_2;
	public ModelRenderer shape11_3;
	public ModelRenderer shape11_4;
	public ModelRenderer shape11_5;
	public ModelRenderer shape11_6;
	public ModelRenderer shape11_7;
	public ModelRenderer shape24;
	public ModelRenderer shape24_1;
	public ModelRenderer shape24_2;
	public ModelRenderer shape24_3;
	public ModelRenderer shape24_4;
	public ModelRenderer shape24_5;

	public ModelKineticDuster() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.cube = new ModelRenderer(this, 0, 0);
		this.cube.setRotationPoint(-8.0F, 15.0F, -8.0F);
		this.cube.addBox(0.0F, 0.0F, 0.0F, 16, 9, 16, 0.0F);
		this.shape11_2 = new ModelRenderer(this, 0, 0);
		this.shape11_2.setRotationPoint(7.0F, 8.0F, -8.0F);
		this.shape11_2.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
		this.shape24_3 = new ModelRenderer(this, 0, 0);
		this.shape24_3.setRotationPoint(-8.0F, 9.0F, -7.0F);
		this.shape24_3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape11_7 = new ModelRenderer(this, 0, 0);
		this.shape11_7.setRotationPoint(-7.0F, 9.0F, 8.0F);
		this.shape11_7.addBox(0.0F, 0.0F, 0.0F, 1, 14, 1, 0.0F);
		this.setRotateAngle(shape11_7, 1.5707963267948966F, 1.5707963267948966F, 0.0F);
		this.shape11_5 = new ModelRenderer(this, 0, 0);
		this.shape11_5.setRotationPoint(7.0F, 9.0F, -7.0F);
		this.shape11_5.addBox(0.0F, 0.0F, 0.0F, 1, 14, 1, 0.0F);
		this.setRotateAngle(shape11_5, 1.5707963267948966F, 0.0F, 0.0F);
		this.shape11_1 = new ModelRenderer(this, 0, 0);
		this.shape11_1.setRotationPoint(-8.0F, 8.0F, -8.0F);
		this.shape11_1.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
		this.shape24_1 = new ModelRenderer(this, 0, 0);
		this.shape24_1.setRotationPoint(-8.0F, 9.0F, -7.0F);
		this.shape24_1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape24_2 = new ModelRenderer(this, 0, 0);
		this.shape24_2.setRotationPoint(7.0F, 9.0F, -7.0F);
		this.shape24_2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape24_5 = new ModelRenderer(this, 0, 0);
		this.shape24_5.setRotationPoint(-7.0F, 9.0F, 7.0F);
		this.shape24_5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(shape24_5, 3.141592653589793F, 0.0F, 0.0F);
		this.shape11_4 = new ModelRenderer(this, 0, 0);
		this.shape11_4.setRotationPoint(-8.0F, 9.0F, -7.0F);
		this.shape11_4.addBox(0.0F, 0.0F, 0.0F, 1, 14, 1, 0.0F);
		this.setRotateAngle(shape11_4, 1.5707963267948966F, 0.0F, 0.0F);
		this.shape11_3 = new ModelRenderer(this, 0, 0);
		this.shape11_3.setRotationPoint(7.0F, 8.0F, 7.0F);
		this.shape11_3.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
		this.shape11_6 = new ModelRenderer(this, 0, 0);
		this.shape11_6.setRotationPoint(-7.0F, 9.0F, -7.0F);
		this.shape11_6.addBox(0.0F, 0.0F, 0.0F, 1, 14, 1, 0.0F);
		this.setRotateAngle(shape11_6, 1.5707963267948966F, 1.5707963267948966F, 0.0F);
		this.shape11 = new ModelRenderer(this, 0, 0);
		this.shape11.setRotationPoint(-8.0F, 8.0F, 7.0F);
		this.shape11.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1, 0.0F);
		this.shape24 = new ModelRenderer(this, 0, 0);
		this.shape24.setRotationPoint(-7.0F, 9.0F, -8.0F);
		this.shape24.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape24_4 = new ModelRenderer(this, 0, 0);
		this.shape24_4.setRotationPoint(7.0F, 9.0F, 8.0F);
		this.shape24_4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(shape24_4, 0.0F, 3.141592653589793F, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.cube.render(f5);
		this.shape11_2.render(f5);
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape24_3.offsetX, this.shape24_3.offsetY, this.shape24_3.offsetZ);
		GL11.glTranslatef(this.shape24_3.rotationPointX * f5, this.shape24_3.rotationPointY * f5, this.shape24_3.rotationPointZ * f5);
		GL11.glScaled(1.0D, 6.0D, 14.0D);
		GL11.glTranslatef(-this.shape24_3.offsetX, -this.shape24_3.offsetY, -this.shape24_3.offsetZ);
		GL11.glTranslatef(-this.shape24_3.rotationPointX * f5, -this.shape24_3.rotationPointY * f5, -this.shape24_3.rotationPointZ * f5);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
		this.shape24_3.render(f5);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		this.shape11_7.render(f5);
		this.shape11_5.render(f5);
		this.shape11_1.render(f5);
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape24_1.offsetX, this.shape24_1.offsetY, this.shape24_1.offsetZ);
		GL11.glTranslatef(this.shape24_1.rotationPointX * f5, this.shape24_1.rotationPointY * f5, this.shape24_1.rotationPointZ * f5);
		GL11.glScaled(1.0D, 6.0D, 14.0D);
		GL11.glTranslatef(-this.shape24_1.offsetX, -this.shape24_1.offsetY, -this.shape24_1.offsetZ);
		GL11.glTranslatef(-this.shape24_1.rotationPointX * f5, -this.shape24_1.rotationPointY * f5, -this.shape24_1.rotationPointZ * f5);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
		this.shape24_1.render(f5);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape24_2.offsetX, this.shape24_2.offsetY, this.shape24_2.offsetZ);
		GL11.glTranslatef(this.shape24_2.rotationPointX * f5, this.shape24_2.rotationPointY * f5, this.shape24_2.rotationPointZ * f5);
		GL11.glScaled(1.0D, 6.0D, 14.0D);
		GL11.glTranslatef(-this.shape24_2.offsetX, -this.shape24_2.offsetY, -this.shape24_2.offsetZ);
		GL11.glTranslatef(-this.shape24_2.rotationPointX * f5, -this.shape24_2.rotationPointY * f5, -this.shape24_2.rotationPointZ * f5);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
		this.shape24_2.render(f5);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape24_5.offsetX, this.shape24_5.offsetY, this.shape24_5.offsetZ);
		GL11.glTranslatef(this.shape24_5.rotationPointX * f5, this.shape24_5.rotationPointY * f5, this.shape24_5.rotationPointZ * f5);
		GL11.glScaled(14.0D, 1.0D, 14.0D);
		GL11.glTranslatef(-this.shape24_5.offsetX, -this.shape24_5.offsetY, -this.shape24_5.offsetZ);
		GL11.glTranslatef(-this.shape24_5.rotationPointX * f5, -this.shape24_5.rotationPointY * f5, -this.shape24_5.rotationPointZ * f5);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
		this.shape24_5.render(f5);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		this.shape11_4.render(f5);
		this.shape11_3.render(f5);
		this.shape11_6.render(f5);
		this.shape11.render(f5);
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape24.offsetX, this.shape24.offsetY, this.shape24.offsetZ);
		GL11.glTranslatef(this.shape24.rotationPointX * f5, this.shape24.rotationPointY * f5, this.shape24.rotationPointZ * f5);
		GL11.glScaled(14.0D, 6.0D, 1.0D);
		GL11.glTranslatef(-this.shape24.offsetX, -this.shape24.offsetY, -this.shape24.offsetZ);
		GL11.glTranslatef(-this.shape24.rotationPointX * f5, -this.shape24.rotationPointY * f5, -this.shape24.rotationPointZ * f5);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
		this.shape24.render(f5);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape24_4.offsetX, this.shape24_4.offsetY, this.shape24_4.offsetZ);
		GL11.glTranslatef(this.shape24_4.rotationPointX * f5, this.shape24_4.rotationPointY * f5, this.shape24_4.rotationPointZ * f5);
		GL11.glScaled(14.0D, 6.0D, 1.0D);
		GL11.glTranslatef(-this.shape24_4.offsetX, -this.shape24_4.offsetY, -this.shape24_4.offsetZ);
		GL11.glTranslatef(-this.shape24_4.rotationPointX * f5, -this.shape24_4.rotationPointY * f5, -this.shape24_4.rotationPointZ * f5);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
		this.shape24_4.render(f5);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public void render(int texture) {
		switch (texture) {
			case 0:
				FMLClientHandler.instance().getClient().getTextureManager().bindTexture(TEXTURE0);
				break;
			case 1:
				FMLClientHandler.instance().getClient().getTextureManager().bindTexture(TEXTURE1);
				break;
			case 2:
				FMLClientHandler.instance().getClient().getTextureManager().bindTexture(TEXTURE2);
				break;
			case 3:
				FMLClientHandler.instance().getClient().getTextureManager().bindTexture(TEXTURE3);
				break;
		}
		render(null, 0, 0, 0, 0, 0, 0.0625F);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
