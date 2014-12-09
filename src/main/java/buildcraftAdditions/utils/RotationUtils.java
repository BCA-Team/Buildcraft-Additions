package buildcraftAdditions.utils;

import net.minecraftforge.common.util.ForgeDirection;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class RotationUtils {

	public ForgeDirection[] rotateDirections(ForgeDirection[] directions, int rotationIndex) {
		int length = directions.length;
		ForgeDirection[] rotatedDirections = new ForgeDirection[length];
		for (int t = 0; t < length; t++) {
			rotatedDirections[t] = rotatateDirection(directions[t], rotationIndex);
		}
		return rotatedDirections;
	}

	public ForgeDirection rotatateDirection(ForgeDirection direction, int rotationIndex) {
		switch (rotationIndex) {
			case 0:
				return direction;

			case 1:
				if (direction == ForgeDirection.NORTH)
					return ForgeDirection.EAST;
				else if (direction == ForgeDirection.EAST)
					return ForgeDirection.SOUTH;
				else if (direction == ForgeDirection.SOUTH)
					return ForgeDirection.WEST;
				else if (direction == ForgeDirection.WEST)
					return ForgeDirection.NORTH;
				//never rotate up and down
				return direction;
			case 2:
				if (direction == ForgeDirection.UP || direction == ForgeDirection.DOWN)
					return direction;
				return direction.getOpposite();
			case 3:
				if (direction == ForgeDirection.NORTH)
					return ForgeDirection.WEST;
				if (direction == ForgeDirection.EAST)
					return ForgeDirection.NORTH;
				if (direction == ForgeDirection.SOUTH)
					return ForgeDirection.EAST;
				if (direction == ForgeDirection.WEST)
					return ForgeDirection.EAST;
				return direction;
		}
		return direction;
	}
}
