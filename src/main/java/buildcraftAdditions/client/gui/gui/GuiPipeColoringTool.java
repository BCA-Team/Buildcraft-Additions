package buildcraftAdditions.client.gui.gui;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.containers.ContainerPipeColoringTool;
import buildcraftAdditions.client.gui.widgets.WidgetBase;
import buildcraftAdditions.client.gui.widgets.WidgetButtonText;
import buildcraftAdditions.client.gui.widgets.WidgetButtonUpdate;
import buildcraftAdditions.networking.MessagePipeColoringTool;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiPipeColoringTool extends GuiBase {

	public static final ResourceLocation TEXTURE = new ResourceLocation("bcadditions", "textures/gui/guiPipeColoringTool.png");

	private int activeWidget;
	public boolean sortMode = false;

	public GuiPipeColoringTool(ItemStack stack) {
		super(new ContainerPipeColoringTool());
		activeWidget = stack.getItemDamage();

		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("SortMode"))
			sortMode = stack.getTagCompound().getBoolean("SortMode");
	}

	@Override
	public ResourceLocation texture() {
		return TEXTURE;
	}

	@Override
	public int getXSize() {
		return 176;
	}

	@Override
	public int getYSize() {
		return 88;
	}

	@Override
	public String getInventoryName() {
		return "pipeColorTool";
	}

	@Override
	public void initialize() {
		for (int i = 0; i < 16; i++)
			addWidget(new PaintWidget(i, guiLeft + 7 + i * 20 - (i > 7 ? 160 : 0), guiTop + (i > 7 ? 37 :17), 176, 0, 20, 20, this));
		((PaintWidget) widgets.get(activeWidget)).setActive(true);

		addWidget(new WidgetButtonText(16, guiLeft() + 88, guiTop + 63, 80, 15, this) {
			@Override
			public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {
				if (!((GuiPipeColoringTool) gui).sortMode) {
					tooltips.add(Utils.localize("tooltip.colorPipeMode"));
					tooltips.add(Utils.localize("tooltip.colorPipeMode.info"));
				} else {
					tooltips.add(Utils.localize("tooltip.colorSortingMode"));
					tooltips.add(Utils.localize("tooltip.colorSortingMode.info"));
				}
			}
		});

		updateTextButton();
	}

	@Override
	public void drawForegroundExtra(int x, int y) {
		drawString(Utils.localize("gui.coloringMode") + ":", titleXoffset, 67);
	}

	@Override
	public void widgetActionPerformed(WidgetBase widget) {
		if (widget.id >= 0 && widget.id < 16) {
			((PaintWidget) widgets.get(activeWidget)).setActive(false);
			activeWidget = widget.id;
		} else if (widget.id == 16) {
			sortMode = !sortMode;
			updateTextButton();
		}

		PacketHandler.instance.sendToServer(new MessagePipeColoringTool((byte) widget.id, sortMode));
	}

	public void updateTextButton() {
		if (sortMode)
			((WidgetButtonText) widgets.get(16)).setText(Utils.localize("gui.colorSortingMode"));
		else
			((WidgetButtonText) widgets.get(16)).setText(Utils.localize("gui.colorPipeMode"));
	}

	public static class PaintWidget extends WidgetButtonUpdate {

		public PaintWidget(int id, int x, int y, int u, int v, int width, int height, GuiBase gui) {
			super(id, x, y, u, v, width, height, gui, "bcadditions:textures/gui/guiPipeColoringTool.png");
		}

		@Override
		public void render(int mouseX, int mouseY) {
			super.render(mouseX, mouseY);
			gui.bindTexture(RenderUtils.MC_ITEM_SHEET);
			gui.drawTexturedModelRectFromIcon(x + 2, y + 2, ItemsAndBlocks.pipeColoringTool.getIconFromDamage(id), 16, 16);
		}
	}
}
