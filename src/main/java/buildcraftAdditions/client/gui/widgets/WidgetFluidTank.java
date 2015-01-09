package buildcraftAdditions.client.gui.widgets;

import buildcraftAdditions.client.gui.gui.GuiBase;
import buildcraftAdditions.utils.Tank;
import buildcraftAdditions.utils.Utils;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class WidgetFluidTank extends WidgetBase {
	private Tank tank;

	public WidgetFluidTank(int id, int x, int y, int width, int height, GuiBase gui, Tank tank) {
		super(id, x, y, 0, 0, width, height, gui);
		setEnableClockSound(false);
		this.tank = tank;
	}

	@Override
	public void render(int mouseX, int mouseY) {
		super.render(mouseX, mouseY);
		if (tank.getFluid() != null)
			drawFluid(tank.getFluid(), (int) (((float) this.tank.getFluid().amount / (float) (tank.getMaxCapacity())) * height), x, y, width, height);
	}

	@Override
	public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {
		String fluid = Utils.localize("gui.empty");

		if (tank.getFluid() != null && tank.getFluidAmount() > 0)
			fluid = tank.getFluid().getLocalizedName();

		tooltips.add(fluid);

		if (!fluid.equals(Utils.localize("gui.empty")))
			tooltips.add(tank.getFluidAmount() + "mB");
	}

	private void drawFluid(FluidStack fluid, float level, int x, int y, int width, int height) {
		if (fluid == null || fluid.getFluid() == null) {
			return;
		}
		IIcon icon = fluid.getFluid().getIcon(fluid);
		gui.mc.renderEngine.bindTexture(MC_BLOCK_SHEET);
		Utils.setGLColorFromInt(fluid.getFluid().getColor(fluid));
		int fullX = width / 16;
		int fullY = height / 16;
		int lastX = width - fullX * 16;
		int lastY = height - fullY * 16;
		float fullLvl = (height - level) / 16;
		float lastLvl = (height - level) - fullLvl * 16;
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

	private void drawCutIcon(IIcon icon, int x, int y, int width, int height, float cut) {
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + height, 0, icon.getMinU(), icon.getInterpolatedV(height));
		tess.addVertexWithUV(x + width, y + height, 0, icon.getInterpolatedU(width), icon.getInterpolatedV(height));
		tess.addVertexWithUV(x + width, y + cut, 0, icon.getInterpolatedU(width), icon.getInterpolatedV(cut));
		tess.addVertexWithUV(x, y + cut, 0, icon.getMinU(), icon.getInterpolatedV(cut));
		tess.draw();
	}
}
