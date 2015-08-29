package buildcraftAdditions.listeners;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

import buildcraftAdditions.networking.MessageFlightSync;
import buildcraftAdditions.networking.PacketHandler;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class FlightTracker {
	private static final HashMap<UUID, Boolean>
			jumpers = new HashMap<UUID, Boolean>(),
			movers = new HashMap<UUID, Boolean>();


	public static boolean wantsToFly(EntityPlayer player) {
		if (player == null || player.getGameProfile() == null || player.getGameProfile().getId() == null)
			return false;
		if (!jumpers.containsKey(player.getGameProfile().getId()))
			jumpers.put(player.getGameProfile().getId(), false);
		return jumpers.get(player.getGameProfile().getId());
	}

	public static boolean wantsToMove(EntityPlayer player) {
		if (player == null || player.getGameProfile() == null || player.getGameProfile().getId() == null)
			return false;
		if (!movers.containsKey(player.getGameProfile().getId()))
			movers.put(player.getGameProfile().getId(), false);
		return movers.get(player.getGameProfile().getId());
	}

	public static void setJumping(EntityPlayer player, boolean newStatus) {
		if (player == null || player.getGameProfile() == null || player.getGameProfile().getId() == null)
			return;
		jumpers.put(player.getGameProfile().getId(), newStatus);
		sync(player);
	}

	public static void setMoving(EntityPlayer player, boolean moving) {
		if (player == null || player.getGameProfile() == null || player.getGameProfile().getId() == null)
			return;
		movers.put(player.getGameProfile().getId(), moving);
		sync(player);
	}

	private static void sync(EntityPlayer player) {
		if (player == null || player.worldObj == null)
			return;
		if (player.worldObj.isRemote)
			PacketHandler.instance.sendToServer(new MessageFlightSync(wantsToFly(player), wantsToMove(player)));
	}
}
