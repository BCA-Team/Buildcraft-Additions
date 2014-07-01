package buildcraftAdditions.client.gui;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import buildcraft.BuildCraftCore;
import buildcraft.core.CoreIconProvider;
import buildcraft.core.gui.GuiBuildCraft;
import buildcraft.core.render.RenderUtils;
import buildcraftAdditions.utils.Utils;
import buildcraftAdditions.entities.TileFluidicCompressor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class GuiFluidicCompressor extends GuiBuildCraft {

    TileFluidicCompressor fluidicCompressor;
    public static ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/fluidicCompressorGUI.png");
    public ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;
    public ResourceLocation ITEM_TEXTURE = TextureMap.locationItemsTexture;

    public GuiFluidicCompressor(InventoryPlayer inventoryplayer, TileFluidicCompressor canner) {
        super(new ContainerFluidicCompressor(inventoryplayer, canner), canner, texture);
        this.fluidicCompressor = canner;
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
			fontRendererObj.drawString(canner.getProgress() * 100 /16 + "%", x + 22, y + 32, textColour);
			fontRendererObj.drawStringWithShadow(Utils.localize("gui.fluid") + ":", x + 22, y + 44, subheaderColour);
			if (canner.tank.getFluid() != null) {
				fontRendererObj.drawString(canner.tank.getFluid().getFluid().getName(), x + 22, y + 56, textColour);
			} else {
				fontRendererObj.drawString(Utils.localize("gui.noFluid"), x + 22, y + 56, textColour);
			}
			fontRendererObj.drawStringWithShadow(Utils.localize("gui.fluidStored") + ":", x + 22, y + 68, subheaderColour);
			fontRendererObj.drawString(Integer.toString(canner.getFluidStored())+" mB", x + 22, y + 80, textColour);

		}

		@Override
		public String getTooltip() {
			return Integer.toString(canner.getProgress()*100 / 16)+"%";
		}
	}
    
    @Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton){
    	super.mouseClicked(mouseX, mouseY, mouseButton);
		int mX = mouseX - guiLeft;
		int mY = mouseY - guiTop;
		if (mX >= 20 && mX <= 39 && mY >= 25 && mY <= 41 && !fluidicCompressor.fill){
			fluidicCompressor.fill = true;
		}
		if (mX >= 20 && mX <= 39 && mY >= 45 && mY <= 61 && fluidicCompressor.fill){
			fluidicCompressor.fill = false;
		}
		fluidicCompressor.sendModeUpdatePacket();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawFluid(fluidicCompressor.getFluid(), fluidicCompressor.getScaledLiquid(52), j+53, k+16, 16, 52);
        mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(j + 52, k + 21, 176, 21, 16, 58);
        if (fluidicCompressor.fill){
        	drawTexturedModalRect(j+20, k+25, 195, 83, 19, 16);
        	drawTexturedModalRect(j+20, k+45, 176, 99, 19, 16);
        } else {
        	drawTexturedModalRect(j+20, k+45, 195, 99, 19, 16);
        	drawTexturedModalRect(j+20, k+25, 176, 83, 19, 16);
        }
        drawTexturedModalRect(j+89, k+53, 176, 3, fluidicCompressor.getProgress(), 4);
        
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String title = Utils.localize("tile.blockFluidicCompressor.name");
        fontRendererObj.drawString(Utils.localize(title), getCenteredOffset(title), 6, 0x404040);
        fontRendererObj.drawString(Utils.localize("gui.inventory"), 8, (ySize - 96) + 2, 0x404040);
    }

    private void drawFluid(FluidStack fluid, int level, int x, int y, int width, int height){
        if(fluid == null || fluid.getFluid() == null) {
            return;
        }
        IIcon icon = fluid.getFluid().getIcon(fluid);
        mc.renderEngine.bindTexture(BLOCK_TEXTURE);
        RenderUtils.setGLColorFromInt(fluid.getFluid().getColor(fluid));
        int fullX = width / 16;
        int fullY = height / 16;
        int lastX = width - fullX * 16;
        int lastY = height - fullY * 16;
        int fullLvl = (height - level) / 16;
        int lastLvl = (height - level) - fullLvl * 16;
        for(int i = 0; i < fullX; i++) {
            for(int j = 0; j < fullY; j++) {
                if(j >= fullLvl) {
                    drawCutIcon(icon, x + i * 16, y + j * 16, 16, 16, j == fullLvl ? lastLvl : 0);
                }
            }
        }
        for(int i = 0; i < fullX; i++) {
            drawCutIcon(icon, x + i * 16, y + fullY * 16, 16, lastY, fullLvl == fullY ? lastLvl : 0);
        }
        for(int i = 0; i < fullY; i++) {
            if(i >= fullLvl) {
                drawCutIcon(icon, x + fullX * 16, y + i * 16, lastX, 16, i == fullLvl ? lastLvl : 0);
            }
        }
        drawCutIcon(icon, x + fullX * 16, y + fullY * 16, lastX, lastY, fullLvl == fullY ? lastLvl : 0);
    }

    private void drawCutIcon(IIcon icon, int x, int y, int width, int height, int cut){
        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.addVertexWithUV(x, y + height, zLevel, icon.getMinU(), icon.getInterpolatedV(height));
        tess.addVertexWithUV(x + width, y + height, zLevel, icon.getInterpolatedU(width), icon.getInterpolatedV(height));
        tess.addVertexWithUV(x + width, y + cut, zLevel, icon.getInterpolatedU(width), icon.getInterpolatedV(cut));
        tess.addVertexWithUV(x, y + cut, zLevel, icon.getMinU(), icon.getInterpolatedV(cut));
        tess.draw();
    }
    
    @Override
	protected void initLedgers(IInventory inventory) {
		super.initLedgers(inventory);
		ledgerManager.add(new FluidicCompressorLedger((TileFluidicCompressor) tile));
	}
    
}
