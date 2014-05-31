package buildcraftAdditions.core;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraft.*;
import buildcraft.api.recipes.BuildcraftRecipes;
import buildcraftAdditions.blocks.BlockEngine;
import buildcraftAdditions.items.*;
import buildcraftAdditions.items.Tools.*;
import buildcraftAdditions.networking.PacketHandeler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import buildcraft.core.proxy.CoreProxy;
import buildcraftAdditions.blocks.BlockChargingStation;
import buildcraftAdditions.blocks.BlockFluidicCompressor;
import buildcraftAdditions.client.gui.GuiHandler;
import buildcraftAdditions.entities.TileChargingStation;
import buildcraftAdditions.entities.TileFluidicCompressor;
import buildcraftAdditions.proxy.CommonProxy;
import buildcraftAdditions.villager.ComponentPowerPlant;
import buildcraftAdditions.villager.PowerPlantCreationHandeler;
import buildcraftAdditions.villager.VillagerTradeHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="bcadditions", name="Buildcraft Additions", version = "@MODVERSION@", dependencies = "required-after:BuildCraft|Energy@{6.0.15}")
public class BuildcraftAdditions {

    public static ItemCanister ironCanister;
    public static ItemCanister goldCanister;
    public static ItemCanister diamondCanister;
    public static BlockFluidicCompressor fluidicCompressorBlock;
    public static BlockChargingStation chargingStationBlock;
    public static BlockEngine engineBlock;
    public static Item mjMeter;
    public static Item poweredShovel;
    public static Item drill;
    public static Item chainsaw;
    public static Item megaHoe;
    public static Item powerCapsuleTier1;
    public static Item powerCapsuleTier2;
    public static Item powerCapsuleTier3;
    public static Item ironStick;
    public static Item toolCore;
    public static Item toolUpgradeHoe;
    public static Item toolUpgradeDigger;
    public static Item toolUpgradeDrill;
    public static Item toolUpgradeChainsaw;
    public static Item kineticTool;
    public static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/villagers/Engineer.png");

    @Instance(value="bcadditions")
    public static BuildcraftAdditions instance;

