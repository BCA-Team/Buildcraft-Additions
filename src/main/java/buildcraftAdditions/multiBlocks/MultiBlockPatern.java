package buildcraftAdditions.multiBlocks;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.blocks.multiBlocks.MultiBlockBase;
import buildcraftAdditions.utils.Location;
import buildcraftAdditions.utils.RotationUtils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockPatern {
	protected final ForgeDirection directions[];
	protected final char[] identifiers;
	protected ForgeDirection rotatedDirections[];
	protected HashMap<String, String> replacements = new HashMap<String, String>(5);
	protected boolean rotatable;


	public MultiBlockPatern(ForgeDirection directions[], char identifier) {
		int length = directions.length;
		this.directions = directions;
		identifiers = new char[length];
		for (int t = 0; t < length; t++)
			identifiers[t] = identifier;
		rotatable = true;
	}

	public MultiBlockPatern(ForgeDirection directions[], char identifiers[], HashMap<String, String> replacements) {
		this.directions = directions;
		this.identifiers = identifiers;
		this.replacements = replacements;
		rotatable = true;
	}

	public MultiBlockPatern(ForgeDirection directions[], char identifier, HashMap<String, String> replacements) {
		int length = directions.length;
		this.directions = directions;
		identifiers = new char[length];
		for (int t = 0; t < length; t++)
			identifiers[t] = identifier;
		this.replacements = replacements;
		rotatable = true;
	}

	public void checkPatern(World world, int x, int y, int z) {
		int rotation = 0;
		boolean valid = false;
		for (int t = 0; t < 4; t++) {
			if (t > 0 && !rotatable)
				break;
			if (isPaternValid(world, x, y, z, t)) {
				rotation = t;
				valid = true;
				break;
			}
		}
		if (valid) {
			rotatedDirections = RotationUtils.rotateDirections(rotation, directions);
			Location location = new Location(world, x, y, z);
			for (ForgeDirection direction : rotatedDirections) {
				location.move(direction);
				if (location.getBlock().getMaterial() != Material.air) {
					location.setMetadata(1);
					IMultiBlockTile slave = (IMultiBlockTile) location.getTileEntity();
					slave.formMultiblock(x, y, z, rotation);
				}
			}
			letNeighboursKnow(world, x, y, z, rotation);
			addMaster(world, x, y, z, rotation);
		}
	}

	public boolean isPaternValid(World world, int startX, int startY, int startZ, int rotationIndex) {
		Location location = new Location(world, startX, startY, startZ);
		int length = directions.length;
		rotatedDirections = RotationUtils.rotateDirections(rotationIndex, directions);
		for (int t = 0; t < length; t++) {
			ForgeDirection direction = rotatedDirections[t];
			location.move(direction);
			if (identifiers[t] == '\n') {
				if (location.getBlock().getMaterial() != Material.air)
					return false;
			} else {
				if (!(location.getBlock() instanceof MultiBlockBase))
					return false;
				MultiBlockBase block = (MultiBlockBase) location.getBlock();
				if (!(block.identifier == identifiers[t]) || location.getMeatadata() != 0) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isPaternValid(World world, int startX, int startY, int startZ) {
		Location location = new Location(world, startX, startY, startZ);
		int length = directions.length;
		for (int t = 0; t < length; t++) {
			ForgeDirection direction = directions[t];
			location.move(direction);
			if (identifiers[t] == '\n') {
				if (location.getBlock().getMaterial() != Material.air)
					return false;
			} else {
				if (!(location.getBlock() instanceof MultiBlockBase))
					return false;
				MultiBlockBase block = (MultiBlockBase) location.getBlock();
				if (!(block.identifier == identifiers[t]) || location.getMeatadata() != 0) {
					return false;
				}
			}
		}
		return true;
	}

	public void destroyMultiblock(World world, int x, int y, int z, int rotationIndex) {
		Location location = new Location(world, x, y, z);
		rotatedDirections = RotationUtils.rotateDirections(rotationIndex, directions);
		for (ForgeDirection direction : rotatedDirections) {
			location.move(direction);
			if (location.getTileEntity() instanceof IMultiBlockTile)
				((IMultiBlockTile) location.getTileEntity()).invalidateBlock();
		}
	}

	public ArrayList<Location> getLocations(World world, int masterX, int masterY, int masterZ, int rotationIndex) {
		ArrayList<Location> list = new ArrayList<Location>(directions.length);
		Location location = new Location(world, masterX, masterY, masterZ);
		rotatedDirections = RotationUtils.rotateDirections(rotationIndex, directions);
		for (ForgeDirection direction : rotatedDirections) {
			location.move(direction);
			list.add(location.copy());
		}
		return list;


	}

	protected void addMaster(World world, int x, int y, int z, int rotationIndex) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null && entity instanceof IMultiBlockTile) {
			IMultiBlockTile master = (IMultiBlockTile) entity;
			master.makeMaster(rotationIndex);
			master.sync();
		}
	}

	public void letNeighboursKnow(World world, int x, int y, int z, int rotationIndex) {
		rotatedDirections = RotationUtils.rotateDirections(rotationIndex, directions);
		Location location = new Location(world, x, y, z);
		for (ForgeDirection direction : rotatedDirections) {
			location.move(direction);
			location.neighbourUpdate();
		}
	}

	public boolean isRotatable() {
		return rotatable;
	}

	public MultiBlockPatern setRotatable(boolean rotatable) {
		this.rotatable = rotatable;
		return this;
	}
}
