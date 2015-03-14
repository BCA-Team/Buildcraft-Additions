package buildcraftAdditions.client.gui.widgets;

import java.util.List;

import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.client.gui.GuiBase;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.utils.PlayerUtils;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.core.SpecialListMananger;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class WidgetSelfDestruct extends WidgetBase {
	private static final ResourceLocation RED_BUTTON = new ResourceLocation("bcadditions:textures/gui/Pieces/RedButton.png");
	private static final ResourceLocation YELLOW_BUTTON = new ResourceLocation("bcadditions:textures/gui/Pieces/YellowButton.png");
	private static final ResourceLocation GREEN_BUTTON = new ResourceLocation("bcadditions:textures/gui/Pieces/GreenButton.png");
	private final boolean isOwner;
	private final String uuidString;
	private final TileKineticEnergyBufferBase keb;
	private boolean primed;
	private int counter;

	public WidgetSelfDestruct(int id, int x, int y, int width, int height, GuiBase gui, TileKineticEnergyBufferBase keb) {
		super(id, x, y, width, height, gui);
		primed = false;
		isOwner = PlayerUtils.playerMatches(keb, BuildcraftAdditions.proxy.getClientPlayer());
		uuidString = PlayerUtils.getPlayerUUIDString(BuildcraftAdditions.proxy.getClientPlayer());
		this.keb = keb;
		counter = 80;
	}

	@Override
	public void render(int mouseX, int mouseY) {
		ResourceLocation button = RED_BUTTON;
		if (primed) {
			if (counter < 40) {
				if (SpecialListMananger.greenButtonList.contains(uuidString)) {
					button = GREEN_BUTTON;
				} else {
					button = YELLOW_BUTTON;
				}
			}
			if (counter <= 0) {
				counter = 80;
			}
			counter--;
		}
		RenderUtils.drawImage(button, x, y, width, height);
	}

	@Override
	public void onWidgetClicked(int x, int y, int button) {
		if (!isOwner)
			return;
		if (!primed)
			primed = true;
		else
			keb.activateSelfDestruct();
	}

	@Override
	public void addTooltip(int mouseX, int mouseY, List<String> list, boolean shift) {
		if (primed) {
			if (SpecialListMananger.specialTexts.containsKey(uuidString)) {
				list.add(SpecialListMananger.specialTexts.get(uuidString) + "!!!");
			} else {
				list.add(Utils.localize("gui.keb.pressForBoom"));
			}
		} else {
			if (SpecialListMananger.specialTexts.containsKey(uuidString)) {
				list.add(SpecialListMananger.specialTexts.get(uuidString) + "?");
			} else {
				list.add(Utils.localize("gui.keb.dangerousButton"));
				list.add(Utils.localize("gui.keb.noPushing"));
			}
		}
	}
}
