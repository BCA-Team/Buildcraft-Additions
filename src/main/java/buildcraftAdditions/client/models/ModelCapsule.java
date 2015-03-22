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
public class ModelCapsule extends ModelBiped {

	public static final ModelCapsule INSTANCE = new ModelCapsule();
	private final ResourceLocation TEXTURE1 = new ResourceLocation("bcadditions", "textures/models/Capsule1-texture.png");
	private final ResourceLocation TEXTURE2 = new ResourceLocation("bcadditions", "textures/models/Capsule2-texture.png");
	private final ResourceLocation TEXTURE3 = new ResourceLocation("bcadditions", "textures/models/Capsule3-texture.png");
	public ModelRenderer capsuleCoreVert;
	public ModelRenderer capsuleThingy1;
	public ModelRenderer capsuleThingy2;
	public ModelRenderer capsuleThingy3;
	public ModelRenderer capsuleThingy4;
	public ModelRenderer capsuleCoreHori;
	public ModelRenderer capsuleCoreBR;
	public ModelRenderer capsuleCoreBL;
	public ModelRenderer capsuleCoreTL;
	public ModelRenderer capsuleCoreTR;

	private ModelCapsule() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.capsuleCoreVert = new ModelRenderer(this, 0, 0);
		this.capsuleCoreVert.setRotationPoint(-1.1F, 18.7F, 0.4F);
		this.capsuleCoreVert.addBox(0.7F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		this.capsuleCoreTR = new ModelRenderer(this, 9, 11);
		this.capsuleCoreTR.setRotationPoint(-1.1F, 18.7F, 0.4F);
		this.capsuleCoreTR.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.capsuleThingy4 = new ModelRenderer(this, 9, 27);
		this.capsuleThingy4.setRotationPoint(-0.6F, 19.8F, 1.4F);
		this.capsuleThingy4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.capsuleCoreBL = new ModelRenderer(this, 1, 16);
		this.capsuleCoreBL.setRotationPoint(0.45F, 20.2F, 0.4F);
		this.capsuleCoreBL.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.capsuleCoreBR = new ModelRenderer(this, 9, 16);
		this.capsuleCoreBR.setRotationPoint(-1.1F, 20.2F, 0.4F);
		this.capsuleCoreBR.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.capsuleThingy1 = new ModelRenderer(this, 2, 21);
		this.capsuleThingy1.setRotationPoint(0.0F, 19.2F, 1.4F);
		this.capsuleThingy1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.capsuleCoreTL = new ModelRenderer(this, 1, 11);
		this.capsuleCoreTL.setRotationPoint(0.45F, 18.7F, 0.4F);
		this.capsuleCoreTL.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.capsuleThingy2 = new ModelRenderer(this, 9, 21);
		this.capsuleThingy2.setRotationPoint(-0.8F, 19.0F, 1.4F);
		this.capsuleThingy2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.capsuleThingy3 = new ModelRenderer(this, 1, 27);
		this.capsuleThingy3.setRotationPoint(0.2F, 20.0F, 1.4F);
		this.capsuleThingy3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.capsuleCoreHori = new ModelRenderer(this, 0, 5);
		this.capsuleCoreHori.setRotationPoint(-1.1F, 18.7F, 0.4F);
		this.capsuleCoreHori.addBox(0.0F, 0.7F, 0.0F, 2, 2, 1, 0.0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int texture) {
		switch (texture) {
			case 1:
				FMLClientHandler.instance().getClient().getTextureManager().bindTexture(TEXTURE1);
				break;
			case 2:
				FMLClientHandler.instance().getClient().getTextureManager().bindTexture(TEXTURE3);
				break;
			case 3:
				FMLClientHandler.instance().getClient().getTextureManager().bindTexture(TEXTURE2);
				break;
		}
		render(entity, f, f1, f2, f3, f4, f5);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GL11.glPushMatrix();
		GL11.glTranslatef(this.capsuleCoreVert.offsetX, this.capsuleCoreVert.offsetY, this.capsuleCoreVert.offsetZ);
		GL11.glTranslatef(this.capsuleCoreVert.rotationPointX * f5, this.capsuleCoreVert.rotationPointY * f5, this.capsuleCoreVert.rotationPointZ * f5);
		GL11.glScaled(0.6D, 1.0D, 1.0D);
		GL11.glTranslatef(-this.capsuleCoreVert.offsetX, -this.capsuleCoreVert.offsetY, -this.capsuleCoreVert.offsetZ);
		GL11.glTranslatef(-this.capsuleCoreVert.rotationPointX * f5, -this.capsuleCoreVert.rotationPointY * f5, -this.capsuleCoreVert.rotationPointZ * f5);
		this.capsuleCoreVert.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.capsuleCoreTR.offsetX, this.capsuleCoreTR.offsetY, this.capsuleCoreTR.offsetZ);
		GL11.glTranslatef(this.capsuleCoreTR.rotationPointX * f5, this.capsuleCoreTR.rotationPointY * f5, this.capsuleCoreTR.rotationPointZ * f5);
		GL11.glScaled(0.45D, 0.5D, 0.5D);
		GL11.glTranslatef(-this.capsuleCoreTR.offsetX, -this.capsuleCoreTR.offsetY, -this.capsuleCoreTR.offsetZ);
		GL11.glTranslatef(-this.capsuleCoreTR.rotationPointX * f5, -this.capsuleCoreTR.rotationPointY * f5, -this.capsuleCoreTR.rotationPointZ * f5);
		this.capsuleCoreTR.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.capsuleThingy4.offsetX, this.capsuleThingy4.offsetY, this.capsuleThingy4.offsetZ);
		GL11.glTranslatef(this.capsuleThingy4.rotationPointX * f5, this.capsuleThingy4.rotationPointY * f5, this.capsuleThingy4.rotationPointZ * f5);
		GL11.glScaled(0.4D, 0.4D, 0.4D);
		GL11.glTranslatef(-this.capsuleThingy4.offsetX, -this.capsuleThingy4.offsetY, -this.capsuleThingy4.offsetZ);
		GL11.glTranslatef(-this.capsuleThingy4.rotationPointX * f5, -this.capsuleThingy4.rotationPointY * f5, -this.capsuleThingy4.rotationPointZ * f5);
		this.capsuleThingy4.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.capsuleCoreBL.offsetX, this.capsuleCoreBL.offsetY, this.capsuleCoreBL.offsetZ);
		GL11.glTranslatef(this.capsuleCoreBL.rotationPointX * f5, this.capsuleCoreBL.rotationPointY * f5, this.capsuleCoreBL.rotationPointZ * f5);
		GL11.glScaled(0.45D, 0.5D, 0.5D);
		GL11.glTranslatef(-this.capsuleCoreBL.offsetX, -this.capsuleCoreBL.offsetY, -this.capsuleCoreBL.offsetZ);
		GL11.glTranslatef(-this.capsuleCoreBL.rotationPointX * f5, -this.capsuleCoreBL.rotationPointY * f5, -this.capsuleCoreBL.rotationPointZ * f5);
		this.capsuleCoreBL.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.capsuleCoreBR.offsetX, this.capsuleCoreBR.offsetY, this.capsuleCoreBR.offsetZ);
		GL11.glTranslatef(this.capsuleCoreBR.rotationPointX * f5, this.capsuleCoreBR.rotationPointY * f5, this.capsuleCoreBR.rotationPointZ * f5);
		GL11.glScaled(0.45D, 0.5D, 0.5D);
		GL11.glTranslatef(-this.capsuleCoreBR.offsetX, -this.capsuleCoreBR.offsetY, -this.capsuleCoreBR.offsetZ);
		GL11.glTranslatef(-this.capsuleCoreBR.rotationPointX * f5, -this.capsuleCoreBR.rotationPointY * f5, -this.capsuleCoreBR.rotationPointZ * f5);
		this.capsuleCoreBR.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.capsuleThingy1.offsetX, this.capsuleThingy1.offsetY, this.capsuleThingy1.offsetZ);
		GL11.glTranslatef(this.capsuleThingy1.rotationPointX * f5, this.capsuleThingy1.rotationPointY * f5, this.capsuleThingy1.rotationPointZ * f5);
		GL11.glScaled(0.4D, 0.4D, 0.4D);
		GL11.glTranslatef(-this.capsuleThingy1.offsetX, -this.capsuleThingy1.offsetY, -this.capsuleThingy1.offsetZ);
		GL11.glTranslatef(-this.capsuleThingy1.rotationPointX * f5, -this.capsuleThingy1.rotationPointY * f5, -this.capsuleThingy1.rotationPointZ * f5);
		this.capsuleThingy1.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.capsuleCoreTL.offsetX, this.capsuleCoreTL.offsetY, this.capsuleCoreTL.offsetZ);
		GL11.glTranslatef(this.capsuleCoreTL.rotationPointX * f5, this.capsuleCoreTL.rotationPointY * f5, this.capsuleCoreTL.rotationPointZ * f5);
		GL11.glScaled(0.45D, 0.5D, 0.5D);
		GL11.glTranslatef(-this.capsuleCoreTL.offsetX, -this.capsuleCoreTL.offsetY, -this.capsuleCoreTL.offsetZ);
		GL11.glTranslatef(-this.capsuleCoreTL.rotationPointX * f5, -this.capsuleCoreTL.rotationPointY * f5, -this.capsuleCoreTL.rotationPointZ * f5);
		this.capsuleCoreTL.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.capsuleThingy2.offsetX, this.capsuleThingy2.offsetY, this.capsuleThingy2.offsetZ);
		GL11.glTranslatef(this.capsuleThingy2.rotationPointX * f5, this.capsuleThingy2.rotationPointY * f5, this.capsuleThingy2.rotationPointZ * f5);
		GL11.glScaled(0.4D, 0.4D, 0.4D);
		GL11.glTranslatef(-this.capsuleThingy2.offsetX, -this.capsuleThingy2.offsetY, -this.capsuleThingy2.offsetZ);
		GL11.glTranslatef(-this.capsuleThingy2.rotationPointX * f5, -this.capsuleThingy2.rotationPointY * f5, -this.capsuleThingy2.rotationPointZ * f5);
		this.capsuleThingy2.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.capsuleThingy3.offsetX, this.capsuleThingy3.offsetY, this.capsuleThingy3.offsetZ);
		GL11.glTranslatef(this.capsuleThingy3.rotationPointX * f5, this.capsuleThingy3.rotationPointY * f5, this.capsuleThingy3.rotationPointZ * f5);
		GL11.glScaled(0.4D, 0.4D, 0.4D);
		GL11.glTranslatef(-this.capsuleThingy3.offsetX, -this.capsuleThingy3.offsetY, -this.capsuleThingy3.offsetZ);
		GL11.glTranslatef(-this.capsuleThingy3.rotationPointX * f5, -this.capsuleThingy3.rotationPointY * f5, -this.capsuleThingy3.rotationPointZ * f5);
		this.capsuleThingy3.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.capsuleCoreHori.offsetX, this.capsuleCoreHori.offsetY, this.capsuleCoreHori.offsetZ);
		GL11.glTranslatef(this.capsuleCoreHori.rotationPointX * f5, this.capsuleCoreHori.rotationPointY * f5, this.capsuleCoreHori.rotationPointZ * f5);
		GL11.glScaled(1.0D, 0.6D, 0.99D);
		GL11.glTranslatef(-this.capsuleCoreHori.offsetX, -this.capsuleCoreHori.offsetY, -this.capsuleCoreHori.offsetZ);
		GL11.glTranslatef(-this.capsuleCoreHori.rotationPointX * f5, -this.capsuleCoreHori.rotationPointY * f5, -this.capsuleCoreHori.rotationPointZ * f5);
		this.capsuleCoreHori.render(f5);
		GL11.glPopMatrix();
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
