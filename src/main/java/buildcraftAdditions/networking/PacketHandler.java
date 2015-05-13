package buildcraftAdditions.networking;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

import buildcraftAdditions.api.networking.MessageByteBuff;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class PacketHandler {

	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel("bcadditions");

	public static void init() {
		instance.registerMessage(MessageByteBuff.class, MessageByteBuff.class, 0, Side.CLIENT);
		instance.registerMessage(MessageConfiguration.class, MessageConfiguration.class, 1, Side.SERVER);
		instance.registerMessage(MessageSelfDestruct.class, MessageSelfDestruct.class, 2, Side.SERVER);
		instance.registerMessage(MessageWidgetUpdate.class, MessageWidgetUpdate.class, 3, Side.SERVER);
		instance.registerMessage(MessagePipeColoringTool.class, MessagePipeColoringTool.class, 4, Side.SERVER);
		instance.registerMessage(MessageFlightSync.class, MessageFlightSync.class, 5, Side.SERVER);
		instance.registerMessage(MessageToggleBoots.class, MessageToggleBoots.class, 6, Side.SERVER);
	}
}
