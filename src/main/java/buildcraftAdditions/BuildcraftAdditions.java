package buildcraftAdditions;

import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftFactory;
import buildcraft.BuildCraftTransport;
import buildcraft.api.recipes.BuildcraftRecipes;
import buildcraft.core.proxy.CoreProxy;
import buildcraft.core.triggers.BCTrigger;
import buildcraftAdditions.api.DusterRecepies;
import buildcraftAdditions.api.EurekaRegistry;
import buildcraftAdditions.blocks.*;
import buildcraftAdditions.client.gui.GuiHandler;
import buildcraftAdditions.core.Configuration;
import buildcraftAdditions.core.EventListener;
import buildcraftAdditions.core.Logger;
import buildcraftAdditions.entities.*;
import buildcraftAdditions.items.*;
import buildcraftAdditions.items.Tools.*;
import buildcraftAdditions.networking.PacketHandeler;
import buildcraftAdditions.proxy.CommonProxy;
import buildcraftAdditions.triggers.*;
import buildcraftAdditions.variables.Variables;
import buildcraftAdditions.villager.ComponentPowerPlant;
import buildcraftAdditions.villager.PowerPlantCreationHandeler;
import buildcraftAdditions.villager.VillagerTradeHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */



@Mod(modid="bcadditions", name="Buildcraft Additions", version = "@MODVERSION@", dependencies = "required-after:BuildCraft|Energy@{6.0.16}")
public class BuildcraftAdditions {

    public static ItemCanister ironCanister;
    public static ItemCanister goldCanister;
    public static ItemCanister diamondCanister;

    public static BlockFluidicCompressor fluidicCompressorBlock;
    public static BlockChargingStation chargingStationBlock;
    public static BlockEngine engineBlock;
    public static BlockHeatedFurnace heatedFurnaceBlock;
    public static BlockBasicCoil basicCoilBlock;
    public static BlockBasicDuster basicDusterBlock;
    public static BlockSemiAutomaticDuster semiAutomaticDusterBlock;

    public static Item mjMeter;
    public static Item poweredShovel;
    public static Item drill;
    public static Item chainsaw;
    public static Item megaHoe;
    public static Item powerCapsuleTier1;
    public static Item powerCapsuleTier2;
    public static Item powerCapsuleTier3;
    public static Item ironStick;
    public static Item goldStick;
    public static Item diamondStick;
    public static Item emeraldStick;
    public static Item toolCore;
    public static Item toolUpgradeHoe;
    public static Item toolUpgradeDigger;
    public static Item toolUpgradeDrill;
    public static Item toolUpgradeChainsaw;
    public static Item itemDust;

    public static ItemKineticTool kineticTool;

    public static BCTrigger triggerCanAcceptCanister = new TriggerCanisterRequested();
    public static BCTrigger triggerHasEmptyCanister = new TriggerHasEmptyCanister();
    public static BCTrigger triggerhasFullCanister = new TriggerHasFullCanister();
    public static BCTrigger triggerDoneCharging = new TriggerDoneCharging();
    public static BCTrigger triggerReadyToCharge = new TriggerReadyToCharge();

    public static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/villagers/Engineer.png");

    @Mod.Instance(value="bcadditions")
    public static BuildcraftAdditions instance;

