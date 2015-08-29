package buildcraftAdditions.listeners;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class KeyListener {

	//private static final KeyBinding toggleKey = new KeyBinding("keybinding.toggleBoots", Keyboard.KEY_F, "Buildcraft Additions");

	public KeyListener() {
		//ClientRegistry.registerKeyBinding(toggleKey);
	}

	private static boolean isKeyBinding(int key) {
		return key > 0;
	}

	private static int shiftMouseButtonID(int button) {
		return button + 100;
	}

	private void onInput() {
		int jumpKey = Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode();
		int forwardKey = Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode();
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.inGameHasFocus) {
			boolean oldStatus = FlightTracker.wantsToFly(mc.thePlayer);
			boolean newStatus = isKeyBinding(jumpKey) ? Keyboard.isKeyDown(jumpKey) : Mouse.isButtonDown(shiftMouseButtonID(jumpKey));
			if (oldStatus != newStatus)
				FlightTracker.setJumping(mc.thePlayer, newStatus);
			oldStatus = FlightTracker.wantsToMove(mc.thePlayer);
			newStatus = isKeyBinding(forwardKey) ? Keyboard.isKeyDown(forwardKey) : Mouse.isButtonDown(shiftMouseButtonID(forwardKey));
			if (oldStatus != newStatus)
				FlightTracker.setMoving(mc.thePlayer, newStatus);
			/*if (toggleKey.getIsKeyPressed()) {
				if (mc.thePlayer.getCurrentArmor(0) != null && mc.thePlayer.getCurrentArmor(0).getItem() == ArmorLoader.hoverBoots) {
					PacketHandler.instance.sendToServer(new MessageToggleBoots());
				}
			}*/
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		onInput();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onMouseInput(InputEvent.MouseInputEvent event) {
		onInput();
	}

}
