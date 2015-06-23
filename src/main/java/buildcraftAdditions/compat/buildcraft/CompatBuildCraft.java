package buildcraftAdditions.compat.buildcraft;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.compat.CompatModule;
import buildcraftAdditions.compat.buildcraft.actions.Actions;
import buildcraftAdditions.compat.buildcraft.schematics.BCASchematics;
import buildcraftAdditions.compat.buildcraft.triggers.Triggers;
import buildcraftAdditions.items.ItemRobotDebugTool;
import buildcraftAdditions.tileEntities.TileItemSorter;
import buildcraftAdditions.utils.fluids.RefineryRecipeConverter;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@CompatModule(id = "Buildcraft", requiredMods = "BuildCraft|Core")
public class CompatBuildCraft {
	public static Item robotDebugTool;

	@CompatModule.Handler
	public void preInit(FMLPreInitializationEvent event) {
		addItemSorter();
		StripesHandler.register();
		BCASchematics.registerSchematics();
		addRobotDebugTool();
	}

	@CompatModule.Handler
	public void doneLoading(FMLLoadCompleteEvent event) {
		RefineryRecipeConverter.doYourThing();
		Triggers.register();
		Actions.register();
		BuildcraftAdditions.proxy.addPowerplant();
	}

	private void addItemSorter() {
		GameRegistry.registerTileEntity(TileItemSorter.class, "ItemSorter");
	}

	private void addRobotDebugTool() {
		robotDebugTool = new ItemRobotDebugTool();
		GameRegistry.registerItem(robotDebugTool, "robotDebugTool");
	}

}
