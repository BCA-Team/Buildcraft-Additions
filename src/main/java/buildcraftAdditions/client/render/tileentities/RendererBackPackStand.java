package buildcraftAdditions.client.render.tileentities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.armour.ItemKineticBackpack;
import buildcraftAdditions.client.models.BackPackModel;
import buildcraftAdditions.client.models.ModelBackpackStand;
import buildcraftAdditions.client.models.ModelCapsule;
import buildcraftAdditions.tileEntities.TileBackpackStand;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class RendererBackPackStand extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float fl) {
		if (entity == null || !(entity instanceof TileBackpackStand))
			return;
		int orientation = entity.getWorldObj().getBlockMetadata(entity.xCoord, entity.yCoord, entity.zCoord);
		int angle;
		switch (orientation) {
			case 2:
				angle = 0;
				break;
			case 3:
				angle = 180;
				break;
			case 4:
				angle = 90;
				break;
			case 5:
				angle = -90;
				break;
			default:
				angle = 0;
		}
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 2.4, z + 0.5);
		GL11.glRotated(angle, 0, 1, 0);
		GL11.glRotated(180, 1, 0, 0);
		GL11.glScaled(10, 10, 10);
		ModelBackpackStand.INSTANCE.render(null, 0, 0, 0, 0, 0, 0.01f);
		GL11.glPopMatrix();

		TileBackpackStand stand = (TileBackpackStand) entity;
		if (stand.inventory.getStackInSlot(0) == null)
			return;
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 1.4, z + 0.5);
		GL11.glRotated(angle, 0, 1, 0);
		GL11.glRotated(180, 1, 0, 0);
		BackPackModel.INSTANCE.render(null, 0, 0, 0, 0, 0, 0.1F);
		GL11.glPopMatrix();

		ItemStack bStack = stand.inventory.getStackInSlot(0);
		ItemKineticBackpack backpack = (ItemKineticBackpack) bStack.getItem();
		if (backpack.getInstalledCapsule(bStack, 0) != 0) {
			GL11.glPushMatrix();
			switch (orientation) {
				case 2:
					GL11.glTranslated(x + 0.7, y + 3.15, z + 0.07);
					break;
				case 3:
					GL11.glTranslated(x + 0.3, y + 3.15, z + 0.93);
					break;
				case 4:
					GL11.glTranslated(x + 0.07, y + 3.15, z + 0.3);
					break;
				case 5:
					GL11.glTranslated(x + 0.92, y + 3.15, z + 0.71);
					break;
			}
			GL11.glScaled(2, 2, 2);
			GL11.glRotated(angle, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			ModelCapsule.INSTANCE.render(null, 0, 0, 0, 0, 0, 0.05F, backpack.getInstalledCapsule(bStack, 0));
			GL11.glPopMatrix();
		}
		if (backpack.getInstalledCapsule(bStack, 1) != 0) {
			GL11.glPushMatrix();
			switch (orientation) {
				case 2:
					GL11.glTranslated(x + 0.3, y + 3.15, z + 0.07);
					break;
				case 3:
					GL11.glTranslated(x + 0.7, y + 3.15, z + 0.93);
					break;
				case 4:
					GL11.glTranslated(x + 0.08, y + 3.15, z + 0.69);
					break;
				case 5:
					GL11.glTranslated(x + 0.93, y + 3.15, z + 0.3);
					break;
			}
			GL11.glRotated(angle, 0, 1, 0);
			GL11.glScaled(2, 2, 2);
			GL11.glRotated(180, 1, 0, 0);
			ModelCapsule.INSTANCE.render(null, 0, 0, 0, 0, 0, 0.05F, backpack.getInstalledCapsule(bStack, 1));
			GL11.glPopMatrix();
		}
		if (backpack.getInstalledCapsule(bStack, 2) != 0) {
			GL11.glPushMatrix();
			switch (orientation) {
				case 2:
					GL11.glTranslated(x + 0.7, y + 2.85, z + 0.07);
					break;
				case 3:
					GL11.glTranslated(x + 0.3, y + 2.85, z + 0.93);
					break;
				case 4:
					GL11.glTranslated(x + 0.07, y + 2.85, z + 0.3);
					break;
				case 5:
					GL11.glTranslated(x + 0.92, y + 2.85, z + 0.71);
					break;
			}

			GL11.glScaled(2, 2, 2);
			GL11.glRotated(angle, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			ModelCapsule.INSTANCE.render(null, 0, 0, 0, 0, 0, 0.05F, backpack.getInstalledCapsule(bStack, 2));
			GL11.glPopMatrix();
		}
		if (backpack.getInstalledCapsule(bStack, 3) != 0) {
			GL11.glPushMatrix();
			switch (orientation) {
				case 2:
					GL11.glTranslated(x + 0.3, y + 2.85, z + 0.07);
					break;
				case 3:
					GL11.glTranslated(x + 0.7, y + 2.85, z + 0.93);
					break;
				case 4:
					GL11.glTranslated(x + 0.07, y + 2.85, z + 0.71);
					break;
				case 5:
					GL11.glTranslated(x + 0.92, y + 2.85, z + 0.31);
					break;
			}
			GL11.glScaled(2, 2, 2);
			GL11.glRotated(angle, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			ModelCapsule.INSTANCE.render(null, 0, 0, 0, 0, 0, 0.05F, backpack.getInstalledCapsule(bStack, 3));
			GL11.glPopMatrix();
		}
	}
}