    @SidedProxy(clientSide="buildcraftAdditions.proxy.ClientProxy", serverSide="buildcraftAdditions.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs bcadditions = new CreativeTabs("BuildcraftAdditions") {

        @Override
        public Item getTabIconItem() {
            return new ItemStack(fluidicCompressorBlock, 1).getItem();
        }

    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        Logger.initiallize();
        Configuration.readConfig(event);
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

        goldStick = new ItemGoldStick();
        CoreProxy.proxy.registerItem(goldStick);

        diamondStick = new ItemDiamondStick();
        CoreProxy.proxy.registerItem(diamondStick);

        emeraldStick = new ItemEmeraldStick();
        CoreProxy.proxy.registerItem(emeraldStick);

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
        BuildcraftRecipes.assemblyTable.addRecipe(2000, new ItemStack(goldStick), new ItemStack(Items.gold_ingot, 4));
        BuildcraftRecipes.assemblyTable.addRecipe(3000, new ItemStack(diamondStick), new ItemStack(Items.diamond, 2));
        BuildcraftRecipes.assemblyTable.addRecipe(8000, new ItemStack(kineticTool), new ItemStack(Items.diamond, 3), ironStick, toolCore);
        BuildcraftRecipes.assemblyTable.addRecipe(1000, new ItemStack(toolUpgradeChainsaw), toolCore, new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.gold_ingot, 2));
        BuildcraftRecipes.assemblyTable.addRecipe(1000, new ItemStack(toolUpgradeDrill), toolCore, new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.gold_ingot, 2));
        BuildcraftRecipes.assemblyTable.addRecipe(1000, new ItemStack(toolUpgradeDigger), toolCore, new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.gold_ingot, 2));
        BuildcraftRecipes.assemblyTable.addRecipe(1000, new ItemStack(toolUpgradeHoe), toolCore, new ItemStack(Items.iron_ingot, 3), new ItemStack(Items.gold_ingot, 2));
        BuildcraftRecipes.integrationTable.addRecipe(new ToolCoreRecepie());
        BuildcraftRecipes.integrationTable.addRecipe(new UpgradeRecepieDrillHead());
        BuildcraftRecipes.integrationTable.addRecipe(new UpgradeRecepieExcavationAttachment());
        BuildcraftRecipes.integrationTable.addRecipe(new UpgradeRecepieSawBlade());
        BuildcraftRecipes.integrationTable.addRecipe(new UpgradeRecepieTiller());
        BuildcraftRecipes.integrationTable.addRecipe(new UpgradeRecepieGoldStick());
        BuildcraftRecipes.integrationTable.addRecipe(new UpgradeRecepieDiamondStick());
        BuildcraftRecipes.integrationTable.addRecipe(new UpgradeRecepieEmeraldStick());

        DusterRecepies.addDusterRecepie(new ItemStack(Blocks.redstone_ore), new ItemStack(Items.redstone, 6));
        DusterRecepies.addDusterRecepie(new ItemStack(Blocks.coal_ore), new ItemStack(Items.coal, 6));
        DusterRecepies.addDusterRecepie(new ItemStack(Blocks.lapis_ore), new ItemStack(Items.dye, 6, 4));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt) {

        FMLCommonHandler.instance().bus().register(new EventListener.FML());
        MinecraftForge.EVENT_BUS.register(new EventListener.Forge());

        GameRegistry.addRecipe(new ItemStack(ironCanister, 4), "PIP", "IGI", "PIP", 'P', BuildCraftTransport.pipeWaterproof, 'I', Items.iron_ingot, 'G', Blocks.glass_pane);
        GameRegistry.addRecipe(new ItemStack(goldCanister), "PGP", "GIG", "PGP", 'P', BuildCraftTransport.pipeWaterproof, 'G', Items.gold_ingot, 'I', ironCanister);
        GameRegistry.addRecipe(new ItemStack(diamondCanister), "PDP", "DGD", "PDP", 'P', BuildCraftTransport.pipeWaterproof, 'D', Items.diamond, 'G', goldCanister);
        GameRegistry.addRecipe(new ItemStack(fluidicCompressorBlock), "IFI", "PGP", "IMI", 'I', BuildCraftCore.ironGearItem, 'F', BuildCraftFactory.floodGateBlock, 'P', Blocks.piston, 'G', goldCanister, 'M', BuildCraftFactory.pumpBlock);
        GameRegistry.addRecipe(new ItemStack(chargingStationBlock), "I I", "WKW", "I I", 'I', BuildCraftCore.ironGearItem, 'W', BuildCraftTransport.pipePowerWood, 'K', powerCapsuleTier2);
        GameRegistry.addRecipe(new ItemStack(powerCapsuleTier1), "IGI","IRI", "IGI", 'I', Items.iron_ingot, 'G', Items.gold_ingot, 'R', Blocks.redstone_block);
        GameRegistry.addRecipe(new ItemStack(powerCapsuleTier2), "GDG", "GPG", "GDG", 'G', Items.gold_ingot, 'D', Items.diamond, 'P', powerCapsuleTier1);
        GameRegistry.addRecipe(new ItemStack(powerCapsuleTier3), "DED", "DPD", "DED", 'D', Items.diamond, 'E', Items.emerald, 'P', powerCapsuleTier2);

        if (evt.getSide()== Side.CLIENT){
            VillagerRegistry.instance().registerVillagerId(457);
            VillagerRegistry.instance().registerVillagerSkin(457, texture);
            VillagerRegistry.instance().registerVillageTradeHandler(457, new VillagerTradeHandler());
            VillagerRegistry.instance().registerVillageCreationHandler(new PowerPlantCreationHandeler());
            try
            {
                MapGenStructureIO.func_143031_a(ComponentPowerPlant.class, "bcadditions:powerplant");
            }
            catch (Throwable e){
                e.printStackTrace();
            }
        }

    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {

        proxy.registerRenderers();
        GameRegistry.registerTileEntity(TileFluidicCompressor.class, "TileFluidicCompressor");
        GameRegistry.registerTileEntity(TileChargingStation.class, "TileChargingStation");
        GameRegistry.registerTileEntity(TileHeatedFurnace.class, "TileHeatedFurnace");
        GameRegistry.registerTileEntity(TileBasicCoil.class, "TileBasicCoil");
        GameRegistry.registerTileEntity(TileBasicDuster.class, "TileBasicDuster");

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        addDusts("Iron");
        addDusts("Gold");
        addDusts("Copper");
        addDusts("Lead");
        addDusts("Nickel");
        addDusts("Platinum");
        addDusts("Silver");
        addDusts("Tin");

        EurekaRegistry.registerKey(Variables.DustT1Key);
        EurekaRegistry.registerKey(Variables.DustT2Key1);
        EurekaRegistry.registerKey(Variables.DustT2Key2);
        EurekaRegistry.registerKey(Variables.CoilT1Key);
        EurekaRegistry.registerKey(Variables.CoilT2Key);
    }

    public void addDusts(String metal){
        ArrayList<ItemStack> list;
        list = OreDictionary.getOres("ingot" + metal);
        if (list.isEmpty())
            return;
        list = OreDictionary.getOres("ore" + metal);
        if (list.isEmpty())
            return;
        itemDust = new ItemDust(metal);
        GameRegistry.registerItem(itemDust, "dust" + metal);
        OreDictionary.registerOre("dust" + metal, itemDust);
        GameRegistry.addSmelting(itemDust, OreDictionary.getOres("ingot" + metal).get(0), 0);
        for (ItemStack stack : list)
            DusterRecepies.addDusterRecepie(stack, new ItemStack(itemDust, 2));
        list = OreDictionary.getOres("ingot" + metal);
        for (ItemStack stack : list)
            DusterRecepies.addDusterRecepie(stack, new ItemStack(itemDust, 1));
    }



    @Mod.EventHandler
    public void initialize(FMLPreInitializationEvent evt) {
        fluidicCompressorBlock = new BlockFluidicCompressor();
        fluidicCompressorBlock.setBlockName("blockFluidicCompressor").setCreativeTab(bcadditions);
        GameRegistry.registerBlock(fluidicCompressorBlock, "blockFluidicCompressor");

        chargingStationBlock = new BlockChargingStation();
        chargingStationBlock.setBlockName("blockChargingStation").setCreativeTab(bcadditions);
        GameRegistry.registerBlock(chargingStationBlock, "blockChargingStation");

        heatedFurnaceBlock = new BlockHeatedFurnace();
        heatedFurnaceBlock.setBlockName("blockHeatedFurnace").setCreativeTab(bcadditions);
        GameRegistry.registerBlock(heatedFurnaceBlock, "blockHeatedFurnace");

        basicCoilBlock = new BlockBasicCoil();
        basicCoilBlock.setBlockName("blockCoilBasic").setCreativeTab(bcadditions);
        GameRegistry.registerBlock(basicCoilBlock, "blockCoilBasic");

        basicDusterBlock = new BlockBasicDuster();
        basicDusterBlock.setBlockName("blockDusterBasic").setCreativeTab(bcadditions);
        GameRegistry.registerBlock(basicDusterBlock, "blockDusterBasic");

        semiAutomaticDusterBlock = new BlockSemiAutomaticDuster();
        semiAutomaticDusterBlock.setBlockName("blockDusterSemiAutomatic").setCreativeTab(bcadditions);
        GameRegistry.registerBlock(semiAutomaticDusterBlock, "blockDusterSemiAutomatic");

        //engineBlock = new BlockEngine();
        //CoreProxy.proxy.registerBlock(engineBlock.setBlockName("blockEngine").setCreativeTab(bcadditions));
    }

}

