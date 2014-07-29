package buildcraftAdditions.client.gui;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraftAdditions.items.Tools.ItemKineticTool;
import buildcraftAdditions.tileEntities.TileBasicCoil;
import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;
import buildcraftAdditions.variables.Variables;
import cpw.mods.fml.client.FMLClientHandler;
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
            case Variables.GuiKineticTool:
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof  ItemKineticTool) {
                    ItemKineticTool tool = (ItemKineticTool) player.getCurrentEquippedItem().getItem();
                    return new GuiKineticTool(player.inventory, tool, tool.getInventory(player), player.getCurrentEquippedItem(), player);
                }
            case Variables.GuiHeatedFurnace:
                    if (tile instanceof TileHeatedFurnace)
                        return new GuiHeatedFurnace(player.inventory, (TileHeatedFurnace) tile);
            case Variables.GuiBasicCoil:
                if (tile instanceof TileBasicCoil)
                    return new GuiBasicCoil(player.inventory, (TileBasicCoil) tile);
	        case Variables.GuiEngineeringDiary:
		        return new GuiEngineeringDiary(player);
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
            case Variables.GuiKineticTool:
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof  ItemKineticTool) {
                    ItemKineticTool tool = (ItemKineticTool) player.getCurrentEquippedItem().getItem();
                    return new ContainerKineticTool(player.inventory, tool, tool.getInventory(player), player.getCurrentEquippedItem(), player);
                }
            case Variables.GuiHeatedFurnace:
                    if (tile instanceof TileHeatedFurnace)
                        return new ContainerHeatedFurnace(player.inventory, (TileHeatedFurnace) tile);
            case Variables.GuiBasicCoil:
                if (tile instanceof TileBasicCoil)
                    return new ContainerBasicCoil(player.inventory, (TileBasicCoil) tile);
	        case Variables.GuiEngineeringDiary:
		        FMLClientHandler.instance().getClientPlayerEntity().getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG));
		        return new ContainerEngineeringDiary(player);
        }
        return null;
    }

}
