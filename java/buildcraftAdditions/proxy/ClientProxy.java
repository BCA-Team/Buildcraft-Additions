package buildcraftAdditions.proxy;

import buildcraftAdditions.client.render.CanisterItemRender;
import buildcraftAdditions.core.BuildcraftAdditions;
import net.minecraftforge.client.MinecraftForgeClient;


public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerRenderers() {
    	MinecraftForgeClient.registerItemRenderer(BuildcraftAdditions.ironCanister, new CanisterItemRender(BuildcraftAdditions.ironCanister));
    	MinecraftForgeClient.registerItemRenderer(BuildcraftAdditions.goldCanister, new CanisterItemRender(BuildcraftAdditions.goldCanister));
    	MinecraftForgeClient.registerItemRenderer(BuildcraftAdditions.diamondCanister, new CanisterItemRender(BuildcraftAdditions.diamondCanister));
    }
}
