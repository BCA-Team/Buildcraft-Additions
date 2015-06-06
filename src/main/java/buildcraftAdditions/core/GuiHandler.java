package buildcraftAdditions.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

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
import buildcraftAdditions.inventories.InventoryKineticMultiTool;
import buildcraftAdditions.inventories.InventoryPortableLaser;
import buildcraftAdditions.inventories.containers.ContainerBasicCoil;
import buildcraftAdditions.inventories.containers.ContainerChargingStation;
import buildcraftAdditions.inventories.containers.ContainerCoolingTower;
import buildcraftAdditions.inventories.containers.ContainerFluidicCompressor;
import buildcraftAdditions.inventories.containers.ContainerHeatedFurnace;
import buildcraftAdditions.inventories.containers.ContainerItemSorter;
import buildcraftAdditions.inventories.containers.ContainerKEB;
import buildcraftAdditions.inventories.containers.ContainerKineticMultiTool;
import buildcraftAdditions.inventories.containers.ContainerMachineConfigurator;
import buildcraftAdditions.inventories.containers.ContainerPipeColoringTool;
import buildcraftAdditions.inventories.containers.ContainerPortableLaser;
import buildcraftAdditions.inventories.containers.ContainerRefinery;
import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.items.Tools.ItemPipeColoringTool;
import buildcraftAdditions.items.Tools.ItemPortableLaser;
import buildcraftAdditions.multiBlocks.IMultiBlockTile;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.tileEntities.TileBasicCoil;
import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.tileEntities.TileCoolingTower;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;
import buildcraftAdditions.tileEntities.TileItemSorter;
import buildcraftAdditions.tileEntities.TileRefinery;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public final class GuiHandler implements IGuiHandler {

	public static final GuiHandler INSTANCE = new GuiHandler();

	private GuiHandler() {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch (Variables.Gui.values()[ID]) {
			case FLUIDIC_COMPRESSOR:
				if (tile instanceof TileFluidicCompressor)
					return new ContainerFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
			case CHARGING_STATION:
				if (tile instanceof TileChargingStation)
					return new ContainerChargingStation(player.inventory, (TileChargingStation) tile);
			case KINETIC_MULTI_TOOL:
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemKineticMultiTool)
					return new ContainerKineticMultiTool(player.inventory, new InventoryKineticMultiTool(player.getHeldItem()));
			case PORTABLE_LASER:
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPortableLaser)
					return new ContainerPortableLaser(player.inventory, new InventoryPortableLaser(player.getHeldItem()));
			case HEATED_FURNACE:
				if (tile instanceof TileHeatedFurnace)
					return new ContainerHeatedFurnace(player.inventory, (TileHeatedFurnace) tile);
			case BASIC_COIL:
				if (tile instanceof TileBasicCoil)
					return new ContainerBasicCoil(player.inventory, (TileBasicCoil) tile);
			case KEB:
				if (tile instanceof TileKineticEnergyBufferBase)
					return new ContainerKEB(player, (TileKineticEnergyBufferBase) tile);
			case MACHINE_CONFIGURATOR:
				return new ContainerMachineConfigurator(player.inventory, tile);
			case REFINERY:
				return new ContainerRefinery(player.inventory, (TileRefinery) tile);
			case COOLING_TOWER:
				return new ContainerCoolingTower((TileCoolingTower) tile);
			case ITEM_SORTER:
				return new ContainerItemSorter(player.inventory, (TileItemSorter) tile);
			case PIPE_COLORING_TOOL:
				if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemPipeColoringTool)
					return new ContainerPipeColoringTool();
		}
		return null;
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
					return new GuiCoolingTower((TileCoolingTower) tile);
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