    @SidedProxy(clientSide="buildcraftAdditions.proxy.ClientProxy", serverSide="buildcraftAdditions.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs bcadditions = new CreativeTabs("BuildcraftAdditions") {

        @Override
        public Item getTabIconItem() {
            return new ItemStack(fluidicCompressorBlock, 1).getItem();
        }

    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        PacketHandeler.init();

        ironCanister = new ItemCanister("ironCanister", 1000);
        CoreProxy.proxy.registerItem(ironCanister);

        goldCanister = new ItemCanister("goldCanister", 4000);
        CoreProxy.proxy.registerItem(goldCanister);

        diamondCanister = new ItemCanister("diamondCanister", 16000);
        CoreProxy.proxy.registerItem(diamondCanister);

        //mjMeter = new ItemMjMeter();
        //CoreProxy.proxy.registerItem(mjMeter);

        poweredShovel = new ItemMegaDigger();
        CoreProxy.proxy.registerItem(poweredShovel);

        drill = new ItemMegaDrill(6000);
        CoreProxy.proxy.registerItem(drill);

        chainsaw = new ItemMegaChainsaw();
        CoreProxy.proxy.registerItem(chainsaw);

        megaHoe = new ItemMegaHoe(6000);
        CoreProxy.proxy.registerItem(megaHoe);

        powerCapsuleTier1 = new ItemPowerCapsuleTier1();
        CoreProxy.proxy.registerItem(powerCapsuleTier1);

        powerCapsuleTier2 = new ItemPowerCapsuleTier2();
        CoreProxy.proxy.registerItem(powerCapsuleTier2);

        powerCapsuleTier3 = new ItemPowerCapsuleTier3();
        CoreProxy.proxy.registerItem(powerCapsuleTier3);

        ironStick = new ItemIronStick();
        CoreProxy.proxy.registerItem(ironStick);

        toolCore = new ItemToolCore();
        CoreProxy.proxy.registerItem(toolCore);

        toolUpgradeHoe = new ItemToolUpgradeHoe();
        CoreProxy.proxy.registerItem(toolUpgradeHoe);

        toolUpgradeDigger = new ItemToolUpgradeDigger();
        CoreProxy.proxy.registerItem(toolUpgradeDigger);

        toolUpgradeDrill = new ItemToolUpgradeDrill();
        CoreProxy.proxy.registerItem(toolUpgradeDrill);

        toolUpgradeChainsaw = new ItemToolUpgradeChainsaw();
        CoreProxy.proxy.registerItem(toolUpgradeChainsaw);

        kineticTool = new ItemKineticTool();
        CoreProxy.proxy.registerItem(kineticTool);

        BuildcraftRecipes.assemblyTable.addRecipe(1000, new ItemStack(ironStick), Items.iron_ingot);
        BuildcraftRecipes.assemblyTable.addRecipe(8000, new ItemStack(kineticTool), new ItemStack(Items.diamond, 3), ironStick, toolCore);
        BuildcraftRecipes.integrationTable.addRecipe(new ToolCoreRecepie());
        BuildcraftRecipes.integrationTable.addRecipe(new UpgradeRecepieDrillHead());
        BuildcraftRecipes.integrationTable.addRecipe(new UpgradeRecepieExcavationAttachment());
        BuildcraftRecipes.integrationTable.addRecipe(new UpgradeRecepieSawBlade());
        BuildcraftRecipes.integrationTable.addRecipe(new UpgradeRecepieTiller());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt) {

        CoreProxy.proxy.addCraftingRecipe(new ItemStack(ironCanister, 4), "PIP", "IGI", "PIP", 'P', BuildCraftTransport.pipeWaterproof, 'I', Items.iron_ingot, 'G', Blocks.glass_pane);
        CoreProxy.proxy.addCraftingRecipe(new ItemStack(goldCanister), "PGP", "GIG", "PGP", 'P', BuildCraftTransport.pipeWaterproof, 'G', Items.gold_ingot, 'I', ironCanister);
        CoreProxy.proxy.addCraftingRecipe(new ItemStack(diamondCanister), "PDP", "DGD", "PDP", 'P', BuildCraftTransport.pipeWaterproof, 'D', Items.diamond, 'G', goldCanister);
        CoreProxy.proxy.addCraftingRecipe(new ItemStack(fluidicCompressorBlock), "IFI", "PGP", "IMI", 'I', BuildCraftCore.ironGearItem, 'F', BuildCraftFactory.floodGateBlock, 'P', Blocks.piston, 'G', goldCanister, 'M', BuildCraftFactory.pumpBlock);
        CoreProxy.proxy.addCraftingRecipe(new ItemStack(chargingStationBlock), "I I", "WBW", "I I", 'I', Items.iron_ingot, 'W', BuildCraftTransport.pipePowerWood, 'B', Blocks.iron_block);
        CoreProxy.proxy.addCraftingRecipe(new ItemStack(powerCapsuleTier1), "IGI","IRI", "IGI", 'I', Items.iron_ingot, 'G', Items.gold_ingot, 'R', Blocks.redstone_block);
        CoreProxy.proxy.addCraftingRecipe(new ItemStack(powerCapsuleTier2), "GDG", "GPG", "GDG", 'G', Items.gold_ingot, 'D', Items.diamond, 'P', powerCapsuleTier1);
        CoreProxy.proxy.addCraftingRecipe(new ItemStack(powerCapsuleTier3), "DED", "DPD", "DED", 'D', Items.diamond, 'E', Items.emerald, 'P', powerCapsuleTier2);

        if (evt.getSide()==Side.CLIENT){
            VillagerRegistry.instance().registerVillagerId(457);
            VillagerRegistry.instance().registerVillagerSkin(457, texture);
            VillagerRegistry.instance().registerVillageTradeHandler(457, new VillagerTradeHandler());
            VillagerRegistry.instance().registerVillageCreationHandler(new PowerPlantCreationHandeler());
            try
            {
                MapGenStructureIO.func_143031_a(ComponentPowerPlant.class, "bcadditions:powerplant");
            }
            catch (Throwable e){
            }
        }

    }

    @EventHandler
    public void load(FMLInitializationEvent event) {

        proxy.registerRenderers();
        CoreProxy.proxy.registerTileEntity(TileFluidicCompressor.class, "TileFluidicCompressor");
        CoreProxy.proxy.registerTileEntity(TileChargingStation.class, "TileChargingStation");


        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }

    @Mod.EventHandler
    public void initialize(FMLPreInitializationEvent evt) {
        fluidicCompressorBlock = new BlockFluidicCompressor();
        CoreProxy.proxy.registerBlock(fluidicCompressorBlock.setBlockName("blockFluidicCompressor").setCreativeTab(bcadditions));

        chargingStationBlock = new BlockChargingStation();
        CoreProxy.proxy.registerBlock(chargingStationBlock.setBlockName("blockChargingStation").setCreativeTab(bcadditions));

        //engineBlock = new BlockEngine();
        //CoreProxy.proxy.registerBlock(engineBlock.setBlockName("blockEngine").setCreativeTab(bcadditions));
    }

}

