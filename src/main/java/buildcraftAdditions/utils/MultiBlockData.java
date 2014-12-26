package buildcraftAdditions.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.multiBlocks.MultiBlockPatern;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockData {
	public int masterX, masterY, masterZ, rotationIndex, oldmasterX, oldmasterY, oldmasterZ;
	public boolean isMaster, partOfMultiBlock, moved;
	public MultiBlockPatern patern;

	public MultiBlockData() {
		isMaster = false;
		partOfMultiBlock = false;
	}

	public MultiBlockData invalidate() {
		masterX = 0;
		masterY = 0;
		masterZ = 0;
		isMaster = false;
		partOfMultiBlock = false;
		return this;
	}

	public MultiBlockData formMultiBlock(int masterX, int masterY, int masterZ) {
		this.masterX = masterX;
		this.masterY = masterY;
		this.masterZ = masterZ;
		partOfMultiBlock = true;
		return this;
	}

	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("masterX", masterX);
		tag.setInteger("masterY", masterY);
		tag.setInteger("masterZ", masterZ);
		tag.setInteger("rotationIndex", rotationIndex);
		tag.setBoolean("isMaster", isMaster);
		tag.setBoolean("partOfMultiBlock", partOfMultiBlock);
	}

	public MultiBlockData readFromNBT(NBTTagCompound tag) {
		masterX = tag.getInteger("masterX");
		masterY = tag.getInteger("masterY");
		masterZ = tag.getInteger("masterZ");
		rotationIndex = tag.getInteger("rotationIndex");
		isMaster = tag.getBoolean("isMaster");
		partOfMultiBlock = tag.getBoolean("partOfMultiBlock");
		return this;
	}

	public MultiBlockData setPatern(MultiBlockPatern patern) {
		this.patern = patern;
		return this;
	}

	public MultiBlockData onMove(ForgeDirection direction) {
		if (isMaster) {
			oldmasterX = masterX;
			oldmasterY = masterY;
			oldmasterZ = masterZ;
			masterX = masterX + direction.offsetX;
			masterY = masterY + direction.offsetY;
			masterZ = masterZ + direction.offsetZ;
			moved = true;
		} else {
			oldmasterX = masterX;
			oldmasterY = masterY;
			oldmasterZ = masterZ;
			moved = true;
			masterX += direction.offsetX;
			masterY += direction.offsetY;
			masterZ += direction.offsetZ;
		}
		return this;
	}

	public void afterMoveCheck(World world) {
		if (!patern.isPaternValid(world, masterX, masterY, masterZ, rotationIndex)) {
			patern.destroyMultiblock(world, masterX, masterY, masterZ, rotationIndex);
			patern.destroyMultiblock(world, oldmasterX, oldmasterY, oldmasterZ, rotationIndex);
		}
		moved = false;
	}
}
