package buildcraftAdditions.ModIntegration.waila;

import java.awt.Dimension;

import buildcraftAdditions.reference.enums.EnumMachineUpgrades;
import buildcraftAdditions.utils.RenderUtils;

import mcp.mobius.waila.api.IWailaCommonAccessor;
import mcp.mobius.waila.api.IWailaTooltipRenderer;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TooltipUpgradeRenderer implements IWailaTooltipRenderer {

	@Override
	public Dimension getSize(String[] params, IWailaCommonAccessor accessor) {
		return new Dimension(15, 15);
	}

	@Override
	public void draw(String[] params, IWailaCommonAccessor accessor) {
		EnumMachineUpgrades upgrade = EnumMachineUpgrades.values()[Integer.valueOf(params[0])];
		RenderUtils.drawImage(upgrade.getTexture(), 0, 0, 15, 15);
	}
}
