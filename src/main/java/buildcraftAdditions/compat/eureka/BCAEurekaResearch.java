package buildcraftAdditions.compat.eureka;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import buildcraftAdditions.blocks.BlockBasic;
import buildcraftAdditions.compat.buildcraft.BCItems;
import buildcraftAdditions.compat.eureka.drophandlers.KineticToolDropHandler;
import buildcraftAdditions.compat.eureka.drophandlers.PortableLaserDropHandler;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;

import eureka.api.BasicDropHandler;
import eureka.api.BasicEurekaCategory;
import eureka.api.BasicEurekaInfo;
import eureka.api.EnumProgressOptions;
import eureka.api.EurekaAPI;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class BCAEurekaResearch {

	private static final Block KEBT2 = new BlockBasic("energyBufferMultiblockSides5");
	private static final Block KEBT3 = new BlockBasic("energyBufferT3MultiblockSides4");

	public static void addEurekeResearch() {
		GameRegistry.registerBlock(KEBT2, "kebT2DisplayItem");
		GameRegistry.registerBlock(KEBT3, "kebT3DisplayItem");

		EurekaAPI.API.registerCategory(new BasicEurekaCategory("BCA-Dusters", ItemsAndBlocks.kineticDusterBlock));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.DustT0Key, "BCA-Dusters", 1, ItemsAndBlocks.basicDusterBlock));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.basicDusterBlock, Variables.Eureka.DustT0Key);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(ItemsAndBlocks.basicDusterBlock), new ItemStack(BCItems.STONE_GEAR, 2), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.stone, 5), new ItemStack(Items.slime_ball)));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.DustT1Key, "BCA-Dusters", 20, ItemsAndBlocks.semiAutomaticDusterBlock, Variables.Eureka.DustT0Key));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.semiAutomaticDusterBlock, Variables.Eureka.DustT1Key);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(ItemsAndBlocks.semiAutomaticDusterBlock), new ItemStack(BCItems.PIPE_ITEMS_GOLD, 2), new ItemStack(Items.gold_ingot), new ItemStack(BCItems.PIPE_ITEMS_GOLD, 2), new ItemStack(Blocks.stone, 3), new ItemStack(Items.slime_ball)));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.DustT2Key1, "BCA-Dusters", 40, ItemsAndBlocks.mechanicalDusterBlock, Variables.Eureka.DustT1Key));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.mechanicalDusterBlock, Variables.Eureka.DustT2Key1);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(ItemsAndBlocks.mechanicalDusterBlock), new ItemStack(BCItems.IRON_GEAR, 2), new ItemStack(Items.gold_ingot, 1), new ItemStack(ItemsAndBlocks.itemGrindingWheel, 1), new ItemStack(Blocks.stone, 5)));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.DustT2Key2, "BCA-Dusters", 20, ItemsAndBlocks.kineticDusterBlock, Variables.Eureka.DustT2Key1));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.kineticDusterBlock, Variables.Eureka.DustT2Key2);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(ItemsAndBlocks.kineticDusterBlock), new ItemStack(Blocks.glass, 3), new ItemStack(BCItems.PIPE_ITEMS_GOLD, 2), new ItemStack(BCItems.GOLD_GEAR, 2), new ItemStack(BCItems.DIAMOND_GEAR)));

		EurekaAPI.API.registerCategory(new BasicEurekaCategory("BCA-Furnace+Coils", ItemsAndBlocks.heatedFurnaceBlock));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.HEATED_FURNACE, "BCA-Furnace+Coils", 5, ItemsAndBlocks.heatedFurnaceBlock));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.heatedFurnaceBlock, Variables.Eureka.HEATED_FURNACE);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(ItemsAndBlocks.heatedFurnaceBlock), new ItemStack(Blocks.furnace), new ItemStack(Items.iron_ingot, 8)));
		EurekaAPI.API.registerProgressOption(Variables.Eureka.HEATED_FURNACE, EnumProgressOptions.PLACE_BLOCK, Blocks.furnace);

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.BASIC_COIL, "BCA-Furnace+Coils", 1, ItemsAndBlocks.basicCoilBlock, Variables.Eureka.HEATED_FURNACE));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.basicCoilBlock, Variables.Eureka.BASIC_COIL);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(ItemsAndBlocks.basicCoilBlock), new ItemStack(Items.iron_ingot), new ItemStack(ItemsAndBlocks.itemIronWire, 8)));
		EurekaAPI.API.registerProgressOption(Variables.Eureka.BASIC_COIL, EnumProgressOptions.PLACE_BLOCK, ItemsAndBlocks.heatedFurnaceBlock);

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.LAVA_COIL, "BCA-Furnace+Coils", 5, ItemsAndBlocks.lavaCoilBlock, Variables.Eureka.BASIC_COIL));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.lavaCoilBlock, Variables.Eureka.LAVA_COIL);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(ItemsAndBlocks.lavaCoilBlock), new ItemStack(Items.iron_ingot), new ItemStack(ItemsAndBlocks.goldWire, 8)));
		EurekaAPI.API.registerProgressOption(Variables.Eureka.LAVA_COIL, EnumProgressOptions.PLACE_BLOCK, ItemsAndBlocks.basicCoilBlock);

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.KINETIC_COIL, "BCA-Furnace+Coils", 4, ItemsAndBlocks.kineticCoil, Variables.Eureka.LAVA_COIL));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.kineticCoil, Variables.Eureka.KINETIC_COIL);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(ItemsAndBlocks.kineticCoil), new ItemStack(Items.iron_ingot), new ItemStack(ItemsAndBlocks.diamondWire, 8)));
		EurekaAPI.API.registerProgressOption(Variables.Eureka.KINETIC_COIL, EnumProgressOptions.PLACE_BLOCK, ItemsAndBlocks.lavaCoilBlock);

		EurekaAPI.API.registerCategory(new BasicEurekaCategory("BCA-KEBS", KEBT3));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.KEBT1, "BCA-KEBS", 3, ItemsAndBlocks.kebT1));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.kebT1, Variables.Eureka.KEBT1);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(ItemsAndBlocks.kebT1), new ItemStack(Items.iron_ingot, 4), new ItemStack(ItemsAndBlocks.powerCapsuleTier1, 3), new ItemStack(BCItems.PIPE_POWER_GOLD, 2)));
		EurekaAPI.API.registerProgressOption(Variables.Eureka.KEBT1, EnumProgressOptions.CRAFTING, ItemsAndBlocks.powerCapsuleTier1);

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.KEBT2, "BCA-KEBS", 4, KEBT2, Variables.Eureka.KEBT1));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.kebT2, Variables.Eureka.KEBT2);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(ItemsAndBlocks.kebT2), new ItemStack(Items.iron_ingot, 6), new ItemStack(BCItems.PIPE_POWER_GOLD, 2), new ItemStack(ItemsAndBlocks.powerCapsuleTier2)));
		EurekaAPI.API.registerProgressOption(Variables.Eureka.KEBT2, EnumProgressOptions.PLACE_BLOCK, ItemsAndBlocks.kebT1);

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.KEBT3, "BCA-KEBS", 2, KEBT3, Variables.Eureka.KEBT2));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.kebT3Plating, Variables.Eureka.KEBT3);
		EurekaAPI.API.bindToKey(ItemsAndBlocks.kebT3Core, Variables.Eureka.KEBT3);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(ItemsAndBlocks.kebT3Plating), new ItemStack(BCItems.PIPE_POWER_DIAMOND, 2), new ItemStack(Items.gold_ingot, 4), new ItemStack(Items.iron_ingot, 3)));
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(ItemsAndBlocks.kebT3Core)));

		EurekaAPI.API.registerCategory(new BasicEurekaCategory("BCA-Tools", ItemsAndBlocks.portableLaser));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.KineticToolKey, "BCA-Tools", 15, ItemsAndBlocks.itemKineticMultiTool));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.itemKineticMultiTool, Variables.Eureka.KineticToolKey);
		EurekaAPI.API.registerDropHandler(new KineticToolDropHandler());

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.PORTABLE_LASER, "BCA-Tools", 4, ItemsAndBlocks.portableLaser));
		EurekaAPI.API.bindToKey(ItemsAndBlocks.portableLaser, Variables.Eureka.PORTABLE_LASER);
		EurekaAPI.API.registerDropHandler(new PortableLaserDropHandler());
		EurekaAPI.API.registerProgressOption(Variables.Eureka.PORTABLE_LASER, EnumProgressOptions.CRAFTING, ((ItemBlock) new ItemStack(BCItems.LASER).getItem()).field_150939_a);
	}

}
