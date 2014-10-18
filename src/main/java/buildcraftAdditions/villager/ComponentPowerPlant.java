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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

public class ComponentPowerPlant extends StructureVillagePieces.House1 {
	public static final ResourceLocation redstoneEngine = new ResourceLocation("bcadditions", "blueprints/Redstone-Engine-df2e537ac33b4d684e783cd4b41653bc872d638e1f0c1afecada2a30e670aa39.bpt");
	public static final ResourceLocation stirlingEngine = new ResourceLocation("bcadditions", "blueprints/Stirling-Engine-6a8295b667031a4687a626ecb7c6bef997dc7e62c19f9e0941c64b33198f87d4.bpt");

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

		fillWithAir(world, sbb, 0, -3, 0, 11, -3, 11);
		fillWithMetadataBlocks(world, sbb, 0, -3, 0, 11, 3, 11, Blocks.stained_hardened_clay, 9, Blocks.stained_hardened_clay, 9, false);
		fillWithMetadataBlocks(world, sbb, 0, 0, 0, 11, 0, 11, Blocks.stained_hardened_clay, 15, Blocks.stained_hardened_clay, 15, false);
		fillWithAir(world, sbb, 6, -2, 0, 6, -1, 0);
		this.placeDoorAtCurrentPosition(world, sbb, random, 6, -2, 0, this.getMetadataWithOffset(Blocks.wooden_door, 1));
		fillWithAir(world, sbb, 1, -2, 1, 10, 2, 10);


		spawnVillagers(world, sbb, 0, 0, 0, 2);
		return true;
	}

	@Override
	protected int getVillagerType(int par1) {
		return 457;
	}
}
