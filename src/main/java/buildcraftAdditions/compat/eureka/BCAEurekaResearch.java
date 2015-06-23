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
import buildcraftAdditions.reference.BlockLoader;
import buildcraftAdditions.reference.ItemLoader;
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

	private static final Block KEBT2 = new BlockBasic("KEB/T2/5");
	private static final Block KEBT3 = new BlockBasic("KEB/T3/4");

	public static void addEurekeResearch() {
		GameRegistry.registerBlock(KEBT2, "kebT2DisplayItem");
		GameRegistry.registerBlock(KEBT3, "kebT3DisplayItem");

		EurekaAPI.API.registerCategory(new BasicEurekaCategory("BCA-Dusters", BlockLoader.kineticDusterBlock));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.DustT0Key, "BCA-Dusters", 1, BlockLoader.basicDusterBlock));
		EurekaAPI.API.bindToKey(BlockLoader.basicDusterBlock, Variables.Eureka.DustT0Key);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(BlockLoader.basicDusterBlock), new ItemStack(BCItems.STONE_GEAR, 2), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.stone, 5), new ItemStack(Items.slime_ball)));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.DustT1Key, "BCA-Dusters", 20, BlockLoader.semiAutomaticDusterBlock, Variables.Eureka.DustT0Key));
		EurekaAPI.API.bindToKey(BlockLoader.semiAutomaticDusterBlock, Variables.Eureka.DustT1Key);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(BlockLoader.semiAutomaticDusterBlock), new ItemStack(BCItems.PIPE_ITEMS_GOLD, 2), new ItemStack(Items.gold_ingot), new ItemStack(BCItems.PIPE_ITEMS_GOLD, 2), new ItemStack(Blocks.stone, 3), new ItemStack(Items.slime_ball)));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.DustT2Key1, "BCA-Dusters", 40, BlockLoader.mechanicalDusterBlock, Variables.Eureka.DustT1Key));
		EurekaAPI.API.bindToKey(BlockLoader.mechanicalDusterBlock, Variables.Eureka.DustT2Key1);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(BlockLoader.mechanicalDusterBlock), new ItemStack(BCItems.IRON_GEAR, 2), new ItemStack(Items.gold_ingot, 1), new ItemStack(ItemLoader.grindingWheel, 1), new ItemStack(Blocks.stone, 5)));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.DustT2Key2, "BCA-Dusters", 20, BlockLoader.kineticDusterBlock, Variables.Eureka.DustT2Key1));
		EurekaAPI.API.bindToKey(BlockLoader.kineticDusterBlock, Variables.Eureka.DustT2Key2);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(BlockLoader.kineticDusterBlock), new ItemStack(Blocks.glass, 3), new ItemStack(BCItems.PIPE_ITEMS_GOLD, 2), new ItemStack(BCItems.GOLD_GEAR, 2), new ItemStack(BCItems.DIAMOND_GEAR)));

		EurekaAPI.API.registerCategory(new BasicEurekaCategory("BCA-Furnace+Coils", BlockLoader.heatedFurnaceBlock));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.HEATED_FURNACE, "BCA-Furnace+Coils", 5, BlockLoader.heatedFurnaceBlock));
		EurekaAPI.API.bindToKey(BlockLoader.heatedFurnaceBlock, Variables.Eureka.HEATED_FURNACE);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(BlockLoader.heatedFurnaceBlock), new ItemStack(Blocks.furnace), new ItemStack(Items.iron_ingot, 8)));
		EurekaAPI.API.registerProgressOption(Variables.Eureka.HEATED_FURNACE, EnumProgressOptions.PLACE_BLOCK, Blocks.furnace);

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.BASIC_COIL, "BCA-Furnace+Coils", 1, BlockLoader.basicCoilBlock, Variables.Eureka.HEATED_FURNACE));
		EurekaAPI.API.bindToKey(BlockLoader.basicCoilBlock, Variables.Eureka.BASIC_COIL);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(BlockLoader.basicCoilBlock), new ItemStack(Items.iron_ingot), new ItemStack(ItemLoader.itemIronWire, 8)));
		EurekaAPI.API.registerProgressOption(Variables.Eureka.BASIC_COIL, EnumProgressOptions.PLACE_BLOCK, BlockLoader.heatedFurnaceBlock);

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.LAVA_COIL, "BCA-Furnace+Coils", 5, BlockLoader.lavaCoilBlock, Variables.Eureka.BASIC_COIL));
		EurekaAPI.API.bindToKey(BlockLoader.lavaCoilBlock, Variables.Eureka.LAVA_COIL);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(BlockLoader.lavaCoilBlock), new ItemStack(Items.iron_ingot), new ItemStack(ItemLoader.goldWire, 8)));
		EurekaAPI.API.registerProgressOption(Variables.Eureka.LAVA_COIL, EnumProgressOptions.PLACE_BLOCK, BlockLoader.basicCoilBlock);

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.KINETIC_COIL, "BCA-Furnace+Coils", 4, BlockLoader.kineticCoil, Variables.Eureka.LAVA_COIL));
		EurekaAPI.API.bindToKey(BlockLoader.kineticCoil, Variables.Eureka.KINETIC_COIL);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(BlockLoader.kineticCoil), new ItemStack(Items.iron_ingot), new ItemStack(ItemLoader.diamondWire, 8)));
		EurekaAPI.API.registerProgressOption(Variables.Eureka.KINETIC_COIL, EnumProgressOptions.PLACE_BLOCK, BlockLoader.lavaCoilBlock);

		EurekaAPI.API.registerCategory(new BasicEurekaCategory("BCA-KEBS", KEBT3));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.KEBT1, "BCA-KEBS", 3, BlockLoader.kebT1));
		EurekaAPI.API.bindToKey(BlockLoader.kebT1, Variables.Eureka.KEBT1);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(BlockLoader.kebT1), new ItemStack(Items.iron_ingot, 4), new ItemStack(ItemLoader.powerCapsuleTier1, 3), new ItemStack(BCItems.PIPE_POWER_GOLD, 2)));
		EurekaAPI.API.registerProgressOption(Variables.Eureka.KEBT1, EnumProgressOptions.CRAFTING, ItemLoader.powerCapsuleTier1);

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.KEBT2, "BCA-KEBS", 4, KEBT2, Variables.Eureka.KEBT1));
		EurekaAPI.API.bindToKey(BlockLoader.kebT2, Variables.Eureka.KEBT2);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(BlockLoader.kebT2), new ItemStack(Items.iron_ingot, 6), new ItemStack(BCItems.PIPE_POWER_GOLD, 2), new ItemStack(ItemLoader.powerCapsuleTier2)));
		EurekaAPI.API.registerProgressOption(Variables.Eureka.KEBT2, EnumProgressOptions.PLACE_BLOCK, BlockLoader.kebT1);

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.KEBT3, "BCA-KEBS", 2, KEBT3, Variables.Eureka.KEBT2));
		EurekaAPI.API.bindToKey(BlockLoader.kebT3Plating, Variables.Eureka.KEBT3);
		EurekaAPI.API.bindToKey(BlockLoader.kebT3Core, Variables.Eureka.KEBT3);
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(BlockLoader.kebT3Plating), new ItemStack(BCItems.PIPE_POWER_DIAMOND, 2), new ItemStack(Items.gold_ingot, 4), new ItemStack(Items.iron_ingot, 3)));
		EurekaAPI.API.registerDropHandler(new BasicDropHandler(new ItemStack(BlockLoader.kebT3Core)));

		EurekaAPI.API.registerCategory(new BasicEurekaCategory("BCA-Tools", ItemLoader.portableLaser));

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.KineticToolKey, "BCA-Tools", 15, ItemLoader.kineticMultiTool));
		EurekaAPI.API.bindToKey(ItemLoader.kineticMultiTool, Variables.Eureka.KineticToolKey);
		EurekaAPI.API.registerDropHandler(new KineticToolDropHandler());

		EurekaAPI.API.register(new BasicEurekaInfo(Variables.Eureka.PORTABLE_LASER, "BCA-Tools", 4, ItemLoader.portableLaser));
		EurekaAPI.API.bindToKey(ItemLoader.portableLaser, Variables.Eureka.PORTABLE_LASER);
		EurekaAPI.API.registerDropHandler(new PortableLaserDropHandler());
		EurekaAPI.API.registerProgressOption(Variables.Eureka.PORTABLE_LASER, EnumProgressOptions.CRAFTING, ((ItemBlock) new ItemStack(BCItems.LASER).getItem()).field_150939_a);
	}

}
