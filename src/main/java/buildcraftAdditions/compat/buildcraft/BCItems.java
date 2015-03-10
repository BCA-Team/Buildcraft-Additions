package buildcraftAdditions.compat.buildcraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Eureka is distributed under the terms of LGPLv3
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public final class BCItems {
	public static final Block QUARRY = GameRegistry.findBlock("BuildCraft|Factory", "machineBlock");
	public static final Block AUTOWORKBENCH = GameRegistry.findBlock("BuildCraft|Factory", "autoWorkbenchBlock");
	public static final Block TANK = GameRegistry.findBlock("BuildCraft|Factory", "tankBlock");
	public static final Block MINING_WELL = GameRegistry.findBlock("BuildCraft|Factory", "miningWellBlock");
	public static final Block PUMP = GameRegistry.findBlock("BuildCraft|Factory", "pumpBlock");
	public static final Block FLOODGATE = GameRegistry.findBlock("BuildCraft|Factory", "floodGateBlock");
	public static final Block REFINERY = GameRegistry.findBlock("BuildCraft|Factory", "refineryBlock");
	public static final Block FILTERED_BUFFER = GameRegistry.findBlock("BuildCraft|Transport", "filteredBufferBlock");
	public static final Block HOPPER = GameRegistry.findBlock("BuildCraft|Factory", "blockHopper");

	public static final Block MARKER = GameRegistry.findBlock("BuildCraft|Builders", "markerBlock");
	public static final Block PATHMARKER = GameRegistry.findBlock("BuildCraft|Builders", "pathMarkerBlock");
	public static final Block LIBRARY = GameRegistry.findBlock("BuildCraft|Builders", "libraryBlock");
	public static final Block FILLER = GameRegistry.findBlock("BuildCraft|Builders", "fillerBlock");
	public static final Block ARCHITECT = GameRegistry.findBlock("BuildCraft|Builders", "architectBlock");
	public static final Block BUILDER = GameRegistry.findBlock("BuildCraft|Builders", "builderBlock");

	public static final Block LASER = GameRegistry.findBlock("BuildCraft|Silicon", "laserBlock");

	public static final Block ENGINES = GameRegistry.findBlock("BuildCraft|Energy", "engineBlock");

	public static final Item WOODEN_GEAR = GameRegistry.findItem("BuildCraft|Core", "woodenGearItem");
	public static final Item STONE_GEAR = GameRegistry.findItem("BuildCraft|Core", "stoneGearItem");
	public static final Item IRON_GEAR = GameRegistry.findItem("BuildCraft|Core", "ironGearItem");
	public static final Item GOLD_GEAR = GameRegistry.findItem("BuildCraft|Core", "goldGearItem");
	public static final Item DIAMOND_GEAR = GameRegistry.findItem("BuildCraft|Core", "diamondGearItem");

	public static final Item PIPE_ITEMS_WOOD = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemswood");
	public static final Item PIPE_ITEMS_COBBLESTONE = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemscobblestone");
	public static final Item PIPE_ITEMS_STONE = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsstone");
	public static final Item PIPE_ITEMS_QUARTZ = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsquartz");
	public static final Item PIPE_ITEMS_SANDSTONE = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemssandstone");
	public static final Item PIPE_ITEMS_GOLD = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsgold");
	public static final Item PIPE_ITEMS_IRON = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsiron");
	public static final Item PIPE_ITEMS_VOID = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsvoid");
	public static final Item PIPE_STRUCTURE = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipestructurecobblestone");
	public static final Item PIPE_ITEMS_OBSIDIAN = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsobsidian");
	public static final Item PIPE_ITEMS_EMERALD = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsemerald");
	public static final Item PIPE_ITEMS_DIAMOND = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsdiamond");
	public static final Item PIPE_ITEMS_LAPIS = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemslapis");
	public static final Item PIPE_ITEMS_DIAZULI = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsdaizuli");

	public static final Item PIPE_FLUID_WOOD = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidswood");
	public static final Item PIPE_FLUID_COBBLESTONE = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidscobblestone");
	public static final Item PIPE_FLUID_STONE = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsstone");
	public static final Item PIPE_FLUID_SANDSTONE = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidssandstone");
	public static final Item PIPE_FLUID_QUARTZ = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsquartz");
	public static final Item PIPE_FLUID_GOLD = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsgold");
	public static final Item PIPE_FLUID_IRON = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsiron");
	public static final Item PIPE_FLUID_EMERALD = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsemerald");
	public static final Item PIPE_FLUID_VOID = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsvoid");

	public static final Item PIPE_POWER_WOOD = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerwood");
	public static final Item PIPE_POWER_COBBLESTONE = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowercobblestone");
	public static final Item PIPE_POWER_STONE = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerstone");
	public static final Item PIPE_POWER_QUARTZ = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerstone");
	public static final Item PIPE_POWER_IRON = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepoweriron");
	public static final Item PIPE_POWER_GOLD = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowergold");
	public static final Item PIPE_POWER_DIAMOND = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerdiamond");
	public static final Item PIPE_POWER_EMERALD = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepoweremerald");

	public static final Item TEMPLATE = GameRegistry.findItem("BuildCraft|Builders", "templateItem");
	public static final Item BLUEPRINT = GameRegistry.findItem("BuildCraft|Builders", "blueprintItem");

	public static final Item LASERTABLES = GameRegistry.findItem("BuildCraft|Silicon", "null");
	public static final Item PIPE_PLUG = GameRegistry.findItem("BuildCraft|Transport", "pipePlug");

	public static final Item SEALANT = GameRegistry.findItem("BuildCraft|Transport", "pipeWaterproof");

	public static final Item CHIPSET_REDSTONE = GameRegistry.findItem("BuildCraft|Silicon", "redstoneChipset");
	public static final Item CHIPSET_DIAMOND = GameRegistry.findItemStack("BuildCraft|Silicon", "redstone_diamond_chipset", 1).getItem();
	public static final Item WRENCH = GameRegistry.findItem("BuildCraft|Core", "wrenchItem");
}
