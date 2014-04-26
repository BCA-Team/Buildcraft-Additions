package fluidicCompressor.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftFactory;
import buildcraft.BuildCraftTransport;
import buildcraft.core.proxy.CoreProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import fluidicCompressor.gui.GuiHandler;
import fluidicCompressor.stuff.BlockFluidicCompressor;
import fluidicCompressor.stuff.ItemCanister;
import fluidicCompressor.stuff.TileFluidicCompressor;

@Mod(modid="FluidicCompressor", name="Fluidic Compressor", version = "1.0",dependencies = "required-after:BuildCraft|Energy@{6.0.5}")
public class FluidicCompressor {
	
	public static final String MODID = "FluidicCompressor";
	public static ItemCanister ironCanister;
	public static ItemCanister goldCanister;
	public static ItemCanister diamondCanister;
	public static BlockFluidicCompressor fluidicCompressorBlock;
	
	@Instance(value="FluidicCompressor")
	public static FluidicCompressor instance;
	
	@SidedProxy(clientSide="fluidicCompressor.core.ClientProxy", serverSide="fluidicCompressor.core.CommonProxy")
    public static CommonProxy proxy;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		
		ironCanister = new ItemCanister("ironCanister", 1000);
		CoreProxy.proxy.registerItem(ironCanister);

		goldCanister = new ItemCanister("goldCanister", 3000);
		CoreProxy.proxy.registerItem(goldCanister);
		
		diamondCanister = new ItemCanister("diamondCanister", 9000);
		CoreProxy.proxy.registerItem(diamondCanister);
    }
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		CoreProxy.proxy.addCraftingRecipe(new ItemStack(ironCanister), "PIP", "IGI", "PIP", 'P', BuildCraftTransport.pipeWaterproof, 'I', Items.iron_ingot, 'G', Blocks.glass_pane);
		CoreProxy.proxy.addCraftingRecipe(new ItemStack(goldCanister), "PGP", "GIG", "PGP", 'P', BuildCraftTransport.pipeWaterproof, 'G', Items.gold_ingot, 'I', ironCanister);
		CoreProxy.proxy.addCraftingRecipe(new ItemStack(diamondCanister), "PDP", "DGD", "PDP", 'P', BuildCraftTransport.pipeWaterproof, 'D', Items.diamond, 'G', goldCanister);
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
    	CoreProxy.proxy.registerBlock(fluidicCompressorBlock.setBlockName("blockFluidicCompressor"));
    	
    }

}

