package buildcraftAdditions.client.gui;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import buildcraft.BuildCraftCore;
import buildcraft.core.CoreIconProvider;
import buildcraft.core.gui.GuiBuildCraft;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.entities.TileChargingStation;

public class GuiChargingStation extends GuiBuildCraft{
	
	TileChargingStation chargingStation;
	public static ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/ChargingStation.png");
	public ResourceLocation ITEM_TEXTURE = TextureMap.locationItemsTexture;

	public GuiChargingStation(InventoryPlayer inventoryPlayer, TileChargingStation tile) {
		super (new ContainerChargingStation(inventoryPlayer, tile), tile, texture);
		chargingStation = tile;
	}
	
	 protected class ChargingStationLedger extends Ledger {

			TileChargingStation chargingStation;
			int headerColour = 0xe1c92f;
			int subheaderColour = 0xaaafb8;
			int textColour = 0x000000;

			public ChargingStationLedger(TileChargingStation chargingStation) {
				this.chargingStation = chargingStation;
				maxHeight = 94;
				overlayColor = 0xd46c1f;
			}

			@Override
			public void draw(int x, int y) {

				// Draw background
				drawBackground(x, y);

				// Draw icon
				Minecraft.getMinecraft().renderEngine.bindTexture(ITEM_TEXTURE);
				drawIcon(BuildCraftCore.iconProvider.getIcon(CoreIconProvider.ENERGY), x + 3, y + 4);

				if (!isFullyOpened()) {
					return;
				}

				fontRendererObj.drawStringWithShadow(Utils.localize("gui.progress"), x + 22, y + 8, headerColour);
				
				fontRendererObj.drawStringWithShadow(Utils.localize("gui.progress") + ":", x + 22, y + 20, subheaderColour);
				fontRendererObj.drawString(String.format("%.1f ", chargingStation.getProgress()*100) + "%", x + 22, y + 32, textColour);
				
				fontRendererObj.drawStringWithShadow(Utils.localize("gui.energyInTool") + ":", x + 22, y + 44, subheaderColour);
				fontRendererObj.drawString(Integer.toString(chargingStation.getToolEnergy()) + " MJ", x + 22, y + 56, textColour);
				
				fontRendererObj.drawStringWithShadow(Utils.localize("gui.maxEnergy") + ":", x + 22, y + 68, subheaderColour);
				fontRendererObj.drawString(Integer.toString(chargingStation.getToolMaxEnergy())+" MJ", x + 22, y + 80, textColour);

			}

			@Override
			public String getTooltip() {
				return String.format("%.1f", chargingStation.getProgress()*100)+"%";
			}
		}

	 @Override
	 protected void initLedgers(IInventory inventory) {
			super.initLedgers(inventory);
			ledgerManager.add(new ChargingStationLedger((TileChargingStation) tile));
		}
	 
	 @Override
	    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
	        super.drawGuiContainerForegroundLayer(par1, par2);
	        String title = Utils.localize("tile.blockChargingStation.name");
	        fontRendererObj.drawString(Utils.localize(title), getCenteredOffset(title), 6, 0x404040);
	        fontRendererObj.drawString(Utils.localize("gui.inventory"), 8, (ySize-110) + 2, 0x404040);
	    }
}
