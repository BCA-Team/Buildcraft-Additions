package buildcraftAdditions.client.gui.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.containers.ContainerChargingStation;
import buildcraftAdditions.tileEntities.TileChargingStation;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiChargingStation extends GuiBase {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/guiChargingStation.png");
	private final TileChargingStation chargingStation;

	public GuiChargingStation(InventoryPlayer inventoryPlayer, TileChargingStation tile) {
		super(new ContainerChargingStation(inventoryPlayer, tile));
		chargingStation = tile;
	}

	@Override
	public ResourceLocation texture() {
		return texture;
	}

	@Override
	public int getXSize() {
		return 176;
	}

	@Override
	public int getYSize() {
		return 53;
	}

	@Override
	public String getInventoryName() {
		return "chargingStation";
	}

	@Override
	public void initialize() {
	}

	/*
	@Override
	protected void initLedgers(IInventory inventory) {
		super.initLedgers(inventory);
		ledgerManager.add(new ChargingStationLedger((TileChargingStation) tile));
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
			Minecraft.getMinecraft().renderEngine.bindTexture(RenderUtils.MC_ITEM_SHEET);
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
