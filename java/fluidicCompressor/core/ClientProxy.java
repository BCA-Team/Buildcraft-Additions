package fluidicCompressor.core;

import net.minecraftforge.client.MinecraftForgeClient;
import fluidicCompressor.stuff.CanisterItemRender;


public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerRenderers() {
    	MinecraftForgeClient.registerItemRenderer(FluidicCompressor.ironCanister, new CanisterItemRender(FluidicCompressor.ironCanister));
    	MinecraftForgeClient.registerItemRenderer(FluidicCompressor.goldCanister, new CanisterItemRender(FluidicCompressor.goldCanister));
    	MinecraftForgeClient.registerItemRenderer(FluidicCompressor.diamondCanister, new CanisterItemRender(FluidicCompressor.diamondCanister));
    }
}
