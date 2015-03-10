package buildcraftAdditions.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.reference.Variables;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RenderUtils {
	public static final ResourceLocation MC_BLOCK_SHEET = TextureMap.locationBlocksTexture;
	public static final ResourceLocation MC_ITEM_SHEET = TextureMap.locationItemsTexture;

	public static TextureManager textureManager() {
		return Minecraft.getMinecraft().getTextureManager();
	}

	public static void bindTexture(ResourceLocation texture) {
		textureManager().bindTexture(texture);
	}

	public static void drawFluid(FluidStack fluid, int level, int x, int y, int width, int height) {
		if (fluid == null || fluid.getFluid() == null) {
			return;
		}
		IIcon icon = fluid.getFluid().getIcon(fluid);
		bindTexture(MC_BLOCK_SHEET);
		Utils.setGLColorFromInt(fluid.getFluid().getColor(fluid));
		int fullX = width / 16;
		int fullY = height / 16;
		int lastX = width - fullX * 16;
		int lastY = height - fullY * 16;
		int fullLvl = (height - level) / 16;
		int lastLvl = (height - level) - fullLvl * 16;
		for (int i = 0; i < fullX; i++) {
			for (int j = 0; j < fullY; j++) {
				if (j >= fullLvl) {
					drawCutIcon(icon, x + i * 16, y + j * 16, 16, 16, j == fullLvl ? lastLvl : 0);
				}
			}
		}
		for (int i = 0; i < fullX; i++) {
			drawCutIcon(icon, x + i * 16, y + fullY * 16, 16, lastY, fullLvl == fullY ? lastLvl : 0);
		}
		for (int i = 0; i < fullY; i++) {
			if (i >= fullLvl) {
				drawCutIcon(icon, x + fullX * 16, y + i * 16, lastX, 16, i == fullLvl ? lastLvl : 0);
			}
		}
		drawCutIcon(icon, x + fullX * 16, y + fullY * 16, lastX, lastY, fullLvl == fullY ? lastLvl : 0);
	}

	public static void drawCutIcon(IIcon icon, int x, int y, int width, int height, float cut) {
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + height, 0, icon.getMinU(), icon.getInterpolatedV(height));
		tess.addVertexWithUV(x + width, y + height, 0, icon.getInterpolatedU(width), icon.getInterpolatedV(height));
		tess.addVertexWithUV(x + width, y + cut, 0, icon.getInterpolatedU(width), icon.getInterpolatedV(cut));
		tess.addVertexWithUV(x, y + cut, 0, icon.getMinU(), icon.getInterpolatedV(cut));
		tess.draw();
	}

	public static IIcon registerIcon(IIconRegister register, String name) {
		return register.registerIcon(Variables.MOD.ID + ":" + name);
	}

	public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
		bindTexture(image);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + height, 0, 0, 1);
		tess.addVertexWithUV(x + width, y + height, 0, 1, 1);
		tess.addVertexWithUV(x + width, y, 0, 1, 0);
		tess.addVertexWithUV(x, y, 0, 0, 0);
		tess.draw();
	}
}
