package buildcraftAdditions.client.render.items;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;

import net.minecraftforge.client.IItemRenderer;

import buildcraftAdditions.client.models.ModelKineticDuster;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class KineticDusterItemRenderer implements IItemRenderer {

	public static final KineticDusterItemRenderer INSTANCE = new KineticDusterItemRenderer();

	private KineticDusterItemRenderer() {
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type.equals(ItemRenderType.INVENTORY) || type.equals(ItemRenderType.ENTITY);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		GL11.glPushMatrix();
		switch (type) {
			case EQUIPPED:
				GL11.glScaled(.75, .75, .75);
				GL11.glRotated(180, 0, 0, 1);
				GL11.glTranslated(-0.75, -1, 0);
				break;
			case EQUIPPED_FIRST_PERSON:
				GL11.glTranslated(1, 1, 0);
				GL11.glRotated(180, 1, 0, 0);
				break;
			case INVENTORY:
				GL11.glRotated(180, 0, 1, 0);
				GL11.glRotated(180, 1, 0, 0);
				GL11.glTranslated(0, -.9, 0);
				break;
			case ENTITY:
				GL11.glRotated(180, 0, 0, 1);
				GL11.glTranslated(0, -1, 0);
				break;
		}
		ModelKineticDuster.INSTANCE2.render(null, 0, 0, 0, 0, 0, 0.06f, 0);

		GL11.glPopMatrix();
	}
}
