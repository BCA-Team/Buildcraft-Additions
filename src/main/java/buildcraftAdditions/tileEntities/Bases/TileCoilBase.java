package buildcraftAdditions.tileEntities.Bases;

import net.minecraft.nbt.NBTTagCompound;

import buildcraft.api.core.NetworkData;
import buildcraft.core.TileBuildCraft;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public abstract class TileCoilBase extends TileBuildCraft {
    @NetworkData
    public boolean shouldHeat, burning;
	@NetworkData
	public int increment;
	@NetworkData
	public int burnTime, fullBurnTime;

    public void startHeating(){
        shouldHeat = true;
    }

    public void stopHeating(){
        shouldHeat = false;
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		shouldHeat = nbt.getBoolean("shouldHeat");
		burning = nbt.getBoolean("burning");
		increment = nbt.getInteger("increment");
		burnTime = nbt.getInteger("burntime");
		fullBurnTime = nbt.getInteger("fullBurnTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("shouldHeat", shouldHeat);
		nbt.setBoolean("burning", burning);
		nbt.setInteger("increment", increment);
		nbt.setInteger("burntime", burnTime);
		nbt.setInteger("fullBurnTime", fullBurnTime);
	}

	public int getIncrement(){
		return increment;
	}

    public boolean isBurning(){
        return burning;
    }

}
