package buildcraftAdditions.client.gui;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraftAdditions.core.Variables;
import buildcraftAdditions.entities.TileChargingStation;
import buildcraftAdditions.entities.TileFluidicCompressor;
import buildcraftAdditions.items.Tools.ItemMegaChainsaw;
import buildcraftAdditions.items.Tools.ItemMegaDigger;
import buildcraftAdditions.items.Tools.ItemMegaDrill;
import buildcraftAdditions.items.Tools.ItemMegaHoe;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {


		TileEntity tile = world.getTileEntity(x, y, z);

		switch (ID) { 
		case Variables.GuiFluidicCompressor:
			if (tile instanceof TileFluidicCompressor)
				return new GuiFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
		case Variables.GuiChargingStation:
			if (tile instanceof TileChargingStation)
				return new GuiChargingStation(player.inventory, (TileChargingStation) tile);
		case Variables.GuiDigger: ItemMegaDigger digger = (ItemMegaDigger) player.getCurrentEquippedItem().getItem(); 
			return new GuiDigger(player.inventory, digger, digger.getInventory(player), player.getCurrentEquippedItem(), player);
		case Variables.GuiChainsaw: ItemMegaChainsaw chainsaw = (ItemMegaChainsaw) player.getCurrentEquippedItem().getItem();
			return new GuiChainsaw(player.inventory, chainsaw, chainsaw.getInventory(player), player.getCurrentEquippedItem(), player);
		case Variables.GuiDrill: ItemMegaDrill drill = (ItemMegaDrill) player.getCurrentEquippedItem().getItem();
			return new GuiDrill(player.inventory, drill, drill.getInventory(player), player.getCurrentEquippedItem(), player);
		case Variables.GuiHoe: ItemMegaHoe hoe = (ItemMegaHoe) player.getCurrentEquippedItem().getItem();
			return new GuiHoe(player.inventory, hoe, hoe.getInventory(player), player.getCurrentEquippedItem(), player);
			}
		return null;	
		}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {

		TileEntity tile = world.getTileEntity(x, y, z);

		switch (ID) {
		case Variables.GuiFluidicCompressor:
			if (tile instanceof TileFluidicCompressor)
			return new ContainerFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
		case Variables.GuiChargingStation:
			if (tile instanceof TileChargingStation)
				return new ContainerChargingStation(player.inventory, (TileChargingStation) tile);
		case Variables.GuiDigger: ItemMegaDigger digger = (ItemMegaDigger) player.getCurrentEquippedItem().getItem();
			return new ContainerDigger(player.inventory, digger, digger.getInventory(player), player.getCurrentEquippedItem(), player);
		case Variables.GuiChainsaw: ItemMegaChainsaw chainsaw = (ItemMegaChainsaw) player.getCurrentEquippedItem().getItem();
			return new ContainerChainsaw(player.inventory, chainsaw, chainsaw.getInventory(player), player.getCurrentEquippedItem(), player);
		case Variables.GuiDrill: ItemMegaDrill drill = (ItemMegaDrill) player.getCurrentEquippedItem().getItem();
			return new ContainerDrill(player.inventory, drill, drill.getInventory(player), player.getCurrentEquippedItem(), player);
		case Variables.GuiHoe: ItemMegaHoe hoe = (ItemMegaHoe) player.getCurrentEquippedItem().getItem();
			return new ContainerHoe(player.inventory, hoe, hoe.getInventory(player), player.getCurrentEquippedItem(), player);
		}
		return null;
	}

}
