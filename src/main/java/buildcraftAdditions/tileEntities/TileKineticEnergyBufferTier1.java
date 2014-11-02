package buildcraftAdditions.tileEntities;

import cpw.mods.fml.common.network.NetworkRegistry;

import buildcraftAdditions.networking.MessageKEBT1;
import buildcraftAdditions.networking.PacketHandeler;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.variables.ItemsAndBlocks;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileKineticEnergyBufferTier1 extends TileKineticEnergyBufferBase {
	public int energyState, lastEnergyState;

	public TileKineticEnergyBufferTier1() {
		super(30000, 1000, 1000, 10, 1);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (maxEnergy == 0)
			return;
		energyState = (energy * 9) / maxEnergy;
		if (lastEnergyState != energyState && worldObj.getBlock(xCoord, yCoord, zCoord) == ItemsAndBlocks.kebT1)
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, energyState, 2);
		lastEnergyState = energyState;
	}

	@Override
	public void sync() {
		if (!worldObj.isRemote)
			PacketHandeler.instance.sendToAllAround(new MessageKEBT1(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 10));
	}
}
