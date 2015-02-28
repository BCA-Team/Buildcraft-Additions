package buildcraftAdditions.proxy;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.Fluid;

import buildcraftAdditions.api.configurableOutput.IConfigurableOutput;
import buildcraftAdditions.client.gui.GuiBasicCoil;
import buildcraftAdditions.client.gui.GuiChargingStation;
import buildcraftAdditions.client.gui.GuiCoolingTower;
import buildcraftAdditions.client.gui.GuiFluidicCompressor;
import buildcraftAdditions.client.gui.GuiHeatedFurnace;
import buildcraftAdditions.client.gui.GuiItemSorter;
import buildcraftAdditions.client.gui.GuiKEB;
import buildcraftAdditions.client.gui.GuiKineticMultiTool;
import buildcraftAdditions.client.gui.GuiMachineConfigurator;
import buildcraftAdditions.client.gui.GuiPipeColoringTool;
import buildcraftAdditions.client.gui.GuiPortableLaser;
import buildcraftAdditions.client.gui.GuiRefinery;
import buildcraftAdditions.client.render.blocks.RendererItemSorter;
import buildcraftAdditions.client.render.entities.EntityLaserShotRenderer;
import buildcraftAdditions.client.render.items.CanisterItemRender;
import buildcraftAdditions.client.render.tileentities.RendererDuster;
import buildcraftAdditions.client.render.tileentities.RendererDusterKinetic;
import buildcraftAdditions.client.render.tileentities.RendererKEBT2;
import buildcraftAdditions.client.render.tileentities.RendererKEBT3;
import buildcraftAdditions.core.BucketHandler;
import buildcraftAdditions.entities.EntityLaserShot;
import buildcraftAdditions.inventories.InventoryKineticMultiTool;
import buildcraftAdditions.inventories.InventoryPortableLaser;
import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.items.Tools.ItemPipeColoringTool;
import buildcraftAdditions.items.Tools.ItemPortableLaser;
import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.tileEntities.TileBasicCoil;
import buildcraftAdditions.tileEntities.TileBasicDuster;
import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.tileEntities.TileCoolingTower;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;
import buildcraftAdditions.tileEntities.TileItemSorter;
import buildcraftAdditions.tileEntities.TileKEBT2;
import buildcraftAdditions.tileEntities.TileKEBT3;
import buildcraftAdditions.tileEntities.TileKineticDuster;
import buildcraftAdditions.tileEntities.TileMechanicalDuster;
import buildcraftAdditions.tileEntities.TileRefinery;
import buildcraftAdditions.tileEntities.TileSemiAutomaticDuster;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;

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

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch (Variables.Gui.values()[ID]) {
			case FLUIDIC_COMPRESSOR:
				if (tile instanceof TileFluidicCompressor)
					return new GuiFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
			case CHARGING_STATION:
				if (tile instanceof TileChargingStation)
					return new GuiChargingStation(player.inventory, (TileChargingStation) tile);
			case KINETIC_MULTI_TOOL:
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemKineticMultiTool)
					return new GuiKineticMultiTool(player.inventory, new InventoryKineticMultiTool(player.getHeldItem()));
			case PORTABLE_LASER:
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPortableLaser)
					return new GuiPortableLaser(player.inventory, new InventoryPortableLaser(player.getHeldItem()));
			case HEATED_FURNACE:
				if (tile instanceof TileHeatedFurnace)
					return new GuiHeatedFurnace(player.inventory, (TileHeatedFurnace) tile);
			case BASIC_COIL:
				if (tile instanceof TileBasicCoil)
					return new GuiBasicCoil(player.inventory, (TileBasicCoil) tile);
			case KEB:
				if (tile instanceof TileKineticEnergyBufferBase)
					return new GuiKEB((TileKineticEnergyBufferBase) tile, player);
			case MACHINE_CONFIGURATOR:
				if (tile instanceof IMultiBlockTile) {
					IMultiBlockTile multiblock = (IMultiBlockTile) tile;
					if (!multiblock.isPartOfMultiblock())
						return null;
					if (!multiblock.isMaster())
						tile = world.getTileEntity(multiblock.getMasterX(), multiblock.getMasterY(), multiblock.getMasterZ());
				}
				if (tile instanceof IConfigurableOutput || tile instanceof IUpgradableMachine)
					return new GuiMachineConfigurator(player.inventory, tile);
			case REFINERY:
				if (tile instanceof TileRefinery)
					return new GuiRefinery(player.inventory, (TileRefinery) tile);
			case COOLING_TOWER:
				if (tile instanceof TileCoolingTower)
					return new GuiCoolingTower(player.inventory, (TileCoolingTower) tile);
			case ITEM_SORTER:
				if (tile instanceof TileItemSorter)
					return new GuiItemSorter(player.inventory, (TileItemSorter) tile);
			case PIPE_COLORING_TOOL:
				if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemPipeColoringTool)
					return new GuiPipeColoringTool(player.getCurrentEquippedItem());
		}
		return null;
	}
}
