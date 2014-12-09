package buildcraftAdditions.multiBlocks;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.blocks.multiBlocks.MultiBlockBase;
import buildcraftAdditions.utils.Location;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class MultiBlockPatern {
	public ForgeDirection directions[];
	public char identifiers[];
	public HashMap<String, String> replacements = new HashMap<String, String>(5);

	public MultiBlockPatern(ForgeDirection directions[], char identifier) {
		int length = directions.length;
		this.directions = directions;
		this.identifiers = new char[length];
		for (int t = 0; t < length; t++)
			this.identifiers[t] = identifier;
	}

	public MultiBlockPatern(ForgeDirection directions[], char identifiers[], HashMap<String, String> replacements) {
		this.directions = directions;
		this.identifiers = identifiers;
		this.replacements = replacements;
	}

	public MultiBlockPatern(ForgeDirection directions[], char identifier, HashMap<String, String> replacements) {
		int length = directions.length;
		this.directions = directions;
		this.identifiers = new char[length];
		for (int t = 0; t < length; t++)
			this.identifiers[t] = identifier;
		this.replacements = replacements;
	}

	public void checkPatern(World world, int x, int y, int z) {
		if (isPaternValid(world, x, y, z)) {
			Location location = new Location(world, x, y, z);
			for (ForgeDirection direction : directions) {
				location.move(direction);
				if (location.getBlock().getMaterial() != Material.air) {
					location.setMetadata(1);
					IMultiBlockTile slave = (IMultiBlockTile) location.getTileEntity();
					slave.formMultiblock(x, y, z);
				}
			}
			addMaster(world, x, y, z);
		}
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

	public void destroyMultiblock(World world, int x, int y, int z) {
		Location location = new Location(world, x, y, z);
		for (ForgeDirection direction: directions) {
			location.move(direction);
			if (location.getTileEntity() instanceof IMultiBlockTile)
				((IMultiBlockTile) location.getTileEntity()).invalidateBlock();
		}
	}

	public ArrayList<Location> getLocations(World world, int masterX, int masterY, int masterZ) {
		ArrayList<Location> list = new ArrayList<Location>(directions.length);
		Location location = new Location(world, masterX, masterY, masterZ);
		for (ForgeDirection direction: directions) {
			location.move(direction);
			list.add(location.copy());
		}
		return list;


	}

	public void addMaster (World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null && entity instanceof IMultiBlockTile) {
			IMultiBlockTile master = (IMultiBlockTile) entity;
			master.makeMaster();
			master.sync();
		}
	}
}
