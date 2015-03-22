package buildcraftAdditions.client.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.armour.ItemKineticBackpack;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 * <p/>
 * ModelBiped - Plenituz
 * Created using Tabula 4.1.1
 */
@SideOnly(Side.CLIENT)
public class BackPackModel extends ModelBiped {

	public static final BackPackModel INSTANCE = new BackPackModel();
	public static final BackPackModel INSTANCE2 = new BackPackModel();
	private final ResourceLocation TEXTURE = new ResourceLocation("bcadditions", "textures/models/armor/kineticBackpack_layer_1.png");

	public ModelRenderer shape21;
	public ModelRenderer shape22;
	public ModelRenderer shape22_1;
	public ModelRenderer shape25;
	public ModelRenderer shape38;
	public ModelRenderer shape38_1;
	public ModelRenderer shape38_2;
	public ModelRenderer shape38_3;
	public ModelRenderer shape38_4;
	public ModelRenderer shape38_5;
	public ModelRenderer shape38_6;
	public ModelRenderer shape21_1;
	public ModelRenderer shape21_2;
	public ModelRenderer shape21_3;
	public ModelRenderer shape21_4;
	public ModelRenderer shape49;
	public ModelRenderer shape49_1;
	public ModelRenderer shape49_2;
	public ModelRenderer handleTopLeft;
	public ModelRenderer handleTopRight;
	public ModelRenderer shape11;
	public ModelRenderer shape11_1;
	public ModelRenderer shape11_2;
	public ModelRenderer shape11_3;
	public ModelRenderer shape11_4;
	public ModelRenderer shape11_5;
	public ModelRenderer shape11_6;
	public ModelRenderer shape11_7;
	public ModelRenderer shape11_8;
	public ModelRenderer shape11_9;

