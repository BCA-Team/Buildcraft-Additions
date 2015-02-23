package buildcraftAdditions.client.gui.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.ForgeDirection;

import buildcraftAdditions.api.configurableOutput.IConfigurableOutput;
import buildcraftAdditions.client.gui.containers.ContainerMachineConfigurator;
import buildcraftAdditions.client.gui.widgets.WidgetBase;
import buildcraftAdditions.client.gui.widgets.WidgetButtonText;
import buildcraftAdditions.client.gui.widgets.WidgetImage;
import buildcraftAdditions.networking.MessageConfiguration;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.reference.enums.EnumMachineUpgrades;
import buildcraftAdditions.tileEntities.interfaces.IUpgradableMachine;
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
	private WidgetButtonText northConfiguration, eastConfiguration, southConfiguration, westConfiguration, upConfiguration, downConfiguration;
	private WidgetButtonText northPriority, eastPriority, southPriority, westPriority, upPriority, downPriority, toggleMode;
	private WidgetImage upgrades[];
	private final IConfigurableOutput configurableOutput;
	private final IUpgradableMachine upgradableMachine;
	private boolean configurable, upgradable, configurationMode;
	private boolean init = true;

	public GuiMachineConfigurator(InventoryPlayer inventoryPlayer, TileEntity entity) {
		super(new ContainerMachineConfigurator(inventoryPlayer, entity));
		if (entity instanceof IConfigurableOutput) {
			configurableOutput = (IConfigurableOutput) entity;
			configurable = true;
		} else {
			configurableOutput = null;
			configurable = false;
		}
		if (entity instanceof IUpgradableMachine) {
			upgradableMachine = (IUpgradableMachine) entity;
			upgradable = true;
		} else {
			upgradableMachine = null;
			upgradable = false;
		}
		configurationMode = configurable;
		setCenterTitle(true);
	}

	@Override
	public ResourceLocation texture() {
		return texture;
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
		toggleMode = new WidgetButtonText(30, guiLeft + 40, guiTop + 145, 140, 15, this).setText(configurationMode ? Utils.localize("gui.configureIO") : Utils.localize("gui.showUpgrades"));
		if (configurationMode) {
			northConfiguration = new WidgetButtonText(2, guiLeft + 45, guiTop + 26, 60, 15, this);
			eastConfiguration = new WidgetButtonText(5, guiLeft + 45, guiTop + 46, 60, 15, this);
			southConfiguration = new WidgetButtonText(3, guiLeft + 45, guiTop + 66, 60, 15, this);
			westConfiguration = new WidgetButtonText(4, guiLeft + 45, guiTop + 86, 60, 15, this);
			upConfiguration = new WidgetButtonText(1, guiLeft + 45, guiTop + 106, 60, 15, this);
			downConfiguration = new WidgetButtonText(0, guiLeft + 45, guiTop + 126, 60, 15, this);

			northPriority = new WidgetButtonText(8, guiLeft + 158, guiTop + 26, 62, 15, this);
			eastPriority = new WidgetButtonText(11, guiLeft + 158, guiTop + 46, 62, 15, this);
			southPriority = new WidgetButtonText(9, guiLeft + 158, guiTop + 66, 62, 15, this);
			westPriority = new WidgetButtonText(10, guiLeft + 158, guiTop + 86, 62, 15, this);
			upPriority = new WidgetButtonText(7, guiLeft + 158, guiTop + 106, 62, 15, this);
			downPriority = new WidgetButtonText(6, guiLeft + 158, guiTop + 126, 62, 15, this);
			addWidget(downConfiguration);
			addWidget(upConfiguration);
			addWidget(northConfiguration);
			addWidget(southConfiguration);
			addWidget(westConfiguration);
			addWidget(eastConfiguration);

			addWidget(downPriority);
			addWidget(upPriority);
			addWidget(northPriority);
			addWidget(southPriority);
			addWidget(westPriority);
			addWidget(eastPriority);
		} else {
			int t = 1;
			for (EnumMachineUpgrades upgrade : upgradableMachine.getInstalledUpgrades()) {
				addWidget(new WidgetImage(14 + t, guiLeft + (25 * t), guiTop + 50, 20, 20, this, upgrade.getTexture(), Utils.localize("item.upgrade." + upgrade.getTag() + ".name")));
				t++;
			}
		}
		if (upgradable && configurable)
			addWidget(toggleMode);
		updateButtons();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		super.drawGuiContainerBackgroundLayer(f, x, y);
		if (configurationMode) {
			drawString(Utils.localize("gui.north") + ":", guiLeft + 10, guiTop + 30);
			drawString(Utils.localize("gui.east") + ": ", guiLeft + 10, guiTop + 50);
			drawString(Utils.localize("gui.south") + ": ", guiLeft + 10, guiTop + 70);
			drawString(Utils.localize("gui.west") + ": ", guiLeft + 10, guiTop + 90);
			drawString(Utils.localize("gui.up") + ": ", guiLeft + 10, guiTop + 110);
			drawString(Utils.localize("gui.down") + ": ", guiLeft + 10, guiTop + 130);

			if (configurableOutput.getStatus(ForgeDirection.NORTH).hasPriority())
				drawString(Utils.localize("gui.priority") + ": ", guiLeft + 110, guiTop + 30);
			if (configurableOutput.getStatus(ForgeDirection.EAST).hasPriority())
				drawString(Utils.localize("gui.priority") + ": ", guiLeft + 110, guiTop + 50);
			if (configurableOutput.getStatus(ForgeDirection.SOUTH).hasPriority())
				drawString(Utils.localize("gui.priority") + ": ", guiLeft + 110, guiTop + 70);
			if (configurableOutput.getStatus(ForgeDirection.WEST).hasPriority())
				drawString(Utils.localize("gui.priority") + ": ", guiLeft + 110, guiTop + 90);
			if (configurableOutput.getStatus(ForgeDirection.UP).hasPriority())
				drawString(Utils.localize("gui.priority") + ": ", guiLeft + 110, guiTop + 110);
			if (configurableOutput.getStatus(ForgeDirection.DOWN).hasPriority())
				drawString(Utils.localize("gui.priority") + ": ", guiLeft + 110, guiTop + 130);
		} else {
			drawString(Utils.localize("gui.installedUpgrades"), guiLeft + 20, guiTop + 25);
		}
	}

	@Override
	public void widgetActionPerformed(WidgetBase widget) {
		if (widget.id < 6)
			configurableOutput.changeStatus(ForgeDirection.getOrientation(widget.id));
		else if (widget.id < 14)
			configurableOutput.changePriority(ForgeDirection.getOrientation(widget.id - 6));
		else if (widget.id == 30)
			configurationMode = !configurationMode;
		if (configurableOutput != null)
			PacketHandler.instance.sendToServer(new MessageConfiguration(configurableOutput));
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		redraw();
		updateButtons();
	}

	private void updateButtons() {
		if (!configurationMode)
			return;
		WidgetButtonText button;
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			button = ((WidgetButtonText) widgets.get(direction.ordinal()));
			button.setText(configurableOutput.getStatus(direction).getText());
			button.setColor(configurableOutput.getStatus(direction).getColor());

			button = ((WidgetButtonText) widgets.get(direction.ordinal() + 6));
			button.setText(configurableOutput.getPriority(direction).getName());
			button.setColor(configurableOutput.getPriority(direction).getColor());
			button.setShouldRender(configurableOutput.getStatus(direction).hasPriority());
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if (configurable)
			PacketHandler.instance.sendToServer(new MessageConfiguration(configurableOutput));
	}
}
