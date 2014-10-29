package buildcraftAdditions.multiBlocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import buildcraftAdditions.core.Logger;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileMultiBlockSlave extends TileEntity {
	private int masterX, masterY, masterZ;

	public TileMultiBlockSlave(int masterX, int masterY, int masterZ, int x, int y, int z) {
		this.masterX = masterX;
		this.masterY = masterY;
		this.masterZ = masterZ;
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		Logger.info("Slave created at " + x + ", " + y + ", " + z + " refering to master at " + masterX + ", " + masterY + ", " + masterZ);
	}

	public void openGui() {
		TileEntity master = (TileEntity) worldObj.getTileEntity(masterX, masterY, masterZ);
		if (master != null && master instanceof IMaster)
			((IMaster) master).openGui();
	}



	public void destroyMultiblock() {
		IMaster master = (IMaster) worldObj.getTileEntity(masterX, masterY, masterZ);
		if (master != null)
			master.destroyMultiblock();
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("masterX", masterX);
		tag.setInteger("masterY", masterY);
		tag.setInteger("masterZ", masterZ);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		masterX = tag.getInteger("masterX");
		masterY = tag.getInteger("masterY");
		masterZ = tag.getInteger("masterZ");
	}
}
