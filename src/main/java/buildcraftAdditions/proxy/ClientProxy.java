package buildcraftAdditions.proxy;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.Fluid;

import buildcraftAdditions.client.render.blocks.RendererItemSorter;
import buildcraftAdditions.client.render.entities.EntityLaserShotRenderer;
import buildcraftAdditions.client.render.items.CanisterItemRender;
import buildcraftAdditions.client.render.tileentities.RendererDuster;
import buildcraftAdditions.client.render.tileentities.RendererDusterKinetic;
import buildcraftAdditions.client.render.tileentities.RendererKEBT2;
import buildcraftAdditions.client.render.tileentities.RendererKEBT3;
import buildcraftAdditions.core.BucketHandler;
import buildcraftAdditions.entities.EntityLaserShot;
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
		MinecraftForgeClient.registerItemRenderer(ItemsAndBlocks.ironCanister, CanisterItemRender.INSTANCE);
		MinecraftForgeClient.registerItemRenderer(ItemsAndBlocks.goldCanister, CanisterItemRender.INSTANCE);
		MinecraftForgeClient.registerItemRenderer(ItemsAndBlocks.diamondCanister, CanisterItemRender.INSTANCE);
		ClientRegistry.bindTileEntitySpecialRenderer(TileBasicDuster.class, new RendererDuster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSemiAutomaticDuster.class, new RendererDuster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMechanicalDuster.class, new RendererDuster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileKineticDuster.class, new RendererDusterKinetic());
		ClientRegistry.bindTileEntitySpecialRenderer(TileKEBT2.class, new RendererKEBT2());
		ClientRegistry.bindTileEntitySpecialRenderer(TileKEBT3.class, new RendererKEBT3());
		RendererItemSorter.RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RendererItemSorter());
		RenderingRegistry.registerEntityRenderingHandler(EntityLaserShot.class, new EntityLaserShotRenderer());
	}

	@Override
	public void registerBucketRenderer() {
		BucketHandler.registerRenderers();
	}

	@Override
	public void addPowerplant() {
		super.addPowerplant();
		VillagerRegistry.instance().registerVillagerSkin(457, texture);
	}

	@Override
	public void cloneFluidTextures(Fluid source, Fluid target) {
		target.setIcons(source.getStillIcon(), source.getFlowingIcon());
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return FMLClientHandler.instance().getClientPlayerEntity();
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getWorldClient();
	}

	@Override
	public int addArmor(String name) {
		return RenderingRegistry.addNewArmourRendererPrefix(name);
	}

}
