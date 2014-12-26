package buildcraftAdditions.multiBlocks;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.common.util.ForgeDirection;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface IMultiBlockTile {

	public void makeMaster(int rotationIndex);

	public void sync();

	public void invalidateMultiblock();

	public boolean onBlockActivated(EntityPlayer player);

	public void formMultiblock(int masterX, int masterY, int masterZ, int rotationIndex);

	public void invalidateBlock();

	public void moved(ForgeDirection direction);

	public int getMasterX();

	public int getMasterY();

	public int getMasterZ();

	public int getRotationIndex();

	public boolean isMaster();

	public boolean isPartOfMultiblock();

	public void setMasterX(int masterX);

	public void setMasterY(int masterY);

	public void setMasterZ(int masterZ);

	public void setIsMaster(boolean isMaster);

	public void setPartOfMultiBlock(boolean partOfMultiBlock);

	public void setRotationIndex(int rotationIndex);
}
