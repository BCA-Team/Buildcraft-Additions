package buildcraftAdditions.client.render.tileentities;

import org.lwjgl.opengl.GL11;

import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.models.ModelKineticDuster;
import buildcraftAdditions.tileEntities.TileKineticDuster;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class RendererDusterKinetic extends RendererDuster {

	private final ModelKineticDuster model = new ModelKineticDuster();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float fl) {
		if (tile != null && tile instanceof TileKineticDuster) {
			TileKineticDuster duster = (TileKineticDuster) tile;
			GL11.glPushMatrix();
			GL11.glTranslated(x + .5, y + 1.5, z + .5);
			GL11.glRotated(180, 1, 0, 0);
			model.render(duster.progressStage);
			GL11.glPopMatrix();
		}
		super.renderTileEntityAt(tile, x, y, z, fl);
	}

	@Override
	protected double getYOffset() {
		//TODO: Change to a proper value once the model's depth test works correctly
		return super.getYOffset();
	}
}
