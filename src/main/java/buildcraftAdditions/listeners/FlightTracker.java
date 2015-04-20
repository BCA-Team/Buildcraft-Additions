package buildcraftAdditions.listeners;

import java.util.HashMap;

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
	private static final HashMap<String, Boolean>
			jumpers = new HashMap<String, Boolean>(),
			movers = new HashMap<String, Boolean>();


	public static boolean wantsToFly(String player) {
		if (!jumpers.containsKey(player))
			jumpers.put(player, false);
		return jumpers.get(player);
	}

	public static boolean wantsToMove(String player) {
		if (!movers.containsKey(player))
			movers.put(player, false);
		return movers.get(player);
	}

	public static void setJumping(EntityPlayer player, boolean newStatus) {
		jumpers.put(player.getDisplayName(), newStatus);
		sync(player);
	}

	public static void setMoving(EntityPlayer player, boolean moving) {
		movers.put(player.getDisplayName(), moving);
		sync(player);
	}

	private static void sync(EntityPlayer player) {
		if (player.worldObj.isRemote)
			PacketHandler.instance.sendToServer(new MessageFlightSync(wantsToFly(player.getDisplayName()), wantsToMove(player.getDisplayName())));
	}
}
