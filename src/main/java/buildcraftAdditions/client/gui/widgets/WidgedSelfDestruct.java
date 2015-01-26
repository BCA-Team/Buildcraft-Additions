package buildcraftAdditions.client.gui.widgets;

import java.util.List;

import net.minecraft.util.ResourceLocation;

import buildcraftAdditions.client.gui.gui.GuiBase;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.SpecialListMananger;
import buildcraftAdditions.utils.Utils;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class WidgedSelfDestruct extends WidgetBase {
	private static final ResourceLocation RED_BUTTON = new ResourceLocation("bcadditions:textures/gui/Pieces/RedButton.png");
	private static final ResourceLocation YELLOW_BUTTON = new ResourceLocation("bcadditions:textures/gui/Pieces/YellowButton.png");
	private static final ResourceLocation GREEN_BUTTON = new ResourceLocation("bcadditions:textures/gui/Pieces/GreenButton.png");
	private boolean primed, isOwner;
	private String playerName;
	private TileKineticEnergyBufferBase keb;
	private int teller;

	public WidgedSelfDestruct(int id, int x, int y, int width, int height, GuiBase gui, String playerName, boolean isOwner, TileKineticEnergyBufferBase keb) {
		super(id, x, y, width, height, gui);
		this.playerName = playerName;
		primed = false;
		this.isOwner = isOwner;
		this.keb = keb;
		teller = 80;
	}

	@Override
	public void render(int mouseX, int mouseY) {
		ResourceLocation button = RED_BUTTON;
		if (primed) {
			if (teller < 40) {
				if (SpecialListMananger.greenButtonList.contains(playerName)) {
					button = GREEN_BUTTON;
				} else {
					button = YELLOW_BUTTON;
				}
			}
			if (teller <= 0) {
				teller = 80;
			}
			teller--;
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
			if (SpecialListMananger.specialTexts.containsKey(playerName)) {
				list.add(SpecialListMananger.specialTexts.get(playerName) + "!!!");
			} else {
				list.add(Utils.localize("gui.keb.pressForBoom"));
			}
		} else {
			if (SpecialListMananger.specialTexts.containsKey(playerName)) {
				list.add(SpecialListMananger.specialTexts.get(playerName) + "?");
			} else {
				list.add(Utils.localize("gui.keb.dangerousButton"));
				list.add(Utils.localize("gui.keb.noPushing"));
			}
		}
	}
}
