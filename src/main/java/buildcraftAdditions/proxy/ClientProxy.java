package buildcraftAdditions.proxy;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import cpw.mods.fml.client.registry.ClientRegistry;

import net.minecraftforge.client.MinecraftForgeClient;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.client.render.CanisterItemRender;
import buildcraftAdditions.client.render.RendererDuster;
import buildcraftAdditions.client.render.RendererDusterKinetic;
import buildcraftAdditions.tileEntities.TileBasicDuster;
import buildcraftAdditions.tileEntities.TileKineticDuster;
import buildcraftAdditions.tileEntities.TileMechanicalDuster;
import buildcraftAdditions.tileEntities.TileSemiAutomaticDuster;


public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		MinecraftForgeClient.registerItemRenderer(BuildcraftAdditions.ironCanister, new CanisterItemRender(BuildcraftAdditions.ironCanister));
		MinecraftForgeClient.registerItemRenderer(BuildcraftAdditions.goldCanister, new CanisterItemRender(BuildcraftAdditions.goldCanister));
		MinecraftForgeClient.registerItemRenderer(BuildcraftAdditions.diamondCanister, new CanisterItemRender(BuildcraftAdditions.diamondCanister));
		ClientRegistry.bindTileEntitySpecialRenderer(TileBasicDuster.class, new RendererDuster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSemiAutomaticDuster.class, new RendererDuster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMechanicalDuster.class, new RendererDuster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileKineticDuster.class, new RendererDusterKinetic());
	}
}
