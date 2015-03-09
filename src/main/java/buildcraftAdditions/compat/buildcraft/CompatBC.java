package buildcraftAdditions.compat.buildcraft;

import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import buildcraft.api.blueprints.BuilderAPI;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.compat.CompatModule;
import buildcraftAdditions.compat.buildcraft.recipe.BCRecipeManager;
import buildcraftAdditions.compat.buildcraft.schematics.SchematicBCABase;
import buildcraftAdditions.compat.buildcraft.schematics.SchematicMulitblock;
import buildcraftAdditions.compat.buildcraft.schematics.SchematicRotatableBCABlock;
import buildcraftAdditions.compat.buildcraft.schematics.SchematicSorter;
import buildcraftAdditions.compat.buildcraft.triggers.Triggers;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.tileEntities.TileItemSorter;
import buildcraftAdditions.utils.fluids.RefineryRecipeConverter;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@CompatModule(id = "Buildcraft", requiredMods = "BuildCraft|Core")
public class CompatBC {

	@CompatModule.Handler
	public void preInit(FMLPreInitializationEvent event) {
		addItemSorter();
		registerSchematics();
		StripesHandler.register();
	}

	@CompatModule.Handler
	public void doneLoading(FMLLoadCompleteEvent event) {
		RefineryRecipeConverter.doYourThing();
		Triggers.register();
		BCRecipeManager.addBCRecipes();
		BuildcraftAdditions.proxy.addPowerplant();
	}

	public void addItemSorter() {
		GameRegistry.registerBlock(ItemsAndBlocks.itemSorter, "blockItemSorter");
		GameRegistry.registerTileEntity(TileItemSorter.class, "ItemSorter");
	}

	public void registerSchematics() {
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
