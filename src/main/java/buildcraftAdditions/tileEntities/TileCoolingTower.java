package buildcraftAdditions.tileEntities;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.tileEntities.Bases.TileBase;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class TileCoolingTower extends TileBase implements IMultiBlockTile {

	protected TileCoolingTower() {
	}

	@Override
	public void makeMaster(int rotationIndex) {

	}

	@Override
	public void sync() {

	}

	@Override
	public void invalidateMultiblock() {

	}

	@Override
	public boolean onBlockActivated(EntityPlayer player) {
		return false;
	}

	@Override
	public void formMultiblock(int masterX, int masterY, int masterZ, int rotationIndex) {

	}

	@Override
	public void invalidateBlock() {

	}

	@Override
	public void moved(ForgeDirection direction) {

	}

	@Override
	public int getMasterX() {
		return 0;
	}

	@Override
	public int getMasterY() {
		return 0;
	}

	@Override
	public int getMasterZ() {
		return 0;
	}

	@Override
	public int getRotationIndex() {
		return 0;
	}

	@Override
	public boolean isMaster() {
		return false;
	}

	@Override
	public boolean isPartOfMultiblock() {
		return false;
	}

	@Override
	public void setMasterX(int masterX) {

	}

	@Override
	public void setMasterY(int masterY) {

	}

	@Override
	public void setMasterZ(int masterZ) {

	}

	@Override
	public void setIsMaster(boolean isMaster) {

	}

	@Override
	public void setPartOfMultiBlock(boolean partOfMultiBlock) {

	}

	@Override
	public void setRotationIndex(int rotationIndex) {

	}

	@Override
	public void updateEntity() {

	}
}
