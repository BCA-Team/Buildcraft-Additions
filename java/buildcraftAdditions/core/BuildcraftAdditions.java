package buildcraftAdditions.core;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftFactory;
import buildcraft.BuildCraftTransport;
import buildcraft.core.proxy.CoreProxy;
import buildcraftAdditions.blocks.BlockChargingStation;
import buildcraftAdditions.blocks.BlockFluidicCompressor;
import buildcraftAdditions.client.gui.GuiHandler;
import buildcraftAdditions.entities.TileChargingStation;
import buildcraftAdditions.entities.TileFluidicCompressor;
import buildcraftAdditions.items.BatteryBase;
import buildcraftAdditions.items.ItemCanister;
import buildcraftAdditions.items.ItemMegaChainsaw;
import buildcraftAdditions.items.ItemMegaDrill;
import buildcraftAdditions.items.ItemMegaHoe;
import buildcraftAdditions.items.ItemMjMeter;
import buildcraftAdditions.items.ItemMegaDigger;
import buildcraftAdditions.items.ItemPowerCapsuleTier1;
import buildcraftAdditions.items.ItemPowerCapsuleTier2;
import buildcraftAdditions.items.ItemPowerCapsuleTier3;
import buildcraftAdditions.proxy.CommonProxy;
import buildcraftAdditions.villager.ComponentPowerPlant;
import buildcraftAdditions.villager.PowerPlantCreationHandeler;
import buildcraftAdditions.villager.VillagerTradeHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="bcadditions", name="Buildcraft Additions", version = "1.3.0",dependencies = "required-after:BuildCraft|Energy@{6.0.13}")
public class BuildcraftAdditions {
	
	public static ItemCanister ironCanister;
	public static ItemCanister goldCanister;
	public static ItemCanister diamondCanister;
	public static BlockFluidicCompressor fluidicCompressorBlock;
	public static BlockChargingStation chargingStationBlock;
	public static Item mjMeter;
	public static Item poweredShovel;
	public static Item drill;
	public static Item chainsaw;
	public static Item megaHoe;
	public static Item powerCapsuleTier1;
	public static Item powerCapsuleTier2;
	public static Item powerCapsuleTier3;
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
		
		chainsaw = new ItemMegaChainsaw(6000);
		CoreProxy.proxy.registerItem(chainsaw);
		
		megaHoe = new ItemMegaHoe(6000);
		CoreProxy.proxy.registerItem(megaHoe);
		
		powerCapsuleTier1 = new ItemPowerCapsuleTier1();
		CoreProxy.proxy.registerItem(powerCapsuleTier1);
		
		powerCapsuleTier2 = new ItemPowerCapsuleTier2();
		CoreProxy.proxy.registerItem(powerCapsuleTier2);
		
		powerCapsuleTier3 = new ItemPowerCapsuleTier3();
		CoreProxy.proxy.registerItem(powerCapsuleTier3);
    }
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		
		CoreProxy.proxy.addCraftingRecipe(new ItemStack(ironCanister, 4), "PIP", "IGI", "PIP", 'P', BuildCraftTransport.pipeWaterproof, 'I', Items.iron_ingot, 'G', Blocks.glass_pane);
		CoreProxy.proxy.addCraftingRecipe(new ItemStack(goldCanister), "PGP", "GIG", "PGP", 'P', BuildCraftTransport.pipeWaterproof, 'G', Items.gold_ingot, 'I', ironCanister);
		CoreProxy.proxy.addCraftingRecipe(new ItemStack(diamondCanister), "PDP", "DGD", "PDP", 'P', BuildCraftTransport.pipeWaterproof, 'D', Items.diamond, 'G', goldCanister);
		
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
    	
    	CoreProxy.proxy.addCraftingRecipe(new ItemStack(fluidicCompressorBlock), "IFI", "PGP", "IMI", 'I', BuildCraftCore.ironGearItem, 'F', BuildCraftFactory.floodGateBlock, 'P', Blocks.piston, 'G', goldCanister, 'M', BuildCraftFactory.pumpBlock);
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    	}
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    @Mod.EventHandler
	public void initialize(FMLPreInitializationEvent evt) {
    	fluidicCompressorBlock = new BlockFluidicCompressor();
    	CoreProxy.proxy.registerBlock(fluidicCompressorBlock.setBlockName("blockFluidicCompressor").setCreativeTab(bcadditions));
    	
    	chargingStationBlock = new BlockChargingStation();
    	CoreProxy.proxy.registerBlock(chargingStationBlock.setBlockName("blockChargingStation").setCreativeTab(bcadditions));
    }

}

