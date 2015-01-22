package buildcraftAdditions.client.gui.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.containers.ContainerFluidicCompressor;
import buildcraftAdditions.client.gui.widgets.WidgetFluidTank;
import buildcraftAdditions.networking.MessageFluidicCompressorA;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiFluidicCompressor extends GuiBase {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/guiFluidicCompressor.png");
	private final TileFluidicCompressor fluidicCompressor;

	public GuiFluidicCompressor(InventoryPlayer inventoryPlayer, TileFluidicCompressor fluidicCompressor) {
		super(new ContainerFluidicCompressor(inventoryPlayer, fluidicCompressor));
		setDrawPlayerInv(true);
		this.fluidicCompressor = fluidicCompressor;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int mX = mouseX - guiLeft;
		int mY = mouseY - guiTop;
		if (mX >= 20 && mX <= 39 && mY >= 25 && mY <= 41 && !fluidicCompressor.fill) {
			fluidicCompressor.fill = true;
			PacketHandler.instance.sendToServer(new MessageFluidicCompressorA(true, fluidicCompressor));
		}
		if (mX >= 20 && mX <= 39 && mY >= 45 && mY <= 61 && fluidicCompressor.fill) {
			fluidicCompressor.fill = false;
			PacketHandler.instance.sendToServer(new MessageFluidicCompressorA(false, fluidicCompressor));
		}

	}

	@Override
	public void drawBackgroundPreWidgets(float f, int x, int y) {
		if (fluidicCompressor.fill) {
			drawTexturedModalRect(guiLeft + 20, guiTop + 25, 195, 83, 19, 16);
			drawTexturedModalRect(guiLeft + 20, guiTop + 45, 176, 99, 19, 16);
		} else {
			drawTexturedModalRect(guiLeft + 20, guiTop + 45, 195, 99, 19, 16);
			drawTexturedModalRect(guiLeft + 20, guiTop + 25, 176, 83, 19, 16);
		}
		drawTexturedModalRect(guiLeft + 89, guiTop + 53, 176, 3, fluidicCompressor.getProgress(), 4);
	}

	@Override
	public void drawBackgroundPostWidgets(float f, int x, int y) {
		drawTexturedModalRect(guiLeft + 52, guiTop + 21, 176, 21, 16, 58);
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
		return 69;
	}

	@Override
	public String getInventoryName() {
		return "fluidicCompressor";
	}

	@Override
	public void initialize() {
		addWidget(new WidgetFluidTank(0, (width - xSize) / 2 + 53, (height - ySize) / 2 + 16, 16, 52, this, fluidicCompressor.tank));
	}

	/*@Override
	protected void initLedgers(IInventory inventory) {
		super.initLedgers(inventory);
		ledgerManager.add(new FluidicCompressorLedger((TileFluidicCompressor) tile));
	}

	protected class FluidicCompressorLedger extends Ledger {

		TileFluidicCompressor canner;
		int headerColour = 0xe1c92f;
		int subheaderColour = 0xaaafb8;
		int textColour = 0x000000;

		public FluidicCompressorLedger(TileFluidicCompressor canner) {
			this.canner = canner;
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
			fontRendererObj.drawString(canner.getProgress() * 100 / 16 + "%", x + 22, y + 32, textColour);
			fontRendererObj.drawStringWithShadow(Utils.localize("gui.fluid") + ":", x + 22, y + 44, subheaderColour);
			if (canner.tank.getFluid() != null) {
				fontRendererObj.drawString(canner.tank.getFluid().getFluid().getName(), x + 22, y + 56, textColour);
			} else {
				fontRendererObj.drawString(Utils.localize("gui.noFluid"), x + 22, y + 56, textColour);
			}
			fontRendererObj.drawStringWithShadow(Utils.localize("gui.fluidStored") + ":", x + 22, y + 68, subheaderColour);
			fontRendererObj.drawString(Integer.toString(canner.getFluidStored()) + " mB", x + 22, y + 80, textColour);

		}

		@Override
		public String getTooltip() {
			return Integer.toString(canner.getProgress() * 100 / 16) + "%";
		}
	}*/

}
