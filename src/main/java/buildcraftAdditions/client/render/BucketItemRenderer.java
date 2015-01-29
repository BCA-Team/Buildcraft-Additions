package buildcraftAdditions.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.Fluid;

import buildcraftAdditions.items.ItemBucketBCA;
import buildcraftAdditions.utils.RenderUtils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BucketItemRenderer implements IItemRenderer {

	public static final BucketItemRenderer INSTANCE = new BucketItemRenderer();

	private BucketItemRenderer() {
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type.equals(ItemRenderType.ENTITY);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (item == null || item.getItem() == null || !(item.getItem() instanceof ItemBucketBCA))
			return;

		ItemBucketBCA bucket = (ItemBucketBCA) item.getItem();
		Fluid fluid = null;
		boolean gaseous = false;

		if (bucket.getFluid() != null) {
			fluid = bucket.getFluid().getFluid();
			if (fluid != null && (fluid.isGaseous() || fluid.getDensity() < 0))
				gaseous = true;
		}

		IIcon overlay = bucket.getOverlay();
		IIcon bucketIcon = Items.bucket.getIconFromDamage(0);

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		if (type.equals(ItemRenderType.EQUIPPED)) {
			GL11.glRotated(180, 0, 0, 1);
			GL11.glTranslated(-1, -1, 0);
		} else if (type.equals(ItemRenderType.ENTITY)) {
			GL11.glRotated(180, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glTranslated(-0.5, -0.9, 0);
			if (item.isOnItemFrame()) {
				GL11.glTranslated(0.075, 0.475, 0);
				GL11.glScaled(0.85, 0.85, 0.85);
			}
		} else if (type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON)) {
			GL11.glTranslated(1, 1, 0);
			GL11.glRotated(180, 0, 0, 1);
		}

		if (fluid != null) {
			Minecraft.getMinecraft().renderEngine.bindTexture(RenderUtils.MC_ITEM_SHEET);
			renderMask(overlay, fluid.getIcon(), type, gaseous);
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(RenderUtils.MC_ITEM_SHEET);

		if (!type.equals(ItemRenderType.INVENTORY)) {
			if (gaseous)
				ItemRenderer.renderItemIn2D(Tessellator.instance, bucketIcon.getMaxU(), bucketIcon.getMinV(), bucketIcon.getMinU(), bucketIcon.getMaxV(), bucketIcon.getIconWidth(), bucketIcon.getIconHeight(), 0.0625F);
			else
				ItemRenderer.renderItemIn2D(Tessellator.instance, bucketIcon.getMinU(), bucketIcon.getMaxV(), bucketIcon.getMaxU(), bucketIcon.getMinV(), bucketIcon.getIconWidth(), bucketIcon.getIconHeight(), 0.0625F);
		} else
			renderIcon(bucketIcon, 0, gaseous);

		GL11.glColor4f(1, 1, 1, 1);
		GL11.glPopMatrix();
	}

	private void renderMask(IIcon mask, IIcon subIcon, ItemRenderType type, boolean flip) {
		if (mask == null || subIcon == null)
			return;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_CULL_FACE);
		Tessellator tessellator = Tessellator.instance;

		tessellator.startDrawingQuads();
		tessellator.setNormal(0, 0, 1);
		if (type.equals(ItemRenderType.INVENTORY))
			preRenderInvIcon(mask, 0.001, flip);
		else
			preRenderWorldIcon(mask, 0.001, flip);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0, 0, -1);
		if (type.equals(ItemRenderType.INVENTORY))
			preRenderInvIcon(mask, -0.0635, flip);
		else
			preRenderWorldIcon(mask, -0.0635, flip);
		tessellator.draw();

		Minecraft.getMinecraft().renderEngine.bindTexture(RenderUtils.MC_BLOCK_SHEET);
		GL11.glDepthFunc(GL11.GL_EQUAL);
		GL11.glDepthMask(false);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0, 0, 1);
		if (type.equals(ItemRenderType.INVENTORY))
			preRenderInvIcon(subIcon, 0.001, flip);
		else
			preRenderWorldIcon(subIcon, 0.001, flip);
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0, 0, -1);
		if (type.equals(ItemRenderType.INVENTORY))
			preRenderInvIcon(subIcon, -0.0635, flip);
		else
			preRenderWorldIcon(subIcon, -0.0635, flip);
		tessellator.draw();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glColor4f(1, 1, 1, 1);
	}

	private void preRenderInvIcon(IIcon icon, double z, boolean flip) {
		Tessellator.instance.addVertexWithUV(16, 0 + (flip ? 1 : 0), z, icon.getMinU(), icon.getMaxV());
		Tessellator.instance.addVertexWithUV(0, 0 + (flip ? 1 : 0), z, icon.getMaxU(), icon.getMaxV());
		Tessellator.instance.addVertexWithUV(0, 16 + (flip ? 1 : 0), z, icon.getMaxU(), icon.getMinV());
		Tessellator.instance.addVertexWithUV(16, 16 + (flip ? 1 : 0), z, icon.getMinU(), icon.getMinV());
	}

	private void preRenderWorldIcon(IIcon icon, double z, boolean flip) {
		Tessellator.instance.addVertexWithUV(1, 0, z, icon.getMinU(), icon.getMaxV());
		Tessellator.instance.addVertexWithUV(0, 0, z, icon.getMaxU(), icon.getMaxV());
		Tessellator.instance.addVertexWithUV(0, 1, z, icon.getMaxU(), icon.getMinV());
		Tessellator.instance.addVertexWithUV(1, 1, z, icon.getMinU(), icon.getMinV());
	}

	private void renderIcon(IIcon icon, double z, boolean flip) {
		Tessellator.instance.startDrawingQuads();
		if (flip) {
			Tessellator.instance.addVertexWithUV(16, 1, z, icon.getMinU(), icon.getMaxV());
			Tessellator.instance.addVertexWithUV(0, 1, z, icon.getMaxU(), icon.getMaxV());
			Tessellator.instance.addVertexWithUV(0, 17, z, icon.getMaxU(), icon.getMinV());
			Tessellator.instance.addVertexWithUV(16, 17, z, icon.getMinU(), icon.getMinV());
		} else {
			Tessellator.instance.addVertexWithUV(16, 0, z, icon.getMaxU(), icon.getMinV());
			Tessellator.instance.addVertexWithUV(0, 0, z, icon.getMinU(), icon.getMinV());
			Tessellator.instance.addVertexWithUV(0, 16, z, icon.getMinU(), icon.getMaxV());
			Tessellator.instance.addVertexWithUV(16, 16, z, icon.getMaxU(), icon.getMaxV());
		}
		Tessellator.instance.draw();
	}
}
