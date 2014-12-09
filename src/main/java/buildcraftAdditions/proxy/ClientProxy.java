package buildcraftAdditions.proxy;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;

import net.minecraftforge.client.MinecraftForgeClient;

import buildcraftAdditions.client.render.CanisterItemRender;
import buildcraftAdditions.client.render.RendererDuster;
import buildcraftAdditions.client.render.RendererDusterKinetic;
import buildcraftAdditions.client.render.RendererKEBT2;
import buildcraftAdditions.client.render.RendererKEBT3;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.tileEntities.TileBasicDuster;
import buildcraftAdditions.tileEntities.TileKEBT2;
import buildcraftAdditions.tileEntities.TileKEBT3;
import buildcraftAdditions.tileEntities.TileKineticDuster;
import buildcraftAdditions.tileEntities.TileMechanicalDuster;
import buildcraftAdditions.tileEntities.TileSemiAutomaticDuster;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		MinecraftForgeClient.registerItemRenderer(ItemsAndBlocks.ironCanister, new CanisterItemRender(ItemsAndBlocks.ironCanister));
		MinecraftForgeClient.registerItemRenderer(ItemsAndBlocks.goldCanister, new CanisterItemRender(ItemsAndBlocks.goldCanister));
		MinecraftForgeClient.registerItemRenderer(ItemsAndBlocks.diamondCanister, new CanisterItemRender(ItemsAndBlocks.diamondCanister));
		ClientRegistry.bindTileEntitySpecialRenderer(TileBasicDuster.class, new RendererDuster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSemiAutomaticDuster.class, new RendererDuster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMechanicalDuster.class, new RendererDuster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileKineticDuster.class, new RendererDusterKinetic());
		ClientRegistry.bindTileEntitySpecialRenderer(TileKEBT2.class, new RendererKEBT2());
		ClientRegistry.bindTileEntitySpecialRenderer(TileKEBT3.class, new RendererKEBT3());
	}

	@Override
	public void addPowerplant() {
		super.addPowerplant();
		VillagerRegistry.instance().registerVillagerSkin(457, texture);
	}

	@Override
	public EntityPlayer getPlayer(String name) {
		return null;
	}
}
