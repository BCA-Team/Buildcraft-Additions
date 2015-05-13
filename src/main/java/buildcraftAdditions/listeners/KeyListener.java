package buildcraftAdditions.listeners;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

import buildcraftAdditions.networking.MessageToggleBoots;
import buildcraftAdditions.networking.PacketHandler;
import buildcraftAdditions.reference.ItemsAndBlocks;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class KeyListener {
	private static final int jumpkey = Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode();
	private static final int forwardKey = Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode();
	private static final KeyBinding toggleKey = new KeyBinding("keybinding.toggleBoots", Keyboard.KEY_F, "Buildcraft Additions");

	public KeyListener() {
		ClientRegistry.registerKeyBinding(toggleKey);
	}


	@SubscribeEvent
	public void InputEvent(InputEvent.KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.inGameHasFocus) {
			boolean oldStatus = FlightTracker.wantsToFly(mc.thePlayer.getDisplayName());
			boolean newStatus = Keyboard.isKeyDown(jumpkey);
			if (oldStatus != newStatus)
				FlightTracker.setJumping(mc.thePlayer, newStatus);
			oldStatus = FlightTracker.wantsToMove(mc.thePlayer.getDisplayName());
			newStatus = Keyboard.isKeyDown(forwardKey);
			if (oldStatus != newStatus)
				FlightTracker.setMoving(mc.thePlayer, newStatus);
			if (toggleKey.getIsKeyPressed()) {
				if (mc.thePlayer.getCurrentArmor(0) != null && mc.thePlayer.getCurrentArmor(0).getItem() == ItemsAndBlocks.hoverBoots) {
					PacketHandler.instance.sendToServer(new MessageToggleBoots());
				}
			}
		}
	}
}
