package buildcraftAdditions.client.gui;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.utils.Utils;

@SideOnly(Side.CLIENT)
public class GuiChargingStation extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/ChargingStation.png");
	public ResourceLocation ITEM_TEXTURE = TextureMap.locationItemsTexture;
	TileChargingStation chargingStation;

	public GuiChargingStation(InventoryPlayer inventoryPlayer, TileChargingStation tile) {
		super(new ContainerChargingStation(inventoryPlayer, tile));
		chargingStation = tile;
	}

	/*@Override
	protected void initLedgers(IInventory inventory) {
		super.initLedgers(inventory);
		ledgerManager.add(new ChargingStationLedger((TileChargingStation) tile));
	}*/

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		String title = Utils.localize("tile.blockChargingStation.name");
		fontRendererObj.drawString(title, 5, 6, 0x404040);
		fontRendererObj.drawString(Utils.localize("gui.inventory"), 8, (ySize - 110) + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	/*protected class ChargingStationLedger extends Ledger {

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
			fontRendererObj.drawString(String.format("%.1f ", chargingStation.getProgress() * 100) + "%", x + 22, y + 32, textColour);

			fontRendererObj.drawStringWithShadow(Utils.localize("gui.energyInTool") + ":", x + 22, y + 44, subheaderColour);
			fontRendererObj.drawString(Integer.toString(chargingStation.getToolEnergy()) + " MJ", x + 22, y + 56, textColour);

			fontRendererObj.drawStringWithShadow(Utils.localize("gui.maxEnergy") + ":", x + 22, y + 68, subheaderColour);
			fontRendererObj.drawString(Integer.toString(chargingStation.getToolMaxEnergy()) + " MJ", x + 22, y + 80, textColour);

		}

		@Override
		public String getTooltip() {
			return String.format("%.1f", chargingStation.getProgress() * 100) + "%";
		}
	}*/
}
