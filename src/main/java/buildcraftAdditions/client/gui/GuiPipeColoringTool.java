package buildcraftAdditions.client.gui;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.widgets.WidgetBase;
import buildcraftAdditions.client.gui.widgets.WidgetButtonText;
import buildcraftAdditions.client.gui.widgets.WidgetButtonUpdate;
import buildcraftAdditions.inventories.containers.ContainerPipeColoringTool;
import buildcraftAdditions.networking.MessagePipeColoringTool;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.reference.ItemLoader;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiPipeColoringTool extends GuiBase {

	public static final ResourceLocation TEXTURE = new ResourceLocation("bcadditions", "textures/gui/guiPipeColoringTool.png");
	public boolean sortMode = false;
	private int activeWidget;

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
		return 198;
	}

	@Override
	public int getYSize() {
		return 99;
	}

	@Override
	public String getInventoryName() {
		return "pipeColorTool";
	}

	@Override
	public void initialize() {
		for (int i = 0; i < 16; i++)
			addWidget(new PaintWidget(i, guiLeft + 18 + i * 20 - (i > 7 ? 160 : 0), guiTop + (i > 7 ? 37 : 17), 176, 0, 20, 20, this));
		((PaintWidget) widgets.get(activeWidget)).setActive(true);

		addWidget(new WidgetButtonText(16, guiLeft + 90, guiTop + 63, 80, 15, this) {
			@Override
			public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {
				if (!((GuiPipeColoringTool) gui).sortMode) {
					tooltips.add(Utils.localize("tooltip.colorNormalMode") + ":");
					tooltips.add(Utils.localize("tooltip.colorNormalMode.info"));
				} else {
					tooltips.add(Utils.localize("tooltip.colorSortingMode") + ":");
					tooltips.add(Utils.localize("tooltip.colorSortingMode.info"));
				}
			}
		});

		updateTextButton();
	}

	@Override
	public void drawForegroundExtra(int x, int y) {
		drawString(Utils.localize("gui.coloringMode") + ":", titleXoffset + 9, 67);
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
			((WidgetButtonText) widgets.get(16)).setText(Utils.localize("gui.colorNormalMode"));
	}

	public static class PaintWidget extends WidgetButtonUpdate {
		private static final ResourceLocation ACTIVE = new ResourceLocation("bcadditions:textures/gui/Pieces/ButtonActive.png");
		private static final ResourceLocation INACTIVE = new ResourceLocation("bcadditions:textures/gui/Pieces/ButtonInactive.png");

		public PaintWidget(int id, int x, int y, int u, int v, int width, int height, GuiBase gui) {
			super(id, x, y, u, v, width, height, gui);
		}

		@Override
		public void render(int mouseX, int mouseY) {
			RenderUtils.drawImage(active ? ACTIVE : INACTIVE, x, y, width, height);
			gui.bindTexture(RenderUtils.MC_ITEM_SHEET);
			gui.drawTexturedModelRectFromIcon(x + 2, y + 2, ItemLoader.pipeColoringTool.getIconFromDamage(id), 16, 16);
		}

		@Override
		public void addTooltip(int mouseX, int mouseY, List<String> tooltips, boolean shift) {
			tooltips.add(Utils.localize("gui.color." + Utils.COLOR_NAMES[id]));
		}
	}
}
