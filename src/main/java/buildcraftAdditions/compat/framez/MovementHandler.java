package buildcraftAdditions.compat.framez;

import com.amadornes.framez.api.movement.BlockMovementType;
import com.amadornes.framez.api.movement.IMovementHandler;
import com.amadornes.framez.api.movement.IMovingBlock;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MovementHandler implements IMovementHandler {

	@Override
	public boolean handleStartMoving(IMovingBlock iMovingBlock) {
		return false;
	}

	@Override
	public boolean handleFinishMoving(IMovingBlock iMovingBlock) {
		TileEntity entity = iMovingBlock.getTileEntity();
		if (entity instanceof IMultiBlockTile)
			((IMultiBlockTile) entity).moved(iMovingBlock.getDirection());
		if (entity instanceof TileHeatedFurnace)
			((TileHeatedFurnace) entity).shouldUpdateCoils = true;
		return false;
	}

	@Override
	public BlockMovementType getMovementType(World world, Integer integer, Integer integer2, Integer integer3) {
		return null;
	}
}
