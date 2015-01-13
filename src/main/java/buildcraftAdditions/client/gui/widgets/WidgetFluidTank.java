package buildcraftAdditions.client.gui.widgets;

import java.util.List;

import buildcraftAdditions.client.gui.gui.GuiBase;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Tank;
import buildcraftAdditions.utils.Utils;

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
		if (tank.getFluid() != null)
			RenderUtils.drawFluid(tank.getFluid(), tank.getFluidAmount() * height / tank.getMaxCapacity(), x, y, width, height);
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
}
