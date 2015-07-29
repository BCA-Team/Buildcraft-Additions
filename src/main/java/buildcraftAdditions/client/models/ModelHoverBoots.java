package buildcraftAdditions.client.models;

import buildcraftAdditions.utils.RenderUtils;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ModelHoverBoots extends ModelBiped {
	public static final ModelHoverBoots INSTANCE = new ModelHoverBoots();
	private final ResourceLocation TEXTURE = new ResourceLocation("bcadditions", "textures/models/armor/hoverBoots.png");

	public ModelHoverBoots() {
		this.bipedLeftLeg.addChild(new HoverBoots(this));
	}

	@Override
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		GL11.glPushMatrix();
		GL11.glScalef(1.0F / 2.0f, 1.0F / 2.0f, 1.0F / 2.0f);
		GL11.glTranslatef(0.0F, 24.0F * p_78088_7_, 0.0F);
		this.bipedLeftLeg.render(p_78088_7_);
		GL11.glPopMatrix();
	}

	private class HoverBoots extends ModelRenderer {

		public HoverBoots(ModelBase modelBase) {
			super(modelBase);
		}

		@Override
		public void render(float p_78785_1_) {
			GL11.glPushMatrix();
			RenderUtils.bindTexture(TEXTURE);
			//GL11.glTranslated(-.25, 0, 0);
			ModelLoader.HOVER_BOOTS.renderAll();
			GL11.glPopMatrix();
		}
	}
}