	public BackPackModel() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.shape38_2 = new ModelRenderer(this, 0, 33);
		this.shape38_2.setRotationPoint(-3.6F, 6.0F, 4.0F);
		this.shape38_2.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
		this.shape38_1 = new ModelRenderer(this, 0, 19);
		this.shape38_1.setRotationPoint(-3.0F, 1.0F, 4.0F);
		this.shape38_1.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
		this.setRotateAngle(shape38_1, 0.0F, 0.0F, 1.5707963267948966F);
		this.shape38_6 = new ModelRenderer(this, 0, 29);
		this.shape38_6.setRotationPoint(1.0F, 4.0F, 4.0F);
		this.shape38_6.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(shape38_6, 0.0F, 0.0F, 1.5707963267948966F);
		this.shape21_2 = new ModelRenderer(this, 48, 0);
		this.shape21_2.setRotationPoint(-4.0F, 0.0F, 3.0F);
		this.shape21_2.addBox(0.0F, 0.0F, 0.0F, 8, 7, 1, 0.0F);
		this.handleTopRight = new ModelRenderer(this, 71, 0);
		this.handleTopRight.setRotationPoint(-5.0F, -1.0F, -3.0F);
		this.handleTopRight.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
		this.shape38_5 = new ModelRenderer(this, 0, 26);
		this.shape38_5.setRotationPoint(-3.0F, 3.0F, 4.0F);
		this.shape38_5.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.shape11_6 = new ModelRenderer(this, 0, 0);
		this.shape11_6.setRotationPoint(4.0F, 0.0F, -3.0F);
		this.shape11_6.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape49 = new ModelRenderer(this, 54, 12);
		this.shape49.setRotationPoint(0.4F, 8.6F, 3.0F);
		this.shape49.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape22 = new ModelRenderer(this, 22, 8);
		this.shape22.setRotationPoint(-4.0F, 7.0F, 2.0F);
		this.shape22.addBox(0.0F, 0.0F, 0.0F, 8, 2, 1, 0.0F);
		this.shape21 = new ModelRenderer(this, 22, 0);
		this.shape21.setRotationPoint(-5.0F, 0.0F, 2.0F);
		this.shape21.addBox(0.0F, 0.0F, 0.0F, 10, 7, 1, 0.0F);
		this.shape21_1 = new ModelRenderer(this, 67, 0);
		this.shape21_1.setRotationPoint(-4.7F, 0.0F, 3.0F);
		this.shape21_1.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
		this.shape38_4 = new ModelRenderer(this, 0, 22);
		this.shape38_4.setRotationPoint(1.0F, 1.0F, 4.0F);
		this.shape38_4.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(shape38_4, 0.0F, 0.0F, 1.5707963267948966F);
		this.shape49_2 = new ModelRenderer(this, 59, 13);
		this.shape49_2.setRotationPoint(-1.5F, 8.6F, 3.0F);
		this.shape49_2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape11_1 = new ModelRenderer(this, 0, 2);
		this.shape11_1.setRotationPoint(0.0F, 2.0F, -3.0F);
		this.shape11_1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape11_4 = new ModelRenderer(this, 0, 0);
		this.shape11_4.setRotationPoint(-3.0F, 1.0F, -3.0F);
		this.shape11_4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape38 = new ModelRenderer(this, 0, 13);
		this.shape38.setRotationPoint(-4.0F, 0.3F, 4.0F);
		this.shape38.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
		this.shape22_1 = new ModelRenderer(this, 22, 11);
		this.shape22_1.setRotationPoint(-3.0F, 9.0F, 2.0F);
		this.shape22_1.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.handleTopLeft = new ModelRenderer(this, 86, 0);
		this.handleTopLeft.setRotationPoint(4.0F, -1.0F, -3.0F);
		this.handleTopLeft.addBox(0.0F, 0.0F, 0.0F, 1, 1, 6, 0.0F);
		this.shape21_4 = new ModelRenderer(this, 49, 9);
		this.shape21_4.setRotationPoint(-3.6F, 7.0F, 3.0F);
		this.shape21_4.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1, 0.0F);
		this.shape11_2 = new ModelRenderer(this, 0, 0);
		this.shape11_2.setRotationPoint(-5.0F, 0.0F, -3.0F);
		this.shape11_2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape11_9 = new ModelRenderer(this, 0, 0);
		this.shape11_9.setRotationPoint(1.0F, 2.0F, -3.0F);
		this.shape11_9.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape25 = new ModelRenderer(this, 0, 36);
		this.shape25.setRotationPoint(-3.0F, 7.0F, 4.0F);
		this.shape25.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.shape11_3 = new ModelRenderer(this, 0, 0);
		this.shape11_3.setRotationPoint(-4.0F, 1.0F, -3.0F);
		this.shape11_3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape11_8 = new ModelRenderer(this, 0, 0);
		this.shape11_8.setRotationPoint(2.0F, 1.0F, -3.0F);
		this.shape11_8.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape11 = new ModelRenderer(this, 0, 2);
		this.shape11.setRotationPoint(-1.0F, 2.0F, -3.0F);
		this.shape11.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape49_1 = new ModelRenderer(this, 56, 15);
		this.shape49_1.setRotationPoint(-0.6F, 8.8F, 3.0F);
		this.shape49_1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape21_3 = new ModelRenderer(this, 44, 0);
		this.shape21_3.setRotationPoint(4.0F, 0.0F, 3.0F);
		this.shape21_3.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);
		this.shape11_5 = new ModelRenderer(this, 0, 0);
		this.shape11_5.setRotationPoint(-2.0F, 2.0F, -3.0F);
		this.shape11_5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape38_3 = new ModelRenderer(this, 0, 16);
		this.shape38_3.setRotationPoint(4.0F, 1.0F, 4.0F);
		this.shape38_3.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);
		this.setRotateAngle(shape38_3, 0.0F, 0.0F, 1.5707963267948966F);
		this.shape11_7 = new ModelRenderer(this, 0, 0);
		this.shape11_7.setRotationPoint(3.0F, 1.0F, -3.0F);
		this.shape11_7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		FMLClientHandler.instance().getClient().getTextureManager().bindTexture(TEXTURE);
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape38_2.offsetX, this.shape38_2.offsetY, this.shape38_2.offsetZ);
		GL11.glTranslatef(this.shape38_2.rotationPointX * f5, this.shape38_2.rotationPointY * f5, this.shape38_2.rotationPointZ * f5);
		GL11.glScaled(0.9D, 1.0D, 1.0D);
		GL11.glTranslatef(-this.shape38_2.offsetX, -this.shape38_2.offsetY, -this.shape38_2.offsetZ);
		GL11.glTranslatef(-this.shape38_2.rotationPointX * f5, -this.shape38_2.rotationPointY * f5, -this.shape38_2.rotationPointZ * f5);
		this.shape38_2.render(f5);
		GL11.glPopMatrix();
		this.shape38_1.render(f5);
		this.shape38_6.render(f5);
		this.shape21_2.render(f5);
		this.handleTopRight.render(f5);
		this.shape38_5.render(f5);
		this.shape11_6.render(f5);
		this.shape49.render(f5);
		this.shape22.render(f5);
		this.shape21.render(f5);
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape21_1.offsetX, this.shape21_1.offsetY, this.shape21_1.offsetZ);
		GL11.glTranslatef(this.shape21_1.rotationPointX * f5, this.shape21_1.rotationPointY * f5, this.shape21_1.rotationPointZ * f5);
		GL11.glScaled(0.7D, 1.0D, 1.0D);
		GL11.glTranslatef(-this.shape21_1.offsetX, -this.shape21_1.offsetY, -this.shape21_1.offsetZ);
		GL11.glTranslatef(-this.shape21_1.rotationPointX * f5, -this.shape21_1.rotationPointY * f5, -this.shape21_1.rotationPointZ * f5);
		this.shape21_1.render(f5);
		GL11.glPopMatrix();
		this.shape38_4.render(f5);
		this.shape49_2.render(f5);
		this.shape11_1.render(f5);
		this.shape11_4.render(f5);
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape38.offsetX, this.shape38.offsetY, this.shape38.offsetZ);
		GL11.glTranslatef(this.shape38.rotationPointX * f5, this.shape38.rotationPointY * f5, this.shape38.rotationPointZ * f5);
		GL11.glScaled(1.0D, 0.7D, 1.0D);
		GL11.glTranslatef(-this.shape38.offsetX, -this.shape38.offsetY, -this.shape38.offsetZ);
		GL11.glTranslatef(-this.shape38.rotationPointX * f5, -this.shape38.rotationPointY * f5, -this.shape38.rotationPointZ * f5);
		this.shape38.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape22_1.offsetX, this.shape22_1.offsetY, this.shape22_1.offsetZ);
		GL11.glTranslatef(this.shape22_1.rotationPointX * f5, this.shape22_1.rotationPointY * f5, this.shape22_1.rotationPointZ * f5);
		GL11.glScaled(1.0D, 1.3D, 1.0D);
		GL11.glTranslatef(-this.shape22_1.offsetX, -this.shape22_1.offsetY, -this.shape22_1.offsetZ);
		GL11.glTranslatef(-this.shape22_1.rotationPointX * f5, -this.shape22_1.rotationPointY * f5, -this.shape22_1.rotationPointZ * f5);
		this.shape22_1.render(f5);
		GL11.glPopMatrix();
		this.handleTopLeft.render(f5);
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape21_4.offsetX, this.shape21_4.offsetY, this.shape21_4.offsetZ);
		GL11.glTranslatef(this.shape21_4.rotationPointX * f5, this.shape21_4.rotationPointY * f5, this.shape21_4.rotationPointZ * f5);
		GL11.glScaled(0.9D, 1.0D, 1.0D);
		GL11.glTranslatef(-this.shape21_4.offsetX, -this.shape21_4.offsetY, -this.shape21_4.offsetZ);
		GL11.glTranslatef(-this.shape21_4.rotationPointX * f5, -this.shape21_4.rotationPointY * f5, -this.shape21_4.rotationPointZ * f5);
		this.shape21_4.render(f5);
		GL11.glPopMatrix();
		this.shape11_2.render(f5);
		this.shape11_9.render(f5);
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape25.offsetX, this.shape25.offsetY, this.shape25.offsetZ);
		GL11.glTranslatef(this.shape25.rotationPointX * f5, this.shape25.rotationPointY * f5, this.shape25.rotationPointZ * f5);
		GL11.glScaled(1.0D, 0.7D, 1.0D);
		GL11.glTranslatef(-this.shape25.offsetX, -this.shape25.offsetY, -this.shape25.offsetZ);
		GL11.glTranslatef(-this.shape25.rotationPointX * f5, -this.shape25.rotationPointY * f5, -this.shape25.rotationPointZ * f5);
		this.shape25.render(f5);
		GL11.glPopMatrix();
		this.shape11_3.render(f5);
		this.shape11_8.render(f5);
		this.shape11.render(f5);
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape49_1.offsetX, this.shape49_1.offsetY, this.shape49_1.offsetZ);
		GL11.glTranslatef(this.shape49_1.rotationPointX * f5, this.shape49_1.rotationPointY * f5, this.shape49_1.rotationPointZ * f5);
		GL11.glScaled(1.0D, 0.6D, 1.0D);
		GL11.glTranslatef(-this.shape49_1.offsetX, -this.shape49_1.offsetY, -this.shape49_1.offsetZ);
		GL11.glTranslatef(-this.shape49_1.rotationPointX * f5, -this.shape49_1.rotationPointY * f5, -this.shape49_1.rotationPointZ * f5);
		this.shape49_1.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.shape21_3.offsetX, this.shape21_3.offsetY, this.shape21_3.offsetZ);
		GL11.glTranslatef(this.shape21_3.rotationPointX * f5, this.shape21_3.rotationPointY * f5, this.shape21_3.rotationPointZ * f5);
		GL11.glScaled(0.7D, 1.0D, 1.0D);
		GL11.glTranslatef(-this.shape21_3.offsetX, -this.shape21_3.offsetY, -this.shape21_3.offsetZ);
		GL11.glTranslatef(-this.shape21_3.rotationPointX * f5, -this.shape21_3.rotationPointY * f5, -this.shape21_3.rotationPointZ * f5);
		this.shape21_3.render(f5);
		GL11.glPopMatrix();
		this.shape11_5.render(f5);
		this.shape38_3.render(f5);
		this.shape11_7.render(f5);
		if (entity != null && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			ItemStack bStack = player.getCurrentArmor(2);
			ItemKineticBackpack backpack = (ItemKineticBackpack) bStack.getItem();
			if (backpack.getInstalledCapsule(bStack, 0) != 0) {
				GL11.glPushMatrix();
				GL11.glTranslated(0.14, -1.35, 0.25);
				GL11.glScaled(1.5, 1.5, 1.5);
				ModelCapsule.INSTANCE.render(null, 0, 0, 0, 0, 0, 0.05F, backpack.getInstalledCapsule(bStack, 0));
				GL11.glPopMatrix();
			}
			if (backpack.getInstalledCapsule(bStack, 1) != 0) {
				GL11.glPushMatrix();
				GL11.glTranslated(-0.14, -1.35, 0.25);
				GL11.glScaled(1.5, 1.5, 1.5);
				ModelCapsule.INSTANCE.render(null, 0, 0, 0, 0, 0, 0.05F, backpack.getInstalledCapsule(bStack, 1));
				GL11.glPopMatrix();
			}
			if (backpack.getInstalledCapsule(bStack, 2) != 0) {
				GL11.glPushMatrix();
				GL11.glTranslated(0.14, -1.15, 0.25);
				GL11.glScaled(1.5, 1.5, 1.5);
				ModelCapsule.INSTANCE.render(null, 0, 0, 0, 0, 0, 0.05F, backpack.getInstalledCapsule(bStack, 2));
				GL11.glPopMatrix();
			}
			if (backpack.getInstalledCapsule(bStack, 3) != 0) {
				GL11.glPushMatrix();
				GL11.glTranslated(-0.14, -1.15, 0.25);
				GL11.glScaled(1.5, 1.5, 1.5);
				ModelCapsule.INSTANCE.render(null, 0, 0, 0, 0, 0, 0.05F, backpack.getInstalledCapsule(bStack, 3));
				GL11.glPopMatrix();
			}
		}
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
