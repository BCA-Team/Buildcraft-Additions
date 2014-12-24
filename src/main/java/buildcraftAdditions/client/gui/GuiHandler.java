package buildcraftAdditions.client.gui;

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

import cpw.mods.fml.common.network.IGuiHandler;

import buildcraftAdditions.items.Tools.ItemKineticTool;
import buildcraftAdditions.items.Tools.ItemPoweredBase;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.tileEntities.TileBasicCoil;
import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;
import buildcraftAdditions.utils.IConfigurableOutput;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {


		TileEntity tile = world.getTileEntity(x, y, z);

		switch (ID) {
			case Variables.GUI_FLUIDIC_COMPRESSOR:
				if (tile instanceof TileFluidicCompressor)
					return new GuiFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
			case Variables.GUI_CHARGING_STATION:
				if (tile instanceof TileChargingStation)
					return new GuiChargingStation(player.inventory, (TileChargingStation) tile);
			case Variables.GUI_KINETIC_TOOL:
				if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemKineticTool) {
					ItemKineticTool tool = (ItemKineticTool) player.getCurrentEquippedItem().getItem();
					return new GuiKineticTool(player.inventory, tool, ItemPoweredBase.getInventory(player), player.getCurrentEquippedItem(), player);
				}
			case Variables.GUI_HEATED_FURNACE:
				if (tile instanceof TileHeatedFurnace)
					return new GuiHeatedFurnace(player.inventory, (TileHeatedFurnace) tile);
			case Variables.GUI_BASIC_COIL:
				if (tile instanceof TileBasicCoil)
					return new GuiBasicCoil(player.inventory, (TileBasicCoil) tile);
			case Variables.GUI_KEB:
				if (tile instanceof TileKineticEnergyBufferBase)
					return new GuiKEB((TileKineticEnergyBufferBase) tile, player);
				case Variables.GUI_MACHINE_CONFIGURATOR:
					if (tile instanceof IConfigurableOutput)
						return new GuiMachineConfigurator((IConfigurableOutput) tile);
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
									  int x, int y, int z) {

		TileEntity tile = world.getTileEntity(x, y, z);

		switch (ID) {
			case Variables.GUI_FLUIDIC_COMPRESSOR:
				if (tile instanceof TileFluidicCompressor)
					return new ContainerFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
			case Variables.GUI_CHARGING_STATION:
				if (tile instanceof TileChargingStation)
					return new ContainerChargingStation(player.inventory, (TileChargingStation) tile);
			case Variables.GUI_KINETIC_TOOL:
				if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemKineticTool) {
					ItemKineticTool tool = (ItemKineticTool) player.getCurrentEquippedItem().getItem();
					return new ContainerKineticTool(player.inventory, tool, ItemPoweredBase.getInventory(player), player.getCurrentEquippedItem(), player);
				}
			case Variables.GUI_HEATED_FURNACE:
				if (tile instanceof TileHeatedFurnace)
					return new ContainerHeatedFurnace(player.inventory, (TileHeatedFurnace) tile);
			case Variables.GUI_BASIC_COIL:
				if (tile instanceof TileBasicCoil)
					return new ContainerBasicCoil(player.inventory, (TileBasicCoil) tile);
			case Variables.GUI_KEB:
				if (tile instanceof TileKineticEnergyBufferBase)
					return new ContainerKEB((TileKineticEnergyBufferBase) tile, player);
				case Variables.GUI_MACHINE_CONFIGURATOR:
					return new ContainerMachineConfigurator();
		}
		return null;
	}

}
