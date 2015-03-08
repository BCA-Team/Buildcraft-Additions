package buildcraftAdditions.api.nbt;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface INBTSaveable {

	void readFromNBT(NBTTagCompound tag);

	void writeToNBT(NBTTagCompound tag);
}
