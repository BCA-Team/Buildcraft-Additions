package buildcraftAdditions.utils;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface IConfigurableOutput {

	SideConfiguration getSideConfiguration();

	void setSideConfiguration(SideConfiguration configuration);

	EnumSideStatus getStatus(ForgeDirection side);

	void changeStatus(ForgeDirection side);

	EnumPriority getPriority(ForgeDirection side);

	void changePriority(ForgeDirection side);

	int getX();

	int getY();

	int getZ();
}
