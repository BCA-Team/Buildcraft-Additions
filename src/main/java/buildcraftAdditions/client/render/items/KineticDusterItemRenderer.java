package buildcraftAdditions.client.render.items;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.client.IItemRenderer;

import buildcraftAdditions.client.models.ModelKineticDuster;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class KineticDusterItemRenderer implements IItemRenderer {

	public static final KineticDusterItemRenderer INSTANCE = new KineticDusterItemRenderer();
	private final ModelKineticDuster model = new ModelKineticDuster();

	private KineticDusterItemRenderer() {
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		GL11.glPushMatrix();
		GL11.glRotated(180, 0, 0, 1);
		switch (type) {
			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON:
				GL11.glTranslated(-0.5, -1.5, 0.5);
				break;
			case INVENTORY:
			case ENTITY:
				GL11.glTranslated(0, -1, 0);
				break;
			default:
				break;
		}
		model.render(0);

		GL11.glPopMatrix();
	}
}
