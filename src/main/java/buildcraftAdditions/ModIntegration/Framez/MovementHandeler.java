package buildcraftAdditions.ModIntegration.Framez;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.tileEntities.TileHeatedFurnace;
import buildcraftAdditions.tileEntities.TileKEBT2;
import buildcraftAdditions.tileEntities.TileKEBT3;


import com.amadornes.framez.api.movement.BlockMovementType;
import com.amadornes.framez.api.movement.IMovementHandler;
import com.amadornes.framez.api.movement.IMovingBlock;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MovementHandeler implements IMovementHandler {

	@Override
	public boolean handleStartMoving(IMovingBlock iMovingBlock) {
		return false;
	}

	@Override
	public boolean handleFinishMoving(IMovingBlock iMovingBlock) {
		TileEntity entity = iMovingBlock.getTileEntity();
		if (entity instanceof TileKEBT2) {
			TileKEBT2 keb = (TileKEBT2) entity;
			if (keb.isMaster) {
				keb.oldmasterX = keb.xCoord;
				keb.oldmasterY = keb.yCoord;
				keb.oldmasterZ = keb.zCoord;
				keb.masterX = keb.xCoord + iMovingBlock.getDirection().offsetX;
				keb.masterY = keb.yCoord + iMovingBlock.getDirection().offsetY;
				keb.masterZ = keb.zCoord + iMovingBlock.getDirection().offsetZ;
				keb.moved = true;
			} else {
				keb.oldmasterX = keb.masterX;
				keb.oldmasterY = keb.masterY;
				keb.oldmasterZ = keb.masterZ;
				keb.moved = true;
				keb.master = null;
				keb.masterX += iMovingBlock.getDirection().offsetX;
				keb.masterY += iMovingBlock.getDirection().offsetY;
				keb.masterZ += iMovingBlock.getDirection().offsetZ;
			}
		}
		if (entity instanceof TileKEBT3){
			TileKEBT3 keb = (TileKEBT3) entity;
			if (keb.isMaster) {
				keb.oldmasterX = keb.xCoord;
				keb.oldmasterY = keb.yCoord;
				keb.oldmasterZ = keb.zCoord;
				keb.masterX = keb.xCoord + iMovingBlock.getDirection().offsetX;
				keb.masterY = keb.yCoord + iMovingBlock.getDirection().offsetY;
				keb.masterZ = keb.zCoord + iMovingBlock.getDirection().offsetZ;
				keb.moved = true;
			} else {
				keb.oldmasterX = keb.masterX;
				keb.oldmasterY = keb.masterY;
				keb.oldmasterZ = keb.masterZ;
				keb.moved = true;
				keb.master = null;
				keb.masterX += iMovingBlock.getDirection().offsetX;
				keb.masterY += iMovingBlock.getDirection().offsetY;
				keb.masterZ += iMovingBlock.getDirection().offsetZ;
			}
		}
		if (entity instanceof TileHeatedFurnace)
			((TileHeatedFurnace) entity).shouldUpdateCoils = true;
		return false;
	}

	@Override
	public BlockMovementType getMovementType(World world, Integer integer, Integer integer2, Integer integer3) {
		return null;
	}
}
