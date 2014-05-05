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
import buildcraftAdditions.gui.GuiHandler;
import buildcraftAdditions.proxy.CommonProxy;
import buildcraftAdditions.stuff.BlockFluidicCompressor;
import buildcraftAdditions.stuff.ItemCanister;
import buildcraftAdditions.stuff.ItemMjMeter;
import buildcraftAdditions.stuff.TileFluidicCompressor;
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

@Mod(modid="bcadditions", name="Buildcraft Additions", version = "1.1.0",dependencies = "required-after:BuildCraft|Energy@{6.0.6}")
public class BuildcraftAdditions {
	
	public static ItemCanister ironCanister;
	public static ItemCanister goldCanister;
	public static ItemCanister diamondCanister;
	public static BlockFluidicCompressor fluidicCompressorBlock;
	public static Item mjMeter;
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
		
		mjMeter = new ItemMjMeter();
		CoreProxy.proxy.registerItem(mjMeter);
    }
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		CoreProxy.proxy.addCraftingRecipe(new ItemStack(ironCanister, 4), "PIP", "IGI", "PIP", 'P', BuildCraftTransport.pipeWaterproof, 'I', Items.iron_ingot, 'G', Blocks.glass_pane);
		CoreProxy.proxy.addCraftingRecipe(new ItemStack(goldCanister), "PGP", "GIG", "PGP", 'P', BuildCraftTransport.pipeWaterproof, 'G', Items.gold_ingot, 'I', ironCanister);
		CoreProxy.proxy.addCraftingRecipe(new ItemStack(diamondCanister), "PDP", "DGD", "PDP", 'P', BuildCraftTransport.pipeWaterproof, 'D', Items.diamond, 'G', goldCanister);
		
		
		VillagerRegistry.instance().registerVillagerId(457);
		VillagerRegistry.instance().registerVillagerSkin(457, texture);
        VillagerRegistry.instance().registerVillageTradeHandler(457, new VillagerTradeHandler());
        VillagerRegistry.instance().registerVillageCreationHandler(new PowerPlantCreationHandeler());
        try
        {
        	MapGenStructureIO.func_143031_a(ComponentPowerPlant.class, "bcadditions:powerplant");
        }
        catch (Throwable e){System.out.println("ERROR");}
	}
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
    	
    	proxy.registerRenderers();
    	CoreProxy.proxy.registerTileEntity(TileFluidicCompressor.class, "TileFluidicCompressor");
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
    	
    }

}

