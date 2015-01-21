package buildcraftAdditions.client.gui.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.client.gui.containers.ContainerMachineConfigurator;
import buildcraftAdditions.client.gui.widgets.WidgetBase;
import buildcraftAdditions.client.gui.widgets.WidgetButton;
import buildcraftAdditions.networking.MessageConfiguration;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.utils.IConfigurableOutput;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiMachineConfigurator extends GuiBase {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions:textures/gui/guiMachineConfigurator.png");
	private final IConfigurableOutput configurableOutput;
	private WidgetButton north, east, south, west, up, down;

	public GuiMachineConfigurator(InventoryPlayer inventoryPlayer, IConfigurableOutput configurableOutput) {
		super(new ContainerMachineConfigurator(inventoryPlayer, configurableOutput));
		this.configurableOutput = configurableOutput;
		setCenterTitle(true);
		setDrawPlayerInv(false);
	}

	@Override
	public ResourceLocation texture() {
		return texture;
	}

	@Override
	public void drawBackgroundPreWidgets(float f, int x, int y) {
		super.drawBackgroundPreWidgets(f, x, y);
	}

	@Override
	public void drawBackgroundPostWidgets(float f, int x, int y) {
		super.drawBackgroundPostWidgets(f, x, y);
	}

	@Override
	public int getXSize() {
		return 224;
	}

	@Override
	public int getYSize() {
		return 165;
	}

	@Override
	public String getInventoryName() {
		return "machineConfigurator";
	}

	@Override
	public void initialize() {
		north = new WidgetButton(2, guiLeft + 45, guiTop + 26, 60, 15, this);
		east = new WidgetButton(5, guiLeft + 45, guiTop + 46, 60, 15, this);
		south = new WidgetButton(3, guiLeft + 45, guiTop + 66, 60, 15, this);
		west = new WidgetButton(4, guiLeft + 45, guiTop + 86, 60, 15, this);
		up = new WidgetButton(1, guiLeft + 45, guiTop + 106, 60, 15, this);
		down = new WidgetButton(0, guiLeft + 45, guiTop + 126, 60, 15, this);
		addWidget(down);
		addWidget(up);
		addWidget(north);
		addWidget(south);
		addWidget(west);
		addWidget(east);
		updateButtons();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		super.drawGuiContainerBackgroundLayer(f, x, y);
		drawString(Utils.localize("gui.north") + ":", guiLeft + 10, guiTop + 30);
		drawString(Utils.localize("gui.east") + ": ", guiLeft + 10, guiTop + 50);
		drawString(Utils.localize("gui.south") + ": ", guiLeft + 10, guiTop + 70);
		drawString(Utils.localize("gui.west") + ": ", guiLeft + 10, guiTop + 90);
		drawString(Utils.localize("gui.up") + ": ", guiLeft + 10, guiTop + 110);
		drawString(Utils.localize("gui.down") + ": ", guiLeft + 10, guiTop + 130);
	}

	@Override
	public void widgetActionPerformed(WidgetBase widget) {
		configurableOutput.changeStatus(ForgeDirection.getOrientation(widget.id));
		PacketHandler.instance.sendToServer(new MessageConfiguration(configurableOutput));
		updateButtons();
	}

	private void updateButtons() {
		for (int t = 0; t < 6; t++) {
			((WidgetButton) widgets.get(t)).setText(configurableOutput.getStatus(ForgeDirection.getOrientation(t)).getText());
			((WidgetButton) widgets.get(t)).setColor(configurableOutput.getStatus(ForgeDirection.getOrientation(t)).getColor());
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		PacketHandler.instance.sendToServer(new MessageConfiguration(configurableOutput));
	}
}
