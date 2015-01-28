package buildcraftAdditions.villager;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

import buildcraftAdditions.reference.ItemsAndBlocks;

public class ComponentPowerPlant extends StructureVillagePieces.House1 {

	private int averageGroundLevel = -1;

	public ComponentPowerPlant() {
	}

	public ComponentPowerPlant(Start villagePiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super();
		this.coordBaseMode = par5;
		this.boundingBox = par4StructureBoundingBox;

	}

	public static ComponentPowerPlant buildComponent(Start villagePiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, -1, 0, 12, 4, 12, p4);
		return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new ComponentPowerPlant(villagePiece, p5, random, structureboundingbox, p4) : null;
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox sbb) {
		if (this.averageGroundLevel < 0) {
			this.averageGroundLevel = this.getAverageGroundLevel(world, sbb);

			if (this.averageGroundLevel < 0) {
				return true;
			}

			this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 5, 0);
		}

		fillWithAir(world, sbb, 0, -3, 0, 10, -3, 10);
		fillWithMetadataBlocks(world, sbb, 0, -3, 0, 10, 3, 10, Blocks.stained_hardened_clay, 9, Blocks.stained_hardened_clay, 9, false);
		fillWithMetadataBlocks(world, sbb, 0, 0, 0, 10, 0, 10, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
		fillWithAir(world, sbb, 5, -2, 0, 5, -1, 0);
		placeDoorAtCurrentPosition(world, sbb, random, 5, -2, 0, this.getMetadataWithOffset(Blocks.wooden_door, 1));
		fillWithAir(world, sbb, 1, -2, 1, 9, 2, 9);

		fillWithMetadataBlocks(world, sbb, 3, 4, 3, 7, 7, 7, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
		fillWithMetadataBlocks(world, sbb, 4, 4, 3, 6, 6, 3, Blocks.stained_hardened_clay, 1, Blocks.stained_hardened_clay, 1, false);
		fillWithMetadataBlocks(world, sbb, 4, 4, 7, 6, 6, 7, Blocks.stained_hardened_clay, 1, Blocks.stained_hardened_clay, 1, false);
		fillWithMetadataBlocks(world, sbb, 3, 4, 4, 3, 6, 6, Blocks.stained_hardened_clay, 1, Blocks.stained_hardened_clay, 1, false);
		fillWithMetadataBlocks(world, sbb, 7, 4, 4, 7, 6, 6, Blocks.stained_hardened_clay, 1, Blocks.stained_hardened_clay, 1, false);
		fillWithMetadataBlocks(world, sbb, 4, 7, 4, 6, 7, 6, Blocks.stained_hardened_clay, 1, Blocks.stained_hardened_clay, 1, false);
		fillWithAir(world, sbb, 4, 3, 4, 6, 6, 6);

		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1, 0, 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1, 0, 9, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 9, 0, 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 9, 0, 9, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1, 0, 5, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 9, 0, 5, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 0, 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 0, 9, sbb);

		fillWithBlocks(world, sbb, 1, -2, 1, 1, -2, 9, Blocks.redstone_wire, Blocks.redstone_wire, false);
		fillWithBlocks(world, sbb, 1, -2, 9, 9, -2, 9, Blocks.redstone_wire, Blocks.redstone_wire, false);
		fillWithBlocks(world, sbb, 9, -2, 1, 9, -2, 9, Blocks.redstone_wire, Blocks.redstone_wire, false);
		placeBlockAtCurrentPosition(world, Blocks.lever, 0, 1, -2, 1, sbb);

		fillWithMetadataBlocks(world, sbb, 8, -2, 1, 8, -2, 7, Blocks.unpowered_repeater, 0, Blocks.unpowered_repeater, 0, false);
		fillWithMetadataBlocks(world, sbb, 2, -2, 2, 2, -2, 7, Blocks.unpowered_repeater, 2, Blocks.unpowered_repeater, 2, false);
		fillWithMetadataBlocks(world, sbb, 4, -2, 8 , 6, -2, 8, Blocks.unpowered_repeater, 1, Blocks.unpowered_repeater, 1, false);

		fillWithBlocks(world, sbb, 7, -1, 1, 7, -1, 7, ItemsAndBlocks.kinesisPipeWood, ItemsAndBlocks.kinesisPipeWood, false);
		fillWithBlocks(world, sbb, 7, 0, 1, 7, 0, 7, ItemsAndBlocks.kinisisPipeStone, ItemsAndBlocks.kinisisPipeStone, false);
		fillWithBlocks(world, sbb, 3, -1, 2, 3, -1, 7, ItemsAndBlocks.kinesisPipeWood, ItemsAndBlocks.kinesisPipeWood, false);
		fillWithBlocks(world, sbb, 3, 0, 2, 3, 0, 7, ItemsAndBlocks.kinisisPipeStone, ItemsAndBlocks.kinisisPipeStone, false);
		fillWithBlocks(world, sbb, 4, -1, 7, 6, -1, 7, ItemsAndBlocks.kinesisPipeWood, ItemsAndBlocks.kinesisPipeWood, false);
		fillWithBlocks(world, sbb, 4, 0, 7, 6, 0, 7, ItemsAndBlocks.kinisisPipeStone, ItemsAndBlocks.kinisisPipeStone, false);

		fillWithBlocks(world, sbb, 5, 0, 5, 5, 7, 5, ItemsAndBlocks.kinisisPipeStone, ItemsAndBlocks.kinisisPipeStone, false);
		placeBlockAtCurrentPosition(world, ItemsAndBlocks.kinisisPipeStone, 0, 5, 0, 6, sbb);

		spawnVillagers(world, sbb, 0, 0, 0, 2);
		return true;
	}

	@Override
	protected int getVillagerType(int par1) {
		return 457;
	}
}
