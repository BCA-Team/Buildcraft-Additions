package buildcraftAdditions.ModIntegration.Buildcraft.Triggers;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import buildcraft.api.statements.IStatement;
import buildcraft.api.statements.IStatementParameter;
import buildcraft.api.statements.ITriggerExternal;

import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class BasicTrigger implements ITriggerExternal {
	public IIcon icon;
	private String name, iconName;

	public BasicTrigger(String name) {
		this(name, name);
	}

	public BasicTrigger(String name, String iconName) {
		this.name = name;
		this.iconName = iconName;
	}

	@Override
	public String getUniqueTag() {
		return Variables.MOD.ID + ":" + name;
	}

	@Override
	public IIcon getIcon() {
		return icon;
	}

	@Override
	public void registerIcons(IIconRegister register) {
		icon = RenderUtils.registerIcon(register, iconName);
	}

	@Override
	public int maxParameters() {
		return 0;
	}

	@Override
	public int minParameters() {
		return 0;
	}

	@Override
	public String getDescription() {
		return Utils.localize("trigger." + name);
	}

	@Override
	public IStatementParameter createParameter(int index) {
		return null;
	}

	@Override
	public IStatement rotateLeft() {
		return this;
	}
}
