package buildcraftAdditions.compat.buildcraft.schematics;

import buildcraft.api.blueprints.BuilderAPI;

import buildcraftAdditions.reference.BlockLoader;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BCASchematics {


	public static void registerSchematics() {
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.kebT1, SchematicBCABase.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.kineticCoil, SchematicBCABase.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.kineticDusterBlock, SchematicBCABase.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.lavaCoilBlock, SchematicBCABase.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.basicCoilBlock, SchematicBCABase.class);

		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.basicDusterBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.heatedFurnaceBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.chargingStationBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.fluidicCompressorBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.mechanicalDusterBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.semiAutomaticDusterBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);

		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.refineryWalls, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.refineryValve, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.coolingTowerWalls, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.coolingTowerValve, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.kebT2, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.kebT3Core, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.kebT3Plating, SchematicMulitblock.class);

		BuilderAPI.schematicRegistry.registerSchematicBlock(BlockLoader.itemSorter, SchematicSorter.class);
	}

}
