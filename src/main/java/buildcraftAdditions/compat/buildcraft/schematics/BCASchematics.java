package buildcraftAdditions.compat.buildcraft.schematics;

import buildcraft.api.blueprints.BuilderAPI;

import buildcraftAdditions.reference.ItemsAndBlocks;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BCASchematics {


	public static void registerSchematics() {
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.kebT1, SchematicBCABase.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.kineticCoil, SchematicBCABase.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.kineticDusterBlock, SchematicBCABase.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.lavaCoilBlock, SchematicBCABase.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.basicCoilBlock, SchematicBCABase.class);

		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.basicDusterBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.heatedFurnaceBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.chargingStationBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.fluidicCompressorBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.mechanicalDusterBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.semiAutomaticDusterBlock, SchematicRotatableBCABlock.class, new int[]{2, 5, 3, 4}, true);

		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.refineryWalls, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.refineryValve, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.coolingTowerWalls, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.coolingTowerValve, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.kebT2, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.kebT3Core, SchematicMulitblock.class);
		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.kebT3Plating, SchematicMulitblock.class);

		BuilderAPI.schematicRegistry.registerSchematicBlock(ItemsAndBlocks.itemSorter, SchematicSorter.class);
	}

}
